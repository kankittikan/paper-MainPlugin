package me.oyashiz.serverplugin.commands;

import com.comphenix.protocol.PacketType;
import me.oyashiz.serverplugin.MainPlugin;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Pose;

public class TestCommand implements CommandExecutor {
    private final MainPlugin plugin;

    public TestCommand(MainPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        for(Entity e : ((Player) sender).getNearbyEntities(2, 2, 2)) e.setSilent(true);
        return false;
    }
}
