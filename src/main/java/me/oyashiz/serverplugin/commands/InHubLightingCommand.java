package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.MainPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import static java.lang.Math.abs;

public class InHubLightingCommand implements CommandExecutor {
    private final MainPlugin plugin;
    private static int flag;

    public InHubLightingCommand(MainPlugin plugin) {
        this.plugin = plugin;
    }

    public void lightTask(Material material) {
        int delay = 1;
        for (int x = 0; x < 21; x++) {
            Location location = new Location(Bukkit.getWorld("world"), x + 24, 2, 34);
            Location location1 = new Location(Bukkit.getWorld("world"), 22, 2, x + 36);
            int x1 = x - 20;
            int z1 = x - 32;
            Location location2 = new Location(Bukkit.getWorld("world"), abs(x1), 2, 34);
            Location location3 = new Location(Bukkit.getWorld("world"), 22, 2, abs(z1));
            BukkitTask t = new BukkitRunnable() {
                @Override
                public void run() {
                    location.getBlock().setType(material);
                    location1.getBlock().setType(material);
                    location2.getBlock().setType(material);
                    location3.getBlock().setType(material);

                }
            }.runTaskLater(plugin, delay);
            delay += 1;
        }
        delay = 2;
        for (int x = 0; x < 21; x++) {
            Location location = new Location(Bukkit.getWorld("world"), x + 24, 2, 34);
            Location location1 = new Location(Bukkit.getWorld("world"), 22, 2, x + 36);
            int x1 = x - 20;
            int z1 = x - 32;
            Location location2 = new Location(Bukkit.getWorld("world"), abs(x1), 2, 34);
            Location location3 = new Location(Bukkit.getWorld("world"), 22, 2, abs(z1));
            BukkitTask t = new BukkitRunnable() {
                @Override
                public void run() {
                    location.getBlock().setType(Material.AIR);
                    location1.getBlock().setType(Material.AIR);
                    location2.getBlock().setType(Material.AIR);
                    location3.getBlock().setType(Material.AIR);

                }
            }.runTaskLater(plugin, delay);
            delay += 1;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Location location = new Location(Bukkit.getWorld("world"), 22, 9, 34);
        if (args.length == 1 && location.getBlock().getType() != Material.AIR) {
            BukkitTask task = null;
            if (args[0].equals("on")) {
                flag = 0;
                int clock = 20;
                task = new BukkitRunnable() {
                    @Override
                    public void run() {
                        lightTask(Material.REDSTONE_BLOCK);
                        if (flag == 1) cancel();

                    }
                }.runTaskTimer(plugin, clock, clock);
            }
            if (args[0].equals("off")) {
                flag = 1;
                lightTask(Material.AIR);
            }
        }

        return false;
    }
}
