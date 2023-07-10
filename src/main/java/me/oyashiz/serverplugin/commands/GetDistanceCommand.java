package me.oyashiz.serverplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetDistanceCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 2) {
            Player player = Bukkit.getPlayerExact(args[0]);
            Player player1 = Bukkit.getPlayerExact(args[1]);
            int distance = (int) player.getLocation().distance(player1.getLocation());
            Bukkit.broadcastMessage("Distance from " + args[0] + " to " + args[1] + " : " + distance + " blocks");
        }
        return false;
    }
}
