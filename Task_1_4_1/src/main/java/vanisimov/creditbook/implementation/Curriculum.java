package vanisimov.creditbook.implementation;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import vanisimov.creditbook.consts.Num;
import vanisimov.creditbook.consts.Subject;

public class Curriculum {

    private final Map<Num, SemesterPlan> plan;

    public Curriculum() {
        this.plan = new EnumMap<>(Num.class);
        initializePlan();
    }

    public SemesterPlan getPlanForSemester(Num semesterId) {
        return plan.getOrDefault(semesterId,
                new SemesterPlan(Collections.emptyList(), Collections.emptyList(), Collections.emptyList()));
    }

    private void initializePlan() {
        plan.put(Num.I, new SemesterPlan (
                List.of(Subject.PE, Subject.ENGLIGH),
                List.of(Subject.IMPPROG, Subject.DECPROG, Subject.PLATFORMS, Subject.STATEHOOD),
                List.of(Subject.MATH, Subject.DLOGIC, Subject.HISTORY)
        ));

        plan.put(Num.II, new SemesterPlan (
                List.of(Subject.HISTORY, Subject.PE),
                List.of(Subject.IMPPROG, Subject.DECPROG, Subject.PLATFORMS, Subject.ENGLIGH),
                List.of(Subject.MATH, Subject.DLOGIC)
        ));

        plan.put(Num.III, new SemesterPlan(
                List.of(Subject.PE),
                List.of(Subject.OS, Subject.OOP, Subject.PROJECT, Subject.ENGLIGH),
                List.of(Subject.COMPUTATIONS, Subject.STATISTICS, Subject.AIINTR, Subject.DCV)
        ));

        plan.put(Num.IV, new SemesterPlan(
                List.of(Subject.PE, Subject.ENGLIGH),
                List.of(Subject.CPP, Subject.FPGA, Subject.NETWORKS, Subject.PROJECT),
                List.of(Subject.OS, Subject.OOP, Subject.COMPUTATIONS, Subject.STATISTICS, Subject.PHYSICS)
        ));

        plan.put(Num.V, new SemesterPlan(
                List.of(Subject.PE, Subject.ENGLIGH),
                List.of(Subject.STORAGE, Subject.SECURITY, Subject.PAC, Subject.COMPMATH, Subject.LING),
                List.of(Subject.ML)
        ));

        plan.put(Num.VI, new SemesterPlan(
                List.of(Subject.PE, Subject.ENGLIGH),
                List.of(Subject.STORAGE, Subject.SECURITY, Subject.PAC, Subject.BIOINF, Subject.PYTHON),
                List.of(Subject.LAW)
        ));

        plan.put(Num.VII, new SemesterPlan(
                List.of(Subject.PRACTICE),
                List.of(Subject.SEMINAR, Subject.BIOINFONE, Subject.BUSINESS,
                        Subject.DOCUMENTATION),
                List.of(Subject.INFSECORITY)
        ));

        plan.put(Num.VIII, new SemesterPlan(
                List.of(),
                List.of(Subject.SEMINAR, Subject.PRACTICE, Subject.LIFESECURITY, Subject.RHETORIC),
                List.of(Subject.ECONOMICS, Subject.PHILOSOPHY)
        ));
    }
}