package me.oyashiz.serverplugin.commands;
import me.oyashiz.serverplugin.utils.StaticFlags;
import me.oyashiz.serverplugin.utils.StaticLists;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LockPlayerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(args.length == 3) {
            if(!args[2].equals("880022")) {
                player.sendMessage(ChatColor.RED + "Wrong password");
                return false;
            }

            Player target = Bukkit.getPlayerExact(args[1]);

            if(args[0].equals("on")) {
                StaticLists.playerLock.add(target);
                target.sendMessage("You has been locked");
                target.sendMessage("Server developer is going to you");
                StaticFlags.lockPlayer = 1;
            }
            if(args[0].equals("off")) {
                target.sendMessage("You are free");
                StaticLists.playerLock.remove(target);
                StaticFlags.lockPlayer = 0;
            }
        }
        return false;
    }
}
