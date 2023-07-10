package me.oyashiz.serverplugin.listeners;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.commands.HubLightingCommand;
import me.oyashiz.serverplugin.commands.InHubLightingCommand;
import me.oyashiz.serverplugin.tasks.SpaceIntruder;
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

public class SignClickListener implements Listener {

    private final MainPlugin plugin;
    private static int flag;

    private SpaceIntruder spaceIntruder;

    public SignClickListener(MainPlugin plugin) {

        this.plugin = plugin;
        spaceIntruder = new SpaceIntruder(plugin);
    }

    @EventHandler
    public void onSignSpawnClick(PlayerInteractEvent e) {
        try {
            if(Objects.requireNonNull(e.getClickedBlock()).getWorld() != Bukkit.getWorld("world")) return;
            if (e.getClickedBlock().getX() == 22 && e.getClickedBlock().getY() == 35 && e.getClickedBlock().getZ() == 35) {
                Player player = e.getPlayer();
                Location location1 = new Location(player.getWorld(), 22, 9, 34);
                if (location1.getBlock().getType() != Material.AIR) {
                    Location location = new Location(Bukkit.getWorld("world"), 22.5, 29, 34.5, 90, 90);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 50, 1));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 150, 1));
                    player.playSound(player.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 0.5f, 2);
                    player.setGameMode(GameMode.ADVENTURE);
                    player.setAllowFlight(false);
                    e.getPlayer().sendMessage(ChatColor.GRAY + "Scanning Player");
                    spaceIntruder.start();
                    flag = 0;
                    int clock = 15;
                    InHubLightingCommand inHubLightingCommand = new InHubLightingCommand(plugin);
                    HubLightingCommand hubLightingCommand = new HubLightingCommand(plugin);
                    BukkitTask taskLight = new BukkitRunnable() {
                        @Override
                        public void run() {
                            inHubLightingCommand.lightTask(Material.REDSTONE_BLOCK);
                            if (flag == 1) cancel();
                        }
                    }.runTaskTimerAsynchronously(plugin, clock, clock);
                    player.teleport(location);

                    BukkitTask task = new BukkitRunnable() {
                        @Override
                        public void run() {
                            hubLightingCommand.closeLight();
                            Location location1 = new Location(player.getWorld(), 22, 9, 34);
                            location1.getBlock().setType(Material.AIR);
                            for(Player p : Bukkit.getOnlinePlayers()) {
                                if(p.getWorld() == Bukkit.getWorld("world")) {
                                    p.sendMessage("Incoming Space Traveller " + ChatColor.GREEN + e.getPlayer().getName() + ChatColor.WHITE + " From Spaceship");
                                }
                            }
                        }
                    }.runTaskLater(plugin, 30L);

                    BukkitTask task1 = new BukkitRunnable() {
                        @Override
                        public void run() {
                            flag = 1;
                            inHubLightingCommand.lightTask(Material.AIR);
                        }
                    }.runTaskLater(plugin, 100L);

                    BukkitTask task2 = new BukkitRunnable() {
                        @Override
                        public void run() {
                            hubLightingCommand.closeLight();
                        }
                    }.runTaskLater(plugin, 150L);

                    BukkitTask task3 = new BukkitRunnable() {
                        @Override
                        public void run() {
                            hubLightingCommand.openLight();
                        }
                    }.runTaskLater(plugin, 151L);

                    BukkitTask task4 = new BukkitRunnable() {
                        @Override
                        public void run() {
                            hubLightingCommand.closeLight();
                            spaceIntruder.stop();
                        }
                    }.runTaskLater(plugin, 153L);

                    BukkitTask task5 = new BukkitRunnable() {
                        @Override
                        public void run() {
                            hubLightingCommand.openLight();
                            Location location1 = new Location(player.getWorld(), 22, 9, 34);
                            location1.getBlock().setType(Material.WHITE_STAINED_GLASS);
                        }
                    }.runTaskLater(plugin, 154L);

                } else {
                    e.getPlayer().sendMessage(ChatColor.RED + "Teleport in using");
                }
            }
        } catch (NullPointerException ignored) {
        }
    }
}
