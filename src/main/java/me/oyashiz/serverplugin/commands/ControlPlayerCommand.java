package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.utils.StaticFlags;
import me.oyashiz.serverplugin.utils.StaticLists;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ControlPlayerCommand implements CommandExecutor, Listener {

    private final MainPlugin mainPlugin;

    private static Player playerLock = null;

    private boolean cancel;

    public ControlPlayerCommand(MainPlugin mainPlugin) {
        this.mainPlugin = mainPlugin;
    }

    @EventHandler
    public void playerMove(PlayerMoveEvent event) {
        if(playerLock == event.getPlayer()) event.setCancelled(true);
    }

    @EventHandler
    public void playerQuit(PlayerQuitEvent event) {
        if(playerLock == event.getPlayer()) cancel = true;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player op = (Player) commandSender;
        if(strings.length == 1) {
            Player player = Bukkit.getPlayer(strings[0]);
            if(player == null || op == player) return false;
            playerLock = player;

            StaticFlags.canChangeMode = true;
            op.setGameMode(GameMode.SPECTATOR);
            StaticFlags.canChangeMode = false;

            op.teleport(player);
            cancel = false;
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.teleport(op.getLocation());
                    if(cancel) {
                        cancel();
                        cancel = false;
                        playerLock = null;
                    }
                }
            }.runTaskTimer(mainPlugin, 0, 1);
        }
        else {
            cancel = true;
        }
        return false;
    }
}
