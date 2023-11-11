package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.MainPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
public class GoCellCommand implements CommandExecutor {
    private final MainPlugin plugin;

    public GoCellCommand(MainPlugin plugin) {
        this.plugin = plugin;
    }

    public static void goCell(Player player) {
        player.sendMessage(ChatColor.RED + "You has been locked");
        player.setGameMode(GameMode.ADVENTURE);
        player.setOp(false);
        Location location = new Location(Bukkit.getWorld("world"), 39 ,5 ,51);
        player.teleport(location);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(args.length == 3) {
            if(!args[2].equals("880022")) {
                player.sendMessage(ChatColor.RED + "Wrong password");
                return false;
            }
            Player target = Bukkit.getPlayerExact(args[0]);
            if(target == null) {
                player.sendMessage(ChatColor.RED + "No player");
                return false;
            }
            if(args[1].equals("on")) {
                target.sendMessage("You are freezing");
                goCell(target);
            }
            if(args[1].equals("all")) {
                for(Player p : Bukkit.getOnlinePlayers()) {
                    if(p != player) goCell(p);
                }
            }
            if(args[1].equals("off")) {
                Location location2 = new Location(Bukkit.getWorld("world"), 39 ,5 ,47);
                target.sendMessage("You are free now");
                BukkitTask task = new BukkitRunnable() {
                    @Override
                    public void run() {
                        target.teleport(location2);

                    }
                }.runTaskLater(plugin, 10);
                BukkitTask task1 = new BukkitRunnable() {
                    @Override
                    public void run() {
                        target.teleport(location2);

                    }
                }.runTaskLater(plugin, 10);
            }
        }
        return false;
    }
}
