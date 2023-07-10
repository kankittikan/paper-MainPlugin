package me.oyashiz.serverplugin.tasks;

import me.oyashiz.serverplugin.utils.StaticFlags;
import me.oyashiz.serverplugin.utils.StaticLocations;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;

public class SignLevel {
    public static void change() {
        Location location = new Location(StaticLocations.fukieWorld, 289, -33, -156);
        Sign sign = (Sign) location.getBlock().getState();
        sign.setLine(2, ChatColor.AQUA + "Level: " + StaticFlags.performingLevel);
        sign.update();
    }
}
