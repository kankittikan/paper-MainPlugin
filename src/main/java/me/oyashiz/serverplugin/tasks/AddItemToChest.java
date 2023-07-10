package me.oyashiz.serverplugin.tasks;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AddItemToChest {
    public static void add(ItemStack itemStack, Location location) {
        Block block = location.getBlock();
        Chest chest = (Chest) block.getState();
        Inventory inv = chest.getInventory();
        inv.addItem(itemStack);
    }
}
