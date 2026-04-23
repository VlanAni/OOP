package edu.taskchecker.vladimir.controller;

import edu.taskchecker.vladimir.cmd.Check;
import edu.taskchecker.vladimir.cmd.Command;
import edu.taskchecker.vladimir.cmd.CommandMapper;
import edu.taskchecker.vladimir.io.Logger;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    public void run(String[] args) {
        Logger logger = new Logger("taskchecker >>> ");

        if (args.length < 1) {
            logger.printError("there is no any argument");
            return;
        }

        CommandMapper cmdMap = new CommandMapper();
        cmdMap.addCommand("check", new Check("config.groovy", logger));

        Command cmd = cmdMap.getCommand(args[0].trim());
        if (cmd == null) {
            logger.printError("unsupported command");
            return;
        }

        List<String> options = new ArrayList<>();
        for (int i = 1; i < args.length; ++i) {
            options.add(args[i].trim());
        }

        cmd.execute(options);
    }
}
