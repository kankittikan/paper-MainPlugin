package me.oyashiz.serverplugin.tasks.level2;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.listeners.minigame.fukie.level2.Level2Listener;
import me.oyashiz.serverplugin.tasks.*;
import me.oyashiz.serverplugin.utils.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;

public class SpawnLevel2 {
    public static final Location locationSetWeather = new Location(StaticLocations.fukieWorld, 323, -33, -164);

    public void spawn(MainPlugin plugin) {
        if (StaticFlags.performingLevel != 2) return;
        Level2Listener.reset();

        StaticFlags.listenerOn = 0;
        locationSetWeather.getBlock().setType(Material.REDSTONE_BLOCK);
        ClearEntity.clearMonster(StaticLocations.fukieWorld);
        SendAdmin.sendMsg("UnBreak On");
        StaticFlags.FukieUnBreak = 1;
        SetTitle.set("Level 2", "", StaticLocations.fukieWorld);
        PlaySound.play(Sound.ITEM_GOAT_HORN_SOUND_0, StaticLocations.fukieWorld);
        ScoreboardPlayer.clearWorld(StaticLocations.fukieWorld);
        RandomSpawn randomSpawn = new RandomSpawn(plugin);
        StaticInt.numSkeleton = 15;
        StaticInt.numBoss = 1;
        randomSpawn.spawn(85, -226, 51, -168, -32, StaticLocations.fukieWorld, EntityType.SKELETON, StaticInt.numSkeleton + 15, new CommonLevel2NBT());
        randomSpawn.spawn(85, -226, 51, -168, -32, StaticLocations.fukieWorld, EntityType.CREEPER, 10, new CommonLevel2NBT());
        StaticFlags.listenerOn = 2;
        locationSetWeather.getBlock().setType(Material.AIR);
    }
}
