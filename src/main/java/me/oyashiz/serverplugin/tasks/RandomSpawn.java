package me.oyashiz.serverplugin.tasks;

import me.oyashiz.serverplugin.MainPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Random;

public class RandomSpawn {
    private final MainPlugin plugin;

    public RandomSpawn(MainPlugin plugin) {
        this.plugin = plugin;
    }

    public void spawn(int x, int z, int x1, int z1, int y, World world, EntityType entityType, int limit, NBT nbt, int ... delay) {
        Random random = new Random();
        int period = 10;

        if(delay.length == 1) {
            period = delay[0];
        }

        int minx = x;
        int maxx = x1;

        int minz = z;
        int maxz = z1;

        if (x > x1) {
            minx = x1;
            maxx = x;
        }
        if (z > z1) {
            minz = z1;
            maxz = z;
        }

        int finalMaxx = maxx;
        int finalMinx = minx;
        int finalMaxz = maxz;
        int finalMinz = minz;
        BukkitTask task = new BukkitRunnable() {
            int ranx;
            int ranz;
            int count = 0;

            @Override
            public void run() {
                if (count >= limit - 1) cancel();
                ranx = random.nextInt(finalMaxx - finalMinx + 1) + finalMinx;
                ranz = random.nextInt(finalMaxz - finalMinz + 1) + finalMinz;
                Location location = new Location(world, ranx, y, ranz);
                if (location.getBlock().getType() == Material.AIR) {
                    Entity entity = world.spawnEntity(location, entityType);
                    if (nbt != null) nbt.setData(entity);
                    if(entity instanceof Monster) {
                        Monster monster = (Monster) entity;
                        monster.setRemoveWhenFarAway(false);
                    }
                    //SendAdmin.sendMsg(count + "");
                    count++;
                }
            }
        }.runTaskTimer(plugin, 10, period);
    }
}