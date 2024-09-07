package pl.blackwater.hardcore.managers;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import pl.blackwater.hardcore.data.Ban;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwaterapi.API;
import pl.blackwaterapi.utils.Logger;
import pl.blackwaterapi.utils.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class BanManager implements Colors
{
    private static HashMap<UUID, Ban> bans;
    
    static {
        bans = new HashMap<>();
    }
    
    public static Ban createBan(final UUID uuid,final String reason,final String admin,final long expireTime)
    {
      final Ban b = new Ban(uuid, reason, admin, expireTime);
      bans.put(uuid, b);
      final OfflinePlayer op = Bukkit.getOfflinePlayer(uuid);
      if (op.isOnline())
      {
        final String kick = MessageConfig.SERVERNAME_TAG + "\n \n" + WarningColor + "       Zostales zbanowany\n" + SpecialSigns + "» " + MainColor + "Dnia: " + ImportantColor + Util.getDate(b.getCreateTime()) + ((b.getExpireTime() > 0L) ? "\n" + SpecialSigns + "» Do: " + ImportantColor + Util.getDate(b.getExpireTime()) : "\n " + SpecialSigns + "» " + MainColor + "Do: " + ImportantColor + "na zawsze") + "\n" + SpecialSigns + "» " + MainColor + "Przez: " + ImportantColor + b.getAdmin() + "\n" + SpecialSigns + "» " + MainColor + "Powod: " + ImportantColor + UnderLined + b.getReason() + "\n\n" + SpecialSigns + "» " + MainColor + "Kontakt TeamSpeak3: " + ImportantColor + "justpvp.pl";
        op.getPlayer().kickPlayer(Util.fixColor(kick));
      }
      return b;
    }
    
    public static void deleteBan(final Ban b) {
        b.delete();
        BanManager.bans.remove(b.getUuid());
    }
    
    public static Ban getBan(final User paramUser) {
        return BanManager.bans.get(paramUser.getUuid());
    }
    
    public static Ban getBan(final UUID paramUuid) {
        return BanManager.bans.get(paramUuid);
    }
    
    public static void setup() {
        ResultSet rs = API.getStore().query("SELECT * FROM `{P}bans` WHERE `unban`=0 AND (`expireTime`=0 OR `expireTime`> " + System.currentTimeMillis() + ")");
        try {
            while (rs.next()) {
            	final Ban b = new Ban(rs);
                BanManager.bans.put(b.getUuid(), b);
            }
            Logger.info("Loaded " + BanManager.bans.size() + " bans!");
        }
        catch (SQLException e) {
            Logger.warning("An error occurred while loading bans!", "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static HashMap<UUID, Ban> getBans() {
        return BanManager.bans;
    }
}
