package pl.blackwater.hardcore.guilds.commands.user;

import org.bukkit.entity.Player;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.guilds.data.Guild;
import pl.blackwater.hardcore.guilds.data.Member;
import pl.blackwater.hardcore.guilds.inventories.ProfilesInventory;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwaterapi.commands.PlayerCommand;
import pl.blackwaterapi.utils.Util;

public class ProfilesCommand extends PlayerCommand implements Colors {
    public ProfilesCommand() {
        super("profiles", "Zarzadzanie profilami graczy w gildii", "/g profile", "guild.main", "profile");
    }

    @Override
    public boolean onCommand(Player player, String[] strings) {
        final Guild guild = Core.getGuildManager().getGuild(player);
        if(guild == null){
            return Util.sendMsg(player, WarningColor  + "Blad: " + WarningColor_2 + "Nie posiadasz gildii");
        }
        final Member member = Core.getMemberManager().getMember(player);
        if(!member.getPermissions().get("manageProfilesPermission").isStatus()){
            return Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Nie posiadasz uprawnien w swojej gildii aby to zrobic");
        }
        ProfilesInventory.openMenu(Core.getGuildManager().getGuild(player),player);
        return false;
    }
}
