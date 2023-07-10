package me.oyashiz.serverplugin.tasks.level3;

import me.oyashiz.serverplugin.tasks.NBT;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Husk;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CommonLevel3CreeperNBT implements NBT {
    @Override
    public void setData(Entity entity) {
        Creeper a = (Creeper) entity;
        a.setMaxHealth(26);
        a.setHealth(26);

        a.setCustomName("Torpedo");
        a.setCustomNameVisible(true);

        a.setRemoveWhenFarAway(false);
    }
}
