package me.oyashiz.serverplugin.tasks.level1;

import me.oyashiz.serverplugin.tasks.NBT;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommonLevel1NBT implements NBT {

    @Override
    public void setData(Entity entity) {
        if (!(entity instanceof Monster)) return;
        try {
            Monster monster = (Monster) entity;

            ItemStack boot = new ItemStack(Material.LEATHER_BOOTS);
            ItemStack legging = new ItemStack(Material.LEATHER_LEGGINGS);

            ItemMeta bootMeta = boot.getItemMeta();
            ItemMeta leggingMeta = legging.getItemMeta();

            bootMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, false);
            leggingMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, false);

            boot.setItemMeta(bootMeta);
            legging.setItemMeta(leggingMeta);

            monster.getEquipment().setBoots(boot);
            monster.getEquipment().setLeggings(legging);

            monster.setMaxHealth(26);
            monster.setHealth(26);

            monster.setCustomName("rob_" + monster.getType());
            monster.setCustomNameVisible(true);

            monster.setPersistent(true);
            monster.setRemoveWhenFarAway(false);
        } catch (NullPointerException ignored) {
        }
    }
}
