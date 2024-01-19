package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.MainPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class IntruderHubLightingCommand implements CommandExecutor {
    private final MainPlugin plugin;
    private World world;

    private ArrayList<Location> locationArrayList;

    private void loadList() {
        locationArrayList = new ArrayList<>();
        world = Bukkit.getWorld("world_intruder");

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

    public IntruderHubLightingCommand(MainPlugin plugin) {
        this.plugin = plugin;

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
        if (args.length == 1) {
            loadList();
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
