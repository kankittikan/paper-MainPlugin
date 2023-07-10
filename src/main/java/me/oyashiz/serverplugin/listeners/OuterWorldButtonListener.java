package me.oyashiz.serverplugin.listeners;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.commands.OutHubLightCommand;
import me.oyashiz.serverplugin.commands.SpaceshipLightCommand;
import me.oyashiz.serverplugin.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Objects;

public class OuterWorldButtonListener implements Listener {
    private final MainPlugin plugin;

    public OuterWorldButtonListener(MainPlugin plugin) {
        this.plugin = plugin;
    }

    private void boardCastList(ArrayList<Player> players, String s) {
        for(Player player : players) {
            player.sendMessage(s);
        }
    }

    private void checkButton(PlayerInteractEvent event, String world, int x, int y, int z){
        if(Objects.requireNonNull(event.getClickedBlock()).getWorld() == Bukkit.getWorld(world)) {
            Location location = event.getClickedBlock().getLocation();
            if (location.getBlockX() == x && location.getBlockY() == y && location.getBlockZ() == z) {
                boardCastList(StaticLists.playerDepartureB147, "Requesting for departure");
                if (StaticFlags.TravelFlag == 1) {
                    BukkitTask task = new BukkitRunnable() {
                        @Override
                        public void run() {
                            boardCastList(StaticLists.playerDepartureB147, ChatColor.RED + "Request denied, gate is using");
                            return;
                        }
                    }.runTaskLater(plugin, 50);
                } else {
                    StaticFlags.TravelFlag = 1;
                    BukkitTask task = new BukkitRunnable() {
                        @Override
                        public void run() {
                            if(StaticLists.playerDepartureB147.isEmpty()) {
                                StaticFlags.TravelFlag = 0;
                                return;
                            }
                            boardCastList(StaticLists.playerDepartureB147, ChatColor.GREEN + "Request approved, you will be depart in 30 seconds");
                            SpaceshipLightCommand spaceshipLightCommand = new SpaceshipLightCommand(plugin);
                            OutHubLightCommand outHubLightCommand = new OutHubLightCommand(plugin);
                            spaceshipLightCommand.setLight("on");
                            outHubLightCommand.setLight("on");
                            BukkitTask goSpace = new BukkitRunnable() {
                                @Override
                                public void run() {
                                    for(Player p : StaticLists.playerDepartureB147) {
                                        p.teleport(StaticLocations.arrivalOut);
                                    }
                                }
                            }.runTaskLater(plugin, 50);
                            BukkitTask goSpaceShip = new BukkitRunnable() {
                                @Override
                                public void run() {
                                    for(Player p : StaticLists.playerDepartureB147) {
                                        p.teleport(StaticLocations.spaceship);
                                    }
                                    boardCastList(StaticLists.playerDepartureB147, "Welcome to Space Hub");
                                    boardCastList(StaticLists.playerDepartureB147, ChatColor.YELLOW + "Hatch will be opened in a minute");
                                    BukkitTask hatchOpen = new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            SpaceshipHatch.openHatch();
                                            boardCastList(StaticLists.playerDepartureB147, ChatColor.GREEN + "Opening Hatch!!");

                                            StaticFlags.TravelFlag = 0;
                                            StaticLists.playerDepartureB147.clear();
                                            spaceshipLightCommand.setLight("off");
                                            outHubLightCommand.setLight("off");
                                        }
                                    }.runTaskLater(plugin, 100);
                                }
                            }.runTaskLater(plugin, 300);
                        }
                    }.runTaskLater(plugin, 50);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event) {
        try{
            checkButton(event, "world_b147", -1035, 112, 178);

        }
        catch (NullPointerException ignored) {}
    }
}
