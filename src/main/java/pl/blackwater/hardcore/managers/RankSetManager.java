package pl.blackwater.hardcore.managers;

import lombok.Getter;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.Rank;
import pl.blackwater.hardcore.data.RankSet;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.guilds.managers.ScoreBoardManager;
import pl.blackwaterapi.API;
import pl.blackwaterapi.utils.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class RankSetManager {
    @Getter
    private static HashMap<UUID, RankSet> ranksets = new HashMap<>();

    public static void setRank(User user, String admin, Rank rank, long time){
        if(ranksets.get(user.getUuid()) != null){
            removeRank(user, ranksets.get(user.getUuid()));
        }
        RankSet rankSet = new RankSet(user.getUuid(), rank.getName(), user.getRank().getName(), admin, time);
        ranksets.put(user.getUuid(), rankSet);
        user.setRank(rank);
        Core.getRankManager().unimplementPermissions(user);
        Core.getRankManager().implementPermissions(user);
        ScoreBoardManager.updateBoard(user.getPlayer());
    }
    public static void removeRank(User u, RankSet rankSet){
        u.setRank(Core.getRankManager().getRank(rankSet.getPreviousRank()));
        Core.getRankManager().unimplementPermissions(u);
        Core.getRankManager().implementPermissions(u);
        ranksets.remove(u.getUuid());
        rankSet.delete();
        ScoreBoardManager.updateBoard(u.getPlayer());
    }
    public static RankSet getSetRank(User u){
        return ranksets.get(u.getUuid());
    }
    public static void setup(){
        final ResultSet rs = API.getStore().query("SELECT * FROM `%P%ranksets`");
        try {
            while (rs.next()) {
                final RankSet e = new RankSet(rs);
                ranksets.put(e.getUser(), e);
            }
            Logger.info("Loaded " + ranksets.size() + " ranksets!");
        }
        catch (SQLException sqlexception) {
            Logger.warning("An error occurred while loading users!", "Error: " + sqlexception.getMessage());
            sqlexception.printStackTrace();
        }
    }
}
