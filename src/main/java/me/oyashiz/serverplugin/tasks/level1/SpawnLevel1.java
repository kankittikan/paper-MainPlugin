package me.oyashiz.serverplugin.tasks.level1;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.listeners.minigame.fukie.level1.Level1Listener;
import me.oyashiz.serverplugin.tasks.*;
import me.oyashiz.serverplugin.utils.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;

public class SpawnLevel1 {
    public static final Location locationDoor = new Location(StaticLocations.fukieWorld, 23, -53, -73);
    public static final Location locationGlass = new Location(StaticLocations.fukieWorld, 23, -53, -75);

    public static final Location locationSetWeather = new Location(StaticLocations.fukieWorld, 323, -33, -161);

    public void spawn(MainPlugin plugin) {
        if (StaticFlags.performingLevel != 1) return;
        Level1Listener.reset();

        locationSetWeather.getBlock().setType(Material.REDSTONE_BLOCK);
        StaticFlags.listenerOn = 0;
        ClearEntity.clearMonster(StaticLocations.fukieWorld);
        SetTitle.set("Level 1", "", StaticLocations.fukieWorld);
        SendAdmin.sendMsg("UnBreak On");
        StaticFlags.FukieUnBreak = 1;
        locationDoor.getBlock().setType(Material.DIRT);
        locationGlass.getBlock().setType(Material.AMETHYST_BLOCK);
        PlaySound.play(Sound.ITEM_GOAT_HORN_SOUND_0, StaticLocations.fukieWorld);
        ScoreboardPlayer.clearWorld(StaticLocations.fukieWorld);
        RandomSpawn randomSpawn = new RandomSpawn(plugin);
        StaticInt.numZombie = 20;
        StaticInt.numSkeleton = 2;
        StaticInt.numBoss = 2;
        randomSpawn.spawn(75, -18, 29, -66, 24, StaticLocations.fukieWorld, EntityType.ZOMBIE, StaticInt.numZombie + 10, new CommonLevel1NBT());
        randomSpawn.spawn(65, -31, 66, -30, 24, StaticLocations.fukieWorld, EntityType.SKELETON, StaticInt.numSkeleton, new CommonLevel1NBT());
        StaticFlags.listenerOn = 1;
        locationSetWeather.getBlock().setType(Material.AIR);
    }
}
