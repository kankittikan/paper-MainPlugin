package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.utils.SendAdmin;
import me.oyashiz.serverplugin.utils.StaticFlags;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UnBreakCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 2) {
            World world = null;
            if(args[0].equals("world_fukie")) world = Bukkit.getWorld(args[0]);

            if(world == null) return false;

            if(args[1].equals("on")) {
                SendAdmin.sendMsg("UnBreak On");
                StaticFlags.FukieUnBreak = 1;
            }
            if(args[1].equals("off")) {
                SendAdmin.sendMsg("UnBreak Off");
                StaticFlags.FukieUnBreak = 0;
            }
        }
        return false;
    }
}
