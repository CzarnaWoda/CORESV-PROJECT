package pl.blackwater.hardcore.guilds.data;

import lombok.Data;
import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import pl.blackwater.hardcore.guilds.settings.GuildConfig;

@Data
public class Cuboid {
    @NonNull private World world;
    @NonNull private int centerX;
    @NonNull private int centerZ;
    @NonNull private int size;

    public Location getCenter(){
        return new Location(getWorld(), getCenterX(),60.0,getCenterZ());
    }

    public boolean addSize(){
        if(getSize() >= GuildConfig.CUBOID_SIZE_MAX){
            return false;
        }
        this.size += GuildConfig.CUBOID_SIZE_ADD;
        return true;
    }
    public boolean isInCuboid(Location loc){
        if(!loc.getWorld().equals(getWorld())){
            return false;
        }
        final int distanceX = Math.abs(loc.getBlockX() - getCenterX());
        final int distanceZ = Math.abs(loc.getBlockZ() - getCenterZ());
        return distanceX <= getSize() && distanceZ <= getSize();
    }
    public boolean isInCentrum(final Location loc, final int top, final int down, final int wall) {
        final Location c = getLocation().clone();
        return c.getBlockY() - down <= loc.getBlockY() && c.getBlockY() + top >= loc.getBlockY() && loc.getBlockX() <= c.getBlockX() + wall && loc.getBlockX() >= c.getBlockX() - wall && loc.getBlockZ() <= c.getBlockZ() + wall && loc.getBlockZ() >= c.getBlockZ() - wall;
    }
    public Location getLocation() {
        return new Location(world, this.getCenterX(), 40.0, this.getCenterZ());
    }

    public boolean isInCuboid(Player player){
        return isInCuboid(player.getLocation());
    }
    public boolean isInCuboid(Block block){
        return isInCuboid(block.getLocation());
    }
}
