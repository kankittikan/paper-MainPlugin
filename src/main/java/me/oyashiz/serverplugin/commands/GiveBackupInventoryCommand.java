package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.utils.SaveInventory;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class GiveBackupInventoryCommand implements CommandExecutor {
    private final MainPlugin plugin;

    public GiveBackupInventoryCommand(MainPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(strings.length == 1) {
            Player player = Bukkit.getPlayerExact(strings[0]);
            if(player == null) return false;
            player.getInventory().clear();
            SaveInventory saveInventory = new SaveInventory(plugin);
            try {
                saveInventory.restoreInventory(player, "player_backup");
            } catch (IOException e) {
                e.printStackTrace();
            }
            player.sendMessage("Congratulation, you just receive your backup inventory");
        }
        return false;
    }
}
