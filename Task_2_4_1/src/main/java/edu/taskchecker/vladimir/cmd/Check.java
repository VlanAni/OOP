package edu.taskchecker.vladimir.cmd;

import edu.taskchecker.vladimir.domain.*;
import edu.taskchecker.vladimir.dsl.CourseDsl;
import edu.taskchecker.vladimir.io.Logger;
import edu.taskchecker.vladimir.services.GitService;
import edu.taskchecker.vladimir.services.ProjectCheckService;
import edu.taskchecker.vladimir.services.ReportService;
import groovy.lang.GroovyShell;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Check implements Command{

    private final String configPath;
    private final Logger logger;
    private final Path repoStorage;

    public Check(String configPath, Path repoStorage, Logger logger) {
        if (configPath == null || logger == null || repoStorage == null) {
            throw new IllegalArgumentException("must be non null");
        }

        this.configPath = configPath;
        this.logger = logger;
        this.repoStorage = repoStorage;
    }

    @Override
    public void execute(List<String> options) {
        Course course = loadCourse();
        if (course == null) {
            logger.printError("error during reading groovy-script");
            return;
        }

        try (GitService gitService = new GitService(repoStorage)) {
            ProjectCheckService projectCheckService = new ProjectCheckService(
                    course.getGradingConfig().getTestTimeoutSeconds()
            );

            List<TaskResult> results = new ArrayList<>();

            int threadAmount = Math.max(1, Runtime.getRuntime().availableProcessors() / 3);
            ExecutorService studentHandlers = Executors.newFixedThreadPool(threadAmount);


            for (CheckAssignment assignment : course.getAssignments()) {
                for (Student student : assignment.getStudents()) {

                    studentHandlers.submit(() -> {
                        logger.printInfo("processing student: " + student.getName());

                        Path repoPath;
                        try {
                            repoPath = gitService.syncRepository(student);
                            for (TaskData taskData : assignment.getTasks()) {
                                logger.printInfo("  checking task: " + taskData.getId());
                                TaskResult result = checkTask(
                                        student, taskData, repoPath, gitService, projectCheckService, course);

                                if (result != null) {
                                    synchronized (results) {
                                        results.add(result);
                                    }
                                }
                            }
                        } catch (IOException | InterruptedException e) {
                            logger.printError("git failed for " +
                                    student.getName() + ": " + e.getMessage());
                        }
                    });
                }
            }

            studentHandlers.shutdown();
            try {
                if (!studentHandlers.awaitTermination(10, TimeUnit.MINUTES)) {
                    studentHandlers.shutdownNow();
                }
            } catch (InterruptedException e) {
                studentHandlers.shutdownNow();
                Thread.currentThread().interrupt();
            }

            ReportService reportService = new ReportService();
            String htmlOutput = reportService.generateHtml(results, course);

            System.out.println(htmlOutput);
            logger.printInfo("Checking finished. Copy the report and open on browser");
        }
    }

    private Course loadCourse() {
        File configFile = new File(configPath);
        if (!configFile.exists()) {
            logger.printError("config not found in " + new File(".").getAbsolutePath());
            return null;
        }
        CompilerConfiguration cfg = new CompilerConfiguration();
        cfg.setScriptBaseClass("edu.taskchecker.vladimir.dsl.CourseDsl");
        GroovyShell shell = new GroovyShell(cfg);
        try {
            CourseDsl script = (CourseDsl) shell.parse(configFile);
            script.run();
            return script.getCourse();
        } catch (IOException | RuntimeException e) {
            return null;
        }
    }

    private TaskResult checkTask(
            Student student,
            TaskData taskData,
            Path repoPath,
            GitService gitService,
            ProjectCheckService buildService,
            Course course) {

        Path taskDir = repoPath.resolve(taskData.getId());

        LocalDate commitDate;
        try {
            commitDate = gitService.lastCommitDate(repoPath, taskData.getId());
        } catch (IOException | InterruptedException e) {
            logger.printError("git log failed: " + e.getMessage());
            return null;
        }

        if (commitDate == null) {
            logger.printInfo("no commits found for " + taskData.getId());
            return null;
        }

        boolean compiled = false;
        try {
            compiled = buildService.compile(taskDir);
        } catch (Exception e) {
            logger.printError("compile error: " + e.getMessage());
        }

        boolean style = false;
        TestStats tests = new TestStats(0, 0, 0);

        if (compiled) {
            try {
                style = buildService.checkStyle(taskDir);
                buildService.generateJavadoc(taskDir);
            } catch (Exception e) {
                logger.printError("style/javadoc error: " + e.getMessage());
            }

            try {
                tests = buildService.testing(taskDir);
            } catch (Exception e) {
                logger.printError("test error: " + e.getMessage());
            }
        }

        return new TaskResult(
                taskData, student, compiled, style,
                tests, commitDate,
                course.getGradingConfig()
        );
    }
}
