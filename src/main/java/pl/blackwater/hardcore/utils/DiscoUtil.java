package pl.blackwater.hardcore.utils;

import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.enums.DiscoType;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwaterapi.gui.actions.InventoryGUI;
import pl.blackwaterapi.utils.ItemBuilder;
import pl.blackwaterapi.utils.Util;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class DiscoUtil implements Colors {

    private static HashMap<Player, DiscoType> discoArmors = new HashMap<>();

    public static void setDisco(Player player, DiscoType type){
        if(discoArmors.get(player) == null){
            discoArmors.put(player, type);
        }else{
            discoArmors.remove(player);
        }
    }

    public static PacketPlayOutEntityEquipment[] getPackets(Player forP, ItemStack nmsItemStack){
        return new PacketPlayOutEntityEquipment[] {new PacketPlayOutEntityEquipment(forP.getEntityId(), 1, nmsItemStack),
        new PacketPlayOutEntityEquipment(forP.getEntityId(), 2, nmsItemStack),
        new PacketPlayOutEntityEquipment(forP.getEntityId(), 3, nmsItemStack),
        new PacketPlayOutEntityEquipment(forP.getEntityId(), 4, nmsItemStack)};
    }

    public static void sendPackets(Player toP, PacketPlayOutEntityEquipment... packets){
        for(PacketPlayOutEntityEquipment packet : packets){
            ((CraftPlayer)toP).getHandle().playerConnection.sendPacket(packet);
        }
    }



    public static void sendDiscoArmor(Player toP, Player forP, int color, boolean randomColor){
        if(toP != forP){
            if(randomColor){
                ItemBuilder boots = new ItemBuilder(Material.LEATHER_BOOTS, 1);
                boots.setColor(Color.fromRGB(ThreadLocalRandom.current().nextInt(color),ThreadLocalRandom.current().nextInt(color),ThreadLocalRandom.current().nextInt(color)));
                PacketPlayOutEntityEquipment eq1 = new PacketPlayOutEntityEquipment(forP.getEntityId(), 1, CraftItemStack.asNMSCopy(boots.build()));
                boots.setColor(Color.fromRGB(ThreadLocalRandom.current().nextInt(color),ThreadLocalRandom.current().nextInt(color),ThreadLocalRandom.current().nextInt(color)));
                PacketPlayOutEntityEquipment eq2 = new PacketPlayOutEntityEquipment(forP.getEntityId(), 2, CraftItemStack.asNMSCopy(boots.build()));
                boots.setColor(Color.fromRGB(ThreadLocalRandom.current().nextInt(color),ThreadLocalRandom.current().nextInt(color),ThreadLocalRandom.current().nextInt(color)));
                PacketPlayOutEntityEquipment eq3 = new PacketPlayOutEntityEquipment(forP.getEntityId(), 3, CraftItemStack.asNMSCopy(boots.build()));
                boots.setColor(Color.fromRGB(ThreadLocalRandom.current().nextInt(color),ThreadLocalRandom.current().nextInt(color),ThreadLocalRandom.current().nextInt(color)));
                PacketPlayOutEntityEquipment eq4 = new PacketPlayOutEntityEquipment(forP.getEntityId(), 4, CraftItemStack.asNMSCopy(boots.build()));
                sendPackets(toP, eq1,eq2,eq3,eq4);
                return;
            }
            ItemBuilder boots = new ItemBuilder(Material.LEATHER_BOOTS, 1);
            boots.setColor(Color.fromRGB(color, color, color));
            ItemStack craftItemStack = CraftItemStack.asNMSCopy(boots.build());
            sendPackets(toP, getPackets(forP, craftItemStack));
        }
    }

    public static void implementDiscoArmors(){
        new BukkitRunnable(){
            int smooth = 0;
            public void run(){
                for(Player player : discoArmors.keySet()){
                    if(discoArmors.get(player).equals(DiscoType.SMOOTH)){
                        for(Player toSend : Bukkit.getOnlinePlayers()){
                            if(!toSend.getName().equalsIgnoreCase(player.getName())){
                                sendDiscoArmor(toSend, player, smooth, false);
                            }
                        }
                    }
                    if(discoArmors.get(player).equals(DiscoType.RANDOM)){
                        for(Player toSend : Bukkit.getOnlinePlayers()){
                            if(!toSend.getName().equalsIgnoreCase(player.getName())){
                                sendDiscoArmor(toSend, player, 255, true);
                            }
                        }
                    }
                }
                smooth++;
                if(smooth >= 255){
                    smooth = 0;
                }
            }
        }.runTaskTimer(Core.getInstance(), 5L, 5L);
    }

    public InventoryGUI buildInventory(){
        InventoryGUI inventoryGUI = new InventoryGUI(Core.getInstance(), Util.fixColor(Util.replaceString(SpecialSigns + "->> " + MainColor + "Menu " + ImportantColor + "disco zbroji" + SpecialSigns + " <<-")), 5);

        return inventoryGUI;
    }
}
