package me.oyashiz.serverplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 5) {
            int x = Integer.parseInt(args[0]);
            int y = Integer.parseInt(args[1]);
            int z = Integer.parseInt(args[2]);
            World world = Bukkit.getWorld(args[3]);
            Location location = new Location(world, x, y, z);
            if (args[4].equals("@a")) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.teleport(location);
                }
            } else {
                Player player = Bukkit.getPlayer(args[4]);
                if (player == null) return false;
                player.teleport(location);
            }

        }
        if (sender instanceof Player) {
            if (args.length == 4) {
                int x = Integer.parseInt(args[0]);
                int y = Integer.parseInt(args[1]);
                int z = Integer.parseInt(args[2]);
                World world = Bukkit.getWorld(args[3]);
                Location location = new Location(world, x, y, z);
                ((Player) sender).teleport(location);
            }
        }
        return false;
    }
}
