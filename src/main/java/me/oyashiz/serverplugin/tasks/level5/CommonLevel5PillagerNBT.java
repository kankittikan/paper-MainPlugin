package me.oyashiz.serverplugin.tasks.level5;

import me.oyashiz.serverplugin.tasks.NBT;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Pillager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommonLevel5PillagerNBT implements NBT {
    @Override
    public void setData(Entity entity) {
        Pillager pillager = (Pillager) entity;

        ItemStack bow = new ItemStack(Material.CROSSBOW);

        ItemMeta meta = bow.getItemMeta();

        meta.addEnchant(Enchantment.PIERCING, 2, true);

        bow.setItemMeta(meta);

        pillager.getEquipment().setItemInMainHand(bow);

        pillager.setMaxHealth(35);
        pillager.setHealth(35);

        pillager.setCustomName("Bandit");
        pillager.setCustomNameVisible(true);

        pillager.setRemoveWhenFarAway(false);
    }
}
