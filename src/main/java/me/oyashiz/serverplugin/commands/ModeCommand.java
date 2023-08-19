package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.utils.StaticFlags;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ModeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1) {
            if(args[0].equals("on")) {
                for(Player p : Bukkit.getOnlinePlayers()) {
                    p.playSound(p.getLocation(), "alarm", 0.3f, 1);
                    p.sendMessage(ChatColor.GREEN + "Maintenance mode enabled");
                    p.sendMessage(ChatColor.YELLOW + "You may get some spotty connection");
                    StaticFlags.maintenance = true;
                }

            }
            if(args[0].equals("off")) {
                for(Player p : Bukkit.getOnlinePlayers()) {
                    p.playSound(p.getLocation(), "alarm", 0.3f, 1);
                    p.sendMessage(ChatColor.GREEN + "Maintenance mode disabled");
                    StaticFlags.maintenance = false;
                }

            }
        }
        return false;
    }
}
