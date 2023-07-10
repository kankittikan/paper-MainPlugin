package me.oyashiz.serverplugin.listeners;

import me.oyashiz.serverplugin.utils.StaticFlags;
import me.oyashiz.serverplugin.utils.StaticLists;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import java.util.Objects;

public class GateButtonListener implements Listener {
    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event) {
        try {
            if(Objects.requireNonNull(event.getClickedBlock()).getWorld() != Bukkit.getWorld("world")) return;
            Location location = Objects.requireNonNull(event.getClickedBlock()).getLocation();

            if (location.getBlockX() == 76 && location.getBlockY() == 6 && location.getBlockZ() == 43 && location.getBlock().getType() == Material.STONE_BUTTON) {
                if (StaticFlags.DepartFlag == 1) {
                    event.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 62, -8, 29));
                    event.getPlayer().sendMessage("You are in Spaceship. Be ready!!");
                    StaticLists.playerDeparture.add(event.getPlayer());
                }
                else{
                    event.getPlayer().sendMessage(ChatColor.RED + "Departure Gate not available");
                }
            }

        } catch (NullPointerException ignored) {

        }
    }
}
