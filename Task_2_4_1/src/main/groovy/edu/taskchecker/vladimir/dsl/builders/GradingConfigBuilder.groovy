package edu.taskchecker.vladimir.dsl.builders

import edu.taskchecker.vladimir.domain.GradingConfig

class GradingConfigBuilder {
    int excellentThreshold = 5
    int goodThreshold      = 4
    int passThreshold      = 3
    double softDeadlinePenalty = 0.5
    int testTimeoutSeconds = 30
    Map<String, Map<String, Integer>> bonusPoints = [:]

    void excellent(int v)            { excellentThreshold = v }
    void good(int v)                 { goodThreshold = v }
    void pass(int v)                 { passThreshold = v }
    void softDeadlinePenalty(double v) { softDeadlinePenalty = v }
    void testTimeout(int v)          { testTimeoutSeconds = v }

    void bonus(String name, String taskId, int score) {
        if (!bonusPoints.containsKey(name)) {
            bonusPoints[name] = [:]
        }
        bonusPoints[name][taskId] = score
    }

    GradingConfig build() {
        return new GradingConfig(
                excellentThreshold,
                goodThreshold,
                passThreshold,
                softDeadlinePenalty,
                testTimeoutSeconds,
                bonusPoints
        )
    }
}

