package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.tasks.SignLevel;
import me.oyashiz.serverplugin.utils.StaticFlags;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetPerformingLevelCommand implements CommandExecutor {
    private final MainPlugin plugin;

    public SetPerformingLevelCommand(MainPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1) {
            try {
                StaticFlags.performingLevel = Integer.parseInt(args[0]);
                plugin.getConfig().set("performing_level", Integer.parseInt(args[0]));
                plugin.saveConfig();
                sender.sendMessage("Set performing_level to " + args[0]);
                SignLevel.change();
            }
            catch (NumberFormatException e) {
                sender.sendMessage("Can not set performing_level");
            }
        }
        return false;
    }
}
