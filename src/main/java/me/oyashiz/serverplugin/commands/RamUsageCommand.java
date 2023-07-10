package me.oyashiz.serverplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RamUsageCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage((Runtime.getRuntime().freeMemory() * Math.pow(10, -6)) + "");
        return false;
    }
}
