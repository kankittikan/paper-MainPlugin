package me.oyashiz.serverplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SendAdmin {
    public static void sendMsg(String s) {
        for(Player player : Bukkit.getOnlinePlayers()) {
            if(player.isOp()) player.sendMessage(s);
        }
    }
}