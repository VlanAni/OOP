package edu.taskchecker.vladimir.dsl.builders

import edu.taskchecker.vladimir.domain.ControlPoint

class ControlPointsBuilder {
    List<ControlPoint> controlPoints = []

    void checkpoint(Closure cl) {
        def builder = new ControlPointBuilder()
        cl.delegate = builder
        cl.resolveStrategy = Closure.DELEGATE_FIRST
        cl.call()
        controlPoints << builder.build()
    }
}