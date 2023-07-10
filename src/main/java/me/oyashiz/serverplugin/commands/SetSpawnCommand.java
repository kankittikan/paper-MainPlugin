package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.utils.ConfigReader;
import me.oyashiz.serverplugin.utils.StaticLocations;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {
    private final MainPlugin plugin;

    public SetSpawnCommand(MainPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            Location location = player.getLocation();
            if (location.getWorld() != StaticLocations.survivalWorld) {
                player.sendMessage(ChatColor.RED + "Can not set home in this world");
                return false;
            }
            MainPlugin.homeConfig.getConfig().set(player.getName(), location);
            ConfigReader.save(MainPlugin.homeConfig);
            player.sendMessage(ChatColor.GREEN + "Home location set");
        }
        return false;
    }
}
