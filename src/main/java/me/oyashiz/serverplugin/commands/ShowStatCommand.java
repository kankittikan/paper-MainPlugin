package me.oyashiz.serverplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;

public class ShowStatCommand implements CommandExecutor{
    private Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    private Objective objective = scoreboard.registerNewObjective("stat", "");

    private ArrayList<String> stats = new ArrayList<>();

    public ShowStatCommand() {
        for(Statistic s : Statistic.values()) {
            stats.add(s.toString());
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1) {
            if(args[0].equals("ping")) {
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                objective.setDisplayName("Ping From Server");
                for(Player p : Bukkit.getOnlinePlayers()) {
                    objective.getScore(p.getName()).setScore(p.getPing());
                }
                for(Player p : Bukkit.getOnlinePlayers()) {
                    p.setScoreboard(scoreboard);
                }
                return false;
            }
            if(stats.contains(args[0])) {
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                objective.setDisplayName(args[0].replaceAll("_", " "));
                for(Player p : Bukkit.getOnlinePlayers()) {
                    objective.getScore(p.getName()).setScore(p.getStatistic(Statistic.valueOf(args[0])));
                }
                for(Player p : Bukkit.getOnlinePlayers()) {
                    p.setScoreboard(scoreboard);
                }
            }

            for(OfflinePlayer p : Bukkit.getOfflinePlayers()) {
                scoreboard.resetScores(p.getName());
            }

        }
        if(args.length == 0) {
            for(Player p : Bukkit.getOnlinePlayers()) {
                p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
            }
            for(OfflinePlayer p : Bukkit.getOfflinePlayers()) {
                scoreboard.resetScores(p.getName());
            }
        }
        return false;
    }
}
