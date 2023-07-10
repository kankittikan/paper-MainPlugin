package me.oyashiz.serverplugin.listeners;

import me.oyashiz.serverplugin.MainPlugin;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;

import java.util.ArrayList;

public class MinecartMoveListener implements Listener {
    private final MainPlugin plugin;

    private ArrayList<ArrayList<Minecart>> minecartChain;

    private World world;

    public MinecartMoveListener(MainPlugin plugin) {
        this.plugin = plugin;
        world = Bukkit.getWorld("tester_world");
    }

    @EventHandler
    public void minecartMove(VehicleMoveEvent event) {
        if (event.getVehicle() instanceof Minecart) {
            Minecart minecart = (Minecart) event.getVehicle();
            if (minecart.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.IRON_BLOCK) {
                minecart.setMaxSpeed(2);
            } else {
                minecart.setMaxSpeed(0.4);
            }
            minecart.setSlowWhenEmpty(false);
            if (!event.getTo().getChunk().equals(event.getFrom().getChunk())) {
                event.getTo().getChunk().setForceLoaded(true);
                Chunk chunk = event.getFrom().getChunk();
                for (Entity entity : chunk.getEntities()) {
                    if (entity instanceof Minecart) return;
                }
                chunk.setForceLoaded(false);
            }
        }
    }

    @EventHandler
    public void minecartSpawn(VehicleCreateEvent event) {
        event.getVehicle().getLocation().getChunk().setForceLoaded(true);

    }

    @EventHandler
    public void minecartDeSpawn(VehicleDestroyEvent event) {
        Chunk chunk = event.getVehicle().getLocation().getChunk();
        for (Entity entity : chunk.getEntities()) {
            if (entity instanceof Minecart) return;
        }
        chunk.setForceLoaded(false);
    }
}
