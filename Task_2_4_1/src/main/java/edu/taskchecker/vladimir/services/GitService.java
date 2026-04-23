package edu.taskchecker.vladimir.services;

import edu.taskchecker.vladimir.domain.Student;
import edu.taskchecker.vladimir.exceptions.GitCommandFailedException;
import edu.taskchecker.vladimir.exceptions.NonExistentDirException;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.concurrent.*;

public class GitService implements Closeable {
    private final Path repoStorage;
    private final ExecutorService gitOutReader = Executors.newSingleThreadExecutor();

    public GitService(Path repoStorage) {
        if (repoStorage == null) {
            throw new IllegalArgumentException("repoStorage must be non-null");
        }

        if (!repoStorage.toFile().exists()) {
            repoStorage.toFile().mkdirs();
        }

        this.repoStorage = repoStorage;
    }

    public Path syncRepository(
            Student student
    ) throws NonExistentDirException, GitCommandFailedException,
            IOException, InterruptedException {
        if (student == null) {
            throw new IllegalArgumentException("student must be non-null");
        }

        Path studentRepoPath = repoStorage.resolve(student.getNickname());

        if (studentRepoPath.toFile().exists()) {
            run(studentRepoPath, "git", "pull");
        } else {
            run(repoStorage, "git", "clone", student.getRepoURL(), student.getNickname());
        }

        return studentRepoPath;
    }

    public LocalDate lastCommitDate(
            Path repoPath,
            String taskId
    ) throws NonExistentDirException, GitCommandFailedException,
            IOException, InterruptedException {
        if (repoPath == null || taskId == null) {
            throw new IllegalArgumentException("arguments must be non-null");
        }

        if (taskId.isBlank()) {
            throw new IllegalArgumentException("taskId must be non-empty");
        }

        String cmdOut = run(repoPath, "git", "log", "-1", "--format=%as", "--", taskId);

        if (cmdOut.isBlank()) {
            return null;
        }

        return LocalDate.parse(cmdOut.trim().substring(0, 10));
    }

    @Override
    public void close() {
        gitOutReader.shutdown();
    }

    private String run(Path dir, String... args) throws NonExistentDirException, GitCommandFailedException,
            IOException, InterruptedException {
        if (dir == null) {
            throw new IllegalArgumentException("dir must be non-null");
        }

        for (String cmd : args) {
            if (cmd == null || cmd.isBlank()) {
                throw new IllegalArgumentException("command must be informative");
            }
        }

        if (!Files.exists(dir) || !Files.isDirectory(dir)) {
            throw new NonExistentDirException("dir doesn't exist");
        }

        ProcessBuilder pb = new ProcessBuilder(args).directory(dir.toFile()).redirectErrorStream(true);

        Process gitExecutor = pb.start();

        Future<String> outputFuture = gitOutReader
                .submit(() -> new String(gitExecutor.getInputStream().readAllBytes()));

        boolean finished = gitExecutor.waitFor(30, TimeUnit.SECONDS);

        if (!finished) {
            gitExecutor.destroyForcibly();
            outputFuture.cancel(true);
            throw new GitCommandFailedException(new StringBuilder()
                    .append("git command timed out: ")
                    .append(String.join(" ", args)).toString());
        }

        int exitCode = gitExecutor.exitValue();

        try {
            String output = outputFuture.get(5, TimeUnit.SECONDS);
            if (exitCode != 0) {
                throw new GitCommandFailedException(
                        "git command failed: " + String.join(" ", args) +
                                "\nOutput: " + output);
            }
            return output;
        } catch (ExecutionException | TimeoutException e) {
            throw new IOException("Failed to read process output", e);
        }
    }
}
