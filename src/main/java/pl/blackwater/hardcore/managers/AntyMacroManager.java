package pl.blackwater.hardcore.managers;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class AntyMacroManager {

    @Getter
    public HashMap<UUID, Integer> clickCount;
    @Getter
    public HashMap<Player, Integer> notifyCount;

    public AntyMacroManager(){
        clickCount = new HashMap<>();
        notifyCount = new HashMap<>();
    }
}
