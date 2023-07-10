package me.oyashiz.serverplugin.listeners;

import me.oyashiz.serverplugin.utils.StaticLists;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.Objects;

public class OuterWorldDoorListener implements Listener {

    private void checkDoor(PlayerInteractEvent event, String world, int x, int y, int z) throws NullPointerException {
        if (Objects.requireNonNull(event.getClickedBlock()).getWorld() == Bukkit.getWorld(world)) {
            Location location = event.getClickedBlock().getLocation();
            if (location.getBlockX() == x && location.getBlockY() == y && location.getBlockZ() == z) {
                event.getPlayer().teleport(new Location(Bukkit.getWorld(world), x + 4, y - 1, z));
                StaticLists.playerDepartureB147.remove(event.getPlayer());
                event.getPlayer().setGameMode(GameMode.SURVIVAL);
            }
            if (location.getBlockX() == x+2 && location.getBlockY() == y && location.getBlockZ() == z) {
                event.getPlayer().teleport(new Location(Bukkit.getWorld(world), x - 1, y + 1, z - 1));
                if (!StaticLists.playerDepartureB147.contains(event.getPlayer()))
                    StaticLists.playerDepartureB147.add(event.getPlayer());
                event.getPlayer().sendMessage(ChatColor.DARK_AQUA + "Welcome to " + world + " Space Capsule");
                event.getPlayer().setGameMode(GameMode.ADVENTURE);
            }
        }
    }

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event) {
        try {
            checkDoor(event, "world_b147", -1032, 112, 181);
        } catch (NullPointerException ignored) {
        }
    }
}
