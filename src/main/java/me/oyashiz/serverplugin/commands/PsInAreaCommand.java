package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.tasks.PlaySound;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PsInAreaCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 8) {
            if(args[0].equals("on")) {
                int[] x = {Integer.parseInt(args[1]), Integer.parseInt(args[3])};
                int[] z = {Integer.parseInt(args[2]), Integer.parseInt(args[4])};
                float volume  = Float.parseFloat(args[7]);
                PlaySound.playInArea(x, z, Bukkit.getWorld(args[5]), args[6], volume);
            }
        }
        return false;
    }
}
