package pl.blackwater.hardcore.guilds.commands;

import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.blackwater.hardcore.guilds.commands.user.*;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwaterapi.commands.PlayerCommand;
import pl.blackwaterapi.utils.Util;

import java.util.Arrays;
import java.util.LinkedHashSet;

public class GuildCommand extends PlayerCommand implements Colors {

    @Getter
    private static final LinkedHashSet<PlayerCommand> subCommands = new LinkedHashSet<>();

    public GuildCommand() {
        super("guild", "glowna komenda systemu gildii", "/guild <subkomenda>", "guild.main", "g","gildia","gildie");
        addSubCommand(new CreateCommand());
        addSubCommand(new DeleteCommand());
        addSubCommand(new HomeCommand());
        addSubCommand(new InviteCommand());
        addSubCommand(new JoinCommand());
        addSubCommand(new KickCommand());
        addSubCommand(new LeaveCommand());
        addSubCommand(new ManageCommand());
        addSubCommand(new PreliderCommand());
        addSubCommand(new PvPCommand());
        addSubCommand(new SetHomeCommand());
        addSubCommand(new DepositCommand());
        addSubCommand(new InfoCommand());
        addSubCommand(new ProfilesCommand());
    }

    @Override
    public boolean onCommand(Player player, String[] strings) {
        if(strings.length == 0){
            return sendHelp(player);
        }
        String subCommand = strings[0];
        PlayerCommand command = getSubCommand(subCommand);
        return (command == null) ? sendHelp(player) : (player.hasPermission(command.getPermission()) ? command.onCommand(player, Arrays.copyOfRange(strings, 1, strings.length)) : Util.sendMsg(player,WarningColor + "Blad: " + WarningColor_2 + "Nie posiadasz uprawnien do tej komendy!"));
    }

    private boolean sendHelp(CommandSender sender){
        Util.sendMsg(sender,Util.replaceString(SpecialSigns + "|->> " + MainColor + "Komendy systemu gildii:"));
        for(PlayerCommand command : getSubCommands()){
            Util.sendMsg(sender,Util.replaceString(SpecialSigns + "  ->> " + ImportantColor + command.getUsage() + SpecialSigns + " * " + MainColor + command.getDescription()));
        }
        Util.sendMsg(sender,Util.replaceString(SpecialSigns + "    ->> " + ImportantColor + "!" + SpecialSigns + " * " + MainColor + "Chat gildyjny"));
        Util.sendMsg(sender,Util.replaceString(SpecialSigns + "    ->> " + ImportantColor + "!!" + SpecialSigns + " * " + MainColor + "Chat sojuszniczy"));
        Util.sendMsg(sender,"");
        return true;
    }


    private void addSubCommand(PlayerCommand command){
        subCommands.add(command);
    }

    private PlayerCommand getSubCommand(String subCommand){
        for(PlayerCommand command : getSubCommands()){
            if(command.getName().equalsIgnoreCase(subCommand) || command.getAliases().contains(subCommand)){
                return command;
            }
        }
        return null;
    }
}
