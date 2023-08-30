package dev.sitek.trylevels.manager;

import dev.sitek.trylevels.TryLevels;
import dev.sitek.trylevels.instance.Level;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static dev.sitek.trylevels.util.TextUtil.*;

public class LevelManager {

    private final TryLevels plugin = TryLevels.getInstance();

    private final Map<UUID, Level> levels = new ConcurrentHashMap<>();
    private final List<UUID> bonuses = new ArrayList<>();

    private double serverExp = 0;

    public LevelManager() { plugin.getDataFolder().mkdir(); }

    public Level setupLevel(UUID uuid) {
        Level level = getLevel(uuid);
        if (level == null) {
            level = Level.newInstance(uuid); levels.put(uuid, level);
        }
        return level;
    }

    public void temporaryServerBonusExp(int bonus, int seconds) {
        this.serverExp = bonus;
        int minutes = seconds / 60;

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendTitle(
                    colorize(String.format("&lBonus Serwerowy &e+%s%%", bonus)),
                    colorize(String.format("&7Aktywowano na czas: &3%d %s", minutes, minutes == 1 ? "minuty" : "minut")));
        }

        Bukkit.getScheduler().runTaskLater(plugin, () -> {

            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendTitle(colorize("&lBonus Serwerowy"), colorize("&7Zakonczyl swoje dzialanie..."));
            }

            this.serverExp = 0;
        }, seconds * 20L);
    }

    public Level getLevel(UUID uuid) { return levels.get(uuid); }
    public Map<UUID, Level> getLevels() { return levels; }
    public List<UUID> getBonuses() { return bonuses; }
    public double getServerExp() { return serverExp; }
}
