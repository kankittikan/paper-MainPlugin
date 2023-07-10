package me.oyashiz.serverplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;

public class SendMsg {
    public static void send(World world, String s) {
        for(Player player : world.getPlayers()) {
            player.sendMessage(s);
        }
    }

    public static void sendList(List<Player> players, String s) {
        for(Player player : players) {
            player.sendMessage(s);
        }
    }
}
