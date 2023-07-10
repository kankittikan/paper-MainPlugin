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

public class StarWarListener implements Listener {

    private final MainPlugin plugin;
    private SaveInventory saveInventory;

    public StarWarListener(MainPlugin plugin) {
        this.plugin = plugin;
        saveInventory = new SaveInventory(plugin);
    }

    @EventHandler
    public void interact(PlayerInteractEvent event) {
        if(event.getClickedBlock() != null) {
            if(event.getPlayer().getWorld().getName().equals("world_star")) {
                if(event.getClickedBlock().getLocation().equals(new Location(Bukkit.getWorld("world_star"), 0, 52, -168))) {
                    try {
                        saveInventory.saveInventory(event.getPlayer(), "world_star");
                        event.getPlayer().getInventory().clear();
                        saveInventory.restoreInventory(event.getPlayer(), "world");
                    } catch (IOException e) {
                        SendAdmin.sendMsg("Restore error");
                    }
                    event.getPlayer().teleport(StaticLocations.home);
                    event.getPlayer().setGameMode(GameMode.ADVENTURE);
                }
            }
        }
    }
}
