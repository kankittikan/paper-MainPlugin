package me.oyashiz.serverplugin.tasks;

import me.oyashiz.serverplugin.utils.SendAdmin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;

import java.util.ArrayList;

public class ClearEntity {
    public static void clearMonster(World world) {
        ArrayList<EntityType> entityTypeArrayList = new ArrayList<>();
        entityTypeArrayList.add(EntityType.ZOMBIE);
        entityTypeArrayList.add(EntityType.DROWNED);
        entityTypeArrayList.add(EntityType.SKELETON);
        entityTypeArrayList.add(EntityType.SPIDER);
        entityTypeArrayList.add(EntityType.CAVE_SPIDER);
        entityTypeArrayList.add(EntityType.ENDERMAN);
        entityTypeArrayList.add(EntityType.CREEPER);
        entityTypeArrayList.add(EntityType.SLIME);
        entityTypeArrayList.add(EntityType.HUSK);
        entityTypeArrayList.add(EntityType.ILLUSIONER);
        entityTypeArrayList.add(EntityType.PILLAGER);
        entityTypeArrayList.add(EntityType.VINDICATOR);
        entityTypeArrayList.add(EntityType.SHULKER);

        int count = 0;
        if (world == null) return;
        for (Entity entity : world.getEntities()) {
            if (entity instanceof Monster) {
                Monster monster = (Monster) entity;
                monster.setHealth(0);
                count++;
            }
        }
        SendAdmin.sendMsg(ChatColor.YELLOW + "Cleared " + count + " monsters in " + world);
    }
}
