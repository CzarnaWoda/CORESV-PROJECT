package pl.blackwater.hardcore.guilds.listeners;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.guilds.commands.user.InfoCommand;
import pl.blackwater.hardcore.guilds.data.Guild;
import pl.blackwater.hardcore.guilds.data.Member;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.ranking.RankingManager;
import pl.blackwaterapi.utils.TimeUtil;
import pl.blackwaterapi.utils.Util;

public class GuildActionListener  implements Colors, Listener {

    @EventHandler
    public void onChestOpen(PlayerInteractEvent e){
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getClickedBlock().getType().equals(Material.CHEST)) {
                final Guild guild = Core.getGuildManager().getGuild(e.getClickedBlock().getLocation());
                if(guild != null) {
                    final Member member = Core.getMemberManager().getMember(e.getPlayer());
                    if (!e.getPlayer().hasPermission("guild.admin.cuboidbypass") && member != null && member.getGuild().equals(guild)) {
                        if(!member.getPermissions().get("openChestPermission").isStatus()){
                            Util.sendMsg(e.getPlayer(), WarningColor + "Blad: " + WarningColor_2 + "Nie posiadasz uprawnien aby otworzyc skrzynke na terenie gildii");
                            e.setCancelled(true);
                        }
                    }
                }
            }else if(e.getClickedBlock().getType().equals(Material.FURNACE)){
                final Guild guild = Core.getGuildManager().getGuild(e.getClickedBlock().getLocation());
                if(guild != null) {
                    final Member member = Core.getMemberManager().getMember(e.getPlayer());
                    if (!e.getPlayer().hasPermission("guild.admin.cuboidbypass") && member != null && member.getGuild().equals(guild)) {
                        if(!member.getPermissions().get("openFurnacesPermission").isStatus()){
                            Util.sendMsg(e.getPlayer(), WarningColor + "Blad: " + WarningColor_2 + "Nie posiadasz uprawnien aby otworzyc piec na terenie gildii");
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak(BlockBreakEvent e){
        final Guild guild = Core.getGuildManager().getGuild(e.getBlock().getLocation());
        final Member member = Core.getMemberManager().getMember(e.getPlayer());
        if (guild != null) {
            if (!e.getPlayer().hasPermission("guild.admin.cuboidbypass") && member != null && member.getGuild().equals(guild)) {
                if(!member.getPermissions().get("destroyPermission").isStatus()){
                    Util.sendMsg(e.getPlayer(), WarningColor + "Blad: " + WarningColor_2 + "Nie posiadasz uprawnien aby niszczyc na terenie gildii");
                    e.setCancelled(true);
                    return;
                }
                if(e.getBlock() != null && e.getBlock().getType().equals(Material.TNT)){
                    if(!member.getPermissions().get("destroyObsidianPermission").isStatus()){
                        Util.sendMsg(e.getPlayer(), WarningColor + "Blad: " + WarningColor_2 + "Nie posiadasz uprawnien aby niszczyc obsydian na terenie gildii");
                        e.setCancelled(true);
                        return;
                    }
                }
            }
        }
        e.setCancelled(cancelAction(e.getPlayer(),e.getBlock().getLocation()));
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockPlace(BlockPlaceEvent e){
        final Guild guild = Core.getGuildManager().getGuild(e.getBlock().getLocation());
        final Member member = Core.getMemberManager().getMember(e.getPlayer());
        if (guild != null) {
            if (!e.getPlayer().hasPermission("guild.admin.cuboidbypass") && member != null && member.getGuild().equals(guild)) {
                if(!member.getPermissions().get("buildPermission").isStatus()){
                    Util.sendMsg(e.getPlayer(), WarningColor + "Blad: " + WarningColor_2 + "Nie posiadasz uprawnien aby budowac na terenie gildii");
                    e.setCancelled(true);
                    e.getPlayer().damage(0);
                    return;
                }
                if(e.getBlockPlaced() != null && e.getBlockPlaced().getType().equals(Material.TNT)){
                    if(!member.getPermissions().get("buildTnTPermission").isStatus()){
                        Util.sendMsg(e.getPlayer(), WarningColor + "Blad: " + WarningColor_2 + "Nie posiadasz uprawnien aby stawiac TNT na terenie gildii");
                        e.setCancelled(true);
                        e.getPlayer().damage(0);
                        return;
                    }
                }
                if(guild.getLastExplodeTime() > System.currentTimeMillis()) {
                    Util.sendMsg(e.getPlayer(), WarningColor + "Na terenie twojej gildii wybuchlo tnt! Budowac mozesz dopiero za " + WarningColor_2 + Util.secondsToString((int) ((guild.getLastExplodeTime() - System.currentTimeMillis()) / 1000L)));
                    e.setCancelled(true);
                    e.getPlayer().damage(0);
                    return;
                }
            }
        }
        if(e.getBlockPlaced().getType() == Material.FIRE){
            return;
        }
        e.setCancelled(cancelAction(e.getPlayer(),e.getBlock().getLocation()));
    }
    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent e){
        e.setCancelled(cancelAction(e.getPlayer(), e.getBlockClicked().getLocation()));
    }
    @EventHandler
    public void onPlayerBucketFill(PlayerBucketFillEvent e){
        e.setCancelled(cancelAction(e.getPlayer(), e.getBlockClicked().getLocation()));
    }
    private static boolean cancelAction(final Player player, final Location location){
        if(player.hasPermission("guild.admin.cuboidbypass")){
            return false;
        }
        final Guild guild = Core.getGuildManager().getGuild(location);
        if(guild == null){
            return false;
        }
        final Member member = Core.getMemberManager().getMember(player);
        if(member != null && member.getGuild().equals(guild)){
            if(guild.getCuboid().isInCentrum(location,3,1,2)){
                Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Nie mozesz budowac ani niszczyc w centrum gildii");
                return true;
            }
            return false;
        }
        Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Nie mozesz budowac oraz niszczyc na terenie tej gildii");
        return true;
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        final Player player = e.getPlayer();
        final Block b = e.getClickedBlock();
        if(e.isCancelled()){
            return;
        }
        if(b == null){
            return;
        }
        final Guild g = Core.getGuildManager().getGuild(b.getLocation());
        final Guild o = Core.getGuildManager().getGuild(player);

        if(b.getType() != Material.DRAGON_EGG){
            return;
        }
        if(g == null){
            return;
        }
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
            Util.sendMsg(player, Util.replaceString(SpecialSigns + "->>->>->>->> [" + MainColor + g.getTag() + SpecialSigns + "] " + WarningColor_2 + "- " + g.getName() + SpecialSigns + " <<-<<-<<-<<-"));
            Util.sendMsg(player, Util.replaceString(SpecialSigns + "->> " + WarningColor_2 + "Zalozyciel: " + MainColor + g.getGuildLider().getName()));
            Util.sendMsg(player, Util.replaceString(SpecialSigns + "->> " + WarningColor_2 + "Zastepca: " + MainColor + (g.getGuildPreLider() == null ? "Brak" : g.getGuildPreLider().getName())));
            Util.sendMsg(player, Util.replaceString(SpecialSigns + "->> " + WarningColor_2 + "Miejsce: " + MainColor + RankingManager.getPlaceGuild(g)));
            Util.sendMsg(player, Util.replaceString(SpecialSigns + "->> " + WarningColor_2 + "Punkty: " + MainColor + g.getPoints()));
            Util.sendMsg(player, Util.replaceString(SpecialSigns + "->> " + WarningColor_2 + "Zabojstwa: " + MainColor + g.getKills()));
            Util.sendMsg(player, Util.replaceString(SpecialSigns + "->> " + WarningColor_2 + "Smierci: " + MainColor + g.getDeaths()));
            Util.sendMsg(player, Util.replaceString(SpecialSigns + "->> " + WarningColor_2 + "Zycia: " + MainColor + g.getLives()));
            final int size = g.getCuboid().getSize() * 2 +1;
            Util.sendMsg(player, Util.replaceString(SpecialSigns + "->> " + WarningColor_2 + "Teren: " + MainColor + (size*size)));
            Util.sendMsg(player, Util.replaceString(SpecialSigns + "->> " + WarningColor_2 + "Utworzona: " + MainColor + Util.getDate(g.getCreateTime())));
            Util.sendMsg(player, Util.replaceString(SpecialSigns + "->> " + WarningColor_2 + "Ochrona tnt: " + MainColor + ((g.getCreateTime()+TimeUtil.HOUR.getTime(24)) > System.currentTimeMillis() ? Util.secondsToString((int)(g.getCreateTime()+TimeUtil.HOUR.getTime(24)/1000)) : WarningColor_2 + "Nie")));
            Util.sendMsg(player, Util.replaceString(SpecialSigns + "->> " + WarningColor_2 + "Wygasa: " + MainColor + Util.secondsToString((int)g.getExpireTime()/1000)));
            Util.sendMsg(player, Util.replaceString(SpecialSigns + "->> " + WarningColor_2 + "Atak: " + MainColor + (System.currentTimeMillis() > g.getLiveCool() ? ImportantColor + "mozliwy" : WarningColor_2 + "mozliwy za " + MainColor + Util.secondsToString((int)g.getLiveCool()/1000))));
            Util.sendMsg(player, Util.replaceString(SpecialSigns + "->> " + WarningColor_2 + "Pvp w gildii: " + MainColor + (g.isPvp() ? ImportantColor + "wlaczone" : WarningColor_2 + "wylaczone")));
            Util.sendMsg(player, Util.replaceString(SpecialSigns + "->> " + WarningColor_2 + "Czlonkowie gildii: " + MainColor + InfoCommand.getMembersToString(g)));
            Util.sendMsg(player, Util.replaceString(SpecialSigns + "->> " + WarningColor_2 + "Sojusze: " + MainColor + StringUtils.join(InfoCommand.getAlliancesList(g), ", ")));
            return;
        }
        e.setCancelled(true);
        final Member member = Core.getMemberManager().getMember(player);
        if(member != null && member.getGuild().equals(g)){
            return;
        }
        if(o == null){
            Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Aby podbic gildie musisz byc czlonkiem innej gildii");
            return;
        }
        if(Core.getAllianceManager().hasAlliance(g,o)){
            Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Nie mozesz podbijac sojuszniczej gildii");
            return;
        }
        final Location loc = g.getCuboid().getCenter();
        loc.setY(40.0);
        if (player.getLocation().distance(loc) > 3) {
            Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Musisz byc blizej centrum gildii!");
            return;
        }
        if(g.getLiveCool() > System.currentTimeMillis()){
            Util.sendMsg(player, WarningColor + "Blad: " + WarningColor_2 + "Ta gildie mozesz podbic za " + Util.secondsToString((int) (g.getLiveCool()/1000L)));
            return;
        }
        if (g.getLives() <= 1) {
            new BukkitRunnable() {
                public void run() {
                    Core.getGuildManager().removeGuild(g);
                }
            }.runTask(Core.getInstance());
            if (o.getLives() < 3) {
                o.setLives(o.getLives() + 1);
            }
            Bukkit.broadcastMessage(Util.fixColor(Util.replaceString(guildPrefix + "Gildia " + SpecialSigns + "[" + ImportantColor + g.getTag() +  SpecialSigns + "]" + ImportantColor + g.getName() + MainColor + " zostala zniszczona przez " + SpecialSigns + "[" + ImportantColor + o.getTag() + SpecialSigns + "]" + ImportantColor + o.getName())));
        }
        else {
            g.setLiveCool(System.currentTimeMillis() + TimeUtil.HOUR.getTime(24));
            g.setLives(g.getLives()-1);
            if (o.getLives() < 3) {
                o.setLives(o.getLives() + 1);
            }
            Bukkit.broadcastMessage(Util.fixColor(Util.replaceString(guildPrefix + "Gildia " + SpecialSigns + "[" + ImportantColor + g.getTag() +  SpecialSigns + "]" + ImportantColor + g.getName() + MainColor + " stracila 1 zycie przez " + SpecialSigns + "[" + ImportantColor + o.getTag() + SpecialSigns + "]" + ImportantColor + o.getName())));
        }
    }
}
