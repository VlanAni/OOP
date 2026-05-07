package integration

importConfig "tasks.groovy"

groups {
    group {
        name "test-group"
        student {
            nickname "VlanAni"
            fullName "Vladimir"
            repoUrl  "https://github.com/VlanAni/OOP.git"
        }
    }
}

checkpoints {
}

check {
    students "VlanAni"
    tasks    "Task_1_1_1", "Task_1_2_2", "Task_2_1_1"
}

config {
    excellent       5
    good            3
    pass            2
    softDeadlinePenalty 0.5
    testTimeout     120
}