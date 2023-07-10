package me.oyashiz.serverplugin.utils;

import org.bukkit.enchantments.Enchantment;

public class Enchant {
    private int level;
    private Enchantment enchantment;

    public Enchant(Enchantment enchantment, int level) {
        this.level = level;
        this.enchantment = enchantment;
    }

    public int getLevel() {
        return level;
    }
    public Enchantment getEnchantment() {
        return enchantment;
    }
}
