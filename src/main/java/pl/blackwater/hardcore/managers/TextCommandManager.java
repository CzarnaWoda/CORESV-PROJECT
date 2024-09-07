package pl.blackwater.hardcore.managers;

import lombok.Getter;
import pl.blackwater.hardcore.data.TextCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TextCommandManager {

    @Getter
    private HashMap<String, TextCommand> textCommands = new HashMap<>();
    @Getter
    private List<String> commands = new ArrayList<>();

    public TextCommand getTextCommand(String command){
        for(TextCommand textCommand : textCommands.values()){
            if(textCommand.getAliases().stream().anyMatch(command::equalsIgnoreCase) || textCommand.getCommand().equalsIgnoreCase(command)){
                return textCommand;
            }
        }
        return null;
    }
}
