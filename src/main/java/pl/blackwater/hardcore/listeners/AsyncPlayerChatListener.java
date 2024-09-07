package pl.blackwater.hardcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.blackwater.hardcore.Core;
import pl.blackwater.hardcore.commands.BackupCommand;
import pl.blackwater.hardcore.data.Rank;
import pl.blackwater.hardcore.data.User;
import pl.blackwater.hardcore.guilds.data.Member;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.inventories.BackupInventory;
import pl.blackwater.hardcore.inventories.ChatInventory;
import pl.blackwater.hardcore.managers.ChatManager;
import pl.blackwater.hardcore.settings.CoreConfig;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwater.hardcore.utils.StringUtil;
import pl.blackwaterapi.utils.ActionBarUtil;
import pl.blackwaterapi.utils.Util;

import java.util.regex.Pattern;

public class AsyncPlayerChatListener implements Listener, Colors{
    private static final Pattern URL_PATTERN = Pattern.compile("((?:(?:https?)://)?[\\w-_.]{2,})\\.([a-zA-Z]{2,3}(?:/\\S+)?)");
    private static final Pattern IPPATTERN = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    private static final Pattern BANNED_FLAMEWAR = Pattern.compile(".*(kurva|hui|gale|chuj|chuja|chujek|chuju|chujem|chujnia|chujowy|chujowa|chujowe|cipa|cipe|cipie|dojebac|dojebal|dojebala|dojebalem|dojebalam|dojebie|dopieprzac|dopierdalac|dopierdala|dopierdalal|dopierdalala|dopierdolil|dopierdole|dopierdoli|dopierdalajacy|dopierdolic|dupa|dupie|dupcia|dupeczka|dupy|dupe|huj|hujek|hujnia|huja|huje|hujem|huju|jebac|jebak|jebaka|jebal|jebany|jebane|jebanka|jebanko|jebankiem|jebanym|jebanej|jebana|jebani|jebanych|jebanymi|jebcie|jebiacy|jebiaca|jebiacego|jebiacej|jebia|jebie|jebliwy|jebnac|jebnal|jebna|jebnela|jebnie|jebnij|jebut|koorwa|korwa|kurestwo|kurew|kurewskiej|kurewska|kurewsko|kurewstwo|kurwa|kurwaa|kurwe|kurwie|kurwiska|kurwo|kurwy|kurwach|kurwami|kurewski|kurwiarz|kurwi\u0105cy|kurwica|kurwic|kurwido\u0142ek|kurwik|kurwiki|kurwiszcze|kurwiszon|kurwiszona|kurwiszonem|kurwiszony|kutas|kutasa|kutasie|kutasem|kutasy|kutasow|kutasach|kutasami|matkojebcy|matkojebca|matkojebcami|matkojebcach|najebac|najebal|najebane|najebany|najebana|najebie|najebia|naopierdalac|naopierdalal|naopierdalala|napierdalac|napierdalajacy|napierdolic|nawpierdalac|nawpierdalal|nawpierdalala|obsrywac|obsrywajacy|odpieprzac|odpieprzy|odpieprzyl|odpieprzyla|odpierdalac|odpierdol|odpierdolil|odpierdolila|odpierdalajacy|odpierdalajaca|odpierdolic|odpierdoli|opieprzaj\u0105cy|opierdalac|opierdala|opierdalajacy|opierdol|opierdolic|opierdoli|opierdola|piczka|pieprzniety|pieprzony|pierdel|pierdlu|pierdolacy|pierdolaca|pierdol|pierdole|pierdolenie|pierdoleniem|pierdoleniu|pierdolec|pierdola|pierdolicie|pierdolic|pierdolil|pierdolila|pierdoli|pierdolniety|pierdolisz|pierdolnac|pierdolnal|pierdolnela|pierdolnie|pierdolnij|pierdolnik|pierdolona|pierdolone|pierdolony|pierdz\u0105cy|pierdziec|pizda|pizde|pizdzie|pizdnac|pizdu|podpierdalac|podpierdala|podpierdalajacypodpierdolic|podpierdoli|pojeb|pojeba|pojebami|pojebanego|pojebanemu|pojebani|pojebany|pojebanych|pojebanym|pojebanymi|pojebem|pojebac|pojebalo|popierdala|popierdalac|popierdolic|popierdoli|popierdolonego|popierdolonemu|popierdolonym|popierdolone|popierdoleni|popierdolony|porozpierdala|porozpierdalac|poruchac|przejebane|przejebac|przyjebali|przepierdalac|przepierdala|przepierdalajacy|przepierdalajaca|przepierdolic|przyjebac|przyjebie|przyjebala|przyjebal|przypieprzac|przypieprzajacy|przypieprzajaca|przypierdalac|przypierdala|przypierdoli|przypierdalajacy|przypierdolic|qrwa|rozjebac|rozjebie|rozjeba\u0142a|rozpierdalac|rozpierdala|rozpierdolic|rozpierdole|rozpierdoli|rozpierducha|skurwiel|skurwiela|skurwielem|skurwielu|skurwysyn|skurwysynow|skurwysyna|skurwysynem|skurwysynu|skurwysyny|skurwysynski|skurwysynstwo|spieprzac|spieprza|spieprzaj|spieprzajcie|spieprzaja|spieprzajacy|spieprzajaca|spierdalac|spierdala|spierdalal|spierdalalcie|spierdalala|spierdalajacy|spierdolic|spierdoli|spierdol\u0105|spierdola|srac|srajacy|srajac|sraj|sukinsyn|sukinsyny|sukinsynom|sukinsynowi|sukinsynow|ujebac|ujebal|ujebana|ujebany|ujebie|ujeba\u0142a|ujebala|upierdalac|upierdala|upierdolic|upierdoli|upierdola|upierdoleni|wjebac|wjebie|wjebia|wjebiemy|wjebiecie|wkurwiac|wkurwial|wkurwiajacy|wkurwiajaca|wkurwi|wkurwiali|wkurwimy|wkurwicie|wkurwic|wpierdalac|wpierdalajacy|wpierdol|wpierdolic|wpizdu|wyjebali|wyjebac|wyjebia|wyjebiesz|wyjebie|wyjebiecie|wyjebiemy|wypieprzac|wypieprza|wypieprzal|wypieprzala|wypieprzy|wypieprzyla|wypieprzyl|wypierdal|wypierdalac|wypierdala|wypierdalaj|wypierdalal|wypierdalala|wypierdolic|wypierdoli|wypierdolimy|wypierdolicie|wypierdola|wypierdolili|wypierdolil|wypierdolila|zajebac|zajebie|zajebia|zajebial|zajebiala|zajebali|zajebana|zajebani|zajebane|zajebany|zajebanych|zajebanym|zajebanymi|zajebiste|zajebisty|zajebistych|zajebista|zajebistym|zajebistymi|zajebiscie|zapieprzyc|zapieprzy|zapieprzyl|zapieprzyla|zapieprza|zapieprz|zapieprzymy|zapieprzycie|zapieprzysz|zapierdala|zapierdalac|zapierdalaja|zapierdalaj|zapierdalajcie|zapierdalala|zapierdalali|zapierdalajacy|zapierdolic|zapierdoli|zapierdolil|zapierdolila|zapierdola|zapierniczac|zapierniczajacy|zasrac|zasranym|zasrywajacy|zesrywac|zesrywajac|zjebac|zjebal|zjebala|zjebana|zjebia|zjebali|zjeby).*");
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
        if(e.isCancelled())
            return;
        final ChatManager manager = Core.getChatManager();
        Player p = e.getPlayer();
        if(!manager.isChat() && !p.hasPermission("core.chat.lock.bypass")) {
            Util.sendMsg(p, Util.fixColor(WarningColor + "Chat na serwerze jest aktualnie " + WarningColor_2 + "wylaczony"));
            e.setCancelled(true);
            return;
        }
        if(manager.isVipChat() && !p.hasPermission("core.chat.vip")) {
            Util.sendMsg(p, Util.fixColor(WarningColor + "Chat na serwerze jest aktualnie dostepny tylko dla rang " + WarningColor_2 + "PREMIUM"));
            e.setCancelled(true);
            return;
        }
        if(ChatInventory.getChangeKillsLimit().contains(p)) {
            if(e.getMessage().equalsIgnoreCase("cancel")) {
                ChatInventory.getChangeKillsLimit().remove(p);
                e.setCancelled(true);
                ActionBarUtil.sendActionBar(p, Util.fixColor(Util.replaceString(WarningColor + "Anulowano ustawienie nowego limitu")));
            }else
            if(Util.isInteger(e.getMessage())) {
                ChatInventory.getChangeKillsLimit().remove(p);
                final int i = Integer.parseInt(e.getMessage());
                final CoreConfig config = new CoreConfig();
                CoreConfig.CHATMANAGER_POINTS = i;
                config.setField("chatmanager.kills", i);
                ChatInventory.openMenu(p);
                Bukkit.broadcastMessage(Util.fixColor(Util.replaceString(SpecialSigns + ">> " + MainColor + "Administrator " + ImportantColor + p.getName() + MainColor + " ustawil nowy limit zabojstw do pisania na chacie " + SpecialSigns + "(" + WarningColor + i + SpecialSigns + ")")));
                e.setCancelled(true);
            }
        }
        if(BackupCommand.getFindPlayer().contains(p)){
            BackupCommand.getFindPlayer().remove(p);
            if(e.getMessage().equalsIgnoreCase("cancel")) {
                e.setCancelled(true);
                ActionBarUtil.sendActionBar(p, Util.fixColor(Util.replaceString(WarningColor + "Anulowano szukanie gracza")));
            }else{
                Player o = Bukkit.getPlayer(e.getMessage());
                if(o == null){
                    ActionBarUtil.sendActionBar(p, Util.fixColor(Util.replaceString(MessageConfig.MESSAGE_COMMAND_UNKNOWNPLAYER)));
                }else{
                    BackupInventory.openPlayerMenu(p, o);
                }
            }
        }
        User u = Core.getUserManager().getUser(p);
        if(u.getPoints() < CoreConfig.CHATMANAGER_POINTS && !p.hasPermission("core.chat.points.bypass")) {
            Util.sendMsg(p, Util.fixColor(WarningColor + "Blad: " + WarningColor_2 + "Nie posiadasz wystarczajacej liczby punktów aby pisac na chacie " + SpecialSigns + "(" + WarningColor + CoreConfig.CHATMANAGER_POINTS + SpecialSigns + ")"));
            e.setCancelled(true);
            return;
        }
        if(!p.hasPermission("core.chat.bypass") && (URL_PATTERN.matcher(e.getMessage()).find() || IPPATTERN.matcher(e.getMessage()).find())) {
            Util.sendMsg(p, Util.fixColor(WarningColor + "Blad: " + WarningColor_2 + "Twoja wiadomosc zawiera niedozwolone tresci! " + SpecialSigns + "(" + WarningColor + "REKLAMA" + SpecialSigns + ")"));
            e.setCancelled(true);
            return;
        }
        if(!p.hasPermission("core.chat.bypass") && (BANNED_FLAMEWAR.matcher(e.getMessage()).find())) {
            Util.sendMsg(p, Util.fixColor(WarningColor + "Blad: " + WarningColor_2 + "Twoja wiadomosc zawiera niedozwolone tresci! " + SpecialSigns + "(" + WarningColor + "OBRAZA" + SpecialSigns + ")"));
            e.setCancelled(true);
            return;
        }
        if(!manager.canSendMessage(p)) {
            final int elapsed = (int) ((System.currentTimeMillis() - manager.getTimes().get(p.getUniqueId())) / 1000L);
            Util.sendMsg(p, Util.fixColor(WarningColor + "Blad: " + WarningColor_2 + "Na chacie bedziesz mogl napisac dopiero za: " + elapsed + "s"));
            e.setCancelled(true);
            return;
        }
        final Member m = Core.getMemberManager().getMember(u);
        Rank rank = u.getRank();
        String globalFormat = Util.fixColor(CoreConfig.CHATMANAGER_FORMAT_GLOBAL
                .replace("{POINTS}", String.valueOf(u.getPoints()))
                .replace("{KILLS}", String.valueOf(u.getKills()))
                .replace("{PREFIX}", rank.getPrefix())
                .replace("{SUFFIX}", rank.getSuffix())
                .replace("{PLAYER}", (!p.hasPermission("core.chat.colornick") ? "%1$s" : StringUtil.coloredString(p.getName())))
                .replace("{MESSAGE}", "%2$s"))
                .replace("{TAG}", (m == null ? "" : Util.fixColor(Util.replaceString(CoreConfig.CHATMANAGER_FORMAT_TAG.replace("{TAG}", m.getGuild().getTag())))))
                .replace("{LVL}", String.valueOf(u.getLevel()));
        String aglobalFormat = Util.fixColor(CoreConfig.CHATMANAGER_FORMAT_AGLOBAL
                .replace("{POINTS}", String.valueOf(u.getPoints()))
                .replace("{KILLS}", String.valueOf(u.getKills()))
                .replace("{PREFIX}", rank.getPrefix())
                .replace("{SUFFIX}", rank.getSuffix())
                .replace("{PLAYER}", "%1$s")
                .replace("{MESSAGE}", "%2$s"))
                .replace("{TAG}", (m == null ? "" : Util.fixColor(Util.replaceString(CoreConfig.CHATMANAGER_FORMAT_TAG.replace("{TAG}", m.getGuild().getTag())))))
                .replace("{LVL}", String.valueOf(u.getLevel()));
        if(p.hasPermission("core.chat.nopoints"))
            e.setFormat(aglobalFormat);
        else
            e.setFormat(globalFormat);
        manager.getTimes().put(p.getUniqueId(), System.currentTimeMillis());
        if (!p.hasPermission("core.chat.colors")) {
            e.setMessage(ChatColor.stripColor(Util.fixColor(e.getMessage().replaceAll("&", "" + ChatColor.COLOR_CHAR))));
        }
    }
}
