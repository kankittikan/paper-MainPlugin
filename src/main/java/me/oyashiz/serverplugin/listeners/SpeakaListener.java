package me.oyashiz.serverplugin.listeners;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.utils.SaveInventory;
import me.oyashiz.serverplugin.utils.SendAdmin;
import me.oyashiz.serverplugin.utils.StaticLocations;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.io.IOException;

public class SpeakaListener implements Listener {

    private SaveInventory saveInventory;
    private final MainPlugin plugin;

    public SpeakaListener(MainPlugin plugin) {
        this.plugin = plugin;
        saveInventory = new SaveInventory(plugin);
    }

    @EventHandler
    public void interact(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null) {
            if (event.getPlayer().getWorld().getName().equals("world_speaka")) {
                if (event.getClickedBlock().getLocation().equals(new Location(Bukkit.getWorld("world_speaka"), -206, 2, -794))) {
                    try {
                        saveInventory.saveInventory(event.getPlayer(), "world_speaka");
                        event.getPlayer().getInventory().clear();
                        saveInventory.restoreInventory(event.getPlayer(), "world");
                    } catch (IOException ex) {
                        SendAdmin.sendMsg(ex.getMessage());
                    }
                    event.getPlayer().teleport(StaticLocations.home);
                    event.getPlayer().setGameMode(GameMode.ADVENTURE);
                }
            }
        }
    }
}
