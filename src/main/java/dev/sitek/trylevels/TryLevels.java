package dev.sitek.trylevels;

import dev.sitek.trylevels.command.BonusCommand;
import dev.sitek.trylevels.command.LevelCommand;
import dev.sitek.trylevels.instance.Level;
import dev.sitek.trylevels.listener.PlayerConnectListner;
import dev.sitek.trylevels.listener.PlayerLevelListener;
import dev.sitek.trylevels.manager.LevelManager;
import dev.sitek.trylevels.manager.file.FileSaver;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public final class TryLevels extends JavaPlugin {

    private static TryLevels plugin;
    private LevelManager levelManager;

    @Override
    public void onEnable() {
        setPlugin(this); levelManager = new LevelManager();

        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new FileSaver(), 6000L, 6000L);

        implementsListeners();
        registerCommands();
    }

    @Override
    public void onDisable() {
        for (Level level : plugin.getLevelManager().getLevels().values()) {
            level.getFileManager().saveConfig();
        }
    }

    private void implementsListeners() {
        getServer().getPluginManager().registerEvents(new PlayerConnectListner(), this);
        getServer().getPluginManager().registerEvents(new PlayerLevelListener(), this);
    }

    private void registerCommands() {
        getCommand("level").setExecutor(new LevelCommand());
        getCommand("bonus").setExecutor(new BonusCommand());
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public void setPlugin(TryLevels plugin) {
        TryLevels.plugin = plugin;
    }

    public static TryLevels getInstance() { return plugin; }
}
