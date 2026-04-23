package edu.taskchecker.vladimir.cmd;

import java.util.List;

public interface Command {
    void execute(List<String> options);
}
