package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.utils.StaticFlags;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

public class OutHubLightCommand implements CommandExecutor {
    private final MainPlugin plugin;
    private ArrayList<Location> locations;

    public OutHubLightCommand(MainPlugin plugin) {
        this.plugin = plugin;
        locations = new ArrayList<>();
        locations.add(new Location(Bukkit.getWorld("world"), 43, 11, 34));
        locations.add(new Location(Bukkit.getWorld("world"), 22, 11, 55));
        locations.add(new Location(Bukkit.getWorld("world"), 1, 11, 34));
        locations.add(new Location(Bukkit.getWorld("world"), 22, 11, 13));
    }

    private void lightTask() {
        for(Location location : locations) {
            location.getBlock().setType(Material.REDSTONE_BLOCK);
        }
        BukkitTask task1 = new BukkitRunnable() {
            @Override
            public void run() {
                for(Location location : locations) {
                    location.getBlock().setType(Material.AIR);
                }
            }
        }.runTaskLater(plugin, 20);
    }

    public void setLight(String action) {
        if(action.equals("on")) {
            StaticFlags.OutHubLightFlag = 0;
            BukkitTask t = new BukkitRunnable() {
                @Override
                public void run() {
                    lightTask();
                    if(StaticFlags.OutHubLightFlag == 1) cancel();
                }
            }.runTaskTimer(plugin, 30, 30);
        }
        if(action.equals("off")) {
            StaticFlags.OutHubLightFlag = 1;
            for(Location location : locations) {
                location.getBlock().setType(Material.AIR);
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1) {
            if(args[0].equals("on") && StaticFlags.OutHubLightFlag == 1) {
                StaticFlags.OutHubLightFlag = 0;
                BukkitTask t = new BukkitRunnable() {
                    @Override
                    public void run() {
                        lightTask();
                        if(StaticFlags.OutHubLightFlag == 1) cancel();
                    }
                }.runTaskTimer(plugin, 30, 30);
            }

            if(args[0].equals("off")) {
                StaticFlags.OutHubLightFlag = 1;
                for(Location location : locations) {
                    location.getBlock().setType(Material.AIR);
                }
            }
        }
        return false;
    }
}
