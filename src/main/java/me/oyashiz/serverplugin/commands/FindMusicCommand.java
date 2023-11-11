package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.MainPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Content;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FindMusicCommand implements CommandExecutor {
    private List<TextComponent> records;

    public FindMusicCommand() {
        records = new ArrayList<>();
        try {
            File file = new File(MainPlugin.getMainPlugin().getDataFolder().getAbsolutePath(), "list.csv");
            System.out.println(file);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] lines = line.split(",");
                lines[0] = StringUtils.leftPad(lines[0], 6, "0");
                if (lines.length == 2) {
                    TextComponent textComponent = new TextComponent(String.format("%s  by %s", lines[0], lines[1]));
                    textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/gplay -t " + lines[0]));
                    records.add(textComponent);
                } else {
                    TextComponent textComponent = new TextComponent(String.format("%s  %s  by %s", lines[0], lines[1], lines[2]));
                    textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/gplay -t " + lines[0]));
                    records.add(textComponent);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Bukkit.getLogger().info(records.size() + "");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1) {
            String query = strings[0];
            if(query.length() < 3) {
                commandSender.sendMessage("fill out at least 3 character");
                return false;
            }
            List<TextComponent> out = new ArrayList<>();
            for (TextComponent i : records) {
                if (i.getText().contains(query)) out.add(i);
            }

            commandSender.sendMessage(String.format("Found %d songs", out.size()));
            for (TextComponent i : out) {
                commandSender.spigot().sendMessage(i);
            }
        }
        return false;
    }
}
