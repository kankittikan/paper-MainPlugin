package me.oyashiz.serverplugin.tasks.level3;

import me.oyashiz.serverplugin.tasks.NBT;
import me.oyashiz.serverplugin.utils.Attribute;
import me.oyashiz.serverplugin.utils.Effect;
import org.bukkit.Material;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Husk;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.Metadatable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class CommonLevel3HuskNBT implements NBT {
    @Override
    public void setData(Entity entity) {
        Husk husk = (Husk) entity;
        husk.setMaxHealth(26);
        husk.setHealth(26);

        ItemStack chain_pant = new ItemStack(Material.CHAINMAIL_LEGGINGS);
        ItemStack chain_teen = new ItemStack(Material.CHAINMAIL_BOOTS);

        ItemMeta chain_pant_meta = chain_pant.getItemMeta();
        ItemMeta chain_teen_meta = chain_teen.getItemMeta();

        chain_pant_meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        chain_teen_meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);

        chain_pant.setItemMeta(chain_pant_meta);
        chain_teen.setItemMeta(chain_teen_meta);

        husk.getEquipment().setLeggings(chain_pant);
        husk.getEquipment().setBoots(chain_teen);

        husk.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));

        husk.setCustomName("Torpedo");
        husk.setCustomNameVisible(true);

        husk.setRemoveWhenFarAway(false);
    }
}
