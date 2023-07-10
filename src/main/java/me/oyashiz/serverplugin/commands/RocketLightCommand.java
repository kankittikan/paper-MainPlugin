package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.tasks.RocketLight;
import me.oyashiz.serverplugin.utils.CheckInArea;
import me.oyashiz.serverplugin.utils.StaticLocations;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RocketLightCommand implements CommandExecutor {

    private final MainPlugin plugin;
    private RocketLight rocketLight;

    public RocketLightCommand(MainPlugin plugin) {
        this.plugin = plugin;
        rocketLight = new RocketLight(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1) {
            if(args[0].equals("on")) {
                for(Player p : StaticLocations.survivalWorld.getPlayers()) {
                    if(CheckInArea.check(p.getLocation(), -22, 38, 164, -115)) {
                        p.sendTitle(ChatColor.GREEN + "Rocket Activated", "");
                        p.playSound(p, Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE, 1, 1);
                    }
                }
                rocketLight.lightTask(Material.REDSTONE_BLOCK);
            }
            if(args[0].equals("off")) {
                rocketLight.lightTask(Material.AIR);
            }
        }
        return false;
    }
}
