package me.oyashiz.serverplugin.utils;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Effect {
    private PotionEffect potionEffect;

    public Effect(PotionEffectType effect, int time, int amp) {
        this.potionEffect = new PotionEffect(effect, time, amp);
    }

    public PotionEffect getEffect() {
        return potionEffect;
    }
}
