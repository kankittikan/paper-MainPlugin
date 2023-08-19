package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.tasks.ResourcePack;
import me.oyashiz.serverplugin.utils.SendMsg;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class ReloadCommand implements CommandExecutor {
    private final MainPlugin plugin;

    public ReloadCommand(MainPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            SendMsg.sendList((List<Player>) Bukkit.getOnlinePlayers(), ChatColor.YELLOW + sender.getName() + " issued reload command");
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.playSound(p.getLocation(), "alarm", 0.1f, 1);
            }
            BukkitTask t = new BukkitRunnable() {
                int c = 5;

                @Override
                public void run() {
                    SendMsg.sendList((List<Player>) Bukkit.getOnlinePlayers(), ChatColor.YELLOW + "Plugin Reload: " + c--);
                    if (c == -1) {
                        cancel();
                        plugin.getServer().reload();
                        SendMsg.sendList((List<Player>) Bukkit.getOnlinePlayers(), ChatColor.GREEN + "Reload complete");
                    }
                }
            }.runTaskTimer(plugin, 0, 20);
        }

        if (args.length == 1) {
            if (args[0].equals("pack")) {
                ResourcePack.setTest((Player) sender);
            }
        }

        return false;
    }
}