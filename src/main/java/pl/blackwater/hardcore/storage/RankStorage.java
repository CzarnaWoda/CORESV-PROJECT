package pl.blackwater.hardcore.storage;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.Rank;
import pl.blackwater.hardcore.utils.CoreUtil;
import pl.blackwaterapi.configs.ConfigCreator;

import java.util.List;

public class RankStorage extends ConfigCreator {

    public static String DEFAULT_RANK;

    public RankStorage() {
        super("ranks.yml", "Ranks and permissions", Core.getInstance());
        DEFAULT_RANK = getConfig().getString("rank.default");
    }
    public void loadRanks(){
        FileConfiguration config = getConfig();

        for(String key : config.getConfigurationSection("ranks").getKeys(false)){
            ConfigurationSection section = getConfigurationSection("ranks." + key);

            final List<String> permissions = section.getStringList("permissions");
            final String prefix = section.getString("prefix");
            final String suffix = section.getString("suffix");
            Core.getRankManager().getRanks().put(key, new Rank(key, prefix, suffix, permissions));
        }
        CoreUtil.sendConsoleMessage("Loaded " + Core.getRankManager().getRanks().size() + " ranks!");
    }
}
