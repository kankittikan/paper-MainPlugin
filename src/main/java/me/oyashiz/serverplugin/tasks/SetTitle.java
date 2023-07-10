package me.oyashiz.serverplugin.tasks;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class SetTitle {
    public static void set(String title, String subtitle, World world) {
        for (Player player : world.getPlayers()) {
            player.sendTitle(title, subtitle);
        }
    }
}
