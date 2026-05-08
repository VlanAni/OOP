package edu.taskchecker.vladimir.dsl.builders

import edu.taskchecker.vladimir.domain.Group
import edu.taskchecker.vladimir.domain.Student

class GroupBuilder {
    String name
    List<Student> students = []

    void name(String v) { name = v }

    void student(Closure cl) {
        def builder = new StudentBuilder()
        cl.delegate = builder
        cl.resolveStrategy = Closure.DELEGATE_FIRST
        cl.call()
        students << builder.build()
    }

    Group build() {
        if (!name) {
            throw new IllegalStateException("empty name");
        }

        return new Group(name, students)
    }
}