package me.oyashiz.serverplugin.listeners.minigame.fukie;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.tasks.PlaySound;
import me.oyashiz.serverplugin.utils.SendMsg;
import me.oyashiz.serverplugin.utils.StaticLocations;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public class FukieMonsterListener implements Listener {

    private final MainPlugin plugin;

    private HashMap<UUID, Long> cooldown;

    public FukieMonsterListener(MainPlugin plugin) {

        this.plugin = plugin;
        cooldown = new HashMap<>();
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity().getWorld() != Bukkit.getWorld("world_fukie")) return;
        String name = event.getEntity().getName();
        if (name.equals("Pius") || name.equals("Maya") || name.equals("Draco") || name.equals("Shiphaai")) {
            event.setCancelled(true);
            return;
        }
        if (event.getEntity() instanceof IronGolem) {
            IronGolem a = (IronGolem) event.getEntity();
            String[] s;
            if (a.getCustomName() == null) s = new String[]{String.valueOf(a.getType())};
            else s = a.getCustomName().split("-");

            a.setCustomName(s[0] + "-Health: " + ChatColor.GREEN + (int) a.getHealth());
            a.setCustomNameVisible(true);
        }
        if (event.getEntity() instanceof Monster) {
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                event.setCancelled(true);
            }
            Monster a = (Monster) event.getEntity();
            String[] s;
            if (a.getCustomName() == null) s = new String[]{String.valueOf(a.getType())};
            else s = a.getCustomName().split("-");

            a.setCustomName(s[0] + "-Health: " + ChatColor.GREEN + (int) a.getHealth());
            a.setCustomNameVisible(true);
        }
    }

    @EventHandler
    public void playerUseSpecial(EntityDamageByEntityEvent event) {
        try {
            if (event.getEntity().getWorld() != StaticLocations.fukieWorld) return;
            if (event.getDamager() instanceof Player && event.getEntity() instanceof Monster) {
                Player player = (Player) event.getDamager();
                PlayerInventory inventory = player.getInventory();
                Monster monster = (Monster) event.getEntity();
                if ((inventory.getItemInMainHand().getItemMeta().hasDisplayName())) {
                    String name = inventory.getItemInMainHand().getItemMeta().getDisplayName();
                    ItemStack itemStack = inventory.getItemInMainHand();
                    if (name.equals("น้ำแข็งผนึกการเคลื่อนที่")) {
                        applyEffect(new PotionEffect(PotionEffectType.SLOW, 100, 4), monster, player, itemStack);
                    }
                    if (name.equals("บ้องดูดแล้วลอย")) {
                        applyEffect(new PotionEffect(PotionEffectType.LEVITATION, 40, 4), monster, player, itemStack);
                    }
                }
            }
        } catch (NullPointerException ignored) {
        }
    }

    @EventHandler
    public void playerUseItem(PlayerInteractEvent event) {
        try {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (event.getPlayer().getWorld() != StaticLocations.fukieWorld) return;
                Player player = event.getPlayer();
                if (player.getInventory().getItemInMainHand().getType() == Material.COOKED_BEEF) {
                    if (!cooldown.containsKey(player.getUniqueId())) {
                        cooldown.put(player.getUniqueId(), System.currentTimeMillis());
                    } else {
                        long elapsed = System.currentTimeMillis() - cooldown.get(player.getUniqueId());
                        if (elapsed >= 10000) {
                            cooldown.put(player.getUniqueId(), System.currentTimeMillis());
                        } else {
                            event.setCancelled(true);
                        }
                    }
                }
                if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasDisplayName()) {
                    String name = event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName();
                    if (name.equals("อันเชิญผู้พิทักษ์")) {
                        //event.getPlayer().sendMessage(ChatColor.RED + "ถูกปิดใช้งาน");
                        PlaySound.play(Sound.ITEM_GOAT_HORN_SOUND_6, StaticLocations.fukieWorld);
                        SendMsg.send(StaticLocations.fukieWorld, event.getPlayer().getName() + " use horn");
                        ItemStack itemStack = event.getPlayer().getInventory().getItemInMainHand();
                        itemStack.setAmount(itemStack.getAmount() - 1);
                        BukkitTask t1 = new BukkitRunnable() {
                            @Override
                            public void run() {
                                PlaySound.play(Sound.MUSIC_DISC_PIGSTEP, StaticLocations.fukieWorld);
                                SendMsg.send(StaticLocations.fukieWorld, event.getPlayer().getName() + " summoned Iron Golem");
                                IronGolem golem = (IronGolem) event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation().add(2, 5, 0), EntityType.IRON_GOLEM);
                                IronGolem golem2 = (IronGolem) event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation().add(-2, 5, 0), EntityType.IRON_GOLEM);

                                event.getPlayer().getWorld().spawnEntity(event.getPlayer().getLocation().add(0, 5, 0), EntityType.LIGHTNING);

                                golem.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 1));
                                golem2.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 1));
                                golem.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
                                golem2.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));

                                golem.setMaxHealth(150);
                                golem2.setMaxHealth(150);
                                golem.setHealth(150);
                                golem2.setHealth(150);

                                BukkitTask golemDead = new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        golem.setHealth(0);
                                        golem2.setHealth(0);
                                        PlaySound.stop(StaticLocations.fukieWorld);
                                        SendMsg.send(StaticLocations.fukieWorld, "Golem has gone");
                                    }
                                }.runTaskLater(plugin, 600);
                            }
                        }.runTaskLater(plugin, 100);
                    }
                    if (name.equals("ระเบิดเวลา (คลิกขวาอย่างระวัง)") && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        //event.getPlayer().sendMessage(ChatColor.RED + "ถูกปิดใช้งาน");

                        BukkitTask sound = new BukkitRunnable() {
                            @Override
                            public void run() {
                                PlaySound.play(Sound.ENTITY_EXPERIENCE_ORB_PICKUP, StaticLocations.fukieWorld);
                            }
                        }.runTaskTimer(plugin, 3, 3);
                        SendMsg.send(StaticLocations.fukieWorld, event.getPlayer().getName() + " use bomb");
                        ItemStack itemStack = event.getPlayer().getInventory().getItemInMainHand();
                        itemStack.setAmount(itemStack.getAmount() - 1);

                        TNTPrimed tntPrimed = (TNTPrimed) StaticLocations.fukieWorld.spawnEntity(event.getClickedBlock().getLocation().add(0, 1, 0), EntityType.PRIMED_TNT);
                        tntPrimed.setFuseTicks(60);

                        BukkitTask stop = new BukkitRunnable() {
                            @Override
                            public void run() {
                                PlaySound.play(Sound.BLOCK_NOTE_BLOCK_COW_BELL, StaticLocations.fukieWorld);
                                sound.cancel();
                            }
                        }.runTaskLater(plugin, 50);
                    }
                }
            }
        } catch (NullPointerException ignored) {
        }
    }

    private void applyEffect(PotionEffect potionEffect, Monster monster, Player player, ItemStack itemStack) {
        monster.addPotionEffect(potionEffect);
        monster.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 100, 4));
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_IRON_XYLOPHONE, 1, 1);
        itemStack.setAmount(itemStack.getAmount() - 1);
    }
}