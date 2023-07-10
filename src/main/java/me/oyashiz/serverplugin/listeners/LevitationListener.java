package me.oyashiz.serverplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class LevitationListener implements Listener {
    @EventHandler
    public void onPlayerStep(PlayerInteractEvent event) {
        try{
            Block block = event.getClickedBlock();
            if(block.getX() == 61 && block.getY() == -3 && block.getZ() == 35 && block.getWorld() == Bukkit.getWorld("world")) {
                event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 50, 3));
            }

        }
        catch (NullPointerException ignored) {}
     }
}
