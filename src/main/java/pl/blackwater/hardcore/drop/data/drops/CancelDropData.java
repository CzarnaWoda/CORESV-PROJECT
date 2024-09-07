package pl.blackwater.hardcore.drop.data.drops;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import pl.blackwater.hardcore.drop.data.DropData;
import pl.blackwater.hardcore.drop.data.DropType;
import pl.blackwaterapi.utils.Util;

public class CancelDropData implements DropData
{
    @Override
    public void breakBlock(final Block block, final Player player, final ItemStack item) {
        block.setType(Material.AIR);
        Util.sendMsg(player, "&4Blad: &cTego bloku nie mozesz wykopac!");
    }
    
    @Override
    public DropType getDropType() {
        return DropType.CANCEL_DROP;
    }
}
