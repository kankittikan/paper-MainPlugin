package me.oyashiz.serverplugin.utils;

import me.oyashiz.serverplugin.MainPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SaveInventory {
    private final MainPlugin plugin;

    public SaveInventory(MainPlugin plugin) {
        this.plugin = plugin;
    }

    public void saveInventory(Player p, String world) throws IOException {
        File f = new File(plugin.getDataFolder().getAbsolutePath() + "/" + world, p.getName() + ".yml");
        FileConfiguration c = YamlConfiguration.loadConfiguration(f);
        c.set("inventory.armor", p.getInventory().getArmorContents());
        c.set("inventory.content", p.getInventory().getContents());
        c.save(f);
    }

    @SuppressWarnings("unchecked")
    public void restoreInventory(Player p, String world) throws IOException {
        File f = new File(plugin.getDataFolder().getAbsolutePath() + "/" + world, p.getName() + ".yml");
        if(!f.exists()) return;
        FileConfiguration c = YamlConfiguration.loadConfiguration(f);
        ItemStack[] content = ((List<ItemStack>) c.get("inventory.armor")).toArray(new ItemStack[0]);
        p.getInventory().setArmorContents(content);
        content = ((List<ItemStack>) c.get("inventory.content")).toArray(new ItemStack[0]);
        p.getInventory().setContents(content);
    }
}
