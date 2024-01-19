package me.oyashiz.serverplugin.listeners;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.tasks.PlaySound;
import me.oyashiz.serverplugin.utils.LiftState;
import me.oyashiz.serverplugin.utils.SendAdmin;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.BoundingBox;

public class HubLiftListener implements Listener {
    private Minecart minecart;
    private World world = Bukkit.getWorld("world");
    private Location spawnLocation;
    private Location bottom = new Location(world, 22.5, -30, 34.5);
    private Location ceiling = new Location(world, 22.5, -1, 34.5);
    private boolean moving = false;
    private LiftState liftState = LiftState.DOWN;
    private Player player;

    private BukkitTask moveTask;

    private Location bound1 = new Location(world, 32, 1, 45);
    private Location bound2 = new Location(world, 11, -30, 22);

    private BoundingBox boundingBox = BoundingBox.of(bound1.getBlock(), bound2.getBlock());

    private void playSoundMoving() {
        for(Player p : world.getPlayers()) {
            if(boundingBox.contains(p.getBoundingBox())) p.playSound(p, "elevator", 0.8f, 1);
        }
    }
    private void stopSoundMoving() {
        for(Player p : world.getPlayers()) {
            p.stopSound("elevator");
        }
    }

    private void playEndSound() {
        for(Player p : world.getPlayers()) {
            if(boundingBox.contains(p.getBoundingBox())) p.playSound(p, Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);
        }
    }

    private void liftUp() {
        if(liftState == LiftState.UP) return;
        if(moveTask != null) moveTask.cancel();
        moving = true;

        playSoundMoving();
        moveTask = new BukkitRunnable() {
            private Location location = minecart.getLocation();
            @Override
            public void run() {
                location.setY(location.getY() + 0.1);
                if(location.getY() >= ceiling.getY()) {
                    moving = false;
                    liftState = LiftState.UP;
                    Location drop = new Location(world, 22, -1, 36);
                    if(player != null) player.teleport(drop);
                    player = null;
                    playEndSound();
                    stopSoundMoving();
                    cancel();
                }
                if(player != null) player.teleport(location);
                minecart.teleport(location);
            }
        }.runTaskTimer(MainPlugin.getMainPlugin(), 0, 1);
    }

    private void liftDown() {
        if(liftState == LiftState.DOWN) return;
        if(moveTask != null) moveTask.cancel();
        moving = true;

        playSoundMoving();
        moveTask = new BukkitRunnable() {
            private Location location = minecart.getLocation();

            @Override
            public void run() {
                location.setY(location.getY() - 0.1);
                if(location.getY() <= bottom.getY()) {
                    moving = false;
                    liftState = LiftState.DOWN;
                    Location drop = new Location(world, 22, -29, 36);
                    if(player != null) player.teleport(drop);
                    player = null;
                    playEndSound();
                    stopSoundMoving();
                    cancel();
                }
                minecart.teleport(location);
                if(player != null) player.teleport(location);
            }
        }.runTaskTimer(MainPlugin.getMainPlugin(), 0, 1);
    }

    public HubLiftListener() {
        spawnLocation = bottom;

        for(Minecart m : world.getEntitiesByClass(Minecart.class)) {
            if(m.getLocation().getChunk().getX() == 1 && m.getChunk().getZ() == 2) {
                m.remove();
            }
        }
        minecart = (Minecart) world.spawnEntity(spawnLocation, EntityType.MINECART);
        minecart.customName(Component.text("hublift"));
        minecart.setGravity(false);
    }

    @EventHandler
    public void button(PlayerInteractEvent event) {
        if(moving) return;
        if(event.getClickedBlock() == null || event.getClickedBlock().getType() != Material.STONE_BUTTON) return;
        Location topButton = new Location(world, 23, 0, 35);
        Location downButton = new Location(world, 23, -29, 35);

        if(event.getClickedBlock().getLocation().equals(topButton)) {
            liftUp();
        }
        if(event.getClickedBlock().getLocation().equals(downButton)) {
            liftDown();
        }
    }

    @EventHandler
    public void playerEnter(VehicleEnterEvent event) {
        if(!(event.getEntered() instanceof Player)) return;
        if(!(event.getVehicle() instanceof Minecart)) return;
        if(event.getVehicle() != minecart) return;
        if(moving) {
            event.setCancelled(true);
            return;
        }

        minecart.removePassenger(event.getEntered());
        player = (Player) event.getEntered();

        if(liftState == LiftState.UP) liftDown();
        if(liftState == LiftState.DOWN) liftUp();
    }
}
