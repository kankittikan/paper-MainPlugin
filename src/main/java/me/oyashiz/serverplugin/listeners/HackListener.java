package me.oyashiz.serverplugin.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class HackListener implements Listener {

    @EventHandler
    public void playerAttack(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player damager = (Player) event.getDamager();
            if(damager.getTargetEntity(5) != event.getEntity() && event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                //damager.kick(Component.text("Detected your attack action"));
            }
        }
    }
}
