package pl.blackwater.hardcore.events;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pl.blackwater.hardcore.guilds.data.Guild;

@EqualsAndHashCode(callSuper = true)
@Data
public class PlayerEnterGuildTerrainEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    @NonNull private Guild enteredGuild;
    @NonNull private Player whoEntered;
    @NonNull private Location enteredLocation;
    private boolean cancelled = false;

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }
    public void setCancelled(boolean cancelled){
        this.cancelled = cancelled;
    }
}
