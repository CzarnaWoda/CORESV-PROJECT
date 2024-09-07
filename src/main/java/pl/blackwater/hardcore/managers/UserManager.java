package pl.blackwater.hardcore.managers;

import lombok.Getter;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.guilds.managers.ScoreBoardManager;
import pl.blackwater.hardcore.ranking.RankingManager;
import pl.blackwater.hardcore.utils.CoreUtil;
import pl.blackwaterapi.API;
import pl.blackwaterapi.utils.Logger;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class UserManager {

    @Getter
    private HashMap<UUID, User> users = new HashMap<>();

    public User getUser(@NotNull final Player player){ return users.get(player.getUniqueId());}

    public User getUser(final String name) {
        for (User u : users.values()){
            if(u.getLastName().equalsIgnoreCase(name)){
                return u;
            }
        }
        return null;
    }
    public User getUser(final UUID uuid){
        return users.get(uuid);
    }
    public User createUser(Player player){
        final User u = new User(player);
        RankingManager.addRanking(u);
        users.put(player.getUniqueId(), u);
        return u;
    }
    public User joinToGame(Player player){
        User u = getUser(player);
        if(u == null){
            createUser(player);
        }else{
            u.setLastName(player.getName());
            u.setLastJoin(System.currentTimeMillis());
            u.setLastIP(player.getAddress().getHostString());
            if(u.getLastLocation() != null){
                player.teleport(u.getLastLocation());
            }
            player.setGameMode(u.getGameMode());
            player.setAllowFlight(u.isFly());
            if(player.getGameMode().equals(GameMode.ADVENTURE)){
                player.setGameMode(GameMode.SURVIVAL);
            }
            Core.getEasyScoreboardManager().createScoreBoard(player);
            ScoreBoardManager.initPlayer(player);
            ScoreBoardManager.updateBoard(player);
        }
        return u;
    }
    public User leaveFromGame(Player player){
        final User u = getUser(player);
        Core.getEasyScoreboardManager().removeScoreBoard(player);
        if(u == null){
            Logger.warning("BIGERROR - USER DATA WAS DELETE [!_!], user wasn't found in hashmap and in MYSQL - SMTH WRONG !!");
            return null;
        }
        u.setFly(player.getAllowFlight());
        u.setGameMode(player.getGameMode());
        u.setLastName(player.getName());
        u.setLastLocation(player.getLocation());
        u.addTimePlay((int)((System.currentTimeMillis() - u.getLastJoin()) / 1000L));
        u.setGod(player.hasPermission("core.god"));
        u.update(true);
        ScoreBoardManager.removeBoard(player);
        return u;
    }

    public void load(){
        final ResultSet rs = API.getStore().query("SELECT * FROM `{P}users`");
        try {
            while (rs.next()){
                User e = new User(rs);
                users.put(e.getUuid(), e);
                RankingManager.addRanking(e);
            }
            CoreUtil.sendConsoleMessage("Loaded " + users.size() + " users!");
        }catch (SQLException | IOException sql){
            Logger.warning("ERROR WITH LOAD USERS - SQLEXCEPTION CHECK ERROR: ");
            sql.printStackTrace();
        }
    }
}
