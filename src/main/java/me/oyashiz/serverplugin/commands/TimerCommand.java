package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.MainPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class TimerCommand implements CommandExecutor {
    private final MainPlugin plugin;

    public TimerCommand(MainPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof BlockCommandSender) {
            if(args.length == 1) {
                BlockCommandSender blockCommandSender = (BlockCommandSender) sender;
                int mSec = Integer.parseInt(args[0]) / 50;
                Location location = blockCommandSender.getBlock().getLocation();
                location.add(0, 2, 0);
                BukkitTask task = new BukkitRunnable() {
                    @Override
                    public void run() {
                        location.getBlock().setType(Material.REDSTONE_BLOCK);

                        BukkitTask t1 = new BukkitRunnable() {
                            @Override
                            public void run() {
                                location.getBlock().setType(Material.AIR);

                            }
                        }.runTaskLater(plugin, 10);

                    }
                }.runTaskLater(plugin, mSec);
            }
        }
        return false;
    }
}
