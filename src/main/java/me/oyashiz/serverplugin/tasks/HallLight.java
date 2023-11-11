package me.oyashiz.serverplugin.tasks;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.utils.ConfigReader;
import me.oyashiz.serverplugin.utils.SendAdmin;
import me.oyashiz.serverplugin.utils.StaticFlags;
import me.oyashiz.serverplugin.utils.StaticLocations;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.Random;

public class HallLight {
    public void save(String name) {
        int[][] light = new int[8][15];
        int r = 0;
        int c = 0;
        for (int y = 14; y >= 7; y--) {
            for (int x = -5; x <= 9; x++) {
                Location location = new Location(StaticLocations.world, x, y, 104);
                if (location.getBlock().getType() == Material.REDSTONE_BLOCK) {
                    light[r][c++] = 1;
                } else {
                    light[r][c++] = 0;
                }
            }
            r++;
            c = 0;
        }
        String out = Arrays.deepToString(light);
        out = out.replace("[", "");
        out = out.replace("]", "");
        out = out.replace(",", "");
        MainPlugin.lightConfig.getConfig().set(name, out);
        ConfigReader.save(MainPlugin.lightConfig);
        SendAdmin.sendMsg("Saved Light " + name);
    }

    public void load(String name, MainPlugin plugin) {
        if(StaticFlags.matrixLight) return;
        StaticFlags.matrixLight = true;
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                String in = (String) MainPlugin.lightConfig.getConfig().get(name);
                if (in == null) {
                    SendAdmin.sendMsg("No this light name");

                }
                String[] arrayIn = in.split(" ");
                int InIndex = 0;
                for (int y = 14; y >= 7; y--) {
                    for (int x = -5; x <= 9; x++) {
                        Location location = new Location(StaticLocations.world, x, y, 104);
                        if (arrayIn[InIndex++].equals("1")) {
                            location.getBlock().setType(Material.REDSTONE_BLOCK);
                        } else {
                            location.getBlock().setType(Material.AIR);
                        }
                    }
                }

            }
        }.runTask(plugin);
        StaticFlags.matrixLight = false;
    }

    public void countDown(MainPlugin plugin) {
        if(StaticFlags.matrixLight) return;
        StaticFlags.matrixLight = true;
        BukkitTask task = new BukkitRunnable() {
            String name = "10";
            @Override
            public void run() {
                if(name.equals("0")) {
                    name = "heart";
                    cancel();
                    StaticFlags.matrixLight = false;
                }
                String in = (String) MainPlugin.lightConfig.getConfig().get(name);
                if (in == null) {
                    SendAdmin.sendMsg("No this light name");

                }
                String[] arrayIn = in.split(" ");
                int InIndex = 0;
                for (int y = 14; y >= 7; y--) {
                    for (int x = -5; x <= 9; x++) {
                        Location location = new Location(StaticLocations.world, x, y, 104);
                        if (arrayIn[InIndex++].equals("1")) {
                            location.getBlock().setType(Material.REDSTONE_BLOCK);
                        } else {
                            location.getBlock().setType(Material.AIR);
                        }
                    }
                }
                PlaySound.playInArea(new int[]{11, -7}, new int[]{103, 77}, StaticLocations.world, Sound.BLOCK_NOTE_BLOCK_BANJO, 1);
                if(!name.equals("heart")) {
                    name = String.valueOf(Integer.parseInt(name) - 1);
                }

            }
        }.runTaskTimer(plugin, 30, 30);
    }

    public void shuffle(MainPlugin plugin) {

        load("cloud", plugin);
        BukkitTask task = new BukkitRunnable() {
            Random random = new Random();
            @Override
            public void run() {
                if(StaticFlags.matrixLight) cancel();
                for (int y = 10; y >= 7; y--) {
                    for (int x = -5; x <= 9; x++) {
                        if(StaticFlags.matrixLight) cancel();
                        Location location = new Location(StaticLocations.world, x, y, 104);
                        if (random.nextInt(7) == 1) {
                            location.getBlock().setType(Material.REDSTONE_BLOCK);
                        } else {
                            location.getBlock().setType(Material.AIR);
                        }
                    }
                }

            }
        }.runTaskTimer(plugin, 30, 20);
    }

    public void clear(MainPlugin plugin) {
        BukkitTask task = new BukkitRunnable() {
            int count = 0;
            @Override
            public void run() {
                StaticFlags.matrixLight = true;
                if(count >= 2) {
                    BukkitTask task1 = new BukkitRunnable() {
                        @Override
                        public void run() {
                            StaticFlags.matrixLight = false;
                            load("clear", plugin);

                        }
                    }.runTaskLater(plugin, 10);
                    cancel();
                }
                count++;

            }
        }.runTaskTimer(plugin, 0, 10);
    }
}
