package pl.blackwater.hardcore.guilds.managers;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.guilds.data.Guild;
import pl.blackwater.hardcore.guilds.data.Member;
import pl.blackwater.hardcore.guilds.settings.GuildConfig;
import pl.blackwater.hardcore.ranking.RankingManager;
import pl.blackwater.hardcore.utils.CoreUtil;
import pl.blackwaterapi.API;
import pl.blackwaterapi.utils.Logger;
import pl.blackwaterapi.utils.SpaceUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class GuildManager {

    @Getter private HashMap<String, Guild> guilds = new HashMap<>();

    public Guild getGuild(Member member){
        return member.getGuild();
    }
    public Guild getGuild(Player player){
        final Member member = Core.getMemberManager().getMember(player);
        if(member != null){
            return member.getGuild();
        }
        return null;
    }
    public Guild getGuild(User user){
        final Member member = Core.getMemberManager().getMember(user);
        if(member != null){
            return member.getGuild();
        }
        return null;
    }
    public Guild getGuild(String NameOrTag){
        for(Guild guild : getGuilds().values()) {
            if (guild.getTag().equalsIgnoreCase(NameOrTag) || guild.getName().equalsIgnoreCase(NameOrTag))
                return guild;
        }
        return null;
    }
    public Guild getGuild(Location location){
        for(Guild guild : getGuilds().values()){
            if(guild.getCuboid().isInCuboid(location)){
                return guild;
            }
        }
        return null;
    }
    public Guild createGuild(String tag, String name, Player owner){
        final Guild g = new Guild(tag,name,owner);
        getGuilds().put(g.getTag().toUpperCase(),g);
        g.update(false);
        final Location center = g.getCuboid().getCenter();
        center.setY(40.0);
        for (final Location loc : SpaceUtil.getSquare(center, 4, 4)) {
            loc.getBlock().setType(Material.AIR);
        }
        for (final Location loc : SpaceUtil.getSquare(center, 2)) {
            loc.getBlock().setType(Material.OBSIDIAN);
        }
        for (final Location loc : SpaceUtil.getCorners(center, 2, 2)) {
            loc.getBlock().setType(Material.OBSIDIAN);
        }
        for (final Location loc : SpaceUtil.getSquare(center, 0, 0)) {
            loc.getBlock().setType(Material.BEDROCK);
        }
        center.add(0.0, 1.0, 0.0);
        for (final Location loc : SpaceUtil.getSquare(center, 0, 0)) {
            loc.getBlock().setType(Material.DRAGON_EGG);
        }
        for (final Location loc : SpaceUtil.getCorners(center, 2, 2)) {
            loc.getBlock().setType(Material.OBSIDIAN);
        }
        center.add(0.0, 3.0, 0.0);
        for (final Location loc : SpaceUtil.getSquare(center, 2)) {
            loc.getBlock().setType(Material.OBSIDIAN);
        }
        RankingManager.addRanking(g);
        ScoreBoardManager.updateBoard(g);
        return g;
    }
    public boolean canCreateGuild(Location loc){
        if (!loc.getWorld().equals(GuildConfig.CUBOID_WORLD)) {
            return false;
        }
        final int spawnX = loc.getWorld().getSpawnLocation().getBlockX();
        final int spawnZ = loc.getWorld().getSpawnLocation().getBlockZ();
        if (GuildConfig.CUBOID_SPAWN_ENABLED && Math.abs(loc.getBlockX() - spawnX) < GuildConfig.CUBOID_SPAWN_DISTANCE && Math.abs(loc.getBlockZ() - spawnZ) < GuildConfig.CUBOID_SPAWN_DISTANCE) {
            return false;
        }
        final int mindistance = GuildConfig.CUBOID_SIZE_MAX * 2 + GuildConfig.CUBOID_SIZE_BETWEEN;
        for (final Guild g : getGuilds().values()) {
            if (Math.abs(g.getCuboid().getCenterX() - loc.getBlockX()) <= mindistance && Math.abs(g.getCuboid().getCenterZ() - loc.getBlockZ()) <= mindistance) {
                return false;
            }
        }
        return true;
    }
    public void removeGuild(Guild guild){
        for(Member member : Core.getMemberManager().getMembers(guild)) {
            Core.getMemberManager().removeMember(member);
            ScoreBoardManager.updateBoard(member.getPlayer());
        }
        guild.delete();
        final Location c = guild.getCuboid().getCenter();
        c.setY(41.0);
        for (final Location loc : SpaceUtil.getSquare(c, 0, 0)) {
            loc.getBlock().setType(Material.AIR);
        }
        c.setY(40.0);
        for (final Location loc : SpaceUtil.getSquare(c, 0, 0)) {
            loc.getBlock().setType(Material.AIR);
        }
        RankingManager.removeRanking(guild);
        final Player owner = Bukkit.getPlayer(guild.getOwner());
        getGuilds().remove(guild.getTag().toUpperCase());
        if(owner != null){
            ScoreBoardManager.updateBoard(owner);
        }
    }
    //test
    public void load(){
        final ResultSet rs = API.getStore().query("SELECT * FROM `{P}guilds`");
        try {
            while (rs.next()){
                final Guild e = new Guild(rs);
                getGuilds().put(e.getTag(),e);
                RankingManager.addRanking(e);
           }
            CoreUtil.sendConsoleMessage("Loaded " + getGuilds().size() + " guilds");
        }catch (SQLException sql){
            Logger.warning("ERROR WITH LOAD GUILD - SQLEXCEPTION CHECK ERROR: ");
            sql.printStackTrace();
        }
    }

}
