package edu.taskchecker.vladimir.dsl

import edu.taskchecker.vladimir.domain.*
import edu.taskchecker.vladimir.dsl.builders.CheckBuilder
import edu.taskchecker.vladimir.dsl.builders.ControlPointsBuilder
import edu.taskchecker.vladimir.dsl.builders.GradingConfigBuilder
import edu.taskchecker.vladimir.dsl.builders.GroupsBuilder
import edu.taskchecker.vladimir.dsl.builders.TasksBuilder
import org.codehaus.groovy.control.CompilerConfiguration

abstract class CourseDsl extends Script {

    protected List<TaskData> taskList = []
    protected List<Group> groupList = []
    protected List<ControlPoint> checkpointList = []
    protected List<CheckAssignment> assignmentList = []
    private GradingConfig gradingConfig = GradingConfig.defaultConfig()
    protected boolean gradingConfigSet = false

    void tasks(Closure cl) {
        def builder = new TasksBuilder()
        cl.delegate = builder
        cl.resolveStrategy = Closure.DELEGATE_FIRST
        cl.call()
        taskList.addAll(builder.tasks)
    }

    void groups(Closure cl) {
        def builder = new GroupsBuilder()
        cl.delegate = builder
        cl.resolveStrategy = Closure.DELEGATE_FIRST
        cl.call()
        groupList.addAll(builder.groups)
    }

    void checkpoints(Closure cl) {
        def builder = new ControlPointsBuilder()
        cl.delegate = builder
        cl.resolveStrategy = Closure.DELEGATE_FIRST
        cl.call()
        checkpointList.addAll(builder.controlPoints)
    }

    void check(Closure cl) {
        def builder = new CheckBuilder()
        cl.delegate = builder
        cl.resolveStrategy = Closure.DELEGATE_FIRST
        cl.call()

        List<Student> students = groupList
                .collectMany { it.getStudents() }
                .findAll { builder.studentNicknames.contains(it.getNickname()) }

        List<TaskData> tasks = taskList
                .findAll { builder.taskIds.contains(it.getId()) }

        Set<String> foundNicknames = students.collect { it.getNickname() }.toSet()
        List<String> missingStudents = builder.studentNicknames - foundNicknames
        if (!missingStudents.isEmpty()) {
            throw new IllegalStateException("students not found: ${missingStudents}")
        }

        Set<String> foundTasks = tasks.collect { it.getId() }.toSet()
        List<String> missingTasks = builder.taskIds - foundTasks
        if (!missingTasks.isEmpty()) {
            throw new IllegalStateException(
                    "there is no tasks: ${missingTasks} "
            )
        }

        assignmentList << new CheckAssignment(students, tasks)
    }

    void config(Closure cl) {
        def builder = new GradingConfigBuilder()
        cl.delegate = builder
        cl.resolveStrategy = Closure.DELEGATE_FIRST
        cl.call()
        gradingConfig = builder.build()
        gradingConfigSet = true
    }

    void importConfig(String path) {
        def cfg = new CompilerConfiguration()
        cfg.scriptBaseClass = 'edu.taskchecker.vladimir.dsl.CourseDsl'

        def shell = new GroovyShell(this.class.classLoader, new Binding(), cfg)
        CourseDsl sub = (CourseDsl) shell.parse(new File(path))
        sub.run()

        taskList.addAll(sub.taskList)
        groupList.addAll(sub.groupList)
        checkpointList.addAll(sub.checkpointList)
        assignmentList.addAll(sub.assignmentList)

        if (sub.gradingConfigSet) {
            gradingConfig = sub.gradingConfig
        }
    }

    Course getCourse() {
        return new Course(taskList, groupList, checkpointList, assignmentList, gradingConfig)
    }
}