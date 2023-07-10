package me.oyashiz.serverplugin.utils;

import org.bukkit.FluidCollisionMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;

import java.util.function.Predicate;

public class RayTrace {

    private Predicate<Entity> notPlayer() {
        return new Predicate<Entity>() {
            @Override
            public boolean test(Entity entity) {
                return !(entity instanceof Player);
            }
        };
    }
    public Block rayTraceBlock(Player player, int distance) {
        RayTraceResult result = player.getWorld().rayTraceBlocks(player.getEyeLocation(), player.getEyeLocation().getDirection(), distance);
        if (result != null) {
            return result.getHitBlock();
        }
        return null;
    }

    public Entity raTraceEntity(Player player, int distance) {
        RayTraceResult result = player.getWorld().rayTraceEntities(player.getEyeLocation(), player.getEyeLocation().getDirection(), distance, notPlayer());
        if (result != null) {
            return result.getHitEntity();
        }
        return null;
    }
}
