package me.oyashiz.serverplugin.tasks;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.utils.StaticLocations;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class SpaceIntruder {
    private final MainPlugin plugin;
    private static boolean stop = false;

    public SpaceIntruder(MainPlugin plugin) {
        this.plugin = plugin;
    }

    public void start() {
            stop = false;

            BukkitTask p = new BukkitRunnable() {
                @Override
                public void run() {
                    for (Player p : StaticLocations.world.getPlayers()) {
                        p.playSound(p.getLocation(), "alarm", 0.1f, 1);
                    }
                    if(stop) cancel();

                }
            }.runTaskTimer(plugin, 30, 40);
        }

        public void stop() {
            stop = true;
        }
}
