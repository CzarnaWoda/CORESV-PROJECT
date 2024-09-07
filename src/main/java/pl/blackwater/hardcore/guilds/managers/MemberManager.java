package pl.blackwater.hardcore.guilds.managers;

import lombok.Getter;
import org.bukkit.entity.Player;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.guilds.data.Guild;
import pl.blackwater.hardcore.guilds.data.Member;
import pl.blackwater.hardcore.utils.CoreUtil;
import pl.blackwaterapi.API;
import pl.blackwaterapi.utils.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class MemberManager {

    @Getter
    private HashMap<UUID, Member> members = new HashMap<>();

    public Member getMember(UUID uuid){
        return members.get(uuid);
    }
    public Member getMember(User user){
        return members.get(user.getUuid());
    }
    public Member getMember(Player player){
        return members.get(player.getUniqueId());
    }
    public Set<Member> getMembers(Guild guild){
        final Set<Member> members = new HashSet<>();
        for(Member member : getMembers().values()){
            if(member.getGuild() == guild){
                members.add(member);
            }
        }
        return members;
    }
    public Set<Member> getOnlineMembers(Guild guild){
        final Set<Member> members = new HashSet<>();
        for(Member member : getMembers().values()){
            if(member.getGuild() == guild){
                if(member.getPlayer() != null) {
                    members.add(member);
                }
            }
        }
        return members;
    }
    public void removeMember(Member member){
        member.getGuild().getMembers().remove(member);
        member.delete();
        getMembers().remove(member.getUuid());
    }
    public void load(){
        final ResultSet rs = API.getStore().query("SELECT * FROM `{P}members`");
        try {
            while (rs.next()){
                Member member = new Member(rs);
                members.put(member.getUuid(),member);
                final Guild guild = member.getGuild();
                if(guild != null)
                    guild.getMembers().add(member);
            }
            CoreUtil.sendConsoleMessage("Loaded " + getMembers().size() + " members");
        }catch (SQLException sql){
            Logger.warning("ERROR WITH LOAD MEMBERS - SQLEXCEPTION CHECK ERROR: ");
            sql.printStackTrace();
        }
    }
}
