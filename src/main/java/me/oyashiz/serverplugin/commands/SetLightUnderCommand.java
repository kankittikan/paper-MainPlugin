package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.utils.StaticFlags;
import me.oyashiz.serverplugin.utils.StaticLocations;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Random;

public class SetLightUnderCommand implements CommandExecutor {

    private final MainPlugin plugin;

    private ArrayList<Material> materials = new ArrayList<>();

    public SetLightUnderCommand(MainPlugin plugin) {
        this.plugin = plugin;
        materials.add(Material.RED_STAINED_GLASS);
        materials.add(Material.ORANGE_STAINED_GLASS);
        materials.add(Material.YELLOW_STAINED_GLASS);
        materials.add(Material.GREEN_STAINED_GLASS);
        materials.add(Material.LIGHT_BLUE_STAINED_GLASS);
        materials.add(Material.BLUE_STAINED_GLASS);
        materials.add(Material.PURPLE_STAINED_GLASS);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            if (args[0].equals("close")) {

                BukkitTask task = new BukkitRunnable() {
                    int count = 0;

                    @Override
                    public void run() {
                        if (count >= 3) {
                            Location location = new Location(StaticLocations.world, 8, 6, 102);
                            location.getBlock().setType(Material.IRON_BLOCK);
                            for (int i = 1; i < 7; i++) {
                                location.setX(location.getBlockX() - 2);
                                location.getBlock().setType(Material.IRON_BLOCK);
                            }
                            cancel();
                        }
                        StaticFlags.underLight = false;
                        count++;
                    }
                }.runTaskTimer(plugin, 0, 5);
            }
            if (args[0].equals("shuffle")) {
                if (!StaticFlags.underLight) {
                    StaticFlags.underLight = true;
                    BukkitTask task = new BukkitRunnable() {
                        int old = 1;
                        int old2 = 4;
                        int value = 2;

                        int value2 = 5;

                        Random random = new Random();

                        Location location = new Location(StaticLocations.world, 8, 6, 102);

                        private void setZero(Location location) {
                            location.setX(8);
                            location.setY(6);
                            location.setZ(102);
                        }

                        @Override
                        public void run() {
                            if (!StaticFlags.underLight) {
                                cancel();
                            }

                            setZero(location);
                            location.setX(location.getBlockX() - (2 * (old - 1)));
                            location.getBlock().setType(Material.IRON_BLOCK);

                            setZero(location);
                            location.setX(location.getBlockX() - (2 * (old2 - 1)));
                            location.getBlock().setType(Material.IRON_BLOCK);

                            value = random.nextInt(1, 8);
                            value2 = random.nextInt(1, 8);

                            setZero(location);
                            location.setX(location.getBlockX() - (2 * (value - 1)));
                            location.getBlock().setType(materials.get(random.nextInt(materials.size())));

                            setZero(location);
                            location.setX(location.getBlockX() - (2 * (value2 - 1)));
                            location.getBlock().setType(materials.get(random.nextInt(materials.size())));

                            old = value;
                            old2 = value2;
                        }
                    }.runTaskTimer(plugin, 0, 20);
                }
            }
        }
        if (args.length == 2) {
            int number = Integer.parseInt(args[0]);
            if (number < 1 || number > 7) {
                sender.sendMessage("location out of limit");
                return false;
            }
            Location location = new Location(StaticLocations.world, 8, 6, 102);
            location.setX(location.getBlockX() - (2 * (number - 1)));
            location.getBlock().setType(Material.valueOf(args[1]));
        }
        return false;
    }
}
