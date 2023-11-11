package me.oyashiz.serverplugin.listeners;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.tasks.SpaceIntruder;
import me.oyashiz.serverplugin.utils.CheckInArea;
import me.oyashiz.serverplugin.utils.SendMsg;
import me.oyashiz.serverplugin.utils.StaticLocations;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Math.abs;

public class AirLockListener implements Listener {

    private final MainPlugin plugin;
    private static int flag;
    private ArrayList<Location> locationArrayListInSide = new ArrayList<>();
    private ArrayList<Location> locationArrayListOutSide = new ArrayList<>();
    private Location locationButtonOut = new Location(Bukkit.getWorld("world"), 49, 6, 35);
    private Location locationButtonIn = new Location(Bukkit.getWorld("world"), 44, 6, 35);
    private Location locationButtonInAirLock = new Location(Bukkit.getWorld("world"), 46, 7, 34);

    private SpaceIntruder spaceIntruder;

    public AirLockListener(MainPlugin plugin) {
        this.plugin = plugin;
        locationArrayListInSide.add(new Location(Bukkit.getWorld("world"), 45, 6, 34));
        locationArrayListInSide.add(new Location(Bukkit.getWorld("world"), 45, 5, 34));
        locationArrayListOutSide.add(new Location(Bukkit.getWorld("world"), 48, 6, 34));
        locationArrayListOutSide.add(new Location(Bukkit.getWorld("world"), 48, 5, 34));
        spaceIntruder = new SpaceIntruder(plugin);
    }

    public void lightTask(Material material) {
        int delay = 1;
        for (int x = 44; x >= 24; x--) {
            Location location = new Location(Bukkit.getWorld("world"), x, 2, 34);
            BukkitTask t = new BukkitRunnable() {
                @Override
                public void run() {
                    location.getBlock().setType(material);

                }
            }.runTaskLater(plugin, delay);
            delay += 1;
        }
        delay = 2;
        for (int x = 44; x >= 24; x--) {
            Location location = new Location(Bukkit.getWorld("world"), x, 2, 34);
            BukkitTask t = new BukkitRunnable() {
                @Override
                public void run() {
                    location.getBlock().setType(Material.AIR);

                }
            }.runTaskLater(plugin, delay);
            delay += 1;
        }
    }

    @EventHandler
    public void onButtonInside(PlayerInteractEvent e) {
        try {
            if (Objects.requireNonNull(e.getClickedBlock()).getWorld() != Bukkit.getWorld("world")) return;
            if (e.getClickedBlock().getLocation().equals(locationButtonIn)) {

                for (Location location : locationArrayListInSide) {
                    location.getBlock().setType(Material.AIR);
                }

                e.getClickedBlock().setType(Material.AIR);
                locationButtonOut.getBlock().setType(Material.AIR);
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 0.5f, 1);

                BukkitTask doorInSideClose = new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Location location : locationArrayListInSide) {
                            location.getBlock().setType(Material.BLACK_STAINED_GLASS);
                        }
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_COW_BELL, 0.5f, 1);
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.AMBIENT_UNDERWATER_ENTER, 0.5f, 1);

                    }
                }.runTaskLater(plugin, 50);

                BukkitTask doorOutSideOpen = new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Location location : locationArrayListOutSide) {
                            location.getBlock().setType(Material.AIR);
                        }
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 0.5f, 1);

                    }
                }.runTaskLater(plugin, 100);

                BukkitTask doorOutSideClose = new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Location location : locationArrayListOutSide) {
                            location.getBlock().setType(Material.BLACK_STAINED_GLASS);
                        }
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_COW_BELL, 0.5f, 1);
                        e.getClickedBlock().setType(Material.TRIPWIRE_HOOK);
                        locationButtonOut.getBlock().setType(Material.TRIPWIRE_HOOK);

                    }
                }.runTaskLater(plugin, 150);
            }

            if (e.getClickedBlock().getLocation().equals(locationButtonOut)) {
                Location locationCheck = new Location(Bukkit.getWorld("world"), 22, 9, 34);
                if (locationCheck.getBlock().getType() == Material.AIR) return;
                int clock = 20;
                flag = 0;
                spaceIntruder.start();
                BukkitTask t = new BukkitRunnable() {
                    @Override
                    public void run() {
                        lightTask(Material.REDSTONE_BLOCK);
                        if (flag == 1) cancel();

                    }
                }.runTaskTimerAsynchronously(plugin, clock, clock);
                locationCheck.getBlock().setType(Material.AIR);

                for (Location location : locationArrayListOutSide) {
                    location.getBlock().setType(Material.AIR);
                }

                e.getClickedBlock().setType(Material.AIR);
                locationButtonIn.getBlock().setType(Material.AIR);
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 0.5f, 1);

                BukkitTask doorOutSideClose = new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Location location : locationArrayListOutSide) {
                            location.getBlock().setType(Material.BLACK_STAINED_GLASS);
                        }
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_COW_BELL, 0.5f, 1);
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.AMBIENT_UNDERWATER_ENTER, 0.5f, 1);
                        e.getPlayer().sendMessage(ChatColor.GRAY + "Scanning Player");

                    }
                }.runTaskLater(plugin, 50);

                BukkitTask doorInSideOpen = new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Location location : locationArrayListInSide) {
                            location.getBlock().setType(Material.AIR);
                        }
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 0.5f, 1);
                        for (Player p : StaticLocations.world.getPlayers()) {
                            if (CheckInArea.check(p.getLocation(), 46, 33, 47, 35)) {
                                SendMsg.send(StaticLocations.world, "Incoming Space Traveller " + ChatColor.GREEN + p.getName() + ChatColor.WHITE + " From Airlock");
                            }
                        }
                        spaceIntruder.stop();

                    }
                }.runTaskLater(plugin, 100);

                BukkitTask doorInSideClose = new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Location location : locationArrayListInSide) {
                            location.getBlock().setType(Material.BLACK_STAINED_GLASS);
                        }
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_COW_BELL, 0.5f, 1);
                        e.getClickedBlock().setType(Material.TRIPWIRE_HOOK);
                        locationButtonIn.getBlock().setType(Material.TRIPWIRE_HOOK);
                        flag = 1;
                        lightTask(Material.AIR);
                        locationCheck.getBlock().setType(Material.WHITE_STAINED_GLASS);

                    }
                }.runTaskLater(plugin, 150);
            }

            if (e.getClickedBlock().getLocation().equals(locationButtonInAirLock)) {
                for (Location location : locationArrayListInSide) {
                    location.getBlock().setType(Material.AIR);
                }
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_SNARE, 0.5f, 1);

                BukkitTask doorInSideClose = new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Location location : locationArrayListInSide) {
                            location.getBlock().setType(Material.BLACK_STAINED_GLASS);
                        }
                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_COW_BELL, 0.5f, 1);

                    }
                }.runTaskLater(plugin, 50);
            }
        } catch (NullPointerException ignored) {
        }
    }
}
