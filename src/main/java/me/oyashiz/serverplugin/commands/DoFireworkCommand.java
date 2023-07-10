package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.tasks.RandomFireworkNBT;
import me.oyashiz.serverplugin.tasks.RandomSpawn;
import me.oyashiz.serverplugin.utils.StaticLocations;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;

public class DoFireworkCommand implements CommandExecutor {
    private final MainPlugin plugin;

    public DoFireworkCommand(MainPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        RandomSpawn randomSpawn = new RandomSpawn(plugin);
        randomSpawn.spawn(9, 80, -5, 100, 10, StaticLocations.world, EntityType.FIREWORK, 20, new RandomFireworkNBT(), 20);
        return false;
    }
}
