package dev.sitek.trylevels.instance;

import dev.sitek.trylevels.TryLevels;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

import static dev.sitek.trylevels.util.TextUtil.colorize;

public class Bonus extends BukkitRunnable {

    private final TryLevels plugin = TryLevels.getInstance();

    private final Level level;
    private final int bonus;
    private final int seconds;

    private final UUID uuid;

    public Bonus(Level level, int bonus, int seconds) {
        this.level = level; this.bonus = bonus; this.seconds = seconds;
        this.uuid = level.getPlayer().getUniqueId();
    }

    public void start() {
        int minutes = seconds/60;
        Bukkit.getPlayer(uuid).sendTitle(colorize(String.format("&lPierscien Doswiadczenia &e+%s%%", bonus)),
                colorize(String.format("&7Aktywowano na czas: &3%d %s", minutes, minutes == 1 ? "minuty" : "minut")));
        level.addBonusExp(bonus);
        plugin.getLevelManager().getBonuses().add(uuid);
        runTaskLater(plugin, seconds * 20L);
    }

    @Override
    public void run() {
        Player player = level.getPlayer();

        if (player != null) {
            player.sendTitle(colorize("&lPierscien doswiadczenia"), colorize("&7Zakonczyl swoje dzialanie..."));
        }

        level.removeBonusExp(bonus);
        plugin.getLevelManager().getBonuses().remove(uuid);
    }

}
