package pl.blackwater.hardcore.data;

import lombok.Data;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;

import java.util.List;

@Data
public class Effect {
    @NonNull private String key;
    @NonNull private List<PotionEffect> potionEffect;
    @NonNull private int cost;
    @NonNull private Material guiMaterial;
}
