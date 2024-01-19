package me.oyashiz.serverplugin.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ChangeWorldCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(strings.length == 2) {
            List<Player> players = new ArrayList<>();
            if(strings[0].equals("@a")) {
                players.addAll(Bukkit.getOnlinePlayers());
            }
            else {
                Player player = Bukkit.getPlayer(strings[0]);
                if(player == null) {
                    commandSender.sendMessage(Component.text("No Player"));
                    return false;
                }
                players.add(player);
            }
            World world = Bukkit.getWorld(strings[1]);
            if(world == null) {
                commandSender.sendMessage(Component.text("No World"));
                return false;
            }
            for(Player p :players) {
                Location old = p.getLocation();
                old.setWorld(world);
                p.teleport(old);
            }
        }
        return false;
    }
}
