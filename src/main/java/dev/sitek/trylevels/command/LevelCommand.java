package dev.sitek.trylevels.command;

import dev.sitek.trylevels.TryLevels;
import dev.sitek.trylevels.instance.Level;
import dev.sitek.trylevels.manager.LevelManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static dev.sitek.trylevels.util.TextUtil.*;

public class LevelCommand implements CommandExecutor {

    private final TryLevels plugin = TryLevels.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Komendy mozna uzyc jedynie z poziomu gracza!");
            return false;
        }

        final Player player = (Player) sender;
        final UUID playerUUID = player.getUniqueId();
        final LevelManager levelManager = plugin.getLevelManager();

        if (args.length == 1) {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            if (!target.hasPlayedBefore()) {
                msgPlayer(player, "&cNie ma takiego gracza w bazie danych!");
                return false;
            }

            final UUID uuid = target.getUniqueId();
            Level targetLevel = levelManager.setupLevel(uuid); targetLevel.showInfo(player); return true;
        }

        levelManager.getLevel(playerUUID).showInfo(player);
        return true;
    }
}
