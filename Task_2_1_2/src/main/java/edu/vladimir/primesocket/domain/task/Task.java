package edu.vladimir.primesocket.domain.task;

import java.io.Serializable;

public class Task implements Serializable {
    private final int[] chunk;
    private final TaskID taskID;

    public Task(int[] chunk, TaskID taskId) {
        this.chunk = chunk;
        this.taskID = taskId;
    }

    public TaskID ID() {
        return this.taskID;
    }

    public int[] chunk() {
        return this.chunk.clone();
    }
}
