package dev.sitek.trylevels.instance;

import dev.sitek.trylevels.TryLevels;
import dev.sitek.trylevels.manager.file.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

import static dev.sitek.trylevels.util.TextUtil.*;

public class Level {

    private final TryLevels plugin = TryLevels.getInstance();

    private final FileManager fileManager;

    private Player player;
    private String name;
    private int level = 1;
    private double exp = 0;
    private double requiredExp = 100;
    private double bonusExp = 0;

    private Level(UUID uuid) {
        fileManager = new FileManager(this, uuid);
        fileManager.setupPlayer();
    }

    /* LEVEL */

    public void addExp(double amount) {
        amount += amount * ((plugin.getLevelManager().getServerExp() + getBonusExp()) / 100d); exp += amount;

        player.sendActionBar(colorize("&lEXP &7+" + format(amount) + " &8(&e" + format(getPercent()) + "%&8)"));

        if (exp >= requiredExp) levelUp();
    }

    private void levelUp() {
        setLevel(getLevel() + 1); setExp(0);
        this.requiredExp *= getLevel() % 10 == 0 ? 1.3 : 1.1;
        player.sendTitle(colorize("&lWyzszy Poziom!"), colorize("&7Awansowales na &e" + getLevel() + " &7poziom"));

        if (getLevel() % 10 == 0 || getLevel() >= 50 ) {
            Bukkit.broadcastMessage(colorize(String.format("&lEXP &7%s awansowal na &e%d &7poziom!", getName(), getLevel())));
        }
    }

    public void bonus(int bonus, int seconds) { new Bonus(this, bonus, seconds).start(); }

    /* TOOLS */

    public void showInfo(Player player) {
        msgPlayer(player, "",
                String.format("       &8[&6Statystyki: &e%s&8]       ", getName()),
                "",
                "   &8→ &7Poziom: &f" + getLevel(),
                "   &8→ &7EXP: &f" + format(getExp()) + "&8/&f" + format(getRequiredExp()),
                "   &8→ &7Postep: &e" + format(getPercent()) + "%",
                "   &8→ &7Dodatkowy EXP: &3+" + getBonusExp() + "%",
                "   &8→ &7Bonus Serwerowy: &3+" + plugin.getLevelManager().getServerExp() + "%",
                "");
    }

    /* INFO */

    public static Level newInstance(UUID uuid) {
        return new Level(uuid);
    }

    public FileManager getFileManager() { return fileManager; }

    public Player getPlayer() {return player;}
    public String getName() { return name; }
    public int getLevel() { return level; }
    public double getExp() { return exp; }
    public double getRequiredExp() { return requiredExp; }
    public double getBonusExp() { return bonusExp; }
    public double getPercent() { return (getExp() / getRequiredExp()) * 100; }

    public void setPlayer(Player player) { this.player = player; }
    public void setName(String name) { this.name = name; }
    public void setLevel(int level) { this.level = level; }
    public void setExp(double exp) { this.exp = exp; }
    public void setRequiredExp(double requiredExp) { this.requiredExp = requiredExp; }
    public void setBonusExp(double bonusExp) { this.bonusExp = bonusExp; }

    public void addBonusExp(int amount) { this.bonusExp += amount; }
    public void removeBonusExp(int amount) { this.bonusExp -= amount; }
}
