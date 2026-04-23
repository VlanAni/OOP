package edu.taskchecker.vladimir.domain;

import java.util.Map;

public class GradingConfig {
    private final int excellentThreshold;
    private final int goodThreshold;
    private final int passThreshold;
    private final double softDeadlinePenalty;
    private final int testTimeoutSeconds;
    private final Map<String, Map<String, Integer>> bonusPoints;

    public GradingConfig(
            int excellentThreshold,
            int goodThreshold,
            int passThreshold,
            double softDeadlinePenalty,
            int testTimeoutSeconds,
            Map<String, Map<String, Integer>> bonusPoints
    ) {
        if (bonusPoints == null) {
            throw new IllegalArgumentException("bonusPoints must be non-null");
        }
        if (excellentThreshold <= goodThreshold
                || goodThreshold <= passThreshold
                || passThreshold < 0) {
            throw new IllegalArgumentException(
                    "thresholds must be: excellent > good > pass >= 0");
        }
        if (softDeadlinePenalty < 0 || softDeadlinePenalty > 1) {
            throw new IllegalArgumentException(
                    "softDeadlinePenalty must be between 0 and 1");
        }
        if (testTimeoutSeconds < 1) {
            throw new IllegalArgumentException(
                    "testTimeoutSeconds must be positive");
        }

        this.excellentThreshold = excellentThreshold;
        this.goodThreshold = goodThreshold;
        this.passThreshold = passThreshold;
        this.softDeadlinePenalty = softDeadlinePenalty;
        this.testTimeoutSeconds = testTimeoutSeconds;
        this.bonusPoints = Map.copyOf(bonusPoints);
    }

    public static GradingConfig defaultConfig() {
        return new GradingConfig(
                5,
                4,
                3,
                0.5,
                30,
                Map.of());
    }

    public int getExcellentThreshold()      { return excellentThreshold; }
    public int getGoodThreshold()           { return goodThreshold; }
    public int getPassThreshold()           { return passThreshold; }
    public double getSoftDeadlinePenalty()  { return softDeadlinePenalty; }
    public int getTestTimeoutSeconds()      { return testTimeoutSeconds; }

    public Map<String, Map<String, Integer>> getBonusPoints() {
        return bonusPoints;
    }

    public int getBonusFor(String nickname, String taskId) {
        return bonusPoints
                .getOrDefault(nickname, Map.of())
                .getOrDefault(taskId, 0);
    }
}
