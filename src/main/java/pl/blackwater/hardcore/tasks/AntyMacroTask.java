package pl.blackwater.hardcore.tasks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.managers.BanManager;
import pl.blackwater.hardcore.settings.CoreConfig;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwaterapi.utils.TitleUtil;
import pl.blackwaterapi.utils.Util;

import java.util.LinkedList;

public class AntyMacroTask {
	
    public static void getRate() {
    	Bukkit.getScheduler().scheduleSyncRepeatingTask(Core.getInstance(), new Runnable() {
        LinkedList<Double> ll = new LinkedList<>();
        double sum = 0.0;
		double total = 0.0;
		@Override
        public void run() {
            for (Player p : Bukkit.getOnlinePlayers()){
                if (Core.getAntyMacroManager().clickCount.containsKey(p.getUniqueId())) {
                    double count = Core.getAntyMacroManager().clickCount.get(p.getUniqueId());
                    if (count > CoreConfig.ANTYMACROMANAGER_CPSAMOUNT) {
                        if (Core.getAntyMacroManager().notifyCount.get(p) == null) {
                            Core.getAntyMacroManager().notifyCount.put(p, 1);
                        } else {
                            int i = Core.getAntyMacroManager().notifyCount.get(p);
                            i++;
                            Core.getAntyMacroManager().notifyCount.replace(p, i);
                            if (i == 5) {
                                TitleUtil.sendFullTitle(p, 10, 100, 5, Util.fixColor(Util.replaceString("&8&l>> &c&lAnty&2&lMacro &8&l<<")), "     &8&l* &7Wykryto zbyt duza ilosc CPS &8(&65 ostrzezenie &8) &8&l*");
                            }
                            if (i == 10) {
                                TitleUtil.sendFullTitle(p, 10, 100, 5, Util.fixColor(Util.replaceString("&8&l>> &c&lAnty&2&lMacro &8&l<<")), "     &8&l* &7Wykryto zbyt duza ilosc CPS &8(&610 ostrzezenie &8) &8&l*");
                            }
                            if (i >= 15) {
                                long time = Util.parseDateDiff("2h", true);
                                if (!p.hasPermission("core.ban.bypass")) {
                                    BanManager.createBan(p.getUniqueId(), "AntyMacro (Powyzej 15CPS)", "AntyMacro-Bot", time);
                                    Core.getAntyMacroManager().notifyCount.remove(p);
                                }
                            }
                        }
                        for (Player on : Bukkit.getOnlinePlayers()) {
                            if (on.hasPermission("core.admin.antymacro")) {
                                on.sendMessage(ChatColor.translateAlternateColorCodes('&', Util.replaceString(MessageConfig.MESSAGE_ANTYMACRO
                                        .replace("{ALERTCOUNT}", (Core.getAntyMacroManager().notifyCount.get(p) == null ? "0" : String.valueOf(Core.getAntyMacroManager().notifyCount.get(p))))
                                        .replace("{PLAYER}", p.getName()).replace("{CLICKS}", String.valueOf(count)))));
                            }
                        }
                    }
                    if (count > 0.0) {
                        ll.add(count);
                        for (double x : ll) {
                            x = count;
                            total = sum + x;
                        }
                    }
                    total = 0.0;
                    try {
                        ll.remove();
                    } catch (Exception ignored) {
                    }
                    Core.getAntyMacroManager().clickCount.put(p.getUniqueId(), 0);
                }
        }
		}
    }, 5L, 20L);
}

}
