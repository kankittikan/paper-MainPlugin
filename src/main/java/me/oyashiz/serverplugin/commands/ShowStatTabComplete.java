package me.oyashiz.serverplugin.commands;

import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class ShowStatTabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("showstat")) {
            if(args.length == 1) {
                ArrayList<String> strings = new ArrayList<>();
                for(Statistic s : Statistic.values()) {
                    if(s.toString().contains(args[0])) {
                        strings.add(s.toString());
                    }
                }
                return strings;
            }
        }
        return null;
    }
}
