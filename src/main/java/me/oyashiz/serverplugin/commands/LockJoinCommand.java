package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.utils.SendAdmin;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class LockJoinCommand implements CommandExecutor {
    public static boolean lock = false;

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender.isOp()) {
            if(strings.length == 1) {
                if(strings[0].equals("0") && lock) {
                    lock = false;
                    Bukkit.broadcast(Component.text(commandSender.getName() + " unlocked server"));
                }
                if(strings[0].equals("1")) {
                    lock = true;
                    SendAdmin.sendMsg(commandSender.getName() + " locked server");
                }
            }
        }
        return false;
    }
}
