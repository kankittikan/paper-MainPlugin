package me.oyashiz.serverplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class AnnouncementCommand implements CommandExecutor {

    private ArrayList<String> strings = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        strings.clear();
        for (String s : args) {
            strings.add(s.replaceAll("-", " "));
        }

        for (Player player1 : Bukkit.getOnlinePlayers()) {
            player1.sendTitle(ChatColor.YELLOW + "announcement", "please check your chat box");
            player1.sendMessage(ChatColor.YELLOW + "Announcement from administrator");
            for (int i = 0; i < strings.size(); i++) {
                player1.sendMessage(i + 1 + ".) " + strings.get(i));
            }
            player1.sendMessage(" ");
            player1.playSound(player1.getLocation(), "pa", 0.2f, 1);
        }
        return false;
    }

    public static void broadcast(ArrayList<String> s) {
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            player1.sendTitle(ChatColor.YELLOW + "announcement", "please check your chat box");
            player1.sendMessage(ChatColor.YELLOW + "Announcement from administrator");
            for (int i = 0; i < s.size(); i++) {
                player1.sendMessage(i + 1 + ".) " + s.get(i));
            }
            player1.sendMessage(" ");
            player1.playSound(player1.getLocation(), "pa", 0.2f, 1);
        }
    }

    public static void broadcastPlayer(Player p, ArrayList<String> s) {
        if(!p.isOnline()) return;
        p.sendTitle(ChatColor.YELLOW + "announcement", "please check your chat box");
        p.sendMessage(ChatColor.YELLOW + "Announcement from administrator");
        for (int i = 0; i < s.size(); i++) {
            p.sendMessage(i + 1 + ".) " + s.get(i));
        }
        p.sendMessage(" ");
        p.playSound(p.getLocation(), "pa", 0.2f, 1);
    }
}
