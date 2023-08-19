package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.MainPlugin;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand implements CommandExecutor {
    private final MainPlugin plugin;

    public TestCommand(MainPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1) {
            Player player = Bukkit.getPlayerExact(args[0]);
            sender.sendMessage(player.getResourcePackStatus().name());
        }
        return false;
    }
}
