package edu.taskchecker.vladimir.dsl.builders

import edu.taskchecker.vladimir.domain.Group

class GroupsBuilder {
    List<Group> groups = []

    void group(Closure cl) {
        def builder = new GroupBuilder()
        cl.delegate = builder
        cl.resolveStrategy = Closure.DELEGATE_FIRST
        cl.call()
        groups << builder.build()
    }
}