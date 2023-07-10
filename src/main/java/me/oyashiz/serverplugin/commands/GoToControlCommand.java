package me.oyashiz.serverplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GoToControlCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            if(sender.isOp()) {
                Location location = new Location(Bukkit.getWorld("world"), 20, 10, 34);
                ((Player) sender).teleport(location);
            }
        }
        return false;
    }
}
