package edu.taskchecker.vladimir.cmd;

import java.util.HashMap;
import java.util.Map;

public class CommandMapper {
    private final Map<String, Command> commandMap;

    public CommandMapper() {
        commandMap = new HashMap<>();
    }

    public void addCommand(String commandName, Command command) {
        if (commandName == null || command == null) {
            return;
        }

        commandMap.put(commandName, command);
    }

    public Command getCommand(String commandName) {
        if (commandName == null) {
            return null;
        }

        return commandMap.get(commandName);
    }
}
