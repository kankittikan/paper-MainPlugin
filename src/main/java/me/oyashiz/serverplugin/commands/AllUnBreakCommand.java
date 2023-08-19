package me.oyashiz.serverplugin.commands;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.jetbrains.annotations.NotNull;

public class AllUnBreakCommand implements CommandExecutor, Listener {
    private static boolean UNBREAK = false;

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        if(UNBREAK) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("UNBREAK ON");
        }
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        if(UNBREAK) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("UNBREAK ON");
        }
    }


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(strings.length == 1) {
            UNBREAK = strings[0].equals("on");
        }
        return false;
    }
}
