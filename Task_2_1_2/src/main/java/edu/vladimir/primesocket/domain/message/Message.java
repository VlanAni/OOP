package edu.vladimir.primesocket.domain.message;

import edu.vladimir.primesocket.domain.task.Task;

import java.io.Serializable;

public class Message implements Serializable {
    private final MessageType messageType;
    private final Task task;
    private final Boolean result;

    public Message() {
        this.messageType = MessageType.SHUTDOWN;
        this.task = null;
        this.result = null;
    }

    public Message(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("task cannot be null");
        }

        this.messageType = MessageType.NEWTASK;
        this.task = task;
        this.result = null;
    }

    public Message(boolean result) {
        this.result = result;
        this.task = null;
        this.messageType = MessageType.TASKRESULT;
    }

    public Task task() {
        return messageType == MessageType.NEWTASK ? task : null;
    }

    public Boolean result() {
        return messageType == MessageType.TASKRESULT ? result : null;
    }

    public MessageType type() {
        return messageType;
    }

    private Message(MessageType type) {
        this.messageType = type;
        this.task = null;
        this.result = null;
    }
}
