package pl.blackwater.hardcore.guilds.managers;


import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_8_R3.Scoreboard;
import net.minecraft.server.v1_8_R3.ScoreboardTeam;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.guilds.data.Guild;
import pl.blackwater.hardcore.guilds.data.Member;
import pl.blackwater.hardcore.guilds.settings.GuildConfig;
import pl.blackwaterapi.utils.Util;

public class ScoreBoardManager {
    private static Scoreboard scoreboard = new Scoreboard();

    public static void initPlayer(Player p) {
        try {
            ScoreboardTeam team = null;
            if (scoreboard.getPlayerTeam(p.getName()) == null) {
                team = scoreboard.createTeam(p.getName());
            }
            assert team != null;
            scoreboard.addPlayerToTeam(p.getName(), team.getName());
            team.setPrefix("");
            team.setDisplayName("");
            PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam(team, 0);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.equals(p)) {
                    continue;
                }
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
            }
            for (Player pp : Bukkit.getOnlinePlayers()) {
                if (pp.equals(p)) {
                    continue;
                }
                ScoreboardTeam t = scoreboard.getTeam(pp.getName());
                PacketPlayOutScoreboardTeam packetShow = new PacketPlayOutScoreboardTeam(t, 0);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetShow);
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void updateBoard(Player p) {
        Bukkit.getScheduler().runTaskAsynchronously(Core.getInstance(), () -> {
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        updateOthersFor(p, online);
                        updateOthersFor(online, p);
                    }
                }
        );
    }

    private static void updateOthersFor(Player send, Player p) {
        ScoreboardTeam team = scoreboard.getPlayerTeam(p.getName());
        User get = Core.getUserManager().getUser(p);
        User s = Core.getUserManager().getUser(send);
        team.setPrefix(getValidPrefix(get, s));
        team.setSuffix(getValidSuffix(get));
        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam(team, 2);
        ((CraftPlayer) send).getHandle().playerConnection.sendPacket(packet);
    }


    private static String getValidPrefix(User get, User send) {
        ChatColor color = ChatColor.DARK_RED;
        ChatColor color2 = ChatColor.WHITE;
        Guild getg = Core.getGuildManager().getGuild(get);
        Guild sendg = Core.getGuildManager().getGuild(send);
        if (Core.getGuildManager().getGuild(get) != null && Core.getGuildManager().getGuild(send) != null) {
            assert getg != null;
            assert sendg != null;
            if (getg.getTag().equals(sendg.getTag())) {
                color = ChatColor.BLUE;
            } else {
                color = ChatColor.DARK_RED;
            }
        }
        String tag = "";
        if (getg != null) {
            tag = ChatColor.DARK_GRAY + "[" + color + getg.getTag() + ChatColor.DARK_GRAY + "] ";
            color2 = color;
        }
        return tag + color2;
    }
    private static String getValidSuffix(User get){
        if(get.getRank().getName().equalsIgnoreCase("root")){
            return  Util.fixColor(Util.replaceString(GuildConfig.SCOREBOARDMANAGER_ROOT));
        }
        if(get.getRank().getName().equalsIgnoreCase("admin")){
            return  Util.fixColor(Util.replaceString(GuildConfig.SCOREBOARDMANAGER_ADMIN));
        }
        if(get.getRank().getName().equalsIgnoreCase("mod")){
            return  Util.fixColor(Util.replaceString(GuildConfig.SCOREBOARDMANAGER_MOD));
        }
        if(get.getRank().getName().equalsIgnoreCase("helper")){
            return  Util.fixColor(Util.replaceString(GuildConfig.SCOREBOARDMANAGER_HELPER));
        }
        if(get.getRank().getName().equalsIgnoreCase("vip")){
            return  Util.fixColor(Util.replaceString(GuildConfig.SCOREBOARDMANAGER_VIP));
        }
        return "";
    }



    public static void removeBoard(Player p) {
        ScoreboardTeam team = scoreboard.getPlayerTeam(p.getName());
        scoreboard.removePlayerFromTeam(p.getName(), team);
        PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam(team, 1);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        for (Player pp : Bukkit.getOnlinePlayers()) {
            if (pp.equals(p)) {
                continue;
            }
            ((CraftPlayer) pp).getHandle().playerConnection.sendPacket(packet);
            ScoreboardTeam t = scoreboard.getTeam(pp.getName());
            PacketPlayOutScoreboardTeam packetHide = new PacketPlayOutScoreboardTeam(t, 1);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetHide);
        }
        scoreboard.removeTeam(team);

    }


    public static void updateBoard(Guild guild) {
        for (Member m : Core.getMemberManager().getOnlineMembers(guild)) {
            ScoreBoardManager.updateBoard(m.getPlayer());
        }
    }
}
