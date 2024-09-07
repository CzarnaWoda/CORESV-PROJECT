package pl.blackwater.hardcore.storage;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.TextCommand;
import pl.blackwaterapi.configs.ConfigCreator;

import java.util.List;

public class TextCommandsStorage extends ConfigCreator {
    public TextCommandsStorage() {
        super("textcommands.yml", "Text Commands Config", Core.getInstance());
    }

    public void loadTextCommands(){
        final FileConfiguration config = getConfig();

        for(String key : config.getConfigurationSection("commands").getKeys(false)){
            final ConfigurationSection section = config.getConfigurationSection("commands." + key);

            final String command = section.getString("command");
            final String permission = section.getString("permission");
            final List<String> aliases = section.getStringList("aliases");
            final List<String> text = section.getStringList("text");

            final TextCommand textCommand = new TextCommand(key,command,permission,aliases,text);

            Core.getTextCommandManager().getTextCommands().put(key,textCommand);
            Core.getTextCommandManager().getCommands().add(command);
            aliases.forEach(alias -> Core.getTextCommandManager().getCommands().add(alias));
        }
    }
}
