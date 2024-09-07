package pl.blackwater.hardcore.guilds.commands.user;

import org.bukkit.entity.Player;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.guilds.data.Guild;
import pl.blackwater.hardcore.guilds.data.Member;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwaterapi.commands.PlayerCommand;
import pl.blackwaterapi.utils.Util;

public class DepositCommand extends PlayerCommand implements Colors {
    public DepositCommand() {
        super("skarbiec", "Otwiera skarbiec gildii", "/g skarbiec", "guild.main", "deposit");
    }

    @Override
    public boolean onCommand(Player player, String[] strings) {
        final Guild guild = Core.getGuildManager().getGuild(player);
        if(guild == null){
            return Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Nie posiadasz gildii");
        }
        final Member member = Core.getMemberManager().getMember(player);
        if(!member.getPermissions().get("openDepositPermission").isStatus()){
            return Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Nie posiadasz uprawnien aby zarzadzac ta gildia");
        }
        guild.buildDepositInventory().openInventory(player);
        return false;
    }
}
