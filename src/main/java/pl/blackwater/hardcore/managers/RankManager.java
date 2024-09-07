package pl.blackwater.hardcore.managers;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.jetbrains.annotations.NotNull;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.Rank;
import pl.blackwater.hardcore.data.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class RankManager {

    @Getter
    public HashMap<UUID, PermissionAttachment> permissions = new HashMap<>();
    @Getter
    private HashMap<String, Rank> ranks = new HashMap<>();

    public void joinPlayer(@NotNull Player player){
        if(permissions.get(player.getUniqueId()) == null) {
            PermissionAttachment attachment = player.addAttachment(Core.getInstance());
            getPermissions().put(player.getUniqueId(), attachment);
        }else{
            permissions.remove(player.getUniqueId());
            PermissionAttachment attachment = player.addAttachment(Core.getInstance());
            getPermissions().put(player.getUniqueId(), attachment);
        }
    }
    public Rank getRank(String name){
        for(Rank r : getRanks().values()){
            if(r.getName().equalsIgnoreCase(name)){
                return r;
            }
        }
        return null;
    }
    public void implementPermissions(@NotNull User u){
        Rank rank = u.getRank();
        if(u.getPlayer() != null){
            PermissionAttachment permissionAttachment = getPermissions().get(u.getPlayer().getUniqueId());
            for(String permissions : rank.getPermissions()){
                permissionAttachment.setPermission(permissions, true);
            }
        }
    }
    public void unimplementPermissions(@NotNull User u){
        Rank rank = u.getRank();
        if(u.getPlayer() != null){
            PermissionAttachment permissionAttachment = getPermissions().get(u.getPlayer().getUniqueId());
            for(String permissions : rank.getPermissions()){
                permissionAttachment.unsetPermission(permissions);
            }
        }
    }
    public Rank createRank(String rank){
        Rank r = new Rank(rank, "CHANGE","CHANGE",new ArrayList<>());
        ranks.put(rank, r);
        r.update(true);
        return r;
    }
}
