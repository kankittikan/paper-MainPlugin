package me.oyashiz.serverplugin.tasks;

import me.oyashiz.serverplugin.utils.CheckInArea;
import me.oyashiz.serverplugin.utils.StaticLocations;
import org.bukkit.*;
import org.bukkit.entity.Player;

public class PlaySound {
    public static void play(Sound sound, World world) {
        for (Player player : world.getPlayers()) {
            player.playSound(player, sound, 0.5f, 1);
        }
    }

    public static void play(Sound sound, World world, float volume) {
        for (Player player : world.getPlayers()) {
            player.playSound(player, sound, volume, 1);
        }
    }

    public static void playAll(Sound sound, float volume) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player, sound, volume, 1);
        }
    }

    public static void playLocation(Sound sound, Location location, float volume) {
        for (Player player : location.getWorld().getPlayers()) {
            player.playSound(location, sound, volume, 1);
        }
    }

    public static void stop(World world) {
        for (Player player : world.getPlayers()) {
            player.stopAllSounds();
        }
    }

    public static void stopAll() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.stopAllSounds();
        }
    }

    public static void playNoteLocation(Location location, Note note) {
        for (Player player : location.getWorld().getPlayers()) {
            player.playNote(location, Instrument.BANJO, note);
        }
    }

    public static void playInArea(int[] x, int[] z, World world, String sound, float volume) {
        for(Player p : world.getPlayers()) {
            if(CheckInArea.check(p.getLocation(), x[0], z[0], x[1], z[1])) {
                p.playSound(p.getLocation(), sound, volume, 1);
            }
        }
    }

    public static void playInArea(int[] x, int[] z, World world, Sound sound, float volume) {
        for(Player p : world.getPlayers()) {
            if(CheckInArea.check(p.getLocation(), x[0], z[0], x[1], z[1])) {
                p.playSound(p.getLocation(), sound, volume, 1);
            }
        }
    }

    public static void allNote(Note.Tone tone, int oc) {
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.playNote(p.getLocation(), Instrument.PIANO, Note.natural(oc, tone));
        }
    }

    public static void allNoteSharp(Note.Tone tone, int oc) {
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.playNote(p.getLocation(), Instrument.PIANO, Note.sharp(oc, tone));
        }
    }
}
