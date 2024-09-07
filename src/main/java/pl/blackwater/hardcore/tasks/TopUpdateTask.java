package pl.blackwater.hardcore.tasks;

import org.bukkit.scheduler.BukkitRunnable;
import pl.blackwater.hardcore.ranking.RankingManager;

public class TopUpdateTask extends BukkitRunnable {
	
	@Override
	public void run() {
		RankingManager.sortGuildRankings();
		RankingManager.sortUserRankings();
	}

}
