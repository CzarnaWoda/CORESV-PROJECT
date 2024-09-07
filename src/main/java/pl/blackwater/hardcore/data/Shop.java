package pl.blackwater.hardcore.data;

import lombok.Data;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

@Data
public class Shop {
    @NonNull private String key;
    @NonNull private int coins;
    @NonNull private ItemStack item;
}
