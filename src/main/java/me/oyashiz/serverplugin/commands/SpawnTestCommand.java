package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.tasks.*;
import me.oyashiz.serverplugin.tasks.level5.CommonLevel5PillagerNBT;
import me.oyashiz.serverplugin.tasks.level5.CommonLevel5ZombieNBT;
import me.oyashiz.serverplugin.utils.SendAdmin;
import me.oyashiz.serverplugin.utils.StaticFlags;
import me.oyashiz.serverplugin.utils.StaticInt;
import me.oyashiz.serverplugin.utils.StaticLocations;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;

public class SpawnTestCommand implements CommandExecutor {
    private final MainPlugin plugin;

    public SpawnTestCommand(MainPlugin plugin) {
        this.plugin = plugin;
    }

    public void spawn(MainPlugin plugin) {
        if (StaticFlags.performingLevel != 5) return;

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
        StaticInt.numBoss = 2;
        randomSpawn.spawn(229, -276, 273, -316, 20, StaticLocations.fukieWorld, EntityType.ZOMBIE_VILLAGER, StaticInt.numZombie + 15, new CommonLevel5ZombieNBT());
        randomSpawn.spawn(229, -276, 273, -316, 20, StaticLocations.fukieWorld, EntityType.PILLAGER, StaticInt.numPillager + 15, new CommonLevel5PillagerNBT());
        StaticFlags.listenerOn = 5;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        spawn(plugin);
        return false;
    }
}
