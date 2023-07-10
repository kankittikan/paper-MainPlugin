package me.oyashiz.serverplugin.tasks.level3;

import me.oyashiz.serverplugin.tasks.NBT;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Husk;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CommonLevel3EnderNBT implements NBT {
    @Override
    public void setData(Entity entity) {
        Enderman a = (Enderman) entity;
        a.setMaxHealth(45);
        a.setHealth(45);

        a.setCustomName("Torpedo");
        a.setCustomNameVisible(true);

        a.setRemoveWhenFarAway(false);
    }
}
