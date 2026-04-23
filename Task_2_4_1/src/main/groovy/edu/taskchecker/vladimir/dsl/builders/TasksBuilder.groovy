package edu.taskchecker.vladimir.dsl.builders

import edu.taskchecker.vladimir.domain.TaskData

class TasksBuilder {
    List<TaskData> tasks = []

    void task(Closure cl) {
        def tb = new TaskBuilder()
        cl.delegate = tb
        cl.resolveStrategy = Closure.DELEGATE_FIRST
        cl.call()
        tasks << tb.build()
    }
}

