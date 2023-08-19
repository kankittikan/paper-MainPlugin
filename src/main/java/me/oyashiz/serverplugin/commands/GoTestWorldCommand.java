package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.utils.SaveInventory;
import me.oyashiz.serverplugin.utils.SendAdmin;
import me.oyashiz.serverplugin.utils.StaticFlags;
import me.oyashiz.serverplugin.utils.StaticLocations;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class GoTestWorldCommand implements CommandExecutor {
    private final MainPlugin plugin;

    public GoTestWorldCommand(MainPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length == 1) {
                SaveInventory saveInventory = new SaveInventory(plugin);
                if(args[0].equals("go")) {
                    StaticFlags.canChangeMode = true;
                    Location testWorld = new Location(Bukkit.getWorld("world_tester"), 0, 10 , 0);
                    try {
                        saveInventory.saveInventory(player, "testSave");
                    } catch (IOException e) {
                        SendAdmin.sendMsg(ChatColor.RED + "Failed restore inventory");
                    }
                    player.teleport(testWorld);
                    player.setGameMode(GameMode.CREATIVE);
                    player.getInventory().clear();
                }
                if(args[0].equals("back")) {
                    if(player.getWorld() != Bukkit.getWorld("world_tester")) return false;
                    StaticFlags.canChangeMode = true;
                    player.getInventory().clear();
                    player.getActivePotionEffects().clear();
                    try {
                        saveInventory.restoreInventory(player, "testSave");
                    } catch (IOException e) {
                        SendAdmin.sendMsg(ChatColor.RED + "Failed restore inventory");
                    }
                    Location home = MainPlugin.homeConfig.getConfig().getLocation(player.getName());
                    if (home == null) {
                        home = new Location(StaticLocations.survivalWorld, 18, 75, 12);
                    }
                    player.teleport(home);
                    player.setGameMode(GameMode.SURVIVAL);
                }
                StaticFlags.canChangeMode = false;
            }
        }
        return false;
    }
}
