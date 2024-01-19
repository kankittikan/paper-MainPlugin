package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.MainPlugin;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.chat.ChatType;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class WaypointCommand implements CommandExecutor {

    private BukkitTask task;

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player) {
            if(strings.length == 0) {
                task.cancel();
                task = null;
            }
            if(strings.length == 2) {
                if(task != null) task.cancel();

                Player player = (Player) commandSender;
                int x = Integer.parseInt(strings[0]);
                int z = Integer.parseInt(strings[1]);

                Location waypoint = new Location(player.getWorld(), x, player.getLocation().getY(), z);

                player.sendMessage(Component.text("Waypoint : " + waypoint.getBlockX() + " " + waypoint.getBlockZ()));

                task = new BukkitRunnable() {
                    @Override
                    public void run() {
                        waypoint.setY(player.getLocation().getY());
                        Location playerLocation = player.getLocation();
                        playerLocation.setPitch(0);

                        Vector waypointVector = waypoint.toVector();
                        Vector playerVector = playerLocation.toVector();
                        Vector playerDirection = playerLocation.getDirection();

                        Vector subtracted = waypointVector.subtract(playerVector);

                        //get theta from two vector
                        double angleRad = subtracted.angle(playerDirection);
                        double degree = Math.toDegrees(angleRad);

                        //find direction by cross product
                        double dot = subtracted.crossProduct(playerDirection).dot(new Vector(0, 1, 0));

                        if(dot > 0) degree *= -1;

                        int degreeInt = (int) Math.ceil(degree);
                        String side;

                        if(degreeInt > -5 && degreeInt < 5) side = "↑";
                        else if(degreeInt == 180 || degreeInt == -180) side = "↓";
                        else if(degreeInt > 85 && degreeInt < 95) side = "←";
                        else if(degreeInt < -85 && degreeInt > -95) side = "→";
                        else if(degreeInt < 0) {
                            if(degreeInt > -90) side = "↗";
                            else side = "↘";
                        }
                        else {
                            if(degreeInt < 90) side = "↖";
                            else side = "↙";
                        }

                        for(Player p :Bukkit.getOnlinePlayers()) {
                            p.sendActionBar(Component.text(side + " distance: " + (int) p.getLocation().distance(waypoint)));
                        }
                    }
                }.runTaskTimer(MainPlugin.getMainPlugin(), 0, 1);
            }
        }
        return false;
    }
}
