package edu.touro.mco152.bm;

import edu.touro.mco152.bm.commands.CommandInterface;

import java.util.ArrayList;
import java.util.List;

public class Invoker {

    private final List<CommandInterface> toCommand = new ArrayList<>();

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
