package me.oyashiz.serverplugin.tasks;

import me.oyashiz.serverplugin.MainPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import static java.lang.Math.abs;

public class RocketLight {
    private final MainPlugin plugin;

    public RocketLight(MainPlugin plugin) {
        this.plugin = plugin;
    }

    public void lightTask(Material material) {
        BukkitTask t1 = new BukkitRunnable() {
            int y = 112;
            @Override
            public void run() {
                if (y == 219) cancel();
                Location location = new Location(Bukkit.getWorld("world_survival"), 74, y, -29);
                location.getBlock().setType(material);
                y++;
            }
        }.runTaskTimer(plugin, 0, 0);
    }
}
