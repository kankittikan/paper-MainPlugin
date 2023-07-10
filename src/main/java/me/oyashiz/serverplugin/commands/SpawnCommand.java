package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.utils.StaticLocations;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    private final MainPlugin plugin;

    public SpawnCommand(MainPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (args.length == 1) {
                Location location = StaticLocations.home;

                if (args[0].equals("@a")) {
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        player.teleport(location);
                    }
                    return true;
                }
                Player player = Bukkit.getServer().getPlayerExact(args[0]);
                if(player == null) {
                    sender.sendMessage("Can not find this player");
                    return false;
                }
                player.teleport(location);
            }
        }
        return true;
    }
}
