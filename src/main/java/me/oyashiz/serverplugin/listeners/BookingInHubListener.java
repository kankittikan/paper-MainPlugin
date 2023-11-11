package me.oyashiz.serverplugin.listeners;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.tasks.PlaySound;
import me.oyashiz.serverplugin.utils.*;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Objects;

public class BookingInHubListener implements Listener {

    private final MainPlugin plugin;

    public BookingInHubListener(MainPlugin plugin) {
        this.plugin = plugin;
    }

    public void depart(PlayerInteractEvent event, Location des) throws NullPointerException {
        if (StaticFlags.TravelFlag == 0) {
            StaticLists.playerDeparture.clear();
            StaticFlags.TravelFlag = 1;
            StaticFlags.DepartFlag = 1;
            SpaceshipHatch.closeHatch();
            PlaySound.play(Sound.ITEM_GOAT_HORN_SOUND_0, StaticLocations.world, 0.5f);
            SendMsg.send(StaticLocations.world, "[All Traveller] " + "Departure Gate is set heading to " + ChatColor.GREEN + des.getWorld().getName());
            BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {
                    SendMsg.send(StaticLocations.world, "[All Traveller] " + "Depart within 1 minute");

                }
            }.runTaskLater(plugin, 50);
            BukkitTask task2 = new BukkitRunnable() {
                @Override
                public void run() {
                    for(Player player : StaticLists.playerDeparture) {
                        player.playSound(player.getLocation(), "engine", 0.2f, 1);
                    }

                }
            }.runTaskLater(plugin, 900);
            BukkitTask task1 = new BukkitRunnable() {
                @Override
                public void run() {
                    if (!StaticLists.playerDeparture.isEmpty()) {
                        for (Player player : StaticLists.playerDeparture) {
                            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1));
                            BukkitTask task3 = new BukkitRunnable() {
                                @Override
                                public void run() {
                                    player.teleport(des);
                                    BukkitTask delayed = new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            player.teleport(des);

                                        }
                                    }.runTaskLater(plugin, 10);
                                    player.sendMessage(ChatColor.GREEN + "Welcome to " + des.getWorld().getName());
                                    StaticLists.playerDeparture.clear();
                                    StaticFlags.TravelFlag = 0;
                                    StaticFlags.DepartFlag = 0;

                                }
                            }.runTaskLater(plugin, 40);
                        }
                    }
                    if (StaticLists.playerDeparture.isEmpty()) {
                        SendMsg.send(StaticLocations.world, "[All Traveller]" + ChatColor.RED + " No one in spaceship and not be departed");
                        StaticFlags.TravelFlag = 0;
                        StaticFlags.DepartFlag = 0;
                    }

                }
            }.runTaskLater(plugin, 1200);


        } else {
            event.getPlayer().sendMessage(ChatColor.RED + "Gate in using");
        }
    }

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event) {
        try {
            if (Objects.requireNonNull(event.getClickedBlock()).getWorld() != Bukkit.getWorld("world")) return;
            Location location = Objects.requireNonNull(event.getClickedBlock()).getLocation();
            if (location.getBlockX() == 15 && location.getBlockY() == 6 && location.getBlockZ() == 50) {
                depart(event, new Location(StaticLocations.survivalWorld, 182, 67, -20));
            }

        } catch (NullPointerException ignored) {

        }
    }
}
