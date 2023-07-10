package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.utils.EmptyChunkGenerator;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.*;

public class CreateWorldCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof ConsoleCommandSender || sender instanceof BlockCommandSender) {
            if(args.length == 2) {
                if(args[0].equals("void")) {
                    WorldCreator wc = new WorldCreator(args[1]);
                    wc.generator(new EmptyChunkGenerator());
                    wc.createWorld();
                }
                if(args[0].equals("flat")) {
                    WorldCreator wc = new WorldCreator(args[1]);
                    wc.type(WorldType.FLAT);
                    wc.createWorld();
                }
                if(args[0].equals("normal")) {
                    WorldCreator wc = new WorldCreator(args[1]);
                    wc.type(WorldType.NORMAL);
                    wc.createWorld();
                }
            }
        }
        return false;
    }
}
