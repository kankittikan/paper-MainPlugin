package me.oyashiz.serverplugin.tasks.level4;

import me.oyashiz.serverplugin.tasks.NBT;
import me.oyashiz.serverplugin.utils.Enchant;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommonLevel4ZombieNBT implements NBT {
    @Override
    public void setData(Entity entity) {
        Zombie zombie = (Zombie) entity;
        zombie.setMaxHealth(32);
        zombie.setHealth(32);

        Enchant enchant = new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3);

        ItemStack leather_chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack leather_pant = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack leather_boot = new ItemStack(Material.LEATHER_BOOTS);

        ItemMeta mc = leather_chest.getItemMeta();
        ItemMeta mp = leather_pant.getItemMeta();
        ItemMeta mb = leather_boot.getItemMeta();

        mc.addEnchant(enchant.getEnchantment(), enchant.getLevel(), true);
        mp.addEnchant(enchant.getEnchantment(), enchant.getLevel(), true);
        mb.addEnchant(enchant.getEnchantment(), enchant.getLevel(), true);

        leather_chest.setItemMeta(mc);
        leather_pant.setItemMeta(mp);
        leather_boot.setItemMeta(mb);

        zombie.getEquipment().setChestplate(leather_chest);
        zombie.getEquipment().setLeggings(leather_pant);
        zombie.getEquipment().setBoots(leather_boot);
        zombie.getEquipment().setItemInMainHand(new ItemStack(Material.WOODEN_AXE));

        zombie.setCustomName("Sergeant");
        zombie.setCustomNameVisible(true);

        zombie.setRemoveWhenFarAway(false);
    }
}
