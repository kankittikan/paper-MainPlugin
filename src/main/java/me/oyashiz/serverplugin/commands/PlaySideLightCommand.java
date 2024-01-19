package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.tasks.FillBlock;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class PlaySideLightCommand implements CommandExecutor {

    private final int startZ = 87;
    private final int endZ = 99;
    private final int leftX = 12;
    private final int rightX = -8;

    private final int startY = 6;
    private final int endY = 13;

    private BukkitTask task;
    private BukkitTask controlTask;

    private void playSlide(boolean invert) {

        if (invert) {
            final int[] z = {endZ, startZ};
            task = new BukkitRunnable() {
                @Override
                public void run() {
                    FillBlock.fill(Bukkit.getWorld("world"), leftX, startY, z[1], leftX, endY, z[1], Material.AIR, MainPlugin.getMainPlugin());
                    FillBlock.fill(Bukkit.getWorld("world"), rightX, startY, z[1], rightX, endY, z[1], Material.AIR, MainPlugin.getMainPlugin());

                    FillBlock.fill(Bukkit.getWorld("world"), leftX, startY, z[0], leftX, endY, z[0], Material.REDSTONE_BLOCK, MainPlugin.getMainPlugin());
                    FillBlock.fill(Bukkit.getWorld("world"), rightX, startY, z[0], rightX, endY, z[0], Material.REDSTONE_BLOCK, MainPlugin.getMainPlugin());
                    z[1] = z[0];
                    z[0]--;
                    if (z[0] < startZ) z[0] = endZ;
                }
            }.runTaskTimer(MainPlugin.getMainPlugin(), 0, 2);
        } else {
            final int[] z = {startZ, endZ};
            task = new BukkitRunnable() {
                @Override
                public void run() {
                    FillBlock.fill(Bukkit.getWorld("world"), leftX, startY, z[1], leftX, endY, z[1], Material.AIR, MainPlugin.getMainPlugin());
                    FillBlock.fill(Bukkit.getWorld("world"), rightX, startY, z[1], rightX, endY, z[1], Material.AIR, MainPlugin.getMainPlugin());

                    FillBlock.fill(Bukkit.getWorld("world"), leftX, startY, z[0], leftX, endY, z[0], Material.REDSTONE_BLOCK, MainPlugin.getMainPlugin());
                    FillBlock.fill(Bukkit.getWorld("world"), rightX, startY, z[0], rightX, endY, z[0], Material.REDSTONE_BLOCK, MainPlugin.getMainPlugin());
                    z[1] = z[0];
                    z[0]++;
                    if (z[0] > endZ) z[0] = startZ;
                }
            }.runTaskTimer(MainPlugin.getMainPlugin(), 0, 2);
        }
    }

    private void playFill() {

        final int[] z = {startZ, endZ, endZ};
        task = new BukkitRunnable() {
            @Override
            public void run() {
                FillBlock.fill(Bukkit.getWorld("world"), leftX, startY, z[1], leftX, endY, z[1], Material.AIR, MainPlugin.getMainPlugin());
                FillBlock.fill(Bukkit.getWorld("world"), rightX, startY, z[1], rightX, endY, z[1], Material.AIR, MainPlugin.getMainPlugin());

                FillBlock.fill(Bukkit.getWorld("world"), leftX, startY, z[0], leftX, endY, z[0], Material.REDSTONE_BLOCK, MainPlugin.getMainPlugin());
                FillBlock.fill(Bukkit.getWorld("world"), rightX, startY, z[0], rightX, endY, z[0], Material.REDSTONE_BLOCK, MainPlugin.getMainPlugin());
                z[1] = z[0];
                z[0]++;
                if (startZ == z[2]) cancel();
                if (z[0] > z[2]) {
                    z[0] = startZ;
                    z[2]--;
                    z[1] = startZ;
                }
            }
        }.runTaskTimer(MainPlugin.getMainPlugin(), 0, 1);

    }

    private void playShuffle() {

        final int[] z = {startZ, endZ};
        Random random = new Random();
        task = new BukkitRunnable() {
            @Override
            public void run() {
                FillBlock.fill(Bukkit.getWorld("world"), leftX, startY, z[0], leftX, endY, z[0], Material.AIR, MainPlugin.getMainPlugin());
                FillBlock.fill(Bukkit.getWorld("world"), rightX, startY, z[0], rightX, endY, z[0], Material.AIR, MainPlugin.getMainPlugin());

                int y = random.nextInt(startY, endY + 1);
                int y1 = random.nextInt(startY, endY + 1);
                FillBlock.fill(Bukkit.getWorld("world"), leftX, y, z[0], leftX, y, z[0], Material.REDSTONE_BLOCK, MainPlugin.getMainPlugin());
                FillBlock.fill(Bukkit.getWorld("world"), rightX, y, z[0], rightX, y, z[0], Material.REDSTONE_BLOCK, MainPlugin.getMainPlugin());
                FillBlock.fill(Bukkit.getWorld("world"), leftX, y1, z[0], leftX, y1, z[0], Material.REDSTONE_BLOCK, MainPlugin.getMainPlugin());
                FillBlock.fill(Bukkit.getWorld("world"), rightX, y1, z[0], rightX, y1, z[0], Material.REDSTONE_BLOCK, MainPlugin.getMainPlugin());
                z[0]++;
                if (z[0] > endZ) z[0] = startZ;
            }
        }.runTaskTimer(MainPlugin.getMainPlugin(), 0, 2);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(controlTask != null) controlTask.cancel();
        if (task != null) task.cancel();
        task = null;
        controlTask = null;

        FillBlock.fill(Bukkit.getWorld("world"), leftX, startY, startZ, leftX, endY, endZ, Material.AIR, MainPlugin.getMainPlugin());
        FillBlock.fill(Bukkit.getWorld("world"), rightX, startY, startZ, rightX, endY, endZ, Material.AIR, MainPlugin.getMainPlugin());

        if (strings.length == 1) {
            if (strings[0].equals("fill")) playFill();
            if (strings[0].equals("shuffle")) playShuffle();
        }
        if (strings.length == 2) {
            if (strings[1].equals("double")) {
                controlTask = new BukkitRunnable() {
                    boolean invert = false;

                    @Override
                    public void run() {
                        if(task != null) task.cancel();
                        playSlide(invert);
                        invert = !invert;
                    }
                }.runTaskTimer(MainPlugin.getMainPlugin(), 0, 25);
            } else {
                playSlide(!strings[0].equals("slide") || !strings[1].equals("0"));
            }
        }
        return false;
    }
}
