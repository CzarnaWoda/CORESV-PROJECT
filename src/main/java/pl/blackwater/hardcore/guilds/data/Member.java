package pl.blackwater.hardcore.guilds.data;

import lombok.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.guilds.enums.Position;
import pl.blackwaterapi.API;
import pl.blackwaterapi.store.Entry;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

@Getter
@Setter
public class Member implements Entry {

    private UUID uuid;
    private Guild guild;
    private Position position;
    private HashMap<String, GuildPermission> permissions;

    public Member(UUID uuid, Guild guild, Position position){
        this.uuid = uuid;
        this.guild = guild;
        this.position = position;
        this.permissions = GuildPermission.generateStandardPermission(position == Position.LIDER);
        insert();
    }
    public Member(ResultSet resultSet) throws SQLException {
        this.uuid = UUID.fromString(resultSet.getString("uuid"));
        this.guild = Core.getGuildManager().getGuild(resultSet.getString("guild"));
        this.position = Position.getByName(resultSet.getString("position"));
        this.permissions = GuildPermission.getPermissionsFromString(resultSet.getString("permissions"));
    }
    @Override
    public void insert() {
        final String sql = "INSERT INTO `{P}members` (`id`,`uuid`,`guild`,`position`,`permissions`) VALUES (NULL,'" + getUuid() + "', '" + getGuild().getTag() + "', '" + getPosition().getType() + "', '" + GuildPermission.getStringFromPermissions(getPermissions()) + "')";
        API.getStore().update(sql);
    }

    @Override
    public void update(boolean b) {
        final String sql = "UPDATE `{P}members` SET `position`='" + getPosition().getType() + "', `permissions`='" + GuildPermission.getStringFromPermissions(getPermissions()) + "' WHERE `uuid`='" + getUuid() + "'";
        API.getStore().update(b,sql);
    }

    @Override
    public void delete() {
        API.getStore().update(false, "DELETE FROM `{P}members` WHERE `uuid` = '" + getUuid() + "'");
    }

    public User getUser(){
        return Core.getUserManager().getUser(getUuid());
    }
    public Player getPlayer(){
        return Bukkit.getPlayer(getUuid());
    }
    public OfflinePlayer getOfflinePlayer(){
        return Bukkit.getOfflinePlayer(getUuid());
    }
}
