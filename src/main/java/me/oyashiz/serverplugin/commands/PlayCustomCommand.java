package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.tasks.PlaySound;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayCustomCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length >= 1) {
                if(args[0].equals("off")) {
                    player.stopAllSounds();
                }
                if(args[0].equals("offAll")) {
                    PlaySound.stop(player.getWorld());
                }
            }
            if(args.length == 3) {
                if(args[0].equals("on")) {
                    player.stopAllSounds();
                    try {
                        player.playSound(player.getLocation(), args[1], Float.parseFloat(args[2]), 1);
                        player.sendMessage(ChatColor.GREEN + "Playing " + args[1].toUpperCase());
                    }
                    catch (IllegalArgumentException e) {
                        player.sendMessage(ChatColor.RED + "No Sound In List");
                    }
                }
            }
            if(args.length == 4) {
                if(args[3].equals("all")) {
                    PlaySound.stopAll();
                    for(Player player1 : Bukkit.getOnlinePlayers()) {
                        player1.playSound(player1.getLocation(), args[1], Float.parseFloat(args[2]), 1);
                    }
                    player.sendMessage(ChatColor.GREEN + "Playing all " + args[1].toUpperCase());
                }
            }
        }

        if(sender instanceof BlockCommandSender) {
            if(args.length == 2) {
                PlaySound.stopAll();
                for(Player player1 : Bukkit.getOnlinePlayers()) {
                    player1.playSound(player1.getLocation(), args[0], Float.parseFloat(args[1]), 1);
                }
            }
        }
        return false;
    }
}
