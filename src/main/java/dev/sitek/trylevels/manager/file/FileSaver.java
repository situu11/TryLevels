package dev.sitek.trylevels.manager.file;

import dev.sitek.trylevels.TryLevels;
import dev.sitek.trylevels.instance.Level;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static dev.sitek.trylevels.util.TextUtil.*;

public class FileSaver implements Runnable {

    private final static TryLevels plugin = TryLevels.getInstance();

    @Override
    public void run() {
        for (Level level : plugin.getLevelManager().getLevels().values()) {
            level.getFileManager().saveConfig();
        }

        final Player player = Bukkit.getPlayer("situ_u");
        if (player != null) {
            msgPlayer(player, "&lEXP &7Zapisano pliki konfiguracyjne graczy!");
        }
    }

}
