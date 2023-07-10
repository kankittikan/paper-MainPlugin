package me.oyashiz.serverplugin.tasks.level2;

import me.oyashiz.serverplugin.tasks.NBT;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CommonLevel2NBT implements NBT {
    @Override
    public void setData(Entity entity) {
        if (!(entity instanceof Monster)) return;
        try {
            Monster monster = (Monster) entity;

            ItemStack chest = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
            ItemStack legging = new ItemStack(Material.CHAINMAIL_LEGGINGS);
            ItemStack bow = new ItemStack(Material.BOW);

            ItemMeta chestMeta = chest.getItemMeta();
            ItemMeta leggingMeta = legging.getItemMeta();
            ItemMeta bowMeta = bow.getItemMeta();

            chestMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
            leggingMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
            bowMeta.addEnchant(Enchantment.ARROW_KNOCKBACK, 1, true);

            chest.setItemMeta(chestMeta);
            legging.setItemMeta(leggingMeta);
            bow.setItemMeta(bowMeta);

            monster.getEquipment().setChestplate(chest);
            monster.getEquipment().setLeggings(legging);
            monster.getEquipment().setItemInMainHand(bow);

            monster.setMaxHealth(26);
            monster.setHealth(26);

            monster.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 1));

            monster.setCustomName("employee_" + monster.getType());
            monster.setCustomNameVisible(true);

            monster.setPersistent(true);
            monster.setRemoveWhenFarAway(false);
        } catch (NullPointerException ignored) {
        }
    }
}
