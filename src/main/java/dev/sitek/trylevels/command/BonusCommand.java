package dev.sitek.trylevels.command;

import dev.sitek.trylevels.TryLevels;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import static dev.sitek.trylevels.util.TextUtil.*;

public class BonusCommand implements CommandExecutor {

    private final TryLevels plugin = TryLevels.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player)) { sender.sendMessage("Mozna uzyc z poziomu gracza!"); return false; }
        if (args.length != 3) { sender.sendMessage(colorize("&lEXP &7Poprawne uzycie: /bonus <gracz/all> <bonus> <czas>")); return false; }

        int bonus = Integer.parseInt(args[1]);
        int seconds = Integer.parseInt(args[2]);

        if (args[0].equalsIgnoreCase("all")) {
            plugin.getLevelManager().temporaryServerBonusExp(bonus, seconds);
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) { sender.sendMessage(colorize("&lEXP &7Nie ma takiego gracza na serwerze lub nie jest online!")); return false; }
        UUID uuid = target.getUniqueId();
        if (plugin.getLevelManager().getBonuses().contains(uuid)) { sender.sendMessage(colorize("&lEXP &7Ten gracz ma juz aktywny bonus!")); return false; }
        plugin.getLevelManager().getLevel(uuid).bonus(bonus, seconds); return true;
    }

}
