package pl.blackwater.hardcore.data;

import lombok.Data;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Data
public class Kit {

    @NonNull public String id;
    @NonNull public List<ItemStack> items;
    @NonNull public long time;
    @NonNull public boolean enable;
    @NonNull public int timeKey;
    @NonNull Material menuItem;
}
