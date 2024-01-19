package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.utils.StaticLocations;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class TuuDanceCommand implements CommandExecutor {

    private static boolean tuuDance;

    public void dance(boolean play) {
        if (tuuDance) return;
        if (play) {
            ArmorStand armorStand = null;
            for (Entity entity : StaticLocations.world.getEntities()) {
                if (entity instanceof ArmorStand && entity.getName().equals("Lung Tuu")) {
                    armorStand = (ArmorStand) entity;
                    break;
                }
            }
            if (armorStand == null) return;

            ArmorStand finalArmorStand = armorStand;

            finalArmorStand.setCustomNameVisible(true);
            BukkitTask task = new BukkitRunnable() {

                Random random = new Random();

                float yaw = 0;

                @Override
                public void run() {
                    finalArmorStand.setLeftArmPose(new EulerAngle(random.nextDouble(0, 1), random.nextDouble(0, 2), 0));
                    finalArmorStand.setRightArmPose(new EulerAngle(random.nextDouble(0, 1), random.nextDouble(0, 2), 0));
                    finalArmorStand.setBodyPose(new EulerAngle(random.nextDouble(0, 0.3), 0, 0));
                    finalArmorStand.setHeadPose(new EulerAngle(random.nextDouble(0, 1), 0, 0));
                    finalArmorStand.setRotation(yaw += 10, 0);
                    if (tuuDance) {
                        cancel();
                        finalArmorStand.setRotation(180, 0);
                        finalArmorStand.setCustomNameVisible(false);
                        tuuDance = false;
                    }

                }
            }.runTaskTimer(MainPlugin.getMainPlugin(), 0, 5);
        }

        if (!play) {
            tuuDance = true;
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(strings.length == 1) {
            dance(strings[0].equals("on"));
        }
        return false;
    }
}
