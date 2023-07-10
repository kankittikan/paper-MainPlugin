package me.oyashiz.serverplugin.tasks.level5;

import me.oyashiz.serverplugin.tasks.NBT;
import me.oyashiz.serverplugin.utils.Enchant;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Monster;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommonLevel5ZombieNBT implements NBT {
    @Override
    public void setData(Entity entity) {
        ZombieVillager zombieVillager = (ZombieVillager) entity;

        ItemStack leather_chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack leather_pant = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack leather_boot = new ItemStack(Material.LEATHER_BOOTS);

        ItemStack stone_pix = new ItemStack(Material.STONE_PICKAXE);

        ItemMeta meta = leather_chest.getItemMeta();

        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);

        leather_chest.setItemMeta(meta);
        leather_pant.setItemMeta(meta);
        leather_boot.setItemMeta(meta);

        zombieVillager.getEquipment().setItemInMainHand(stone_pix);
        zombieVillager.getEquipment().setChestplate(leather_chest);
        zombieVillager.getEquipment().setLeggings(leather_pant);
        zombieVillager.getEquipment().setBoots(leather_boot);

        zombieVillager.setMaxHealth(26);
        zombieVillager.setHealth(26);

        zombieVillager.setCustomName("Bandit");
        zombieVillager.setCustomNameVisible(true);

        zombieVillager.setRemoveWhenFarAway(false);
    }
}
