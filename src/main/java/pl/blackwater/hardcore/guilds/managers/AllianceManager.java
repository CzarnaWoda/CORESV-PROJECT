package pl.blackwater.hardcore.guilds.managers;

import lombok.Getter;
import pl.blackwater.hardcore.guilds.data.Alliance;
import pl.blackwater.hardcore.guilds.data.Guild;
import pl.blackwater.hardcore.utils.CoreUtil;
import pl.blackwaterapi.API;
import pl.blackwaterapi.utils.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AllianceManager {

    @Getter private List<Alliance> alliances = new ArrayList<>();
    @Getter private List<String> invites = new ArrayList<>();
    public Alliance getAlliance(Guild guild, Guild other){
        for(Alliance alliance : alliances) {
            if((alliance.getGuild_1().equals(guild) && alliance.getGuild_2().equals(other)) || (alliance.getGuild_2().equals(guild) && alliance.getGuild_1().equals(other))) {
                return alliance;
            }
        }
        return null;
    }
    public boolean hasAlliance(Guild g, Guild o){
        for(Alliance a : getAlliances()){
            if (((a.getGuild_1().equals(g)) && (a.getGuild_2().equals(o))) || ((a.getGuild_2().equals(g)) && (a.getGuild_1().equals(o)))) {
                return true;
            }
        }
        return false;
    }
    public void createAlliance(Guild g, Guild o){
        if (hasAlliance(g, o)) {
            return;
        }
        Alliance a = new Alliance(g,o);
        getAlliances().add(a);
    }
    public void removeAlliance(Guild g, Guild o){
        if(!hasAlliance(g,o)){
            return;
        }
        Alliance a = getAlliance(g,o);
        a.delete();
        getAlliances().remove(a);
    }
    public Set<Alliance> getAlliances(Guild g){
        Set<Alliance> gAlliances = new HashSet<>();
        for(Alliance a : getAlliances()){
            if(a.getGuild_1().equals(g) || a.getGuild_2().equals(g)){
                gAlliances.add(a);
            }
        }
        return gAlliances;
    }
    public Set<Guild> getAlliancesAsGuilds(Guild g){
        Set<Guild> gAlliances = new HashSet<>();
        for(Alliance a : getAlliances()){
            if(a.getGuild_1().equals(g) || a.getGuild_2().equals(g)){
                gAlliances.add((a.getGuild_1().equals(g) ? a.getGuild_2() : a.getGuild_1()));
            }
        }
        return gAlliances;
    }
    public void load(){
        ResultSet rs = API.getStore().query("SELECT * FROM `{P}alliances`");
            try {
                while(rs.next()){
                    Alliance alliance = new Alliance(rs);
                    alliances.add(alliance);
                }
                CoreUtil.sendConsoleMessage("Loaded " + getAlliances().size() + " alliances");
            } catch (SQLException e) {
                Logger.warning("ERROR WITH LOAD ALLIANCE - SQLEXCEPTION CHECK ERROR: ");
                e.printStackTrace();
            }
    }
}
