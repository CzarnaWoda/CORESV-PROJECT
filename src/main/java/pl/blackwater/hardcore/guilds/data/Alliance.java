package pl.blackwater.hardcore.guilds.data;

import lombok.Data;
import pl.blackwater.hardcore.Core;
import pl.blackwaterapi.API;
import pl.blackwaterapi.store.Entry;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class Alliance implements Entry {

    private Guild guild_1, guild_2;
    private boolean pvp;

    public Alliance(Guild g1, Guild g2){
        this.guild_1 = g1;
        this.guild_2 = g2;
        this.pvp = false;
        insert();
    }

    public Alliance(ResultSet rs) throws SQLException{
        this.guild_1 = Core.getGuildManager().getGuild(rs.getString("guild1"));
        this.guild_2 = Core.getGuildManager().getGuild(rs.getString("guild2"));
        this.pvp = false;
    }

    public void insert(){
        String sql = "INSERT INTO `{P}alliances`(`id`,`guild1`,`guild2`) VALUES (NULL, '" + getGuild_1().getTag() + "', '" + getGuild_2().getTag() + "');";
        API.getStore().update(sql);
    }

    @Override
    public void update(boolean b) {
        throw new RuntimeException("Can not update this object!");
    }

    public void delete(){
        String sql = "DELETE FROM `{P}alliances` WHERE `guild1`='" + getGuild_1().getTag() + "' AND `guild2` = '" + getGuild_2().getTag() + "'";
        API.getStore().update(sql);

    }
}
