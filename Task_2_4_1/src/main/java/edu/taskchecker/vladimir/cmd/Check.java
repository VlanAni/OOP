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

public class Check implements Command{

    private final String configPath;
    private final Logger logger;

    public Check(String configPath, Logger logger) {
        if (configPath == null || logger == null) {
            throw new IllegalArgumentException("must be non null");
        }

        this.configPath = configPath;
        this.logger = logger;
    }

    @Override
    public void execute(List<String> options) {
        Course course = loadCourse();
        if (course == null) {
            logger.printError("error during reading groovy-script");
            return;
        }

        Path repoStorage = Path.of("student-repo");
        try (GitService gitService = new GitService(repoStorage)) {
            ProjectCheckService projectCheckService = new ProjectCheckService(
                    course.getGradingConfig().getTestTimeoutSeconds()
            );

            List<TaskResult> results = new ArrayList<>();

            for (CheckAssignment assignment : course.getAssignments()) {
                for (Student student : assignment.getStudents()) {
                    logger.printInfo("processing student: " + student.getName());

                    Path repoPath;
                    try {
                        repoPath = gitService.syncRepository(student);
                        for (Task task : assignment.getTasks()) {
                            logger.printInfo("  checking task: " + task.getId());
                            TaskResult result = checkTask(
                                    student, task, repoPath, gitService, projectCheckService, course);

                            if (result != null) {
                                results.add(result);
                            }
                        }
                    } catch (IOException | InterruptedException e) {
                        logger.printError("git failed for " +
                                student.getName() + ": " + e.getMessage());
                    }
                }
            }
            new ReportService().generate(results, course);
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
        } catch (IOException e) {
            return null;
        }
    }

    private TaskResult checkTask(
            Student student,
            Task task,
            Path repoPath,
            GitService gitService,
            ProjectCheckService buildService,
            Course course) {

        Path taskDir = repoPath.resolve(task.getId());

        LocalDate commitDate;
        try {
            commitDate = gitService.lastCommitDate(repoPath, task.getId());
        } catch (IOException | InterruptedException e) {
            logger.printError("git log failed: " + e.getMessage());
            return null;
        }

        if (commitDate == null) {
            logger.printInfo("no commits found for " + task.getId());
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
                task, student, compiled, style,
                tests, commitDate,
                course.getGradingConfig()
        );
    }
}
