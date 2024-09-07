package pl.blackwater.hardcore.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.blackwater.hardcore.enums.TabListType;
import pl.blackwater.hardcore.settings.TabListConfig;
import pl.blackwater.hardcore.tablist.AbstractTablist;
import pl.blackwaterapi.utils.Ticking;


public class TabUpdateTask extends BukkitRunnable {

	public static TabListType type;

	@Override
	public void run() {
		if(((int)Ticking.getResult()) > 14) {
			int index = type.index;
			if(index == TabListType.values().length){
				index = 1;
			}else{
				index++;
			}
			type = TabListType.getByIndex(index);
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(!AbstractTablist.hasTablist(p)){
					AbstractTablist.createTablist(TabListConfig.playerList,TabListConfig.TABLIST_HEADER,TabListConfig.TABLIST_FOOTER,9999,p);
				}
				AbstractTablist.getTablist(p).send();
			}
		}
	}
		
}
