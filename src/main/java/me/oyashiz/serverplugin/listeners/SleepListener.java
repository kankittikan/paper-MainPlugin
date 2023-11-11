package me.oyashiz.serverplugin.listeners;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.utils.SendMsg;
import me.oyashiz.serverplugin.utils.StaticLocations;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class SleepListener implements Listener {

    private final MainPlugin plugin;

    public SleepListener(MainPlugin plugin) {
        this.plugin = plugin;
    }

    private int countSleep() {
        int i = 0;
        for (Player p : StaticLocations.survivalWorld.getPlayers()) {
            if (p.isSleeping()) i++;
        }
        return i;
    }

    @EventHandler
    public void sleep(PlayerBedEnterEvent event) {
        if (StaticLocations.survivalWorld.getPlayers().size() == 1) return;
        BukkitTask bukkitTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (countSleep() == 1) {
                    SendMsg.send(StaticLocations.survivalWorld, ChatColor.GRAY + "Force sleep will be executed in 30 seconds");
                    BukkitTask task = new BukkitRunnable() {
                        int i = 30;

                        @Override
                        public void run() {
                            i--;
                            if (countSleep() == StaticLocations.survivalWorld.getPlayers().size() || countSleep() == 0 || StaticLocations.survivalWorld.getPlayers().size() == 1) {
                                cancel();
                                SendMsg.send(StaticLocations.survivalWorld, ChatColor.RED + "Canceled Force Sleep");
                            }
                            if (i == 0) {
                                cancel();
                                for (Player p : StaticLocations.survivalWorld.getPlayers()) {
                                    p.setSleepingIgnored(true);
                                }

                                BukkitTask task1 = new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        for (Player p : StaticLocations.survivalWorld.getPlayers()) {
                                            p.setSleepingIgnored(false);
                                        }
                                        SendMsg.send(StaticLocations.survivalWorld, ChatColor.GREEN + "Force sleep has been executed");

                                    }
                                }.runTaskLater(plugin, 100);
                            }

                        }
                    }.runTaskTimer(plugin, 0, 20);
                }

            }
        }.runTaskLater(plugin, 20);

    }
}
