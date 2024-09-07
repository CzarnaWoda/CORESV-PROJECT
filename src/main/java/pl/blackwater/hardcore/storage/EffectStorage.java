package pl.blackwater.hardcore.storage;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.data.Effect;
import pl.blackwaterapi.configs.ConfigCreator;
import pl.blackwaterapi.utils.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class EffectStorage extends ConfigCreator {

    private HashMap<String, Effect> effects;
    public EffectStorage() {
        super("effect.yml", "EffectStorage", Core.getInstance());

        effects = loadEffects();
    }
    private HashMap<String,Effect> loadEffects(){
        final FileConfiguration config = getConfig();
        final HashMap<String, Effect> effects = new HashMap<>();

        for(String key : config.getConfigurationSection("effects").getKeys(false)){
            final ConfigurationSection section = config.getConfigurationSection("effects." + key);

            final List<PotionEffect> potionEffect = convertPotionEffect(section.getString("effect"));
            final int cost = section.getInt("cost");
            final Material guiMaterial = Material.getMaterial(section.getString("guiMaterial"));

            effects.put(key, new Effect(key,potionEffect,cost,guiMaterial));
        }
        return effects;
    }
    //potioneffect-duration-amplifer
    public List<PotionEffect> convertPotionEffect(String potionEffect){
        final List<PotionEffect> potionEffects = new ArrayList<>();
        for(String potion : potionEffect.split(";")) {
            String[] array = potionEffect.split("-");
            final PotionEffectType potionEffectType = PotionEffectType.getByName(array[0]);
            final int seconds = TimeUtil.SECOND.getTick(Integer.parseInt(array[1]));
            final int amplifer = Integer.parseInt(array[2]);
            potionEffects.add(new PotionEffect(potionEffectType, seconds, amplifer));
        }
        return potionEffects;
    }
}
