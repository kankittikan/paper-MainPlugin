package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.tasks.PlaySound;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PsCommand implements CommandExecutor {
    private final MainPlugin plugin;

    public PsCommand(MainPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof BlockCommandSender) {
            if(args.length == 3 &&args[0].equals("on")) {
                PlaySound.playAll(Sound.valueOf(args[1].toUpperCase()), Float.parseFloat(args[2]));
            }
            else{
                PlaySound.stopAll();
            }
        }

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
                        player.playSound(player, Sound.valueOf(args[1].toUpperCase()), Float.parseFloat(args[2]), 1);
                        player.sendMessage(ChatColor.GREEN + "Playing " + args[1].toUpperCase());
                    }
                    catch (IllegalArgumentException e) {
                        player.sendMessage(ChatColor.RED + "No Sound In List");
                    }
                }
            }
            if(args.length == 4) {
                if(args[3].equals("all")) {
                    PlaySound.playAll(Sound.valueOf(args[1].toUpperCase()), Float.parseFloat(args[2]));
                    player.sendMessage(ChatColor.GREEN + "Playing all " + args[1].toUpperCase());
                }
            }
        }
        return false;
    }
}
