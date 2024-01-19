package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.tasks.NBT;
import me.oyashiz.serverplugin.tasks.level1.CommonLevel1NBT;
import me.oyashiz.serverplugin.tasks.level2.CommonLevel2NBT;
import me.oyashiz.serverplugin.tasks.level3.CommonLevel3HuskNBT;
import me.oyashiz.serverplugin.tasks.level4.CommonLevel4ZombieNBT;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Shulker;
import org.jetbrains.annotations.NotNull;

public class IntruderWorldCommand implements CommandExecutor {
    private void spawnMonster() {
        World world = Bukkit.getWorld("world_intruder");
        if(world == null) return;

        Entity entity;
        NBT nbt;

        for(int i = 0; i < 7; i++) {
            entity = world.spawnEntity(new Location(world, 1, 5, 34), EntityType.ZOMBIE);
            nbt = new CommonLevel1NBT();
            nbt.setData(entity);

            entity = world.spawnEntity(new Location(world, 36, 5, 34), EntityType.SKELETON);
            nbt = new CommonLevel2NBT();
            nbt.setData(entity);

            entity = world.spawnEntity(new Location(world, 22, 5, 15), EntityType.HUSK);
            nbt = new CommonLevel3HuskNBT();
            nbt.setData(entity);

            entity = world.spawnEntity(new Location(world, 22, 5, 49), EntityType.ZOMBIE);
            nbt = new CommonLevel4ZombieNBT();
            nbt.setData(entity);
        }

        Shulker shulker = (Shulker) world.spawnEntity(new Location(world, 22, 8, 34), EntityType.SHULKER);
        shulker.setRemoveWhenFarAway(false);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(strings.length == 1) {
            if(strings[0].equals("spawn")) {
                spawnMonster();
            }
            if(strings[0].equals("waypoint")) {
                Bukkit.dispatchCommand(commandSender, "pa please-follow-the-direction");
                Bukkit.dispatchCommand(commandSender, "setwaypoint 76 34");
            }
        }
        return false;
    }
}