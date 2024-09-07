package pl.blackwater.hardcore.managers;

import org.bukkit.entity.Player;
import pl.blackwater.hardcore.data.Combat;

import java.util.concurrent.ConcurrentHashMap;

public class CombatManager {
    private static final ConcurrentHashMap<Player, Combat> combats;

    static {
        combats = new ConcurrentHashMap<>();
    }

    public static Combat getCombat(Player p) {
        return CombatManager.combats.get(p);
    }

    public static void createCombat(Player p) {
        final Combat combat = new Combat(p);
        CombatManager.combats.put(p, combat);
    }

    public static void removeCombat(Player p) {
        CombatManager.combats.remove(p);
    }

    public static ConcurrentHashMap<Player, Combat> getCombats() {
        return CombatManager.combats;
    }
}