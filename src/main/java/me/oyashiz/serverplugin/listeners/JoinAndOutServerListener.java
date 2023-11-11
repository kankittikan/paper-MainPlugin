package me.oyashiz.serverplugin.listeners;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.tasks.Auth;
import me.oyashiz.serverplugin.tasks.ResourcePack;
import me.oyashiz.serverplugin.tasks.ScoreboardPlayer;
import me.oyashiz.serverplugin.tasks.VerifyIpCountry;
import me.oyashiz.serverplugin.utils.*;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.profile.PlayerTextures;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

public class JoinAndOutServerListener implements Listener {

    private final MainPlugin plugin;

    private ArrayList<String> strings;

    public JoinAndOutServerListener(MainPlugin plugin) {
        this.plugin = plugin;
        strings = new ArrayList<>();
        //strings.add("Thai font not ready");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        String ip = player.getAddress().getAddress().toString().substring(1);
        String port = String.valueOf(player.getAddress().getPort());

        new BukkitRunnable() {
            @Override
            public void run() {
                player.sendMessage(" ");
                player.sendMessage("=========");
                player.sendMessage("Inbound Connection Info: ");

                player.sendMessage(ChatColor.RED + "Bypass IP Service");

//                player.sendMessage("Public Ip : " + ip);
//                player.sendMessage("Public Port : " + port);

                if (true) {
                    player.sendMessage(ChatColor.GREEN + "Your Inbound is allowed");
                    player.sendMessage("=========");
                    player.sendMessage(" ");
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            ResourcePack.set(player);

                            if (player.isOp()) {
                                event.setJoinMessage(ChatColor.GREEN + player.getName() + ChatColor.WHITE + " join this server " + ChatColor.GOLD + "[Operator]");
                            } else {
                                event.setJoinMessage(ChatColor.GREEN + player.getName() + ChatColor.WHITE + " join this server " + ChatColor.GOLD + "[Player]");
                            }

                            if (!strings.isEmpty()) {
                                player.sendMessage(ChatColor.YELLOW + "Announcement from administrator");
                                for (int i = 0; i < strings.size(); i++) {
                                    player.sendMessage(i + 1 + ".) " + strings.get(i));
                                }
                                player.sendMessage(" ");
                            } else {
                                player.sendMessage(ChatColor.GREEN + "No announcement from administrator");
                            }

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    Auth auth = new Auth();
                                    auth.doAuth(player);

                                }
                            }.runTaskLater(plugin, 1);


                            if (player.getWorld().getName().equals("world_fukie")) {
                                Location hub = new Location(Bukkit.getWorld("world_fukie"), 315, -60, -162);
                                player.teleport(hub);

                            }

                            Location location = new Location(Bukkit.getWorld("world"), 22, 33, 33);
                            if (player.getLastPlayed() == 0) {
                                BukkitTask task1 = new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        player.teleport(location);
                                        player.setGameMode(GameMode.ADVENTURE);

                                    }
                                }.runTaskLater(plugin, 10);
                            }
                            BukkitTask task12 = new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (player.getWorld() == StaticLocations.world) {
                                        playWelcomeMusic(player);
                                    }

                                }
                            }.runTaskLater(plugin, 30);

                        }
                    }.runTaskLater(plugin, 20);

                } else {
                    player.kick(Component.text("Cannot verify your ip"));
                }

            }
        }.runTaskLater(plugin, 20);

    }

    @EventHandler
    public void onPlayerOut(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(ChatColor.GOLD + player.getDisplayName() + ChatColor.RED + " log out from this server.");
    }

    @EventHandler
    public void onPlayerDead(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (player.getWorld().getName().equals("world") && CheckInArea.check(player.getLocation(), 39, 51, 28, 50)) {
            Location location = player.getLocation();
            location.setY(6);
            event.setRespawnLocation(location);
            return;
        }

        if (player.getWorld().getName().equals("tester_world")) {
            Location location = player.getLocation();
            location.setY(100);
            event.setRespawnLocation(location);
            return;
        }

        if (player.getWorld().getName().equals("world_star")) {
            event.setRespawnLocation(new Location(Bukkit.getWorld("world_star"), 0, 51, -143));
            return;
        }

        if (player.getWorld().getName().equals("world_speaka")) {
            event.setRespawnLocation(new Location(Bukkit.getWorld("world_speaka"), -216, 4, -794));
            return;
        }

        if (player.getWorld().getName().equals("world_fukie")) {
            if (StaticInt.teamLife <= 0) {
                for (Player p : StaticLocations.fukieWorld.getPlayers()) {
                    event.setRespawnLocation(StaticLocations.smith);
                    p.teleport(StaticLocations.smith);
                    p.sendMessage("การตายของทีมครบ 3 ครั้งแล้ว");
                    p.sendMessage("จึงต้องเล่นด่านแรกใหม่");
                    p.getInventory().clear();
                }
                StaticFlags.performingLevel = 1;
                plugin.getConfig().set("performing_level", StaticFlags.performingLevel);
                plugin.saveConfig();
                StaticInt.teamLife = 3;
                return;
            }
            StaticInt.teamLife--;
            SendMsg.send(StaticLocations.fukieWorld, ChatColor.RED + "จำนวนการตายของทีมคงเหลือ: " + StaticInt.teamLife);
            player.sendMessage(ChatColor.RED + "จำนวนการตายของทีมคงเหลือ: " + StaticInt.teamLife);
            Location coolDown = new Location(Bukkit.getWorld("world_fukie"), 238, -23, -175);
            Location smith = new Location(Bukkit.getWorld("world_fukie"), 290, -33, -159, -180, 0);
            event.setRespawnLocation(coolDown);
            ScoreboardPlayer.clear(player);
            player.sendMessage("Waiting for cool down time");
            BukkitTask down = new BukkitRunnable() {
                @Override
                public void run() {
                    player.teleport(smith);

                }
            }.runTaskLater(plugin, 200);
            return;
        }
        Location location = new Location(Bukkit.getWorld("world"), 36, 5, 23);
        if (player.getBedSpawnLocation() == null || player.getBedSpawnLocation().getWorld() == StaticLocations.fukieWorld) {
            event.setRespawnLocation(location);
            player.sendMessage(ChatColor.GREEN + "You have been rescued and come back in Spaceship");
            return;
        }
    }

    @EventHandler
    public void changeWorld(PlayerChangedWorldEvent event) {
        playWelcomeMusic(event.getPlayer());
    }

    private void playWelcomeMusic(Player player) {
        player.stopAllSounds();
        if (player.getWorld().getName().equals("world")) {
            player.playSound(player, Sound.MUSIC_UNDER_WATER, 0.3f, 1);
        }
    }
}
