package me.oyashiz.serverplugin.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class SetLightUnderTabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equals("setlightunder")) {
            List<String> strings = new ArrayList<>();
            if(args.length == 1) {
                for(int i = 1; i < 8; i++) {
                    strings.add(String.valueOf(i));
                }
                return strings;
            }
            if(args.length == 2) {
                for(Material m : Material.values()) {
                    if(m.toString().endsWith("STAINED_GLASS") && m.toString().contains(args[1])) {
                        strings.add(m.toString());
                    }
                }
                strings.add(Material.IRON_BLOCK.toString());
                return strings;
            }
        }
        return null;
    }
}
