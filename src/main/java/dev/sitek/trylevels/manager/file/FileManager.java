package dev.sitek.trylevels.manager.file;

import dev.sitek.trylevels.TryLevels;
import dev.sitek.trylevels.instance.Level;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileManager {

    private final TryLevels plugin = TryLevels.getInstance();

    private final Level level;
    private final UUID uuid;

    private YamlConfiguration config;

    public FileManager(Level level, UUID uuid) {
        this.level = level; this.uuid = uuid;
        loadConfig();
    }

    private void loadConfig() {
        File file = new File(plugin.getDataFolder(), uuid.toString());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void setupPlayer() {
        if (!config.contains(uuid.toString())) {
            saveConfig();
        }
        level.setName(Bukkit.getPlayer(uuid).getName());
        level.setLevel(config.getInt(uuid + ".level"));
        level.setExp(config.getDouble(uuid + ".exp"));
        level.setRequiredExp(config.getDouble(uuid + ".required-exp"));
        level.setBonusExp(config.getDouble(uuid + ".bonus-exp"));
    }





    public void saveConfig() {
        File file = new File(plugin.getDataFolder(), uuid.toString());

        config.set(uuid + ".name", level.getName());
        config.set(uuid + ".level", level.getLevel());
        config.set(uuid + ".exp", level.getExp());
        config.set(uuid + ".required-exp", level.getRequiredExp());
        config.set(uuid + ".bonus-exp", level.getBonusExp());

        try { config.save(file); } catch (IOException e) { throw new RuntimeException(e); }
    }
}
