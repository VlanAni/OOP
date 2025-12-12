package vanisimov.creditbook.implementation;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import vanisimov.creditbook.consts.SemesterNum;
import vanisimov.creditbook.consts.Subject;

public class Curriculum {

    private final Map<SemesterNum, SemesterPlan> plan;

    public Curriculum() {
        this.plan = new EnumMap<>(SemesterNum.class);
        initializePlan();
    }

    public SemesterPlan getPlanForSemester(SemesterNum semesterId) {
        return plan.getOrDefault(semesterId,
                new SemesterPlan(Collections.emptyList(), Collections.emptyList(), Collections.emptyList()));
    }

    private void initializePlan() {
        plan.put(SemesterNum.I, new SemesterPlan (
                List.of(Subject.PE, Subject.ENGLIGH),
                List.of(Subject.IMPPROG, Subject.DECPROG, Subject.PLATFORMS, Subject.STATEHOOD),
                List.of(Subject.MATH, Subject.DLOGIC, Subject.HISTORY)
        ));

        plan.put(SemesterNum.II, new SemesterPlan (
                List.of(Subject.HISTORY, Subject.PE),
                List.of(Subject.IMPPROG, Subject.DECPROG, Subject.PLATFORMS, Subject.ENGLIGH),
                List.of(Subject.MATH, Subject.DLOGIC)
        ));

        plan.put(SemesterNum.III, new SemesterPlan(
                List.of(Subject.PE),
                List.of(Subject.OS, Subject.OOP, Subject.PROJECT, Subject.ENGLIGH),
                List.of(Subject.COMPUTATIONS, Subject.STATISTICS, Subject.AIINTR, Subject.DCV)
        ));

        plan.put(SemesterNum.IV, new SemesterPlan(
                List.of(Subject.PE, Subject.ENGLIGH),
                List.of(Subject.CPP, Subject.FPGA, Subject.NETWORKS, Subject.PROJECT),
                List.of(Subject.OS, Subject.OOP, Subject.COMPUTATIONS, Subject.STATISTICS, Subject.PHYSICS)
        ));

        plan.put(SemesterNum.V, new SemesterPlan(
                List.of(Subject.PE, Subject.ENGLIGH),
                List.of(Subject.STORAGE, Subject.SECURITY, Subject.PAC, Subject.COMPMATH, Subject.LING),
                List.of(Subject.ML)
        ));

        plan.put(SemesterNum.VI, new SemesterPlan(
                List.of(Subject.PE, Subject.ENGLIGH),
                List.of(Subject.STORAGE, Subject.SECURITY, Subject.PAC, Subject.BIOINF, Subject.PYTHON),
                List.of(Subject.LAW)
        ));

        plan.put(SemesterNum.VII, new SemesterPlan(
                List.of(Subject.PRACTICE),
                List.of(Subject.SEMINAR, Subject.BIOINFONE, Subject.BUSINESS,
                        Subject.DOCUMENTATION),
                List.of(Subject.INFSECORITY)
        ));

        plan.put(SemesterNum.VIII, new SemesterPlan(
                List.of(),
                List.of(Subject.SEMINAR, Subject.PRACTICE, Subject.LIFESECURITY, Subject.RHETORIC),
                List.of(Subject.ECONOMICS, Subject.PHILOSOPHY)
        ));
    }
}