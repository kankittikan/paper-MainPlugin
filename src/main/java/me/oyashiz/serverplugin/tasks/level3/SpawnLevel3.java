package me.oyashiz.serverplugin.tasks.level3;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.listeners.minigame.fukie.level3.Level3Listener;
import me.oyashiz.serverplugin.tasks.*;
import me.oyashiz.serverplugin.utils.SendAdmin;
import me.oyashiz.serverplugin.utils.StaticFlags;
import me.oyashiz.serverplugin.utils.StaticInt;
import me.oyashiz.serverplugin.utils.StaticLocations;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;

public class SpawnLevel3 {
    public void spawn(MainPlugin plugin) {
        if (StaticFlags.performingLevel != 3) return;
        Level3Listener.reset();

        StaticFlags.listenerOn = 0;
        ClearEntity.clearMonster(StaticLocations.fukieWorld);
        SendAdmin.sendMsg("UnBreak On");
        StaticFlags.FukieUnBreak = 1;
        SetTitle.set("Level 3", "", StaticLocations.fukieWorld);
        PlaySound.play(Sound.ITEM_GOAT_HORN_SOUND_0, StaticLocations.fukieWorld);
        ScoreboardPlayer.clearWorld(StaticLocations.fukieWorld);
        RandomSpawn randomSpawn = new RandomSpawn(plugin);
        StaticInt.numZombie = 22;
        StaticInt.numEnder = 5;
        StaticInt.numBoss = 3;
        randomSpawn.spawn(147, -213, 193, -161, 20, StaticLocations.fukieWorld, EntityType.HUSK, StaticInt.numZombie + 15, new CommonLevel3HuskNBT());
        randomSpawn.spawn(147, -213, 193, -161, 20, StaticLocations.fukieWorld, EntityType.ENDERMAN, StaticInt.numEnder + 5, new CommonLevel3EnderNBT());
        randomSpawn.spawn(147, -213, 193, -161, 20, StaticLocations.fukieWorld, EntityType.CREEPER, 10, new CommonLevel3CreeperNBT());
        StaticFlags.listenerOn = 3;
    }
}
