package me.oyashiz.serverplugin.commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class PsTabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("ps")) {
            if (args.length == 2) {
                List<String> soundList = new ArrayList<>();
                for (Sound s : Sound.values()) {
                    String temp = s.toString();
                    if(temp.contains(args[1])) {
                        soundList.add(temp);
                    }
                }
                return soundList;
            }
            if (args.length == 1) {
                List<String> option = new ArrayList<>();
                option.add("on");
                option.add("off");
                return option;
            }
        }
        return null;
    }
}
