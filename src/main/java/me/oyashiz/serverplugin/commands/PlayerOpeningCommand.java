package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.MainPlugin;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class PlayerOpeningCommand implements CommandExecutor {
    private BukkitTask task;

    private void run(Player player) {
        Location door = new Location(Bukkit.getWorld("world"), 2, 6, 96);
        Location start = new Location(Bukkit.getWorld("world"), 2.5, 1, 96.5, 180, 0);
        Location end = start.clone();
        end.setY(9);

        door.getBlock().setType(Material.AIR);

        task = new BukkitRunnable() {
            @Override
            public void run() {
                player.teleport(start);
                start.setY(start.getY() + 0.05);
                if(start.getY() < door.getY() - 2) end.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, end, 1);
                if(start.getY() >= end.getY()) {
                    cancel();
                    door.getBlock().setType(Material.IRON_BLOCK);
                }
            }
        }.runTaskTimer(MainPlugin.getMainPlugin(), 0, 1);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(task != null) task.cancel();
        if(strings.length == 1) {
            Player player = Bukkit.getPlayer(strings[0]);
            if(player == null) {
                commandSender.sendMessage("Player not found");
                return false;
            }
            run(player);
        }
        return false;
    }
}
