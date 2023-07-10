package me.oyashiz.serverplugin.tasks;

import me.oyashiz.serverplugin.MainPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;

public class FillBlock {
    public static void fill(World world, int x1, int y1, int z1, int x2, int y2, int z2, Material material, MainPlugin plugin) {
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                int[] x = {x1, x2};
                int[] y = {y1, y2};
                int[] z = {z1, z2};
                Arrays.sort(x);
                Arrays.sort(y);
                Arrays.sort(z);
                for (int yTemp = y[0]; yTemp <= y[1]; yTemp++) {
                    for (int xTemp = x[0]; xTemp <= x[1]; xTemp++) {
                        for (int zTemp = z[0]; zTemp <= z[1]; zTemp++) {
                            Location location = new Location(world, xTemp, yTemp, zTemp);
                            location.getBlock().setType(material);
                        }
                    }
                }
            }
        }.runTask(plugin);
    }

}
