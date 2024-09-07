package pl.blackwater.hardcore.managers;

import org.bukkit.Bukkit;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.User;
import pl.blackwaterapi.API;
import pl.blackwaterapi.utils.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class WhiteListManager
{
    private static boolean whitelist;
    private static Set<UUID> whitelisted;
    
    static {
        WhiteListManager.whitelist = false;
        whitelisted = new HashSet<>();
    }
    
    public static void addWhitelist(final User user) {
        WhiteListManager.whitelisted.add(user.getUuid());
        API.getStore().update(false, "INSERT INTO `{P}whitelisted`(`id`, `uuid`) VALUES (NULL,'" + user.getUuid().toString() + "')");
    }
    
    public static void removeWhitelist(final User user) {
    	final Iterator<UUID> w = WhiteListManager.whitelisted.iterator();
        UUID s;
        while (w.hasNext()) {
            s = w.next();
            if (s.equals(user.getUuid())) {
                w.remove();
            }
        }
        API.getStore().update(false, "DELETE FROM `{P}whitelisted` WHERE `uuid` = '" + user.getUuid().toString() + "'");
    }
    
    public static boolean isWhitelist(final UUID uuid) {
        for (UUID s : WhiteListManager.whitelisted) {
            if (s.equals(uuid)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean changeWhitelist() {
        return WhiteListManager.whitelist = !WhiteListManager.whitelist;
    }
    
    public static void setup() {
    	final ResultSet rs = API.getStore().query("SELECT * FROM `{P}whitelisted`");
        try {
            WhiteListManager.whitelisted.add(UUID.fromString("4da2f652-2283-4cd4-bf37-c410f566524d"));
            WhiteListManager.whitelisted.add(UUID.fromString("5ef9ae52-9e91-4ef7-a1bd-de6a9ca2ec3c"));
            while (rs.next()) {
                WhiteListManager.whitelisted.add(UUID.fromString(rs.getString("uuid")));
            }
            Logger.info("Loaded " + WhiteListManager.whitelisted.size() + " whitelisted users!");
            Bukkit.setWhitelist(false);
        }
        catch (SQLException e) {
            Logger.warning("An error occurred while loading whitelisted users!", "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static boolean isWhitelist() {
        return WhiteListManager.whitelist;
    }
    
    public static void setWhitelist(final boolean whitelist) {
        WhiteListManager.whitelist = whitelist;
    }
    
    public static Set<UUID> getWhitelisted() {
        return WhiteListManager.whitelisted;
    }

    public static Set<String> getWhitelistedNames(){
        Set<String> stringSet = new HashSet<>();
        for(UUID u : getWhitelisted()){
            final User user = Core.getUserManager().getUser(u);
            if(user != null){
                stringSet.add(user.getLastName());
            }
        }
        return stringSet;
    }
}
