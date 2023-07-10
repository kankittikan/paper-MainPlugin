package me.oyashiz.serverplugin.utils;

import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class EffectList {
    List<Effect> effectList;

    public EffectList() {
        effectList = new ArrayList<>();
    }

    public void addEffect(Effect effect) {
        effectList.add(effect);
    }

    public List<Effect> getEffectList() {
        return effectList;
    }
}
