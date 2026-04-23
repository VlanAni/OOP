package edu.taskchecker.vladimir.dsl.builders

import edu.taskchecker.vladimir.domain.ControlPoint

import java.time.LocalDate

class ControlPointBuilder {
    String name
    LocalDate date

    void name(String v) { name = v }
    void date(String v) { date = LocalDate.parse(v) }

    ControlPoint build() {
        if (!name || !date) {
            throw new IllegalStateException("empty fields")
        }

        return new ControlPoint(name, date)
    }
}

