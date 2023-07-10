package me.oyashiz.serverplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class StaticLocations {
    public static Location arrivalOut = new Location(Bukkit.getWorld("world"), 78, 13, -3, 37, 20);
    public static Location spaceship = new Location(Bukkit.getWorld("world"), 62, -8, 29);
    public static Location home = new Location(Bukkit.getWorld("world"), 22, 33, 34);

    public static World fukieWorld = Bukkit.getServer().getWorld("world_fukie");

    public static World survivalWorld = Bukkit.getServer().getWorld("world_survival");


    public static World world = Bukkit.getServer().getWorld("world");

    public static Location smith = new Location(Bukkit.getServer().getWorld("world_fukie"), 288, -34, -161);

}
