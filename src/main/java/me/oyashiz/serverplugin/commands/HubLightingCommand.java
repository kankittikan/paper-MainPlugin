package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.MainPlugin;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class HubLightingCommand implements CommandExecutor {
    private final MainPlugin plugin;
    private final World world;

    private ArrayList<Location> locationArrayList;

    public HubLightingCommand(MainPlugin plugin) {
        this.plugin = plugin;
        locationArrayList = new ArrayList<>();
        world = Bukkit.getWorld("world");

        for (int x = -3; x < 20; x++) {
            locationArrayList.add(new Location(world, x, 8, 37));
        }
        for (int x = -3; x < 20; x++) {
            locationArrayList.add(new Location(world, x, 8, 31));
        }
        for (int x = 25; x < 48; x++) {
            locationArrayList.add(new Location(world, x, 8, 31));
        }
        for (int x = 25; x < 48; x++) {
            locationArrayList.add(new Location(world, x, 8, 37));
        }
        for (int x = 16; x < 29; x++) {
            locationArrayList.add(new Location(world, x, 13, 41));
        }
        for (int x = 16; x < 29; x++) {
            locationArrayList.add(new Location(world, x, 13, 27));
        }
        for (int z = 37; z < 60; z++) {
            locationArrayList.add(new Location(world, 19, 8, z));
        }
        for (int z = 9; z < 32; z++) {
            locationArrayList.add(new Location(world, 19, 8, z));
        }
        for (int z = 9; z < 32; z++) {
            locationArrayList.add(new Location(world, 25, 8, z));
        }
        for (int z = 37; z < 60; z++) {
            locationArrayList.add(new Location(world, 25, 8, z));
        }
    }

    public void openLight() {
        for (Location location : locationArrayList) {
            location.getBlock().setType(Material.SEA_LANTERN);
        }
    }

    public void closeLight(){
        for (Location location : locationArrayList) {
            location.getBlock().setType(Material.IRON_BLOCK);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (world == null) {
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                if (player.isOp()) {
                    player.sendMessage(ChatColor.RED + "Can not find lobby world");
                }
            }
            return false;
        }
        if (args.length == 1) {
            if (args[0].equals("on")) {
                openLight();
            }
            if (args[0].equals("off")) {
                closeLight();
            }
        }

        return false;
    }
}
