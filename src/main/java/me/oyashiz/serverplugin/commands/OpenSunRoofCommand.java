package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.tasks.FillBlock;
import me.oyashiz.serverplugin.tasks.PlaySound;
import me.oyashiz.serverplugin.utils.StaticLocations;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class OpenSunRoofCommand implements CommandExecutor {

    private final MainPlugin plugin;

    public OpenSunRoofCommand(MainPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1) {
            int[] x = {11, -7};
            int[] z = {77, 103};
            if(args[0].equals("on")) {
                FillBlock.fill(StaticLocations.world, 9, 15, 93, -5, 15, 79, Material.TINTED_GLASS, plugin);
                PlaySound.playInArea(x, z, StaticLocations.world, Sound.BLOCK_PISTON_CONTRACT, 1);
            }
            if(args[0].equals("off")) {
                FillBlock.fill(StaticLocations.world, 9, 15, 93, -5, 15, 79, Material.IRON_BLOCK, plugin);
                PlaySound.playInArea(x, z, StaticLocations.world, Sound.BLOCK_PISTON_EXTEND, 1);
            }
        }
        return false;
    }
}
