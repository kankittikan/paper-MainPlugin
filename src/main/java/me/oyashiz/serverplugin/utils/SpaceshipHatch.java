package me.oyashiz.serverplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

public class SpaceshipHatch {
    private static final Location location = new Location(Bukkit.getWorld("world"), 64, 3, 35);

    public static void closeHatch(){
        location.getBlock().setType(Material.REDSTONE_BLOCK);
    }

    public static void openHatch(){
        location.getBlock().setType(Material.IRON_BLOCK);
    }
}
