package me.oyashiz.serverplugin.tasks;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.utils.SendMsg;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class ResourcePack {

    public static void set(Player player) {
        player.setResourcePack(MainPlugin.resourcePack, null, (Component) null, true);
    }

    public static void setTest(Player player) {
        player.setResourcePack(MainPlugin.testPack, null, (Component) null, true);
    }

    public static void setAll() {
        SendMsg.sendList((List<Player>) Bukkit.getOnlinePlayers(), "Reloading resource pack");
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setResourcePack(MainPlugin.resourcePack, null, (Component) null, true);
        }
    }
}
