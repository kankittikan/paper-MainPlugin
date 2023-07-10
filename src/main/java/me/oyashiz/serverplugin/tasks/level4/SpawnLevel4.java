package me.oyashiz.serverplugin.tasks.level4;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.listeners.minigame.fukie.level4.Level4Listener;
import me.oyashiz.serverplugin.tasks.*;
import me.oyashiz.serverplugin.utils.SendAdmin;
import me.oyashiz.serverplugin.utils.StaticFlags;
import me.oyashiz.serverplugin.utils.StaticInt;
import me.oyashiz.serverplugin.utils.StaticLocations;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;

public class SpawnLevel4 {
    public void spawn(MainPlugin plugin) {
        if (StaticFlags.performingLevel != 4) return;
        Level4Listener.reset();

        StaticFlags.listenerOn = 0;
        ClearEntity.clearMonster(StaticLocations.fukieWorld);
        SendAdmin.sendMsg("UnBreak On");
        StaticFlags.FukieUnBreak = 1;
        SetTitle.set("Level 4", "", StaticLocations.fukieWorld);
        PlaySound.play(Sound.ITEM_GOAT_HORN_SOUND_0, StaticLocations.fukieWorld);
        ScoreboardPlayer.clearWorld(StaticLocations.fukieWorld);
        RandomSpawn randomSpawn = new RandomSpawn(plugin);
        StaticInt.numZombie = 24;
        StaticInt.numBoss = 3;
        randomSpawn.spawn(229, -276, 273, -316, 20, StaticLocations.fukieWorld, EntityType.ZOMBIE, StaticInt.numZombie + 15, new CommonLevel4ZombieNBT());
        StaticFlags.listenerOn = 4;
    }
}
