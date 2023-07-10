package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.tasks.PlaySound;
import me.oyashiz.serverplugin.utils.StaticLocations;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class EjectCommand implements CommandExecutor {

    private final MainPlugin plugin;

    public EjectCommand(MainPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (args.length == 1) {
            if (!args[0].equals("880022")) {
                player.sendMessage(ChatColor.RED + "Wrong password");
                return false;
            }
            for (int i = 28; i <= 39; i++) {
                for (int j = 50; j <= 51; j++) {
                    Location location = new Location(StaticLocations.world, i, 4, j);
                    location.getBlock().setType(Material.AIR);
                }
            }

            PlaySound.play(Sound.BLOCK_PISTON_CONTRACT, StaticLocations.world, 1);
            BukkitTask t1 = new BukkitRunnable() {
                @Override
                public void run() {
                    for (int i = 28; i <= 39; i++) {
                        for (int j = 50; j <= 51; j++) {
                            Location location = new Location(StaticLocations.world, i, 4, j);
                            location.getBlock().setType(Material.GRAY_CONCRETE);
                        }
                    }
                    PlaySound.play(Sound.BLOCK_PISTON_EXTEND, StaticLocations.world, 1);
                }
            }.runTaskLater(plugin, 20);
        }
        return false;
    }
}
