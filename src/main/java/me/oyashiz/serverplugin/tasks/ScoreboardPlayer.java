package me.oyashiz.serverplugin.tasks;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardPlayer {
    public static void clearWorld(World world) {
        for (Player player : world.getPlayers()) {
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
    }

    public static void clear(Player player) {
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }

    public static void set(World world, Scoreboard scoreboard) {
        for (Player player : world.getPlayers()) {
            player.setScoreboard(scoreboard);
        }
    }
}
