package pl.blackwater.hardcore.data;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter
@Setter
public class Combat {
    private Player player;
    private long lastAttactTime;
    private Player lastAttactkPlayer;
    private long lastAsystTime;
    private Player lastAsystPlayer;

    public Combat(Player p) {
        this.player = p;
        this.lastAttactTime = 0L;
        this.lastAttactkPlayer = null;
        this.lastAsystPlayer = null;
        this.lastAsystTime = 0L;
    }
    public boolean hasFight() {
        return this.getLastAttactTime() > System.currentTimeMillis();
    }

    public boolean wasFight() {
        return this.getLastAttactkPlayer() != null;
    }
}
