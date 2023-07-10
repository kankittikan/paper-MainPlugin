package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.tasks.PlaySound;
import me.oyashiz.serverplugin.utils.SendMsg;
import me.oyashiz.serverplugin.utils.StaticLocations;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Note;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PianoComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("piano")) {
            if (args.length == 1) {
                if(args[0].length() == 0) return null;
                if(args[0].length() == 1) SendMsg.sendList((List<Player>) Bukkit.getOnlinePlayers(), ChatColor.YELLOW + sender.getName() + " Playing Piano");
                String input = args[0].substring(args[0].length() - 1);
                if(input.equals("q")) PlaySound.allNote(Note.Tone.G, 0);
                if(input.equals("w")) PlaySound.allNote(Note.Tone.A, 0);
                if(input.equals("e")) PlaySound.allNote(Note.Tone.B, 0);
                if(input.equals("r")) PlaySound.allNote(Note.Tone.C, 0);
                if(input.equals("t")) PlaySound.allNote(Note.Tone.D, 0);
                if(input.equals("y")) PlaySound.allNote(Note.Tone.E, 0);
                if(input.equals("u")) PlaySound.allNote(Note.Tone.F, 0);

                if(input.equals("g")) PlaySound.allNote(Note.Tone.G, 1);
                if(input.equals("h")) PlaySound.allNote(Note.Tone.A, 1);
                if(input.equals("j")) PlaySound.allNote(Note.Tone.B, 1);
                if(input.equals("k")) PlaySound.allNote(Note.Tone.C, 1);
                if(input.equals("l")) PlaySound.allNote(Note.Tone.D, 1);
                if(input.equals(";")) PlaySound.allNote(Note.Tone.E, 1);
                if(input.equals("\'")) PlaySound.allNote(Note.Tone.F, 1);

                if(input.equals("2")) PlaySound.allNoteSharp(Note.Tone.G, 0);
                if(input.equals("3")) PlaySound.allNoteSharp(Note.Tone.A, 0);
                if(input.equals("4")) PlaySound.allNoteSharp(Note.Tone.B, 0);
                if(input.equals("5")) PlaySound.allNoteSharp(Note.Tone.C, 0);
                if(input.equals("6")) PlaySound.allNoteSharp(Note.Tone.D, 0);
                if(input.equals("7")) PlaySound.allNoteSharp(Note.Tone.E, 0);

                ArrayList<String> strings = new ArrayList<>();
                strings.add("-");
                return strings;
            }
        }
        return null;
    }
}
