package me.oyashiz.serverplugin.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class CheckInArea {
    public static boolean check(Location location, int x, int z, int x1, int z1) {
        int xCheck = location.getBlockX();
        int zCheck = location.getBlockZ();

        int minx = x;
        int maxx = x1;

        int minz = z;
        int maxz = z1;

        if (x > x1) {
            minx = x1;
            maxx = x;
        }
        if (z > z1) {
            minz = z1;
            maxz = z;
        }

        return xCheck >= minx && xCheck <= maxx && zCheck >= minz && zCheck <= maxz;
    }

    public static boolean isPlayerInArea(World world, int x, int z, int x1, int z1) {
        int count = -1;
        for(Player player : world.getPlayers()) {
            if(check(player.getLocation(), x, z, x1, z1)) {
                count++;
            }
        }
        return count > 0;
    }
}
