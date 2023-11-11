package me.oyashiz.serverplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;

public class EndPortalListener implements Listener {
    @EventHandler
    public void itemEndPortal(EntityPortalEvent event) {
        if(event.getFrom().getWorld().getEnvironment() == World.Environment.THE_END) {
            event.setTo(new Location(Bukkit.getWorld("world_survival"), -801, 100, -500));
        }
    }
}
