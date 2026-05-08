package edu.taskchecker.vladimir.domain;

public class TestStats {
    private final int passedTests;
    private final int failedTests;
    private final int ignoredTests;

    public TestStats(int passedTests, int failedTests, int ignoredTests) {
        if (passedTests < 0 || failedTests < 0 || ignoredTests < 0) {
            throw new IllegalArgumentException("arguments must be non-negative");
        }

        this.passedTests = passedTests;
        this.failedTests = failedTests;
        this.ignoredTests = ignoredTests;
    }

    public int getPassedTests() {
        return this.passedTests;
    }

    public int getFailedTests() {
        return this.failedTests;
    }

    public int getIgnoredTests() {
        return this.ignoredTests;
    }
}
