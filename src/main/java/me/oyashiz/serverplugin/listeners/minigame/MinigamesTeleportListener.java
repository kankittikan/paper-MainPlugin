package me.oyashiz.serverplugin.listeners.minigame;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.tasks.SignLevel;
import me.oyashiz.serverplugin.utils.*;
import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.IOException;
import java.util.Objects;

public class MinigamesTeleportListener implements Listener {

    private final MainPlugin plugin;

    SaveInventory saveInventory;

    public static boolean grant = false;

    public MinigamesTeleportListener(MainPlugin plugin) {

        this.plugin = plugin;
        saveInventory = new SaveInventory(plugin);
    }

    @EventHandler
    public void onPlayerStepOnPressure(PlayerInteractEvent e) {
        try {
            if (Objects.requireNonNull(e.getClickedBlock()).getWorld() != Bukkit.getWorld("world")) return;

            if (e.getClickedBlock().getX() == 4 && e.getClickedBlock().getY() == 5 && e.getClickedBlock().getZ() == 21) {
                Location hub = new Location(Bukkit.getWorld("world_fukie"), 315, -46, -162);
                Location hub1 = new Location(Bukkit.getWorld("world_fukie"), 315.3, -49, -161.5);
                World world = Bukkit.getWorld("world_fukie");
                if (world == null) {
                    e.getPlayer().sendMessage(ChatColor.RED + "Can not find World");
                    return;
                }
                if(StaticLocations.fukieWorld.getPlayers().isEmpty()) {
                    StaticFlags.performingLevel = 1;
                    plugin.getConfig().set("performing_level", StaticFlags.performingLevel);
                    plugin.saveConfig();
                    StaticInt.teamLife = 3;
                    SignLevel.change();
                }
                BukkitTask t = new BukkitRunnable() {
                    @Override
                    public void run() {
                        try {
                            saveInventory.saveInventory(e.getPlayer(), "world");
                            e.getPlayer().getInventory().clear();
                            saveInventory.restoreInventory(e.getPlayer(), "world_fukie");
                        } catch (IOException ex) {
                            SendAdmin.sendMsg(ex.getMessage());
                        } catch (NullPointerException ignored) {
                        }
                        e.getPlayer().teleport(hub);
                        BukkitTask t1 = new BukkitRunnable() {
                            @Override
                            public void run() {
                                e.getPlayer().teleport(hub1);
                                e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30, 1));
                                e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 80, 1));
                                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 0.5f, 2);
                                e.getPlayer().sendTitle("Devil Asteroid", ChatColor.RED + "โปรดอ่านกฎก่อนเล่น");
                            }
                        }.runTaskLater(plugin, 100);
                    }
                }.runTaskLater(plugin, 5);
            }
            if (e.getClickedBlock().getLocation().equals(new Location(StaticLocations.world, 4, 5, 24))) {
                try {
                    saveInventory.saveInventory(e.getPlayer(), "world");
                    e.getPlayer().getInventory().clear();
                    saveInventory.restoreInventory(e.getPlayer(), "world_speaka");
                } catch (IOException ex) {
                    SendAdmin.sendMsg(ex.getMessage());
                }
                e.getPlayer().teleport(new Location(Bukkit.getWorld("world_speaka"), -221, 4, -794));
                e.getPlayer().setGameMode(GameMode.SURVIVAL);
                e.getPlayer().playSound(e.getPlayer().getLocation(), "park", 0.2f, 1);
            }
            if (e.getClickedBlock().getLocation().equals(new Location(StaticLocations.world, 4, 5, 27))) {
                try {
                    saveInventory.saveInventory(e.getPlayer(), "world");
                    e.getPlayer().getInventory().clear();
                    saveInventory.restoreInventory(e.getPlayer(), "world_star");
                } catch (IOException ex) {
                    SendAdmin.sendMsg(ex.getMessage());
                }
                e.getPlayer().teleport(new Location(Bukkit.getWorld("world_star"), 0, 51, -143));
                e.getPlayer().setGameMode(GameMode.SURVIVAL);
                e.getPlayer().playSound(e.getPlayer().getLocation(), "starwar", 0.1f, 1);
            }
        } catch (NullPointerException ignored) {
        }
    }
}
