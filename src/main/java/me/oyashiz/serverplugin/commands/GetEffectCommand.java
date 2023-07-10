package me.oyashiz.serverplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class GetEffectCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            Player target = Bukkit.getPlayerExact(args[0]);
            Player player = (Player) sender;
            if (target == null) {
                player.sendMessage(ChatColor.RED + "No Player Found");
            }
            for (PotionEffect p : target.getActivePotionEffects()) {
                player.sendMessage(p.getType().getName() + " Duration:" + (int) (p.getDuration() * 0.05) + " Amplifier:" + p.getAmplifier());
            }
        }
        return false;
    }
}