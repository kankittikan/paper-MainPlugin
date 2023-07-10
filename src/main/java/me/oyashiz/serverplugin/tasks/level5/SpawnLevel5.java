package me.oyashiz.serverplugin.tasks.level5;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.listeners.minigame.fukie.level5.Level5Listener;
import me.oyashiz.serverplugin.tasks.*;
import me.oyashiz.serverplugin.utils.SendAdmin;
import me.oyashiz.serverplugin.utils.StaticFlags;
import me.oyashiz.serverplugin.utils.StaticInt;
import me.oyashiz.serverplugin.utils.StaticLocations;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;

public class SpawnLevel5 {
    public void spawn(MainPlugin plugin) {
        if (StaticFlags.performingLevel != 5) return;
        Level5Listener.reset();

        Location start = new Location(StaticLocations.fukieWorld, 260, -37, -274);
        start.getBlock().setType(Material.REDSTONE_BLOCK);
        start.getBlock().setType(Material.AIR);

        StaticFlags.listenerOn = 0;
        ClearEntity.clearMonster(StaticLocations.fukieWorld);
        SendAdmin.sendMsg("UnBreak On");
        StaticFlags.FukieUnBreak = 1;
        SetTitle.set("Level 5", "", StaticLocations.fukieWorld);
        PlaySound.play(Sound.ITEM_GOAT_HORN_SOUND_0, StaticLocations.fukieWorld);
        ScoreboardPlayer.clearWorld(StaticLocations.fukieWorld);
        RandomSpawn randomSpawn = new RandomSpawn(plugin);
        StaticInt.numZombie = 10;
        StaticInt.numPillager = 10;
        StaticInt.numBoss = 4;
        randomSpawn.spawn(229, -276, 273, -316, 20, StaticLocations.fukieWorld, EntityType.ZOMBIE_VILLAGER, StaticInt.numZombie + 15, new CommonLevel5ZombieNBT());
        randomSpawn.spawn(229, -276, 273, -316, 20, StaticLocations.fukieWorld, EntityType.PILLAGER, StaticInt.numPillager + 15, new CommonLevel5PillagerNBT());
        StaticFlags.listenerOn = 5;
    }
}
