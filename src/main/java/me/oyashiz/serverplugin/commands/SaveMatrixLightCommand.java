package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.tasks.HallLight;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SaveMatrixLightCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        HallLight hallLight = new HallLight();
        hallLight.save(args[0]);
        return false;
    }
}
