package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.tasks.ClearEntity;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearEntityCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (args.length == 1) {
            ClearEntity.clearMonster(Bukkit.getWorld(args[0]));
        }
        if (args.length == 0) {
            ClearEntity.clearMonster(player.getWorld());
        }
        return false;
    }
}
