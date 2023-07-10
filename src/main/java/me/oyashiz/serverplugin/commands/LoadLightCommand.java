package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.tasks.HallLight;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class LoadLightCommand implements CommandExecutor {
    private final MainPlugin plugin;

    public LoadLightCommand(MainPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1) {
            HallLight hallLight = new HallLight();
            switch (args[0]) {
                case "count":
                    hallLight.countDown(plugin);
                    break;
                case "shuffle":
                    hallLight.shuffle(plugin);
                    break;
                case "clear":
                    hallLight.clear(plugin);
                    break;
                default:
                    hallLight.load(args[0], plugin);
                    break;
            }
        }
        return false;
    }
}
