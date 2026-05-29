package edu.vladimir.primesocket.domain.task;

import java.io.Serializable;

public class TaskID implements Serializable {
    private final int value;

    public TaskID(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("id must be non null");
        }

        this.value = value;
    }

    @Override
    public boolean equals(Object id) {
        if (id == null) {
            return false;
        }

        if (id.getClass() != TaskID.class) {
            return false;
        }

        return this.value == ((TaskID) id).value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }

    @Override
    public String toString() {
        return Integer.toString(this.value);
    }
}
