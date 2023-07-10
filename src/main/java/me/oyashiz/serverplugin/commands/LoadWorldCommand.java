package me.oyashiz.serverplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class LoadWorldCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof BlockCommandSender || sender instanceof ConsoleCommandSender) {
            if (args.length == 1) {
                for(Player player : Bukkit.getOnlinePlayers()) {
                    if(player.isOp()) player.sendMessage(ChatColor.YELLOW + "Control Room: " + ChatColor.WHITE + "Loading " + args[0]);
                }
                World worldCreator = new WorldCreator(args[0]).createWorld();
                for(Player player : Bukkit.getOnlinePlayers()) {
                    if(player.isOp()) player.sendMessage(ChatColor.GREEN + "Load World complete");
                }
            }
        }
        return false;
    }
}
