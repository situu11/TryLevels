package dev.sitek.trylevels.listener;

import dev.sitek.trylevels.TryLevels;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerConnectListner implements Listener {

    private final TryLevels plugin = TryLevels.getInstance();

    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
        final Player player = e.getPlayer(); final UUID uuid = player.getUniqueId();
        plugin.getLevelManager().setupLevel(uuid).setPlayer(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        final Player player = e.getPlayer(); final UUID uuid = player.getUniqueId();

        plugin.getLevelManager().getLevel(uuid).getFileManager().saveConfig();

        if (!plugin.getLevelManager().getBonuses().contains(uuid)) {
            plugin.getLevelManager().getLevels().remove(uuid);
        }
    }
}
