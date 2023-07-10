package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.MainPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TestCommand implements CommandExecutor {
    private final MainPlugin plugin;

    public TestCommand(MainPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        return false;
    }
}
