package pl.blackwater.hardcore.data;

import lombok.Data;
import pl.blackwaterapi.API;
import pl.blackwaterapi.store.Entry;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class BanIP implements Entry
{
    private int ID;
    private String reason,admin,ip;
    private long createTime,expireTime;
    private boolean unban;
    
    public BanIP(final String ip,final String reason,final String admin,final long expireTime) {
        super();
        this.ID = -1;
        this.ip = ip;
        this.reason = reason;
        this.admin = admin;
        this.createTime = System.currentTimeMillis();
        this.expireTime = expireTime;
        this.unban = false;
        this.insert();
    }
    
    public BanIP(final ResultSet rs) throws SQLException {
        super();
        this.ID = rs.getInt("id");
        this.ip = rs.getString("ip");
        this.reason = rs.getString("reason");
        this.admin = rs.getString("admin");
        this.createTime = rs.getLong("createTime");
        this.expireTime = rs.getLong("expireTime");
        this.unban = (rs.getInt("unban") == 1);
    }
    
    public boolean isAlive() {
        return this.unban || (this.expireTime != 0L && this.expireTime < System.currentTimeMillis());
    }
    
    public void insert() {
        String q = "INSERT INTO `{P}ipbans`(`id`, `ip`, `admin`, `reason`, `createTime`, `expireTime`, `unban`) VALUES (NULL,'" + this.getIp() + "','" + this.getAdmin() + "','" + this.getReason() + "','" + this.getCreateTime() + "','" + this.getExpireTime() + "','" + (this.unban ? 1 : 0) + "')";
        try {
        	final ResultSet rs = API.getStore().update(q);
            this.ID = ((Number)rs.getObject(1)).intValue();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void update(final boolean paramBoolean) {
    	final String query = "UPDATE `{P}ipbans` SET `admin` = '" + this.getAdmin() + "', `reason` = '" + this.getReason() + "', `createTime` = '" + this.getCreateTime() + "', `expireTime` = '" + this.getExpireTime() + "', `unban` = '" + (this.isUnban() ? 1 : 0) + "' WHERE `id` = '" + this.getID() + "'";
        API.getStore().update(paramBoolean, query);
    }
    
    public void delete() {
        API.getStore().update(false, "DELETE FROM `{P}ipbans` WHERE `id` = '" + this.getID() + "'");
    }
}
