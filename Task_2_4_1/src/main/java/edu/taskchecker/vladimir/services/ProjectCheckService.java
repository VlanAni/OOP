package edu.taskchecker.vladimir.services;

import edu.taskchecker.vladimir.domain.TestStats;
import edu.taskchecker.vladimir.exceptions.BuildCommandFailedException;
import edu.taskchecker.vladimir.exceptions.NonExistentDirException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProjectCheckService {
    private final long operationTimeout;

    public ProjectCheckService(long operationTimeout) {
        if (operationTimeout < 0) {
            throw new IllegalArgumentException("timeout must be non-negative");
        }

        this.operationTimeout = operationTimeout;
    }

    public boolean compile(Path taskDir)
            throws NonExistentDirException, BuildCommandFailedException,
            IOException, InterruptedException{
        if (taskDir == null) {
            throw new IllegalArgumentException("taskDir must be non-null");
        }

        File gradlew = new File(taskDir.resolve("/gradlew").toUri());

        if (gradlew.exists()) {
            throw new IOException("builder doesn't exists");
        }

        return run(taskDir, "sh", "./gradlew", "compileJava");
    }

    public boolean checkStyle(Path taskDir)
            throws NonExistentDirException, BuildCommandFailedException,
            IOException, InterruptedException{
        if (taskDir == null) {
            throw new IllegalArgumentException("taskDir must be non-null");
        }

        return run(taskDir, "sh", "./gradlew", "checkstyleMain");
    }

    public boolean generateJavadoc(Path taskDir)
            throws NonExistentDirException, BuildCommandFailedException,
            IOException, InterruptedException {
        if (taskDir == null) {
            throw new IllegalArgumentException("taskDir must be non-null");
        }

        return run(taskDir, "sh","./gradlew", "javadoc");
    }

    public TestStats testing(Path taskDir)
            throws NonExistentDirException, BuildCommandFailedException,
            IOException, InterruptedException{
        if (taskDir == null) {
            throw new IllegalArgumentException("taskDir must be non-null");
        }

        run(taskDir, "sh", "./gradlew", "test");
        return parseTestResult(taskDir);
    }

    private boolean run(Path dir, String... args)
            throws NonExistentDirException, BuildCommandFailedException, IOException, InterruptedException {
        if (dir == null) {
            throw new IllegalArgumentException("dir must be non-null");
        }

        for (String cmd : args) {
            if (cmd == null || cmd.isBlank()) {
                throw new IllegalArgumentException("command must be informative");
            }
        }

        if (!dir.toFile().exists()) {
            throw new NonExistentDirException("dir for the process doesn't exist");
        }

        ProcessBuilder pb = new ProcessBuilder(args)
                .directory(dir.toFile())
                .redirectError(ProcessBuilder.Redirect.DISCARD)
                .redirectOutput(ProcessBuilder.Redirect.DISCARD);

        Process executor = pb.start();

        boolean finished = executor.waitFor(this.operationTimeout, TimeUnit.SECONDS);

        if (!finished) {
            executor.destroyForcibly();
            throw new BuildCommandFailedException(new StringBuilder()
                    .append("project command timed out: ")
                    .append(String.join(" ", args)).toString());
        }

        return executor.exitValue() == 0;
    }

    private TestStats parseTestResult(Path taskDir) throws IOException {
        if (taskDir == null) {
            throw new IllegalArgumentException("taskDir must be non-null");
        }

        Path testingResultDir = taskDir.resolve("build/test-results/test");

        if (!Files.exists(testingResultDir) || !Files.isDirectory(testingResultDir)) {
            return new TestStats(0, 0, 0);
        }

        List<Path> testRepFiles;
        try (Stream<Path> paths = Files.list(testingResultDir)) {
            testRepFiles = paths
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".xml"))
                    .collect(Collectors.toList());
        }

        if (testRepFiles.isEmpty()) {
            return new TestStats(0, 0, 0);
        }

        SAXParserFactory factory = SAXParserFactory.newInstance();

        SAXParser parser;
        try {
            parser = factory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            throw new IOException(e);
        }

        TestParseHandler handler = new TestParseHandler();

        for (Path xmlFile : testRepFiles) {
            try {
                parser.parse(xmlFile.toFile(), handler);
            } catch (SAXException ignored) {
            }
        }

        return handler.getTestStats();
    }


    private static class TestParseHandler extends DefaultHandler {
        private int passed = 0;
        private int failed = 0;
        private int skipped = 0;

        @Override
        public void startElement(
                String uri,
                String localName,
                String qName,
                Attributes attributes
        ) {
            if (!qName.equals("testsuite")) {
                return;
            }

            int tests    = safetyParseInt(attributes, "tests");
            int failures = safetyParseInt(attributes, "failures");
            int errors   = safetyParseInt(attributes, "errors");
            int skipped  = safetyParseInt(attributes, "skipped");

            int failed  = failures + errors;
            int passed  = tests - failed - skipped;

            this.passed  += Math.max(0, passed);
            this.failed  += failed;
            this.skipped += skipped;
        }

        private TestStats getTestStats() {
            return new TestStats(passed, failed, skipped);
        }

        private int safetyParseInt(Attributes attributes, String name) {
            String value = attributes.getValue(name);
            if (value == null || value.isBlank()) {
                return 0;
            }
            try {
                return Integer.parseInt(value.trim());
            } catch (NumberFormatException e) {
                return 0;
            }
        }
    }
}
