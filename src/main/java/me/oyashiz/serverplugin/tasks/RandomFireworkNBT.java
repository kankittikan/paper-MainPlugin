package me.oyashiz.serverplugin.tasks;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.block.data.type.Fire;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Random;

public class RandomFireworkNBT implements NBT {
    @Override
    public void setData(Entity entity) {
        if (entity instanceof Firework) {
            Random random = new Random();
            Firework firework = (Firework) entity;
            FireworkMeta fireworkMeta = firework.getFireworkMeta();
            fireworkMeta.setPower(1);
            switch (random.nextInt(5)) {
                case 0:
                    fireworkMeta.addEffect(FireworkEffect.builder().withColor(Color.GREEN).withColor(Color.RED).flicker(true).build());
                    break;
                case 1:
                    fireworkMeta.addEffect(FireworkEffect.builder().withColor(Color.BLUE).withColor(Color.MAROON).with(FireworkEffect.Type.BURST).flicker(true).build());
                    break;
                case 2:
                    fireworkMeta.addEffect(FireworkEffect.builder().withColor(Color.PURPLE).with(FireworkEffect.Type.CREEPER).flicker(true).build());
                    break;
                case 3:
                    fireworkMeta.addEffect(FireworkEffect.builder().withColor(Color.SILVER).with(FireworkEffect.Type.STAR).flicker(true).build());
                    break;
                case 4:
                    fireworkMeta.addEffect(FireworkEffect.builder().withColor(Color.ORANGE).with(FireworkEffect.Type.STAR).flicker(true).build());
                    break;
            }
            firework.setFireworkMeta(fireworkMeta);
        }
    }
}
