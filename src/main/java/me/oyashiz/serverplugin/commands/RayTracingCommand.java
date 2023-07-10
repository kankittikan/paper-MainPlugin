package me.oyashiz.serverplugin.commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;

import java.util.function.Predicate;

public class RayTracingCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            World world = player.getWorld();

            Predicate<Entity> entityPredicate = new Predicate<Entity>() {
                @Override
                public boolean test(Entity entity) {
                    return !(entity instanceof Player);
                }
            };

            RayTraceResult result = world.rayTraceEntities(player.getEyeLocation(), player.getEyeLocation().getDirection(), 10, entityPredicate);

            if(result != null) {
                player.sendMessage(result.getHitEntity().getName());
            }
        }
        return false;
    }
}
