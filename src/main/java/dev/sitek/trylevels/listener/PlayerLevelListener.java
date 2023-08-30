package dev.sitek.trylevels.listener;

import dev.sitek.trylevels.TryLevels;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.UUID;

public class PlayerLevelListener implements Listener {

    private final TryLevels plugin = TryLevels.getInstance();

    @EventHandler
    public void noKill(EntityDeathEvent e) {
        if (e.getEntity().getKiller() != null) {
            final UUID uuid = e.getEntity().getKiller().getUniqueId();
            plugin.getLevelManager().getLevel(uuid).addExp(50);
        }
    }
}
