package me.oyashiz.serverplugin.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

public class AuthListener implements Listener {
    public static List<Player> players = new ArrayList<>();

    public static void addPlayer(Player player) {
        if(!players.contains(player)) {
            player.setInvulnerable(true);
            players.add(player);
        }
    }

    public static void removePlayer(Player player) {
        player.setInvulnerable(false);
        player.sendMessage(Component.text("เข้าสู่ระบบสำเร็จ").color(TextColor.fromHexString("#D2E3C8")));
        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE, 1, 1);
        players.remove(player);
    }

    @EventHandler
    public void move(PlayerMoveEvent event) {
        if(players.contains(event.getPlayer())) event.setCancelled(true);
    }

    @EventHandler
    public void drop(PlayerDropItemEvent event) {
        if(players.contains(event.getPlayer())) event.setCancelled(true);
    }
}
