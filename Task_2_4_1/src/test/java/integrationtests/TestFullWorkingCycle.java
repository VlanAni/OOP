package integrationtests;

import edu.taskchecker.vladimir.domain.*;
import edu.taskchecker.vladimir.dsl.CourseDsl;
import edu.taskchecker.vladimir.io.Logger;
import edu.taskchecker.vladimir.services.*;
import groovy.lang.GroovyShell;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

@Tag("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestFullWorkingCycle {

    private static final String resourcesPath = "./src/test/resources/integration/";
    private static final Path configPath = Path.of(resourcesPath, "config.groovy");
    private static final String propPath = "integration/integrationtestconfig.properties";

    private static Properties props;
    private static Path reposStorage;

    @BeforeAll
    static void setUp() throws Exception {
        props = loadProperties();
        checkConfigured();

        reposStorage = resolveHomePath(props.getProperty("repos.storage.path"));
    }

    @Test
    @Order(1)
    void testFullCycleAndHtmlContent() throws Exception {
        Course course = loadCourse();
        assertNotNull(course);

        Logger logger = new Logger("test: ");
        try (GitService gitService = new GitService(reposStorage)) {
            ProjectCheckService buildService = new ProjectCheckService(300);
            ReportService reportService = new ReportService();

            List<TaskResult> allResults = new ArrayList<>();

            for (CheckAssignment assignment : course.getAssignments()) {
                for (Student student : assignment.getStudents()) {
                    Path repoPath = gitService.syncRepository(student);

                    for (TaskData task : assignment.getTasks()) {
                        TaskResult result = runProjectCheck(student, task, repoPath, gitService, buildService, course, logger);
                        if (result != null) {
                            allResults.add(result);
                        }
                    }
                }
            }

            String htmlReport = reportService.generateHtml(allResults, course);

            assertAll(
                    () -> assertNotNull(htmlReport),
                    () -> assertTrue(htmlReport.contains("<!DOCTYPE html>")),
                    () -> assertTrue(htmlReport.contains("Vladimir")),
                    () -> assertTrue(htmlReport.contains("Task_1_1_1")),
                    () -> assertTrue(htmlReport.contains("Sorting")),
                    () -> assertTrue(htmlReport.contains("FINAL"))
            );

            System.out.println(htmlReport);
        }
    }

    private static Course loadCourse() throws Exception {
        CompilerConfiguration cfg = new CompilerConfiguration();
        cfg.setScriptBaseClass("edu.taskchecker.vladimir.dsl.CourseDsl");

        GroovyShell shell = new GroovyShell(cfg);
        CourseDsl script = (CourseDsl) shell.parse(configPath.toFile());
        script.run();
        return script.getCourse();
    }

    private TaskResult runProjectCheck(Student student, TaskData taskData, Path repoPath,
                                       GitService gitService, ProjectCheckService buildService,
                                       Course course, Logger logger) {
        Path taskDir = repoPath.resolve(taskData.getId());
        try {
            var commitDate = gitService.lastCommitDate(repoPath, taskData.getId());
            if (commitDate == null) return null;

            boolean compiled = buildService.compile(taskDir);
            TestStats tests = new TestStats(0, 0, 0);
            boolean style = false;

            if (compiled) {
                style = buildService.checkStyle(taskDir);
                tests = buildService.testing(taskDir);
            }

            return new TaskResult(taskData, student, compiled, style, tests, commitDate, course.getGradingConfig());
        } catch (Exception e) {
            return null;
        }
    }

    private static Properties loadProperties() throws IOException {
        Properties p = new Properties();
        try (InputStream is = TestFullWorkingCycle.class.getClassLoader().getResourceAsStream(propPath)) {
            if (is == null) throw new FileNotFoundException("properties not found");
            p.load(is);
        }
        return p;
    }

    private static void checkConfigured() {
        assumeTrue(props.getProperty("repos.storage.path") != null);
    }

    private static Path resolveHomePath(String path) {
        return Path.of(path.replace("~", System.getProperty("user.home")));
    }
}