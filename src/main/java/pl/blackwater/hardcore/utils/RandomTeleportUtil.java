package pl.blackwater.hardcore.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.settings.CoreConfig;
import pl.blackwaterapi.utils.ItemUtil;
import pl.blackwaterapi.utils.RandomUtil;
import pl.blackwaterapi.utils.Util;

import java.util.Collections;
import java.util.List;

public class RandomTeleportUtil  implements Colors {

    public static boolean RandomTeleport(Player player){
        final int x = RandomUtil.getRandInt(-CoreConfig.BORDERMANAGER_BORDER_X,CoreConfig.BORDERMANAGER_BORDER_X);
        final int z = RandomUtil.getRandInt(-CoreConfig.BORDERMANAGER_BORDER_Z,CoreConfig.BORDERMANAGER_BORDER_Z);
        final Location loc = new Location(player.getWorld(),x,64,z);
        loc.setY(loc.getWorld().getHighestBlockYAt(loc) + 1.5f);
        final Biome biome = loc.getBlock().getBiome();
        if(biome == Biome.OCEAN || biome == Biome.DEEP_OCEAN || Core.getGuildManager().getGuild(loc) != null){
            return RandomTeleport(player);
        }
        player.teleport(loc, PlayerTeleportEvent.TeleportCause.PLUGIN);
        ItemUtil.giveItems(Collections.singletonList(new ItemStack(Material.COOKED_BEEF,64)),player);
        player.updateInventory();
        Util.sendMsg(player,Util.replaceString(ImportantColor + "&lSOLO-RANDOMTP " + SpecialSigns + "->> " + MainColor + "Zostales przeteleportowany na " + ImportantColor + "&nLOSOWE KORDYNATY"));
        return true;
    }
    public static boolean RandomTeleport(List<Player> players){
       int x = RandomUtil.getRandInt(-CoreConfig.BORDERMANAGER_BORDER_X,CoreConfig.BORDERMANAGER_BORDER_X);
        int z = RandomUtil.getRandInt(-CoreConfig.BORDERMANAGER_BORDER_Z,CoreConfig.BORDERMANAGER_BORDER_Z);
        Location loc = new Location(Bukkit.getWorlds().get(0),x,64,z);
        loc.setY(loc.getWorld().getHighestBlockYAt(loc) + 1.5f);
        Biome biome = loc.getBlock().getBiome();
        while(biome == Biome.OCEAN || biome == Biome.DEEP_OCEAN || Core.getGuildManager().getGuild(loc) != null){
            x = RandomUtil.getRandInt(-CoreConfig.BORDERMANAGER_BORDER_X,CoreConfig.BORDERMANAGER_BORDER_X);
            z = RandomUtil.getRandInt(-CoreConfig.BORDERMANAGER_BORDER_Z,CoreConfig.BORDERMANAGER_BORDER_Z);
            loc = new Location(Bukkit.getWorlds().get(0),x,64,z);
            loc.setY(loc.getWorld().getHighestBlockYAt(loc) + 1.5f);
            biome = loc.getBlock().getBiome();
        }
        for(Player player : players){
            player.teleport(loc, PlayerTeleportEvent.TeleportCause.PLUGIN);
            Util.sendMsg(player,Util.replaceString(ImportantColor + "&lGROUP-RANDOMTP " + SpecialSigns + "->> " + MainColor + "Zostales przeteleportowany na " + ImportantColor + "&nLOSOWE KORDYNATY"));
        }
        return true;
    }
}
