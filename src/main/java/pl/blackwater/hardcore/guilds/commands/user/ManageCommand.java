package pl.blackwater.hardcore.guilds.commands.user;

import org.bukkit.entity.Player;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.guilds.data.Guild;
import pl.blackwater.hardcore.guilds.data.Member;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwaterapi.commands.PlayerCommand;
import pl.blackwaterapi.utils.Util;

public class ManageCommand extends PlayerCommand implements Colors {

    public ManageCommand() {
        super("zarzadzanie", "Zarzadzanie gildia", "/g zarzadzanie", "guild.main", "managing","zarzadzaj","manage");
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        final Guild guild = Core.getGuildManager().getGuild(player);
        if(guild == null){
            return Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Nie posiadasz gildii");
        }
        final Member member = Core.getMemberManager().getMember(player);
        if(!member.getPermissions().get("managePermission").isStatus()){
            return Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Nie posiadasz uprawnien aby zarzadzac ta gildia");
        }
        guild.buildInventory().openInventory(player);
        return false;
    }
}
