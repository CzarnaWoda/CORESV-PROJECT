package pl.blackwater.hardcore.managers;

import lombok.Data;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.blackwater.hardcore.interfaces.Colors;
import pl.blackwater.hardcore.settings.CoreConfig;
import pl.blackwater.hardcore.settings.MessageConfig;
import pl.blackwaterapi.utils.TimeUtil;
import pl.blackwaterapi.utils.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
public class ChatManager implements Colors
{
	private boolean chat;
	private boolean vipChat;
	private Map<UUID, Long> times;
	public ChatManager(){
		chat = true;
		vipChat = false;
		times = new HashMap<>();
	}
	
	public final void clearChat(final String sender)
	{
		for(Player p : Bukkit.getOnlinePlayers())
		{
			for(int i = 0; i < 100; i++) 
				p.sendMessage("");
			Util.sendMsg(p, Util.replaceString("                " + MessageConfig.SERVERNAME_TAG + "                "));
			Util.sendMsg(p, "");
			Util.sendMsg(p, Util.replaceString(SpecialSigns + "           >> " + MainColor + "Czat zostal " + ImportantColor + UnderLined + "wyczyszczony" + MainColor + "!" ));
			Util.sendMsg(p, Util.replaceString(SpecialSigns + "           >> " + MainColor + "Przez " + ImportantColor + BOLD + sender));
			Util.sendMsg(p, "");
			Util.sendMsg(p, "");
		}
	}
	
	public final void toggleChat(final String sender)
	{
		setChat(!isChat());
		for(Player p : Bukkit.getOnlinePlayers())
		{
			for(int i = 0; i < 100; i++) 
				p.sendMessage("");
			Util.sendMsg(p, Util.replaceString("                " + MessageConfig.SERVERNAME_TAG + "                "));
			Util.sendMsg(p, "");
			Util.sendMsg(p, Util.replaceString(SpecialSigns + "           >> " + MainColor + "Czat zostal " + (isChat() ? ChatColor.GREEN + "wlaczony" : ChatColor.DARK_RED + "wylaczony") + MainColor + "!" ));
			Util.sendMsg(p, Util.replaceString(SpecialSigns + "           >> " + MainColor + "Przez " + ImportantColor + BOLD + sender));
			Util.sendMsg(p, "");
			Util.sendMsg(p, "");
		}
	}
	
	public final void toggleVipChat(final String sender)
	{
		setVipChat(!isVipChat());
		for(Player p : Bukkit.getOnlinePlayers())
		{
			for(int i = 0; i < 100; i++) 
				p.sendMessage("");
			Util.sendMsg(p, Util.replaceString("                " + MessageConfig.SERVERNAME_TAG + "                "));
			Util.sendMsg(p, "");
			Util.sendMsg(p, Util.replaceString(SpecialSigns + "           >> " + MainColor + "Czat tylko dla " + ChatColor.GOLD + ChatColor.UNDERLINE + "VIP" + MainColor + " zostal " + (isVipChat() ? ChatColor.GREEN + "wlaczony" : ChatColor.DARK_RED + "wylaczony") + MainColor + "!" ));
			Util.sendMsg(p, Util.replaceString(SpecialSigns + "           >> " + MainColor + "Przez " + ImportantColor + BOLD + sender));
			Util.sendMsg(p, "");
			Util.sendMsg(p, "");
		}
	}
	
    public boolean canSendMessage(final Player p) {
        if (p.hasPermission("core.chat.slow.bypass")) {
            return true;
        }
        Long time = times.get(p.getUniqueId());
        return time == null || System.currentTimeMillis() - time >= TimeUtil.SECOND.getTime(CoreConfig.CHATMANAGER_SLOWMODE);
    }
    public final void clearPlayerChat(final Player p, final int amount) {
    	for(int i = 0;i < amount; i ++)
    		p.sendMessage("");
    }
}
