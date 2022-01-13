package edu.touro.mco152.bm;

import edu.touro.mco152.bm.commands.CommandInterface;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * This class is an invoker for the instances of the CommandInterface.
 * It allows clients to add commands to a list, and then, when the client calls invoke(),
 * it simply calls the execute method for each command in its list.
 */

public class Invoker {

    private final Queue<CommandInterface> toCommand = new ArrayDeque<>();

    public void invoke(){

        for (CommandInterface command : toCommand) {
            command.execute();
        }

    }
    public void addCommand(CommandInterface command){
        toCommand.add(command);
    }

    public void addListOfCommands(List<CommandInterface> commands){
        toCommand.addAll(commands);
    }
}
