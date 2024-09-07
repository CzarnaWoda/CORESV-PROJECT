package pl.blackwater.hardcore.data;

import lombok.Data;
import pl.blackwaterapi.API;
import pl.blackwaterapi.store.Entry;
import pl.blackwaterapi.utils.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Data
public class RankSet implements Entry {
    private UUID user;
    private String rank,previousRank,admin;
    private Long expireTime;

    public RankSet(UUID user, String rank, String previousRank, String admin, Long expireTime){
        this.user = user;
        this.rank = rank;
        this.previousRank = previousRank;
        this.admin = admin;
        this.expireTime = expireTime;

        insert();
    }

    public RankSet(ResultSet rs) throws SQLException{
        this.user = UUID.fromString(rs.getString("user"));
        this.rank = rs.getString("rank");
        this.previousRank = rs.getString("previousRank");
        this.admin = rs.getString("admin");
        this.expireTime = rs.getLong("expireTime");
    }

    @Override
    public void insert() {
        final String sql = "INSERT INTO `{P}ranksets` (`id`,`user`,`rank`,`previousRank`,`admin`,`expireTime`) VALUES (NULL,'" + getUser().toString() + "', '" + getRank() + "', '" + getPreviousRank() + "', '" + getAdmin() + "', '" + getExpireTime() + "')";
        API.getStore().update(true, sql);
    }

    @Override
    public void update(boolean b) {
        Logger.warning("ERROR #1 -> RankSet can't be update ! Check code and make a change !");
    }

    @Override
    public void delete() {
        API.getStore().update(false, "DELETE FROM `{P}ranksets` WHERE `user` = '" + getUser() + "'");
    }
}
