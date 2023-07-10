package me.oyashiz.serverplugin.utils;

import java.util.ArrayList;
import java.util.List;

public class EnchantList {
    private List<Enchant> enchantList;

    public EnchantList() {
        enchantList = new ArrayList<>();
    }

    public void addEnchant(Enchant enchant) {
        enchantList.add(enchant);
    }

    public List<Enchant> getEnchantList() {
        return enchantList;
    }
}
