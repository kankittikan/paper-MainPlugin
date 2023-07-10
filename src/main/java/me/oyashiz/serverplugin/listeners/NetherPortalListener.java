package me.oyashiz.serverplugin.listeners;

import me.oyashiz.serverplugin.utils.StaticLocations;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.world.PortalCreateEvent;

public class NetherPortalListener implements Listener {
    @EventHandler
    public void onNetherPortalCreate(PortalCreateEvent event) {
        if (event.getWorld() == StaticLocations.survivalWorld) return;
        if (event.getWorld() == Bukkit.getWorld("world_nether")) return;
        if (event.getWorld() == Bukkit.getWorld("world_the_end")) return;
        event.setCancelled(true);
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getWorld() == event.getWorld()) {
                p.sendMessage(ChatColor.RED + "Unavailable to create portal this world");
            }
        }
    }
    @EventHandler
    public void portal(PlayerPortalEvent event) {
        if (event.getFrom().getWorld().getEnvironment() == World.Environment.NETHER) {
            Location newTo = event.getFrom().multiply(8.0D);
            newTo.setWorld(StaticLocations.survivalWorld);
            event.setTo(newTo);
        }
    }

    @EventHandler
    public void itemPortal(EntityPortalEvent event) {
        if (event.getFrom().getWorld().getEnvironment() == World.Environment.NETHER) {
            Location newTo = event.getFrom().multiply(8.0D);
            newTo.setWorld(StaticLocations.survivalWorld);
            event.setTo(newTo);
        }
    }
}
