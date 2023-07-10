package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.utils.RayTrace;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

public class ShowStatusCommand implements CommandExecutor {
    private boolean run;

    private final MainPlugin plugin;

    public ShowStatusCommand(MainPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            if (args[0].equals("off")) {
                run = false;
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
                }
                return true;
            }

            Player target = Bukkit.getPlayerExact(args[0]);
            if (target == null) return false;

            run = true;
            ScoreboardManager manager = Bukkit.getScoreboardManager();
            Scoreboard scoreboard = manager.getNewScoreboard();
            Objective objective = scoreboard.registerNewObjective("status", "Dummy", args[0]);
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);

            Team world = scoreboard.registerNewTeam("world");
            world.addEntry("World: ");
            Team pos = scoreboard.registerNewTeam("pos");
            pos.addEntry("Position: ");
            Team facing = scoreboard.registerNewTeam("Facing");
            facing.addEntry("Facing: ");
            Team health = scoreboard.registerNewTeam("velocity");
            health.addEntry("Health: ");
            Team biomes = scoreboard.registerNewTeam("Biomes");
            biomes.addEntry("Biome: ");
            Team ping = scoreboard.registerNewTeam("Ping");
            ping.addEntry("Latency (ms): ");

            Team rayBlock = scoreboard.registerNewTeam("rayBlock");
            rayBlock.addEntry("RayTrace Block: ");
            Team rayEntity = scoreboard.registerNewTeam("rayEntity");
            rayEntity.addEntry("RayTrace Entity: ");

            objective.getScore("World: ").setScore(8);
            objective.getScore("Position: ").setScore(7);
            objective.getScore("Facing: ").setScore(6);
            objective.getScore("Health: ").setScore(5);
            objective.getScore("Biome: ").setScore(4);
            objective.getScore("Latency (ms): ").setScore(3);
            objective.getScore(" ").setScore(2);
            objective.getScore("RayTrace Block: ").setScore(1);
            objective.getScore("RayTrace Entity: ").setScore(0);

            RayTrace rayTrace = new RayTrace();

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!run) cancel();
                    world.setSuffix(ChatColor.AQUA + target.getWorld().getName());
                    Location loc = target.getLocation();
                    pos.setSuffix(ChatColor.AQUA + "" + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ());
                    facing.setSuffix(ChatColor.AQUA + target.getFacing().name().toLowerCase());
                    health.setSuffix(ChatColor.AQUA + "" + (int) target.getHealth());
                    biomes.setSuffix(ChatColor.AQUA + "" + loc.getBlock().getBiome().name().toLowerCase());
                    ping.setSuffix(ChatColor.AQUA + "" + target.getPing());

                    Block block = rayTrace.rayTraceBlock(target, 100);
                    Entity entity = rayTrace.raTraceEntity(target, 100);

                    if (block != null) rayBlock.setSuffix(ChatColor.AQUA + block.getType().name().toLowerCase());
                    else rayBlock.setSuffix("");
                    if (entity != null) rayEntity.setSuffix(ChatColor.AQUA + entity.getName().toLowerCase());
                    else rayEntity.setSuffix("");
                }
            }.runTaskTimer(plugin, 0, 1);

            for (Player player : Bukkit.getOnlinePlayers()) {
                player.setScoreboard(scoreboard);
            }
        }
        return false;
    }
}
