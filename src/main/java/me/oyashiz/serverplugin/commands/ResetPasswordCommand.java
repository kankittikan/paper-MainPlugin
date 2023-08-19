package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.utils.ConfigReader;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ResetPasswordCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(strings.length == 1) {
            Player target = Bukkit.getPlayerExact(strings[0]);
            if(target == null) return false;
            MainPlugin.passwordConfig.getConfig().set(target.getName(), null);
            commandSender.sendMessage(Component.text("Reset completed"));
            ConfigReader.save(MainPlugin.passwordConfig);
            target.kick(Component.text("Your password has been reset"));
        }
        return false;
    }
}
