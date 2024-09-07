package pl.blackwater.hardcore.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.blackwater.hardcore.data.BanIP;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwaterapi.API;
import pl.blackwaterapi.utils.Logger;
import pl.blackwaterapi.utils.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class BanIPManager implements Colors
{
    private static HashMap<String, BanIP> bansIp;
    
    static {
        bansIp = new HashMap<>();
    }
    
    public static BanIP createBan(final String ip,final String reason,final String admin,final long expireTime)
    {
      final BanIP b = new BanIP(ip, reason, admin, expireTime);
      bansIp.put(ip, b);
      final String kick = MessageConfig.SERVERNAME_TAG + "\n \n" + WarningColor + "       Zostales zbanowany\n" + SpecialSigns + "» " + MainColor + "Dnia: " + ImportantColor + Util.getDate(b.getCreateTime()) + ((b.getExpireTime() > 0L) ? "\n" + SpecialSigns + "» Do: " + ImportantColor + Util.getDate(b.getExpireTime()) : "\n " + SpecialSigns + "» Do: " + ImportantColor + "na zawsze") + "\n" + SpecialSigns + "» " + MainColor + "Przez: " + ImportantColor + b.getAdmin() + "\n" + SpecialSigns + "» " + MainColor + "Powod: " + ImportantColor + UnderLined + b.getReason() + "\n\n" + SpecialSigns + "» " + MainColor + "Kontakt TeamSpeak3:" + ImportantColor + "justpvp.pl";
      for (Player p : Bukkit.getOnlinePlayers()) {
        if (ip.equalsIgnoreCase(p.getAddress().getHostString())) {
          p.kickPlayer(Util.fixColor(kick));
        }
      }
      return b;
    }
    
    public static void deleteBan(final BanIP b) {
        b.delete();
        BanIPManager.bansIp.remove(b.getIp());
    }
    
    public static BanIP getBanIP(final User u) {
        if (BanIPManager.bansIp.containsKey(u.getFirstIP())) {
            return BanIPManager.bansIp.get(u.getFirstIP());
        }
        if (BanIPManager.bansIp.containsKey(u.getLastIP())) {
            return BanIPManager.bansIp.get(u.getLastIP());
        }
        return null;
    }
    
    public static BanIP getBanIP(String ip) {
        return BanIPManager.bansIp.get(ip);
    }
    
    public static void setup() {
    	final ResultSet rs = API.getStore().query("SELECT * FROM `{P}ipbans` WHERE `unban`=0 AND (`expireTime`=0 OR `expireTime`> " + System.currentTimeMillis() + ")");
        try {
            while (rs.next()) {
            	final BanIP b = new BanIP(rs);
                BanIPManager.bansIp.put(b.getIp(), b);
            }
            Logger.info("Loaded " + BanIPManager.bansIp.size() + " ip-bans!");
        }
        catch (SQLException e) {
            Logger.warning("An error occurred while loading ip-bans!", "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static HashMap<String, BanIP> getBansIp() {
        return BanIPManager.bansIp;
    }
}
