package pl.blackwater.hardcore.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.Combat;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.guilds.data.Guild;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.settings.MessageConfig;

public class DeathUtil implements Colors {

    public static String deathMessage(final  int plus, final int minus, final Player player, final Player killer){
        final Guild pGuild = Core.getGuildManager().getGuild(player);
        final Guild kGuild = Core.getGuildManager().getGuild(killer);
        return MessageConfig.MESSAGE_DEATH_KILL
                .replace("{VICTIM}",player.getDisplayName())
                .replace("{KILLER}", killer.getDisplayName())
                .replace("{GVICTIM}" , (pGuild == null ? "" : SpecialSigns + "[" + MainColor + pGuild.getTag() + SpecialSigns + "] "))
                .replace("{GKILLER}" , (kGuild == null ? "" : SpecialSigns + "[" + MainColor + kGuild.getTag() + SpecialSigns + "] "))
                .replace("{+}",plus >= 0 ? "+" + plus : String.valueOf(plus))
                .replace("{-}",plus >= 0 ? "-" + minus : String.valueOf(minus));
    }
    public static String assistMessage(final int plus, final Player player){
        final Guild g = Core.getGuildManager().getGuild(player);
        return MessageConfig.MESSAGE_DEATH_ASSIST.replace("{GUILD}", (g == null ? "" : SpecialSigns + "[" + MainColor + g.getTag() + SpecialSigns + "] "))
                .replace("{PLAYER}", player.getDisplayName())
                .replace("{+}",plus >= 0 ? "+" + plus : String.valueOf(plus));

    }
    public static void strike(final Location l){
        final World world = l.getWorld();
        world.strikeLightning(l);
        world.setThundering(false);
        world.setStorm(false);
        world.setWeatherDuration(1000000);
    }
    public static void remove(final Combat combat) {
        if (combat == null) {
            return;
        }
        combat.setLastAttactTime(0L);
        combat.setLastAsystTime(0L);
        combat.setLastAsystPlayer(null);
        combat.setLastAttactkPlayer(null);
    }
    public static boolean isLastKill(final User u, final Player p) {
        return u.getLastKillTime() > System.currentTimeMillis() && u.getLastKill().equalsIgnoreCase(p.getName());
    }
    public static boolean isAsyst(final Combat c) {
        return c.getLastAsystPlayer() != null && c.getLastAsystTime() > System.currentTimeMillis();
    }
}
