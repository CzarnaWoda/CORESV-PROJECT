package pl.blackwater.hardcore.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import pl.blackwater.hardcore.Core;
import pl.blackwaterapi.utils.RandomUtil;

import java.util.ArrayList;
import java.util.List;

public class LocationUtil
{
  public static Location convertStringToBlockLocation(final String string)
  {
    if (string != null)
    {
      final String[] wxyz = string.split(",");
      final World w = Bukkit.getWorld(wxyz[0]);
      final int x = Integer.parseInt(wxyz[1]);
      final int y = Integer.parseInt(wxyz[2]);
      final int z = Integer.parseInt(wxyz[3]);
      return new Location(w, x, y, z);
    }
    return null;
  }
  
  public static Location convertStringToLocation(final String string)
  {
    if (string == null) {
      return null;
    }
    final String[] wxyzPitchYaw = string.split(",");
    final World w = Bukkit.getWorld(wxyzPitchYaw[0]);
    final double x = Double.parseDouble(wxyzPitchYaw[1]);
    final double y = Double.parseDouble(wxyzPitchYaw[2]);
    final double z = Double.parseDouble(wxyzPitchYaw[3]);
    final int pitch = Integer.parseInt(wxyzPitchYaw[4]);
    final int yaw = Integer.parseInt(wxyzPitchYaw[5]);
    final Location location = new Location(w, x, y, z);
    location.setPitch(pitch);
    location.setYaw(yaw);
    return location;
  }
  
  public static String convertLocationBlockToString(final Location location)
  {
    String world = location.getWorld().getName();
    int x = location.getBlockX();
    int y = location.getBlockY();
    int z = location.getBlockZ();
    return world + "," + x + "," + y + "," + z;
  }
  
  public static String convertLocationToString(final Location location)
  {
	final String world = location.getWorld().getName();
	final double x = location.getX();
	final double y = location.getY();
	final double z = location.getZ();
	final int pitch = (int)location.getPitch();
	final int yaw = (int)location.getYaw();
    return world + "," + x + "," + y + "," + z + "," + pitch + "," + yaw;
  }
  
  public static void TeleportToRandomLocationFromLocationList(final Player player, final List<Location> locationList, final int delay) {
	  Location randomLocation;
	  if(locationList.size() > 1) {
		  randomLocation = locationList.get(RandomUtil.getRandInt(0, locationList.size() - 1));
	  }else
		  randomLocation = locationList.get(0);
	  if(randomLocation != null)
		  TeleportToLocationWithDelay(player, randomLocation, 10L);
  }
  
  public static void TeleportToLocationWithDelay(final Player player, final Location location, final long delay) {
	  Bukkit.getScheduler().runTaskLater(Core.getInstance(), () -> player.teleport(location), delay);
  }
    public static List<Player> getPlayersInRadius(final Location location, final int size) {
        final List<Player> players = new ArrayList<>();
        for (final Player p : location.getWorld().getPlayers()) {
            if (location.distance(p.getLocation()) <= size) {
                players.add(p);
            }
        }
        return players;
    }
}
