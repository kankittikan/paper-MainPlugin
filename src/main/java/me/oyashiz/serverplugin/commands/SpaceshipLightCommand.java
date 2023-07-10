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

public class SpaceshipLightCommand implements CommandExecutor {
    private final MainPlugin plugin;
    private final Location location;

    public SpaceshipLightCommand(MainPlugin plugin) {
        this.plugin = plugin;
        location = new Location(Bukkit.getWorld("world"), 62, -4, 22);
    }

    private void lightTask() {
        location.getBlock().setType(Material.REDSTONE_BLOCK);
        BukkitTask task1 = new BukkitRunnable() {
            @Override
            public void run() {
                location.getBlock().setType(Material.AIR);
            }
        }.runTaskLater(plugin, 20);
    }

    public void setLight(String action) {
        if(action.equals("on")) {
            StaticFlags.SpaceshipLightFlag = 0;
            BukkitTask t = new BukkitRunnable() {
                @Override
                public void run() {
                    lightTask();
                    if(StaticFlags.SpaceshipLightFlag == 1) cancel();
                }
            }.runTaskTimer(plugin, 30, 30);
        }
        if(action.equals("off")) {
            StaticFlags.SpaceshipLightFlag = 1;
            location.getBlock().setType(Material.AIR);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1) {

            if(args[0].equals("on") && StaticFlags.SpaceshipLightFlag == 1) {
                StaticFlags.SpaceshipLightFlag = 0;
                BukkitTask t = new BukkitRunnable() {
                    @Override
                    public void run() {
                        lightTask();
                        if(StaticFlags.SpaceshipLightFlag == 1) cancel();
                    }
                }.runTaskTimer(plugin, 30, 30);
            }

            if(args[0].equals("off")) {
                StaticFlags.SpaceshipLightFlag = 1;
                location.getBlock().setType(Material.AIR);
            }
        }
        return false;
    }
}
