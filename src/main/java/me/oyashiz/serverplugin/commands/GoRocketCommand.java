package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.utils.StaticLocations;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GoRocketCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.getWorld() == StaticLocations.survivalWorld) {
                player.teleport(new Location(StaticLocations.survivalWorld, 18, 75, 12));
            }
            else {
                player.sendMessage(ChatColor.RED + "Can not go rocket in this world");
            }
        }
        return false;
    }
}
