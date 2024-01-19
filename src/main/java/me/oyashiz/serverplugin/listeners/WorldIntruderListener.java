package me.oyashiz.serverplugin.listeners;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.commands.OutHubLightCommand;
import me.oyashiz.serverplugin.commands.SpaceshipLightCommand;
import me.oyashiz.serverplugin.tasks.RocketLight;
import me.oyashiz.serverplugin.utils.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WorldIntruderListener implements Listener {

    @EventHandler
    public void onButtonClick(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        Block block = event.getClickedBlock();
        if (block.getLocation().equals(new Location(Bukkit.getWorld("world_intruder"), 76, 6, 43)) && block.getType() == Material.OAK_BUTTON) {
            event.getPlayer().teleport(new Location(Bukkit.getWorld("world_intruder"), 62, -8, 26));
        }
        if (block.getLocation().equals(new Location(Bukkit.getWorld("world_intruder"), 62, -7, 18)) && block.getType() == Material.OAK_BUTTON) {

            if (!event.getPlayer().isOp()) return;
            Bukkit.dispatchCommand(event.getPlayer(), "setwaypoint");
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.playSound(player.getLocation(), "engine", 0.5f, 1);
            }

            new BukkitRunnable() {
                @Override
                public void run() {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100, 1));
                        BukkitTask task3 = new BukkitRunnable() {
                            @Override
                            public void run() {
                                OutHubLightCommand outHubLightCommand = new OutHubLightCommand(MainPlugin.getMainPlugin());
                                SpaceshipLightCommand spaceshipLightCommand = new SpaceshipLightCommand(MainPlugin.getMainPlugin());
                                spaceshipLightCommand.setLight("on");
                                outHubLightCommand.setLight("on");
                                SpaceshipHatch.closeHatch();
                                BukkitTask goSpace = new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        for (Player p : Bukkit.getOnlinePlayers()) {
                                            p.teleport(StaticLocations.arrivalOut);
                                            p.setGameMode(GameMode.ADVENTURE);
                                            p.showTitle(Title.title(Component.text("one year later"), Component.empty()));
                                        }
                                    }
                                }.runTaskLater(MainPlugin.getMainPlugin(), 50);
                                BukkitTask goSpaceShip = new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        for (Player p : Bukkit.getOnlinePlayers()) {
                                            p.teleport(StaticLocations.spaceship);
                                        }
                                        SendMsg.sendList((List<Player>) Bukkit.getOnlinePlayers(), "Welcome to Space Hub");
                                        SendMsg.sendList((List<Player>) Bukkit.getOnlinePlayers(), ChatColor.YELLOW + "Hatch will be opened in a minute");
                                        BukkitTask hatchOpen = new BukkitRunnable() {
                                            @Override
                                            public void run() {
                                                SpaceshipHatch.openHatch();
                                                SendMsg.sendList((List<Player>) Bukkit.getOnlinePlayers(), ChatColor.GREEN + "Opening Hatch!!");

                                                spaceshipLightCommand.setLight("off");
                                                outHubLightCommand.setLight("off");

                                            }
                                        }.runTaskLater(MainPlugin.getMainPlugin(), 100);

                                    }
                                }.runTaskLater(MainPlugin.getMainPlugin(), 400);
                            }
                        }.runTaskLater(MainPlugin.getMainPlugin(), 40);
                    }
                }
            }.runTaskLater(MainPlugin.getMainPlugin(), 240);
        }
    }
}
