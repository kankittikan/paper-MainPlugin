package me.oyashiz.serverplugin.listeners.minigame.fukie;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.tasks.level1.SpawnLevel1;
import me.oyashiz.serverplugin.tasks.level2.SpawnLevel2;
import me.oyashiz.serverplugin.tasks.level3.SpawnLevel3;
import me.oyashiz.serverplugin.tasks.level4.SpawnLevel4;
import me.oyashiz.serverplugin.tasks.level5.SpawnLevel5;
import me.oyashiz.serverplugin.utils.*;
import me.oyashiz.serverplugin.utils.Effect;
import org.bukkit.*;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Ravager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.IOException;
import java.util.*;

public class FukieListener implements Listener {
    private final MainPlugin plugin;

    public FukieListener(MainPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void button(PlayerInteractEvent event) {
        try {
            if (event.getClickedBlock() == null) return;
            if (!event.getClickedBlock().getLocation().getWorld().getName().equals("world_fukie")) return;

            ArrayList<Location> locationsSmith = new ArrayList<>();
            locationsSmith.add(new Location(StaticLocations.fukieWorld, 314, -60, -164));
            locationsSmith.add(new Location(StaticLocations.fukieWorld, 171, -8, -160));
            locationsSmith.add(new Location(StaticLocations.fukieWorld, 224, -35, -253));

            if (event.getClickedBlock().getX() == 315 && event.getClickedBlock().getY() == -60 && event.getClickedBlock().getZ() == -162) {
                event.getPlayer().sendMessage(ChatColor.YELLOW + "Throwing to the space hub");
                SaveInventory saveInventory = new SaveInventory(plugin);
                try {
                    saveInventory.saveInventory(event.getPlayer(), "world_fukie");
                    event.getPlayer().getInventory().clear();
                    saveInventory.restoreInventory(event.getPlayer(), "world");
                } catch (IOException e) {
                    SendAdmin.sendMsg(e.getMessage());
                } catch (NullPointerException ignored) {
                }
                BukkitTask task = new BukkitRunnable() {
                    @Override
                    public void run() {
                        event.getPlayer().teleport(StaticLocations.home);

                    }
                }.runTaskLater(plugin, 20);
            }

            if (locationsSmith.contains(event.getClickedBlock().getLocation())) {
                Location smith = new Location(Bukkit.getWorld("world_fukie"), 290, -33, -159, -180, 0);
                event.getPlayer().teleport(smith);
            }

            if (event.getClickedBlock().getX() == 291 && event.getClickedBlock().getY() == -32 && event.getClickedBlock().getZ() == -156) {
                Location hub = new Location(Bukkit.getWorld("world_fukie"), 315, -60, -162);
                event.getPlayer().teleport(hub);
            }

            if (event.getClickedBlock().getX() == 289 && event.getClickedBlock().getY() == -32 && event.getClickedBlock().getZ() == -156) {
                if (StaticFlags.performingLevel == 1) {
                    event.getPlayer().teleport(new Location(StaticLocations.fukieWorld, 75, -55, -45));
                    if (!CheckInArea.isPlayerInArea(StaticLocations.fukieWorld, 18, -78, 83, -10)) {
                        BukkitTask t = new BukkitRunnable() {
                            @Override
                            public void run() {
                                SpawnLevel1 spawnLevel1 = new SpawnLevel1();
                                spawnLevel1.spawn(plugin);

                            }
                        }.runTaskLater(plugin, 50);
                    }
                }
                if (StaticFlags.performingLevel == 2) {
                    event.getPlayer().teleport(new Location(StaticLocations.fukieWorld, 38, -60, -162));
                    if (!CheckInArea.isPlayerInArea(StaticLocations.fukieWorld, 32, -156, 93, -230)) {
                        BukkitTask t = new BukkitRunnable() {
                            @Override
                            public void run() {
                                SpawnLevel2 spawnLevel2 = new SpawnLevel2();
                                spawnLevel2.spawn(plugin);

                            }
                        }.runTaskLater(plugin, 50);
                    }

                }
                if (StaticFlags.performingLevel == 3) {
                    event.getPlayer().teleport(new Location(StaticLocations.fukieWorld, 168, -9, -159));
                    if (!CheckInArea.isPlayerInArea(StaticLocations.fukieWorld, 197, -153, 137, -223)) {
                        BukkitTask t = new BukkitRunnable() {
                            @Override
                            public void run() {
                                SpawnLevel3 spawnLevel3 = new SpawnLevel3();
                                spawnLevel3.spawn(plugin);

                            }
                        }.runTaskLater(plugin, 50);
                    }
                }
                if (StaticFlags.performingLevel == 4) {
                    event.getPlayer().teleport(new Location(StaticLocations.fukieWorld, 225, -37, -250, 180, 0));
                    if (!CheckInArea.isPlayerInArea(StaticLocations.fukieWorld, 290, -323, 205, -241)) {
                        BukkitTask t = new BukkitRunnable() {
                            @Override
                            public void run() {
                                SpawnLevel4 spawnLevel4 = new SpawnLevel4();
                                spawnLevel4.spawn(plugin);

                            }
                        }.runTaskLater(plugin, 50);
                    }
                }
                if (StaticFlags.performingLevel == 5) {
                    event.getPlayer().teleport(new Location(StaticLocations.fukieWorld, 225, -37, -250, 180, 0));
                    event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 800, 1));
                    if (!CheckInArea.isPlayerInArea(StaticLocations.fukieWorld, 290, -323, 205, -241)) {
                        BukkitTask t = new BukkitRunnable() {
                            @Override
                            public void run() {
                                SpawnLevel5 spawnLevel5 = new SpawnLevel5();
                                spawnLevel5.spawn(plugin);

                            }
                        }.runTaskLater(plugin, 50);
                    }
                }
            }

            if (event.getClickedBlock().getX() == 287 && event.getClickedBlock().getY() == -32 && event.getClickedBlock().getZ() == -156) {
                Player player = event.getPlayer();
                String[] values = getGoldValue(player).split(" ");
                int goldInt = Integer.parseInt(values[0]);
                int valueInt = Integer.parseInt(values[1]);
                valueInt *= 0.6;
                int value = goldInt + valueInt;
                player.getInventory().clear();
                ItemStack gold = new ItemStack(Material.GOLD_NUGGET, value);
                player.getInventory().addItem(gold);
                player.sendMessage(ChatColor.GREEN + "คุณไม่มีอาชีพแล้ว");
                MainPlugin.roleConfig.getConfig().set(player.getName(), "NULL");
                ConfigReader.save(MainPlugin.roleConfig);
            }

            if (event.getClickedBlock().getX() == 308 && event.getClickedBlock().getY() == -32 && event.getClickedBlock().getZ() == -156) {
                event.getPlayer().getInventory().addItem(new ItemStack(Material.COOKED_BEEF, 64));
            }

            if (event.getClickedBlock().getX() == 285 && event.getClickedBlock().getY() == -33 && event.getClickedBlock().getZ() == -164) {
                Player player = event.getPlayer();
                String role = "WARRIOR";
                EnchantList st = new EnchantList();
                st.addEnchant(new Enchant(Enchantment.DURABILITY, 5));
                ItemStack stone_sword = getItem(new ItemStack(Material.STONE_SWORD), null, st, false);
                ItemStack shield = new ItemStack(Material.SHIELD);
                ItemStack boot = getItem(new ItemStack(Material.LEATHER_BOOTS), null, null, true);
                ItemStack pant = getItem(new ItemStack(Material.LEATHER_LEGGINGS), null, null, true);
                ItemStack chest = getItem(new ItemStack(Material.LEATHER_CHESTPLATE), null, null, true);
                ItemStack head = getItem(new ItemStack(Material.LEATHER_HELMET), null, null, true);
                if (getRole(player) == null) {
                    MainPlugin.roleConfig.getConfig().set(player.getName(), role);
                    ConfigReader.save(MainPlugin.roleConfig);
                    player.getInventory().addItem(stone_sword, shield, boot, pant, chest, head);
                }
                if (getRole(player) != null && !getRole(player).equals(role)) {
                    String[] values = getGoldValue(player).split(" ");
                    int goldInt = Integer.parseInt(values[0]);
                    int valueInt = Integer.parseInt(values[1]);
                    valueInt *= 0.6;
                    int value = goldInt + valueInt;
                    player.getInventory().clear();
                    ItemStack gold = new ItemStack(Material.GOLD_NUGGET, value);
                    player.getInventory().addItem(gold);
                    player.sendMessage(ChatColor.YELLOW + "Changed Role");
                    player.getInventory().addItem(stone_sword, shield, boot, pant, chest, head);
                    MainPlugin.roleConfig.getConfig().set(player.getName(), role);
                    ConfigReader.save(MainPlugin.roleConfig);
                }
                player.sendMessage(ChatColor.RED + "You are " + role + " (นักรบ)");
                player.teleport(new Location(StaticLocations.fukieWorld, 285, -33, -168));
            }
            if (event.getClickedBlock().getX() == 293 && event.getClickedBlock().getY() == -33 && event.getClickedBlock().getZ() == -164) {
                Player player = event.getPlayer();
                String role = "SNIPER";
                EnchantList wood_sword = new EnchantList();
                wood_sword.addEnchant(new Enchant(Enchantment.DURABILITY, 3));
                EnchantList protect1 = new EnchantList();
                protect1.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1));
                ItemStack stone_sword = getItem(new ItemStack(Material.WOODEN_SWORD), null, wood_sword, false, null);
                ItemStack leather_head = getItem(new ItemStack(Material.LEATHER_HELMET), null, protect1, true, null);
                ItemStack leather_chest = getItem(new ItemStack(Material.LEATHER_CHESTPLATE), null, protect1, true, null);
                ItemStack leather_pant = getItem(new ItemStack(Material.LEATHER_LEGGINGS), null, protect1, true, null);
                ItemStack leather_boot = getItem(new ItemStack(Material.LEATHER_BOOTS), null, protect1, true, null);
                ItemStack bow = new ItemStack(Material.BOW);
                ItemStack arrow = new ItemStack(Material.ARROW, 64);
                if (getRole(player) == null) {
                    MainPlugin.roleConfig.getConfig().set(player.getName(), role);
                    ConfigReader.save(MainPlugin.roleConfig);
                    player.getInventory().addItem(stone_sword, leather_head, leather_chest, leather_pant, leather_boot, bow, arrow);
                }
                if (getRole(player) != null && !getRole(player).equals(role)) {
                    String[] values = getGoldValue(player).split(" ");
                    int goldInt = Integer.parseInt(values[0]);
                    int valueInt = Integer.parseInt(values[1]);
                    valueInt *= 0.6;
                    int value = goldInt + valueInt;
                    player.getInventory().clear();
                    ItemStack gold = new ItemStack(Material.GOLD_NUGGET, value);
                    player.getInventory().addItem(gold);
                    player.sendMessage(ChatColor.YELLOW + "Changed Role");
                    player.getInventory().addItem(stone_sword, leather_head, leather_chest, leather_pant, leather_boot, bow, arrow);
                    MainPlugin.roleConfig.getConfig().set(player.getName(), role);
                    ConfigReader.save(MainPlugin.roleConfig);
                }
                player.sendMessage(ChatColor.AQUA + "You are " + role + " (พลซุ่มยิง)");
                player.teleport(new Location(StaticLocations.fukieWorld, 293, -33, -168));
            }
            if (event.getClickedBlock().getX() == 301 && event.getClickedBlock().getY() == -33 && event.getClickedBlock().getZ() == -164) {
                Player player = event.getPlayer();
                String role = "DRUG ADDICT";

                EnchantList wood_sword = new EnchantList();
                wood_sword.addEnchant(new Enchant(Enchantment.DAMAGE_ALL, 1));

                EnchantList protect1 = new EnchantList();
                protect1.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1));

                EffectList heal = new EffectList();
                heal.addEffect(new Effect(PotionEffectType.HEAL, 1, 1));

                ItemStack wood = getItem(new ItemStack(Material.WOODEN_SWORD), null, wood_sword, true);
                ItemStack gold_head = getItem(new ItemStack(Material.GOLDEN_HELMET), null, protect1, true);
                ItemStack gold_chest = getItem(new ItemStack(Material.GOLDEN_CHESTPLATE), null, protect1, true);
                ItemStack gold_pant = getItem(new ItemStack(Material.GOLDEN_LEGGINGS), null, protect1, true);
                ItemStack gold_boot = getItem(new ItemStack(Material.GOLDEN_BOOTS), null, protect1, true);
                ItemStack apple = getItem(new ItemStack(Material.GOLDEN_APPLE), null, null, false);
                ItemStack potion = getItemPotion(new ItemStack(Material.SPLASH_POTION), "Heal", heal);
                if (getRole(player) == null) {
                    MainPlugin.roleConfig.getConfig().set(player.getName(), role);
                    ConfigReader.save(MainPlugin.roleConfig);
                    player.getInventory().addItem(wood, gold_head, gold_chest, gold_pant, gold_boot, apple, potion, potion);
                }
                if (getRole(player) != null && !getRole(player).equals(role)) {
                    String[] values = getGoldValue(player).split(" ");
                    int goldInt = Integer.parseInt(values[0]);
                    int valueInt = Integer.parseInt(values[1]);
                    valueInt *= 0.6;
                    int value = goldInt + valueInt;
                    player.getInventory().clear();
                    ItemStack gold = new ItemStack(Material.GOLD_NUGGET, value);
                    player.getInventory().addItem(gold);
                    player.sendMessage(ChatColor.YELLOW + "Changed Role");
                    player.getInventory().addItem(wood, gold_head, gold_chest, gold_pant, gold_boot, apple, potion, potion);
                    MainPlugin.roleConfig.getConfig().set(player.getName(), role);
                    ConfigReader.save(MainPlugin.roleConfig);
                }
                player.sendMessage(ChatColor.LIGHT_PURPLE + "You are " + role + " (นักเล่นยา)");
                player.teleport(new Location(StaticLocations.fukieWorld, 301, -33, -168));
            }

            if (event.getClickedBlock().getX() == 310 && event.getClickedBlock().getY() == -33 && event.getClickedBlock().getZ() == -164) {
                Player player = event.getPlayer();
                String role = "MYSTERY";

                EnchantList wood_sword = new EnchantList();
                wood_sword.addEnchant(new Enchant(Enchantment.DAMAGE_ALL, 1));

                ItemStack wood = getItem(new ItemStack(Material.WOODEN_SWORD), null, wood_sword, false);
                ItemStack chain_head = getItem(new ItemStack(Material.CHAINMAIL_HELMET), null, null, true);
                ItemStack chain_chest = getItem(new ItemStack(Material.CHAINMAIL_CHESTPLATE), null, null, true);
                ItemStack chain_pant = getItem(new ItemStack(Material.CHAINMAIL_LEGGINGS), null, null, true);
                ItemStack chain_boot = getItem(new ItemStack(Material.CHAINMAIL_BOOTS), null, null, true);
                ItemStack ender = getItem(new ItemStack(Material.ENDER_PEARL, 3), null, null, false);

                if (getRole(player) == null) {
                    MainPlugin.roleConfig.getConfig().set(player.getName(), role);
                    ConfigReader.save(MainPlugin.roleConfig);
                    player.getInventory().addItem(wood, chain_chest, chain_head, chain_pant, chain_boot, ender);
                }
                if (getRole(player) != null && !getRole(player).equals(role)) {
                    String[] values = getGoldValue(player).split(" ");
                    int goldInt = Integer.parseInt(values[0]);
                    int valueInt = Integer.parseInt(values[1]);
                    valueInt *= 0.6;
                    int value = goldInt + valueInt;
                    player.getInventory().clear();
                    ItemStack gold = new ItemStack(Material.GOLD_NUGGET, value);
                    player.getInventory().addItem(gold);
                    player.sendMessage(ChatColor.YELLOW + "Changed Role");
                    player.getInventory().addItem(wood, chain_chest, chain_head, chain_pant, chain_boot, ender);
                    MainPlugin.roleConfig.getConfig().set(player.getName(), role);
                    ConfigReader.save(MainPlugin.roleConfig);
                }
                player.sendMessage("You are " + ChatColor.MAGIC + role + ChatColor.GOLD + " (คนเล่นของ)");
                player.teleport(new Location(StaticLocations.fukieWorld, 309, -33, -168));
            }

            //level 2 shop
            if (event.getClickedBlock().getX() == 39 && event.getClickedBlock().getY() == -58 && event.getClickedBlock().getZ() == -163) {
                Location smith = new Location(Bukkit.getWorld("world_fukie"), 290, -33, -159, -180, 0);
                event.getPlayer().teleport(smith);
            }

            if (event.getClickedBlock().getX() == 282 && event.getClickedBlock().getY() == -30 && event.getClickedBlock().getZ() == -161) {
                Player player = event.getPlayer();
                Ravager ravager = (Ravager) player.getWorld().spawnEntity(player.getLocation(), EntityType.RAVAGER);
                ravager.addPassenger(player);
            }
        } catch (NullPointerException ignored) {
            ignored.printStackTrace();
        }
    }

    private String getRole(Player player) {
        return MainPlugin.roleConfig.getConfig().getString(player.getName());
    }

    private String getGoldValue(Player player) {
        int gold = 0;
        int value = 0;
        ItemStack[] all = player.getInventory().getContents();

        for (ItemStack itemStack : all) {
            if (itemStack == null) continue;
            if (itemStack.getType() == Material.GOLD_NUGGET) {
                gold += itemStack.getAmount();
                continue;
            }
            if (itemStack.getItemMeta().hasLore()) {
                List<String> lores = itemStack.getItemMeta().getLore();
                String[] ss = lores.get(0).split(" ");
                value += Integer.parseInt(ss[0].substring(2));
            }
        }
        return gold + " " + value;
    }

    @EventHandler
    public void Merchant(PlayerInteractEntityEvent event) {
        if (event.getPlayer().getWorld() != StaticLocations.fukieWorld) return;
        Player player = event.getPlayer();
        if (event.getRightClicked().getName().equals("Pius")) {
            player.openInventory(showInventory("Shop-Warrior"));
        }
        if (event.getRightClicked().getName().equals("Maya")) {
            player.openInventory(showInventory("Shop-Sniper"));
        }
        if (event.getRightClicked().getName().equals("Draco")) {
            player.openInventory(showInventory("Shop-Drug"));
        }
        if (event.getRightClicked().getName().equals("Shiphaai")) {
            player.openInventory(showInventory("Shop-Mysterious"));
        }
    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent event) {
        try {
            if (!event.getWhoClicked().getWorld().getName().equals("world_fukie") || event.getCurrentItem() == null)
                return;
            String s = event.getView().getTitle();
            if (!s.startsWith("Shop")) return;
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            player.playSound(player, Sound.BLOCK_NOTE_BLOCK_COW_BELL, 0.5f, 1);
            if (event.getView().getTitle().startsWith("Shop-Warrior")) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals("ชุดเกราะ")) {
                    Inventory inventory = Bukkit.createInventory(null, 9 * 6, "Shop-Warrior | Armors");
                    player.openInventory(inventory);

                    EnchantList leather_cap = new EnchantList();
                    leather_cap.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4));
                    leather_cap.addEnchant(new Enchant(Enchantment.OXYGEN, 1));

                    EnchantList chain_helmet = new EnchantList();
                    chain_helmet.addEnchant(new Enchant(Enchantment.PROTECTION_EXPLOSIONS, 1));
                    chain_helmet.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5));
                    chain_helmet.addEnchant(new Enchant(Enchantment.OXYGEN, 1));

                    EnchantList iron_helmet = new EnchantList();
                    iron_helmet.addEnchant(new Enchant(Enchantment.PROTECTION_EXPLOSIONS, 1));
                    iron_helmet.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 7));

                    EnchantList diamond_helmet = new EnchantList();
                    diamond_helmet.addEnchant(new Enchant(Enchantment.PROTECTION_EXPLOSIONS, 1));
                    diamond_helmet.addEnchant(new Enchant(Enchantment.PROTECTION_FIRE, 1));
                    diamond_helmet.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 1));
                    diamond_helmet.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 7));

                    EnchantList leather_chest = new EnchantList();
                    leather_chest.addEnchant(new Enchant(Enchantment.PROTECTION_EXPLOSIONS, 1));
                    leather_chest.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3));

                    EnchantList chain_chest = new EnchantList();
                    chain_chest.addEnchant(new Enchant(Enchantment.PROTECTION_EXPLOSIONS, 1));
                    chain_chest.addEnchant(new Enchant(Enchantment.PROTECTION_FIRE, 1));
                    chain_chest.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 1));
                    chain_chest.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4));

                    EnchantList iron_chest = new EnchantList();
                    iron_chest.addEnchant(new Enchant(Enchantment.PROTECTION_FIRE, 2));
                    iron_chest.addEnchant(new Enchant(Enchantment.PROTECTION_EXPLOSIONS, 2));
                    iron_chest.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 3));
                    iron_chest.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5));

                    EnchantList diamond_chest = new EnchantList();
                    diamond_chest.addEnchant(new Enchant(Enchantment.PROTECTION_FIRE, 3));
                    diamond_chest.addEnchant(new Enchant(Enchantment.PROTECTION_EXPLOSIONS, 3));
                    diamond_chest.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 4));
                    diamond_chest.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 6));

                    EnchantList leather_pant = new EnchantList();
                    leather_pant.addEnchant(new Enchant(Enchantment.PROTECTION_FIRE, 1));
                    leather_pant.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2));

                    EnchantList chain_pant = new EnchantList();
                    chain_pant.addEnchant(new Enchant(Enchantment.PROTECTION_FIRE, 1));
                    chain_pant.addEnchant(new Enchant(Enchantment.PROTECTION_EXPLOSIONS, 1));
                    chain_pant.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 2));
                    chain_pant.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2));

                    EnchantList iron_pant = new EnchantList();
                    iron_pant.addEnchant(new Enchant(Enchantment.PROTECTION_FIRE, 2));
                    iron_pant.addEnchant(new Enchant(Enchantment.PROTECTION_EXPLOSIONS, 1));
                    iron_pant.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 2));
                    iron_pant.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3));

                    EnchantList diamond_pant = new EnchantList();
                    diamond_pant.addEnchant(new Enchant(Enchantment.PROTECTION_FIRE, 3));
                    diamond_pant.addEnchant(new Enchant(Enchantment.PROTECTION_EXPLOSIONS, 1));
                    diamond_pant.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 3));
                    diamond_pant.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3));

                    EnchantList leather_boot = new EnchantList();
                    leather_boot.addEnchant(new Enchant(Enchantment.PROTECTION_FALL, 1));
                    leather_boot.addEnchant(new Enchant(Enchantment.PROTECTION_FIRE, 1));
                    leather_boot.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3));

                    EnchantList chain_boot = new EnchantList();
                    chain_boot.addEnchant(new Enchant(Enchantment.PROTECTION_FALL, 2));
                    chain_boot.addEnchant(new Enchant(Enchantment.PROTECTION_FIRE, 2));
                    chain_boot.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3));

                    EnchantList iron_boot = new EnchantList();
                    iron_boot.addEnchant(new Enchant(Enchantment.DEPTH_STRIDER, 1));
                    iron_boot.addEnchant(new Enchant(Enchantment.PROTECTION_FALL, 2));
                    iron_boot.addEnchant(new Enchant(Enchantment.PROTECTION_FIRE, 1));
                    iron_boot.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4));

                    EnchantList diamond_boot = new EnchantList();
                    diamond_boot.addEnchant(new Enchant(Enchantment.DEPTH_STRIDER, 1));
                    diamond_boot.addEnchant(new Enchant(Enchantment.PROTECTION_FALL, 2));
                    diamond_boot.addEnchant(new Enchant(Enchantment.PROTECTION_FIRE, 1));
                    diamond_boot.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 1));
                    diamond_boot.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4));


                    inventory.setItem(1, getItem(new ItemStack(Material.LEATHER_HELMET), null, leather_cap, true, ChatColor.GOLD + "6 Gold Nuggets"));
                    inventory.setItem(3, getItem(new ItemStack(Material.CHAINMAIL_HELMET), null, chain_helmet, true, ChatColor.GOLD + "17 Gold Nuggets"));
                    inventory.setItem(5, getItem(new ItemStack(Material.IRON_HELMET), null, iron_helmet, true, ChatColor.GOLD + "26 Gold Nuggets"));
                    inventory.setItem(7, getItem(new ItemStack(Material.DIAMOND_HELMET), null, diamond_helmet, true, ChatColor.GOLD + "41 Gold Nuggets"));
                    inventory.setItem(10, getItem(new ItemStack(Material.LEATHER_CHESTPLATE), null, leather_chest, true, ChatColor.GOLD + "10 Gold Nuggets"));
                    inventory.setItem(12, getItem(new ItemStack(Material.CHAINMAIL_CHESTPLATE), null, chain_chest, true, ChatColor.GOLD + "20 Gold Nuggets"));
                    inventory.setItem(14, getItem(new ItemStack(Material.IRON_CHESTPLATE), null, iron_chest, true, ChatColor.GOLD + "31 Gold Nuggets"));
                    inventory.setItem(16, getItem(new ItemStack(Material.DIAMOND_CHESTPLATE), null, diamond_chest, true, ChatColor.GOLD + "46 Gold Nuggets"));
                    inventory.setItem(19, getItem(new ItemStack(Material.LEATHER_LEGGINGS), null, leather_pant, true, ChatColor.GOLD + "8 Gold Nuggets"));
                    inventory.setItem(21, getItem(new ItemStack(Material.CHAINMAIL_LEGGINGS), null, chain_pant, true, ChatColor.GOLD + "11 Gold Nuggets"));
                    inventory.setItem(23, getItem(new ItemStack(Material.IRON_LEGGINGS), null, iron_pant, true, ChatColor.GOLD + "28 Gold Nuggets"));
                    inventory.setItem(25, getItem(new ItemStack(Material.DIAMOND_LEGGINGS), null, diamond_pant, true, ChatColor.GOLD + "43 Gold Nuggets"));
                    inventory.setItem(28, getItem(new ItemStack(Material.LEATHER_BOOTS), null, leather_boot, true, ChatColor.GOLD + "6 Gold Nuggets"));
                    inventory.setItem(30, getItem(new ItemStack(Material.CHAINMAIL_BOOTS), null, chain_boot, true, ChatColor.GOLD + "12 Gold Nuggets"));
                    inventory.setItem(32, getItem(new ItemStack(Material.IRON_BOOTS), null, iron_boot, true, ChatColor.GOLD + "25 Gold Nuggets"));
                    inventory.setItem(34, getItem(new ItemStack(Material.DIAMOND_BOOTS), null, diamond_boot, true, ChatColor.GOLD + "39 Gold Nuggets"));

                    inventory.setItem(45, getItem(new ItemStack(Material.RED_BED), "Back To Menu", null, false, "Click To Interact"));
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals("อาวุธ")) {
                    Inventory inventory = Bukkit.createInventory(null, 9 * 6, "Shop-Warrior | Combats");
                    player.openInventory(inventory);

                    EnchantList virgin_axe = new EnchantList();
                    virgin_axe.addEnchant(new Enchant(Enchantment.DAMAGE_ARTHROPODS, 1));
                    virgin_axe.addEnchant(new Enchant(Enchantment.DAMAGE_ALL, 4));
                    virgin_axe.addEnchant(new Enchant(Enchantment.DAMAGE_UNDEAD, 2));
                    virgin_axe.addEnchant(new Enchant(Enchantment.DURABILITY, 1));

                    EnchantList low_axe = new EnchantList();
                    low_axe.addEnchant(new Enchant(Enchantment.KNOCKBACK, 1));
                    low_axe.addEnchant(new Enchant(Enchantment.DAMAGE_ALL, 6));
                    low_axe.addEnchant(new Enchant(Enchantment.DAMAGE_UNDEAD, 3));
                    low_axe.addEnchant(new Enchant(Enchantment.DURABILITY, 3));

                    EnchantList privilege_axe = new EnchantList();
                    privilege_axe.addEnchant(new Enchant(Enchantment.FIRE_ASPECT, 1));
                    privilege_axe.addEnchant(new Enchant(Enchantment.KNOCKBACK, 2));
                    privilege_axe.addEnchant(new Enchant(Enchantment.DAMAGE_ALL, 8));
                    privilege_axe.addEnchant(new Enchant(Enchantment.DAMAGE_UNDEAD, 4));
                    privilege_axe.addEnchant(new Enchant(Enchantment.SWEEPING_EDGE, 1));
                    privilege_axe.addEnchant(new Enchant(Enchantment.DURABILITY, 3));

                    EnchantList fire_axe = new EnchantList();
                    fire_axe.addEnchant(new Enchant(Enchantment.DAMAGE_ARTHROPODS, 4));
                    fire_axe.addEnchant(new Enchant(Enchantment.FIRE_ASPECT, 1));
                    fire_axe.addEnchant(new Enchant(Enchantment.KNOCKBACK, 1));
                    fire_axe.addEnchant(new Enchant(Enchantment.DAMAGE_ALL, 10));
                    fire_axe.addEnchant(new Enchant(Enchantment.DAMAGE_UNDEAD, 5));
                    fire_axe.addEnchant(new Enchant(Enchantment.SWEEPING_EDGE, 1));
                    fire_axe.addEnchant(new Enchant(Enchantment.DURABILITY, 1));

                    EnchantList leader_axe = new EnchantList();
                    leader_axe.addEnchant(new Enchant(Enchantment.DAMAGE_ARTHROPODS, 5));
                    leader_axe.addEnchant(new Enchant(Enchantment.FIRE_ASPECT, 2));
                    leader_axe.addEnchant(new Enchant(Enchantment.KNOCKBACK, 3));
                    leader_axe.addEnchant(new Enchant(Enchantment.DAMAGE_ALL, 13));
                    leader_axe.addEnchant(new Enchant(Enchantment.DAMAGE_UNDEAD, 7));
                    leader_axe.addEnchant(new Enchant(Enchantment.DURABILITY, 1));

                    EnchantList virgin_sword = new EnchantList();
                    virgin_sword.addEnchant(new Enchant(Enchantment.KNOCKBACK, 1));
                    virgin_sword.addEnchant(new Enchant(Enchantment.DAMAGE_ALL, 5));
                    virgin_sword.addEnchant(new Enchant(Enchantment.DAMAGE_UNDEAD, 2));
                    virgin_sword.addEnchant(new Enchant(Enchantment.DURABILITY, 2));

                    EnchantList noob_sword = new EnchantList();
                    noob_sword.addEnchant(new Enchant(Enchantment.KNOCKBACK, 2));
                    noob_sword.addEnchant(new Enchant(Enchantment.DAMAGE_ALL, 9));
                    noob_sword.addEnchant(new Enchant(Enchantment.DAMAGE_UNDEAD, 2));
                    noob_sword.addEnchant(new Enchant(Enchantment.SWEEPING_EDGE, 1));
                    noob_sword.addEnchant(new Enchant(Enchantment.DURABILITY, 3));

                    EnchantList inherit_sword = new EnchantList();
                    inherit_sword.addEnchant(new Enchant(Enchantment.FIRE_ASPECT, 1));
                    inherit_sword.addEnchant(new Enchant(Enchantment.KNOCKBACK, 1));
                    inherit_sword.addEnchant(new Enchant(Enchantment.DAMAGE_ALL, 11));
                    inherit_sword.addEnchant(new Enchant(Enchantment.DAMAGE_UNDEAD, 3));
                    inherit_sword.addEnchant(new Enchant(Enchantment.SWEEPING_EDGE, 1));
                    inherit_sword.addEnchant(new Enchant(Enchantment.DURABILITY, 3));

                    EnchantList bata_sword = new EnchantList();
                    bata_sword.addEnchant(new Enchant(Enchantment.DAMAGE_ARTHROPODS, 4));
                    bata_sword.addEnchant(new Enchant(Enchantment.FIRE_ASPECT, 1));
                    bata_sword.addEnchant(new Enchant(Enchantment.KNOCKBACK, 1));
                    bata_sword.addEnchant(new Enchant(Enchantment.DAMAGE_ALL, 13));
                    bata_sword.addEnchant(new Enchant(Enchantment.DAMAGE_UNDEAD, 4));
                    bata_sword.addEnchant(new Enchant(Enchantment.SWEEPING_EDGE, 2));
                    bata_sword.addEnchant(new Enchant(Enchantment.DURABILITY, 3));

                    EnchantList samurai_sword = new EnchantList();
                    samurai_sword.addEnchant(new Enchant(Enchantment.DAMAGE_ARTHROPODS, 5));
                    samurai_sword.addEnchant(new Enchant(Enchantment.FIRE_ASPECT, 2));
                    samurai_sword.addEnchant(new Enchant(Enchantment.KNOCKBACK, 2));
                    samurai_sword.addEnchant(new Enchant(Enchantment.DAMAGE_ALL, 15));
                    samurai_sword.addEnchant(new Enchant(Enchantment.DAMAGE_UNDEAD, 6));
                    samurai_sword.addEnchant(new Enchant(Enchantment.SWEEPING_EDGE, 2));
                    samurai_sword.addEnchant(new Enchant(Enchantment.DURABILITY, 1));

                    EnchantList unreal_axe = new EnchantList();
                    unreal_axe.addEnchant(new Enchant(Enchantment.DAMAGE_ALL, 3));
                    unreal_axe.addEnchant(new Enchant(Enchantment.DAMAGE_UNDEAD, 1));
                    unreal_axe.addEnchant(new Enchant(Enchantment.DURABILITY, 1));

                    EnchantList unreal_sword = new EnchantList();
                    unreal_sword.addEnchant(new Enchant(Enchantment.KNOCKBACK, 1));
                    unreal_sword.addEnchant(new Enchant(Enchantment.DAMAGE_ALL, 7));
                    unreal_sword.addEnchant(new Enchant(Enchantment.DURABILITY, 4));

                    inventory.setItem(0, getItem(new ItemStack(Material.STONE_SWORD), null, null, false, ChatColor.GOLD + "5 Gold Nuggets"));
                    inventory.setItem(2, getItem(new ItemStack(Material.IRON_AXE), "ขวานเวอร์จิ้น", virgin_axe, false, ChatColor.GOLD + "24 Gold Nuggets"));
                    inventory.setItem(4, getItem(new ItemStack(Material.IRON_AXE), "ขวานนักรบชั้นต่ำ", low_axe, false, ChatColor.GOLD + "29 Gold Nuggets"));
                    inventory.setItem(6, getItem(new ItemStack(Material.IRON_AXE), "ขวานเด็กเส้น", privilege_axe, false, ChatColor.GOLD + "40 Gold Nuggets"));
                    inventory.setItem(11, getItem(new ItemStack(Material.DIAMOND_AXE), "ขวานนักรบอัคคี", fire_axe, false, ChatColor.GOLD + "49 Gold Nuggets"));
                    inventory.setItem(13, getItem(new ItemStack(Material.DIAMOND_AXE), "ผู้นำนักรบใหญ่", leader_axe, false, ChatColor.GOLD + "56 Gold Nuggets"));
                    inventory.setItem(20, getItem(new ItemStack(Material.IRON_SWORD), "ดาบเวอร์จิ้น", virgin_sword, false, ChatColor.GOLD + "20 Gold Nuggets"));
                    inventory.setItem(22, getItem(new ItemStack(Material.IRON_SWORD), "ดาบนักรบกระโปก", noob_sword, false, ChatColor.GOLD + "30 Gold Nuggets"));
                    inventory.setItem(24, getItem(new ItemStack(Material.IRON_SWORD), "ดาบผู้สืบทอด", inherit_sword, false, ChatColor.GOLD + "36 Gold Nuggets"));
                    inventory.setItem(29, getItem(new ItemStack(Material.DIAMOND_SWORD), "นักดาบเพลิงจรดบาทา", bata_sword, false, ChatColor.GOLD + "48 Gold Nuggets"));
                    inventory.setItem(31, getItem(new ItemStack(Material.DIAMOND_SWORD), "ซามูไรสุริยะ", samurai_sword, false, ChatColor.GOLD + "60 Gold Nuggets"));
                    inventory.setItem(38, getItem(new ItemStack(Material.GOLDEN_AXE), "ขวานทองปลอม", unreal_axe, false, ChatColor.GOLD + "13 Gold Nuggets"));
                    inventory.setItem(40, getItem(new ItemStack(Material.GOLDEN_SWORD), "ดาบทองปลอม", unreal_sword, false, ChatColor.GOLD + "10 Gold Nuggets"));
                    inventory.setItem(42, getItem(new ItemStack(Material.SHIELD), null, null, false, ChatColor.GOLD + "7 Gold Nuggets"));

                    inventory.setItem(45, getItem(new ItemStack(Material.RED_BED), "Back To Menu", null, false, "Click To Interact"));
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals("อื่น ๆ")) {
                    Inventory inventory = Bukkit.createInventory(null, 9 * 6, "Shop-Warrior | Stuffs");
                    player.openInventory(inventory);

                    EffectList strength = new EffectList();
                    strength.addEffect(new Effect(PotionEffectType.INCREASE_DAMAGE, 18 * 100, 1));

                    inventory.setItem(3, getItem(new ItemStack(Material.SNOWBALL, 16), null, null, false, ChatColor.GOLD + "3 Gold Nuggets"));
                    inventory.setItem(5, getItem(new ItemStack(Material.FIREWORK_ROCKET, 2), null, null, false, ChatColor.GOLD + "2 Gold Nuggets"));
                    inventory.setItem(21, getItemPotion(new ItemStack(Material.POTION), "Strength Potion", strength, ChatColor.GOLD + "22 Gold Nuggets"));
                    inventory.setItem(23, getItem(new ItemStack(Material.EXPERIENCE_BOTTLE, 4), null, null, false, ChatColor.GOLD + "1 Gold Nuggets"));

                    inventory.setItem(45, getItem(new ItemStack(Material.RED_BED), "Back To Menu", null, false, "Click To Interact"));
                }

                if (event.getCurrentItem().getItemMeta().getDisplayName().equals("Back To Menu")) {
                    player.openInventory(showInventory("Shop-Warrior"));
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals("Exit From Shop")) {
                    player.closeInventory();
                    player.teleport(new Location(StaticLocations.fukieWorld, 285, -34, -161));
                }

            }
            if (event.getView().getTitle().startsWith("Shop-Sniper")) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals("ชุดเกราะ")) {
                    Inventory inventory = Bukkit.createInventory(null, 9 * 6, "Shop-Sniper | Armors");
                    player.openInventory(inventory);

                    EnchantList leather_head = new EnchantList();
                    leather_head.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2));
                    leather_head.addEnchant(new Enchant(Enchantment.OXYGEN, 1));

                    EnchantList leather_chest = new EnchantList();
                    leather_chest.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 1));
                    leather_chest.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2));

                    EnchantList leather_pant = new EnchantList();
                    leather_pant.addEnchant(new Enchant(Enchantment.PROTECTION_EXPLOSIONS, 1));
                    leather_pant.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1));

                    EnchantList leather_boot = new EnchantList();
                    leather_boot.addEnchant(new Enchant(Enchantment.PROTECTION_FALL, 1));
                    leather_boot.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2));

                    EnchantList gold_helmet = new EnchantList();
                    gold_helmet.addEnchant(new Enchant(Enchantment.WATER_WORKER, 1));
                    gold_helmet.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 1));
                    gold_helmet.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2));
                    gold_helmet.addEnchant(new Enchant(Enchantment.OXYGEN, 1));

                    EnchantList gold_chest = new EnchantList();
                    gold_chest.addEnchant(new Enchant(Enchantment.PROTECTION_EXPLOSIONS, 1));
                    gold_chest.addEnchant(new Enchant(Enchantment.PROTECTION_FIRE, 1));
                    gold_chest.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2));
                    gold_chest.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 2));

                    EnchantList gold_pant = new EnchantList();
                    gold_pant.addEnchant(new Enchant(Enchantment.PROTECTION_EXPLOSIONS, 1));
                    gold_pant.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 1));
                    gold_pant.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1));

                    EnchantList gold_boot = new EnchantList();
                    gold_boot.addEnchant(new Enchant(Enchantment.DEPTH_STRIDER, 1));
                    gold_boot.addEnchant(new Enchant(Enchantment.PROTECTION_FALL, 1));
                    gold_boot.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3));

                    EnchantList chain_head = new EnchantList();
                    chain_head.addEnchant(new Enchant(Enchantment.PROTECTION_EXPLOSIONS, 1));
                    chain_head.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 2));
                    chain_head.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3));
                    chain_head.addEnchant(new Enchant(Enchantment.OXYGEN, 1));

                    EnchantList chain_chest = new EnchantList();
                    chain_chest.addEnchant(new Enchant(Enchantment.PROTECTION_FIRE, 1));
                    chain_chest.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 2));
                    chain_chest.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4));

                    EnchantList chain_pant = new EnchantList();
                    chain_pant.addEnchant(new Enchant(Enchantment.PROTECTION_EXPLOSIONS, 1));
                    chain_pant.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 1));
                    chain_pant.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2));

                    EnchantList chain_boot = new EnchantList();
                    chain_boot.addEnchant(new Enchant(Enchantment.DEPTH_STRIDER, 1));
                    chain_boot.addEnchant(new Enchant(Enchantment.PROTECTION_FALL, 2));
                    chain_boot.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3));

                    EnchantList iron_head = new EnchantList();
                    iron_head.addEnchant(new Enchant(Enchantment.PROTECTION_EXPLOSIONS, 2));
                    iron_head.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 3));
                    iron_head.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5));
                    iron_head.addEnchant(new Enchant(Enchantment.OXYGEN, 1));

                    EnchantList iron_chest = new EnchantList();
                    iron_chest.addEnchant(new Enchant(Enchantment.PROTECTION_FIRE, 2));
                    iron_chest.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 2));
                    iron_chest.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3));

                    EnchantList iron_pant = new EnchantList();
                    iron_pant.addEnchant(new Enchant(Enchantment.PROTECTION_EXPLOSIONS, 1));
                    iron_pant.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 2));
                    iron_pant.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2));

                    EnchantList iron_boot = new EnchantList();
                    iron_boot.addEnchant(new Enchant(Enchantment.DEPTH_STRIDER, 1));
                    iron_boot.addEnchant(new Enchant(Enchantment.PROTECTION_FALL, 2));
                    iron_boot.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4));

                    inventory.setItem(1, getItem(new ItemStack(Material.LEATHER_HELMET), null, leather_head, true, ChatColor.GOLD + "6 Gold Nuggets"));
                    inventory.setItem(3, getItem(new ItemStack(Material.LEATHER_CHESTPLATE), null, leather_chest, true, ChatColor.GOLD + "11 Gold Nuggets"));
                    inventory.setItem(5, getItem(new ItemStack(Material.LEATHER_LEGGINGS), null, leather_pant, true, ChatColor.GOLD + "9 Gold Nuggets"));
                    inventory.setItem(7, getItem(new ItemStack(Material.LEATHER_BOOTS), null, leather_boot, true, ChatColor.GOLD + "9 Gold Nuggets"));
                    inventory.setItem(10, getItem(new ItemStack(Material.GOLDEN_HELMET), null, gold_helmet, true, ChatColor.GOLD + "14 Gold Nuggets"));
                    inventory.setItem(12, getItem(new ItemStack(Material.GOLDEN_CHESTPLATE), null, gold_chest, true, ChatColor.GOLD + "17 Gold Nuggets"));
                    inventory.setItem(14, getItem(new ItemStack(Material.GOLDEN_LEGGINGS), null, gold_pant, true, ChatColor.GOLD + "14 Gold Nuggets"));
                    inventory.setItem(16, getItem(new ItemStack(Material.GOLDEN_BOOTS), null, gold_boot, true, ChatColor.GOLD + "14 Gold Nuggets"));
                    inventory.setItem(19, getItem(new ItemStack(Material.CHAINMAIL_HELMET), null, chain_head, true, ChatColor.GOLD + "24 Gold Nuggets"));
                    inventory.setItem(21, getItem(new ItemStack(Material.CHAINMAIL_CHESTPLATE), null, chain_chest, true, ChatColor.GOLD + "28 Gold Nuggets"));
                    inventory.setItem(23, getItem(new ItemStack(Material.CHAINMAIL_LEGGINGS), null, chain_pant, true, ChatColor.GOLD + "25 Gold Nuggets"));
                    inventory.setItem(25, getItem(new ItemStack(Material.CHAINMAIL_BOOTS), null, chain_boot, true, ChatColor.GOLD + "22 Gold Nuggets"));
                    inventory.setItem(28, getItem(new ItemStack(Material.IRON_HELMET), null, iron_head, true, ChatColor.GOLD + "31 Gold Nuggets"));
                    inventory.setItem(30, getItem(new ItemStack(Material.IRON_CHESTPLATE), null, iron_chest, true, ChatColor.GOLD + "38 Gold Nuggets"));
                    inventory.setItem(32, getItem(new ItemStack(Material.IRON_LEGGINGS), null, iron_pant, true, ChatColor.GOLD + "36 Gold Nuggets"));
                    inventory.setItem(34, getItem(new ItemStack(Material.IRON_BOOTS), null, iron_boot, true, ChatColor.GOLD + "30 Gold Nuggets"));

                    inventory.setItem(45, getItem(new ItemStack(Material.RED_BED), "Back To Menu", null, false, "Click To Interact"));
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals("อาวุธ")) {
                    Inventory inventory = Bukkit.createInventory(null, 9 * 6, "Shop-Sniper | Combats");
                    player.openInventory(inventory);

                    EnchantList st = new EnchantList();
                    st.addEnchant(new Enchant(Enchantment.KNOCKBACK, 2));

                    EnchantList iron_sword = new EnchantList();
                    iron_sword.addEnchant(new Enchant(Enchantment.KNOCKBACK, 2));
                    iron_sword.addEnchant(new Enchant(Enchantment.DAMAGE_ALL, 1));

                    EnchantList noob_bow = new EnchantList();
                    noob_bow.addEnchant(new Enchant(Enchantment.ARROW_DAMAGE, 2));
                    noob_bow.addEnchant(new Enchant(Enchantment.ARROW_KNOCKBACK, 1));
                    noob_bow.addEnchant(new Enchant(Enchantment.VANISHING_CURSE, 1));

                    EnchantList ting_bow = new EnchantList();
                    ting_bow.addEnchant(new Enchant(Enchantment.ARROW_INFINITE, 1));
                    ting_bow.addEnchant(new Enchant(Enchantment.ARROW_KNOCKBACK, 1));
                    ting_bow.addEnchant(new Enchant(Enchantment.VANISHING_CURSE, 1));

                    EnchantList blood_bow = new EnchantList();
                    blood_bow.addEnchant(new Enchant(Enchantment.ARROW_INFINITE, 1));
                    blood_bow.addEnchant(new Enchant(Enchantment.ARROW_DAMAGE, 2));
                    blood_bow.addEnchant(new Enchant(Enchantment.ARROW_KNOCKBACK, 1));
                    AttributeList blood_bow_att = new AttributeList();
                    blood_bow_att.addAttribute(new Attribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH, new AttributeModifier(UUID.randomUUID(), "", -4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND)));

                    EnchantList saksit_bow = new EnchantList();
                    saksit_bow.addEnchant(new Enchant(Enchantment.ARROW_FIRE, 1));
                    saksit_bow.addEnchant(new Enchant(Enchantment.ARROW_INFINITE, 1));
                    saksit_bow.addEnchant(new Enchant(Enchantment.ARROW_DAMAGE, 3));
                    saksit_bow.addEnchant(new Enchant(Enchantment.ARROW_KNOCKBACK, 2));
                    AttributeList saksit_bow_att = new AttributeList();
                    saksit_bow_att.addAttribute(new Attribute(org.bukkit.attribute.Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(UUID.randomUUID(), "", -0.05, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND)));
                    saksit_bow_att.addAttribute(new Attribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH, new AttributeModifier(UUID.randomUUID(), "", -2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND)));

                    EnchantList quick_cross = new EnchantList();
                    quick_cross.addEnchant(new Enchant(Enchantment.PIERCING, 2));
                    quick_cross.addEnchant(new Enchant(Enchantment.QUICK_CHARGE, 2));

                    EnchantList break_cross = new EnchantList();
                    break_cross.addEnchant(new Enchant(Enchantment.KNOCKBACK, 3));
                    break_cross.addEnchant(new Enchant(Enchantment.MULTISHOT, 1));
                    break_cross.addEnchant(new Enchant(Enchantment.PIERCING, 4));
                    break_cross.addEnchant(new Enchant(Enchantment.QUICK_CHARGE, 4));
                    break_cross.addEnchant(new Enchant(Enchantment.DAMAGE_ALL, 3));

                    EffectList arrow_heal = new EffectList();
                    arrow_heal.addEffect(new Effect(PotionEffectType.HEAL, 1, 0));

                    EffectList arrow_heal2 = new EffectList();
                    arrow_heal2.addEffect(new Effect(PotionEffectType.HEAL, 1, 1));

                    EffectList arrow_slow = new EffectList();
                    arrow_slow.addEffect(new Effect(PotionEffectType.SLOW, 400, 3));

                    EffectList arrow_turtle = new EffectList();
                    arrow_turtle.addEffect(new Effect(PotionEffectType.SLOW, 400, 5));
                    arrow_turtle.addEffect(new Effect(PotionEffectType.DAMAGE_RESISTANCE, 400, 3));

                    inventory.setItem(3, getItem(new ItemStack(Material.STONE_SWORD), null, st, false, ChatColor.GOLD + "8 Gold Nuggets"));
                    inventory.setItem(5, getItemDurability(new ItemStack(Material.IRON_SWORD), null, iron_sword, 250 - 35, ChatColor.GOLD + "7 Gold Nuggets"));
                    inventory.setItem(10, getItemDurability(new ItemStack(Material.BOW), "ธนูกาก ๆ", noob_bow, 384 - 80, ChatColor.GOLD + "12 Gold Nuggets"));
                    inventory.setItem(12, getItemDurability(new ItemStack(Material.BOW), "ธนูเส้นตึง", ting_bow, 384 - 210, ChatColor.GOLD + "16 Gold Nuggets"));
                    inventory.setItem(14, getItemAttribute(new ItemStack(Material.BOW), "ธนูสูบโลหิต", blood_bow, blood_bow_att, ChatColor.GOLD + "36 Gold Nuggets"));
                    inventory.setItem(16, getItemAttribute(new ItemStack(Material.BOW), "ศรศักดิ์สิทธิ์", saksit_bow, saksit_bow_att, ChatColor.GOLD + "46 Gold Nuggets"));
                    inventory.setItem(19, getItem(new ItemStack(Material.CROSSBOW), "ระดมยิง", quick_cross, false, ChatColor.GOLD + "11 Gold Nuggets"));
                    inventory.setItem(21, getItem(new ItemStack(Material.CROSSBOW), "กระสุนแตก", break_cross, false, ChatColor.GOLD + "18 Gold Nuggets"));
                    inventory.setItem(31, getItem(new ItemStack(Material.SHIELD), null, null, false, ChatColor.GOLD + "13 Gold Nuggets"));
                    inventory.setItem(38, getItem(new ItemStack(Material.ARROW, 10), null, null, false, ChatColor.GOLD + "1 Gold Nuggets"));
                    inventory.setItem(39, getItem(new ItemStack(Material.SPECTRAL_ARROW), null, null, false, ChatColor.GOLD + "2 Gold Nuggets"));
                    inventory.setItem(40, getItemPotionColor(new ItemStack(Material.TIPPED_ARROW), "Arrow of Healing", arrow_heal, Color.ORANGE, ChatColor.GOLD + "2 Gold Nuggets"));
                    inventory.setItem(41, getItemPotionColor(new ItemStack(Material.TIPPED_ARROW), "Arrow of Healing II", arrow_heal2, Color.RED, ChatColor.GOLD + "4 Gold Nuggets"));
                    inventory.setItem(42, getItemPotionColor(new ItemStack(Material.TIPPED_ARROW), "Arrow of Slowness", arrow_slow, Color.GRAY, ChatColor.GOLD + "5 Gold Nuggets"));
                    inventory.setItem(43, getItemPotionColor(new ItemStack(Material.TIPPED_ARROW), "Arrow of the Turtle Master", arrow_turtle, Color.LIME, ChatColor.GOLD + "6 Gold Nuggets"));

                    inventory.setItem(45, getItem(new ItemStack(Material.RED_BED), "Back To Menu", null, false, "Click To Interact"));

                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals("อื่น ๆ")) {
                    Inventory inventory = Bukkit.createInventory(null, 9 * 6, "Shop-Sniper | Stuffs");
                    player.openInventory(inventory);

                    EffectList night_potion = new EffectList();
                    night_potion.addEffect(new Effect(PotionEffectType.NIGHT_VISION, 3600, 0));

                    inventory.setItem(5, getItem(new ItemStack(Material.EXPERIENCE_BOTTLE, 5), null, null, false, ChatColor.GOLD + "1 Gold Nuggets"));
                    inventory.setItem(21, getItemPotion(new ItemStack(Material.POTION), "Potion of Night Vision", night_potion, ChatColor.GOLD + "20 Gold Nuggets"));
                    inventory.setItem(23, getItem(new ItemStack(Material.FIREWORK_ROCKET), null, null, false, ChatColor.GOLD + "1 Gold Nuggets"));
                    inventory.setItem(40, getItem(new ItemStack(Material.SNOWBALL, 16), null, null, false, ChatColor.GOLD + "1 Gold Nuggets"));

                    inventory.setItem(45, getItem(new ItemStack(Material.RED_BED), "Back To Menu", null, false, "Click To Interact"));
                }

                if (event.getCurrentItem().getItemMeta().getDisplayName().equals("Back To Menu")) {
                    player.openInventory(showInventory("Shop-Sniper"));
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals("Exit From Shop")) {
                    player.closeInventory();
                    player.teleport(new Location(StaticLocations.fukieWorld, 293, -34, -161));
                }
            }

            if (event.getView().getTitle().startsWith("Shop-Drug")) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals("ชุดเกราะ")) {
                    Inventory inventory = Bukkit.createInventory(null, 9 * 6, "Shop-Drug | Armors");
                    player.openInventory(inventory);

                    EnchantList leather_head = new EnchantList();
                    leather_head.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2));
                    leather_head.addEnchant(new Enchant(Enchantment.OXYGEN, 1));

                    EnchantList leather_chest = new EnchantList();
                    leather_chest.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 1));
                    leather_chest.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2));

                    EnchantList leather_pant = new EnchantList();
                    leather_pant.addEnchant(new Enchant(Enchantment.PROTECTION_EXPLOSIONS, 1));
                    leather_pant.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2));

                    EnchantList leather_boot = new EnchantList();
                    leather_boot.addEnchant(new Enchant(Enchantment.PROTECTION_FALL, 1));
                    leather_boot.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2));

                    EnchantList gold_helmet = new EnchantList();
                    gold_helmet.addEnchant(new Enchant(Enchantment.WATER_WORKER, 1));
                    gold_helmet.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 1));
                    gold_helmet.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2));
                    gold_helmet.addEnchant(new Enchant(Enchantment.OXYGEN, 1));

                    EnchantList gold_chest = new EnchantList();
                    gold_chest.addEnchant(new Enchant(Enchantment.PROTECTION_EXPLOSIONS, 1));
                    gold_chest.addEnchant(new Enchant(Enchantment.PROTECTION_FIRE, 1));
                    gold_chest.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2));
                    gold_chest.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 2));

                    EnchantList gold_pant = new EnchantList();
                    gold_pant.addEnchant(new Enchant(Enchantment.PROTECTION_EXPLOSIONS, 1));
                    gold_pant.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 1));
                    gold_pant.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2));

                    EnchantList gold_boot = new EnchantList();
                    gold_boot.addEnchant(new Enchant(Enchantment.DEPTH_STRIDER, 1));
                    gold_boot.addEnchant(new Enchant(Enchantment.PROTECTION_FALL, 1));
                    gold_boot.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3));

                    EnchantList chain_head = new EnchantList();
                    chain_head.addEnchant(new Enchant(Enchantment.PROTECTION_EXPLOSIONS, 1));
                    chain_head.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 2));
                    chain_head.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3));
                    chain_head.addEnchant(new Enchant(Enchantment.OXYGEN, 1));

                    EnchantList chain_chest = new EnchantList();
                    chain_chest.addEnchant(new Enchant(Enchantment.PROTECTION_FIRE, 1));
                    chain_chest.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 2));
                    chain_chest.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4));

                    EnchantList chain_pant = new EnchantList();
                    chain_pant.addEnchant(new Enchant(Enchantment.PROTECTION_EXPLOSIONS, 1));
                    chain_pant.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 1));
                    chain_pant.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3));

                    EnchantList chain_boot = new EnchantList();
                    chain_boot.addEnchant(new Enchant(Enchantment.DEPTH_STRIDER, 1));
                    chain_boot.addEnchant(new Enchant(Enchantment.PROTECTION_FALL, 2));
                    chain_boot.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3));

                    EnchantList iron_head = new EnchantList();
                    iron_head.addEnchant(new Enchant(Enchantment.PROTECTION_EXPLOSIONS, 2));
                    iron_head.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 3));
                    iron_head.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5));
                    iron_head.addEnchant(new Enchant(Enchantment.OXYGEN, 1));

                    EnchantList iron_chest = new EnchantList();
                    iron_chest.addEnchant(new Enchant(Enchantment.PROTECTION_FIRE, 2));
                    iron_chest.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 2));
                    iron_chest.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3));

                    EnchantList iron_pant = new EnchantList();
                    iron_pant.addEnchant(new Enchant(Enchantment.PROTECTION_EXPLOSIONS, 1));
                    iron_pant.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 2));
                    iron_pant.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3));

                    EnchantList iron_boot = new EnchantList();
                    iron_boot.addEnchant(new Enchant(Enchantment.DEPTH_STRIDER, 1));
                    iron_boot.addEnchant(new Enchant(Enchantment.PROTECTION_FALL, 2));
                    iron_boot.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4));

                    inventory.setItem(1, getItem(new ItemStack(Material.LEATHER_HELMET), null, leather_head, true, ChatColor.GOLD + "6 Gold Nuggets"));
                    inventory.setItem(3, getItem(new ItemStack(Material.LEATHER_CHESTPLATE), null, leather_chest, true, ChatColor.GOLD + "10 Gold Nuggets"));
                    inventory.setItem(5, getItem(new ItemStack(Material.LEATHER_LEGGINGS), null, leather_pant, true, ChatColor.GOLD + "9 Gold Nuggets"));
                    inventory.setItem(7, getItem(new ItemStack(Material.LEATHER_BOOTS), null, leather_boot, true, ChatColor.GOLD + "7 Gold Nuggets"));
                    inventory.setItem(10, getItem(new ItemStack(Material.GOLDEN_HELMET), null, gold_helmet, true, ChatColor.GOLD + "13 Gold Nuggets"));
                    inventory.setItem(12, getItem(new ItemStack(Material.GOLDEN_CHESTPLATE), null, gold_chest, true, ChatColor.GOLD + "17 Gold Nuggets"));
                    inventory.setItem(14, getItem(new ItemStack(Material.GOLDEN_LEGGINGS), null, gold_pant, true, ChatColor.GOLD + "14 Gold Nuggets"));
                    inventory.setItem(16, getItem(new ItemStack(Material.GOLDEN_BOOTS), null, gold_boot, true, ChatColor.GOLD + "11 Gold Nuggets"));
                    inventory.setItem(19, getItem(new ItemStack(Material.CHAINMAIL_HELMET), null, chain_head, true, ChatColor.GOLD + "24 Gold Nuggets"));
                    inventory.setItem(21, getItem(new ItemStack(Material.CHAINMAIL_CHESTPLATE), null, chain_chest, true, ChatColor.GOLD + "28 Gold Nuggets"));
                    inventory.setItem(23, getItem(new ItemStack(Material.CHAINMAIL_LEGGINGS), null, chain_pant, true, ChatColor.GOLD + "25 Gold Nuggets"));
                    inventory.setItem(25, getItem(new ItemStack(Material.CHAINMAIL_BOOTS), null, chain_boot, true, ChatColor.GOLD + "22 Gold Nuggets"));
                    inventory.setItem(28, getItem(new ItemStack(Material.IRON_HELMET), null, iron_head, true, ChatColor.GOLD + "31 Gold Nuggets"));
                    inventory.setItem(30, getItem(new ItemStack(Material.IRON_CHESTPLATE), null, iron_chest, true, ChatColor.GOLD + "38 Gold Nuggets"));
                    inventory.setItem(32, getItem(new ItemStack(Material.IRON_LEGGINGS), null, iron_pant, true, ChatColor.GOLD + "36 Gold Nuggets"));
                    inventory.setItem(34, getItem(new ItemStack(Material.IRON_BOOTS), null, iron_boot, true, ChatColor.GOLD + "30 Gold Nuggets"));

                    inventory.setItem(45, getItem(new ItemStack(Material.RED_BED), "Back To Menu", null, false, "Click To Interact"));
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals("อาวุธ")) {
                    Inventory inventory = Bukkit.createInventory(null, 9 * 6, "Shop-Drug | Weapons");
                    player.openInventory(inventory);

                    EnchantList sword = new EnchantList();
                    sword.addEnchant(new Enchant(Enchantment.FIRE_ASPECT, 2));

                    EnchantList gold_hoe = new EnchantList();
                    gold_hoe.addEnchant(new Enchant(Enchantment.DAMAGE_ALL, 15));
                    gold_hoe.addEnchant(new Enchant(Enchantment.DAMAGE_UNDEAD, 2));
                    gold_hoe.addEnchant(new Enchant(Enchantment.DURABILITY, 3));

                    EnchantList iron_hoe = new EnchantList();
                    iron_hoe.addEnchant(new Enchant(Enchantment.DAMAGE_ARTHROPODS, 2));
                    iron_hoe.addEnchant(new Enchant(Enchantment.KNOCKBACK, 2));
                    iron_hoe.addEnchant(new Enchant(Enchantment.DAMAGE_ALL, 15));
                    iron_hoe.addEnchant(new Enchant(Enchantment.DAMAGE_UNDEAD, 2));

                    EnchantList diamond_hoe = new EnchantList();
                    diamond_hoe.addEnchant(new Enchant(Enchantment.DAMAGE_ARTHROPODS, 3));
                    diamond_hoe.addEnchant(new Enchant(Enchantment.KNOCKBACK, 2));
                    diamond_hoe.addEnchant(new Enchant(Enchantment.DAMAGE_ALL, 21));
                    diamond_hoe.addEnchant(new Enchant(Enchantment.DAMAGE_UNDEAD, 3));

                    EnchantList nether_hoe = new EnchantList();
                    nether_hoe.addEnchant(new Enchant(Enchantment.DAMAGE_ARTHROPODS, 2));
                    nether_hoe.addEnchant(new Enchant(Enchantment.KNOCKBACK, 2));
                    nether_hoe.addEnchant(new Enchant(Enchantment.FIRE_ASPECT, 1));
                    nether_hoe.addEnchant(new Enchant(Enchantment.DAMAGE_ALL, 18));
                    nether_hoe.addEnchant(new Enchant(Enchantment.DAMAGE_UNDEAD, 3));

                    EffectList slow_fall = new EffectList();
                    slow_fall.addEffect(new Effect(PotionEffectType.SLOW_FALLING, 1800, 0));

                    EffectList jump = new EffectList();
                    jump.addEffect(new Effect(PotionEffectType.JUMP, 3600, 0));

                    EffectList heal = new EffectList();
                    heal.addEffect(new Effect(PotionEffectType.HEAL, 1, 2));

                    EffectList heal2 = new EffectList();
                    heal2.addEffect(new Effect(PotionEffectType.HEAL, 1, 3));

                    EffectList strength = new EffectList();
                    strength.addEffect(new Effect(PotionEffectType.INCREASE_DAMAGE, 9600, 0));

                    EffectList strength2 = new EffectList();
                    strength2.addEffect(new Effect(PotionEffectType.INCREASE_DAMAGE, 1800, 1));

                    EffectList regen = new EffectList();
                    regen.addEffect(new Effect(PotionEffectType.REGENERATION, 1800, 0));

                    EffectList regen2 = new EffectList();
                    regen2.addEffect(new Effect(PotionEffectType.REGENERATION, 440, 1));

                    EffectList fire_re = new EffectList();
                    fire_re.addEffect(new Effect(PotionEffectType.FIRE_RESISTANCE, 9600, 0));

                    EffectList night = new EffectList();
                    night.addEffect(new Effect(PotionEffectType.NIGHT_VISION, 9600, 0));

                    EffectList speed = new EffectList();
                    speed.addEffect(new Effect(PotionEffectType.SPEED, 9600, 0));

                    EffectList speed2 = new EffectList();
                    speed2.addEffect(new Effect(PotionEffectType.SPEED, 1800, 1));

                    inventory.setItem(1, getItem(new ItemStack(Material.AMETHYST_SHARD), "น้ำแข็งผนึกการเคลื่อนที่", null, false, ChatColor.GOLD + "2 Gold Nuggets"));
                    inventory.setItem(10, getItem(new ItemStack(Material.BAMBOO), "บ้องดูดแล้วลอย", null, false, ChatColor.GOLD + "2 Gold Nuggets"));
                    inventory.setItem(19, getItemDurability(new ItemStack(Material.WOODEN_SWORD), "ไม้ขีดไฟ", sword, 59 - 5, ChatColor.GOLD + "5 Gold Nuggets"));
                    inventory.setItem(3, getItem(new ItemStack(Material.GOLDEN_HOE), "เคียวทองลองเกี่ยว", gold_hoe, false, ChatColor.GOLD + "10 Gold Nuggets"));
                    inventory.setItem(12, getItem(new ItemStack(Material.IRON_HOE), "เคียวเหล็กเด็กน้อย", iron_hoe, false, ChatColor.GOLD + "21 Gold Nuggets"));
                    inventory.setItem(21, getItem(new ItemStack(Material.DIAMOND_HOE), "เคียวเพชรเส็จสม", diamond_hoe, false, ChatColor.GOLD + "35 Gold Nuggets"));
                    inventory.setItem(30, getItem(new ItemStack(Material.NETHERITE_HOE), "เคียวคล้ำลำพอง", nether_hoe, false, ChatColor.GOLD + "48 Gold Nuggets"));
                    inventory.setItem(39, getItem(new ItemStack(Material.SHIELD), null, null, false, ChatColor.GOLD + "7 Gold Nuggets"));
                    inventory.setItem(5, getItemPotionColor(new ItemStack(Material.SPLASH_POTION), "Slow Fall", slow_fall, Color.GRAY, ChatColor.GOLD + "2 Gold Nuggets"));
                    inventory.setItem(14, getItemPotionColor(new ItemStack(Material.SPLASH_POTION), "Jump Boost", jump, Color.LIME, ChatColor.GOLD + "1 Gold Nuggets"));
                    inventory.setItem(23, getItemPotionColor(new ItemStack(Material.SPLASH_POTION), "Heal", heal, Color.RED, ChatColor.GOLD + "1 Gold Nuggets"));
                    inventory.setItem(32, getItemPotionColor(new ItemStack(Material.SPLASH_POTION), "Heal II", heal2, Color.ORANGE, ChatColor.GOLD + "3 Gold Nuggets"));
                    inventory.setItem(41, getItemPotionColor(new ItemStack(Material.SPLASH_POTION), "Strength", strength, Color.AQUA, ChatColor.GOLD + "2 Gold Nuggets"));
                    inventory.setItem(50, getItemPotionColor(new ItemStack(Material.SPLASH_POTION), "Strength II", strength2, Color.BLUE, ChatColor.GOLD + "5 Gold Nuggets"));
                    inventory.setItem(7, getItemPotionColor(new ItemStack(Material.SPLASH_POTION), "Regeneration", regen, Color.FUCHSIA, ChatColor.GOLD + "5 Gold Nuggets"));
                    inventory.setItem(16, getItemPotionColor(new ItemStack(Material.SPLASH_POTION), "Regeneration II", regen2, Color.MAROON, ChatColor.GOLD + "10 Gold Nuggets"));
                    inventory.setItem(25, getItemPotionColor(new ItemStack(Material.SPLASH_POTION), "Fire Resistance", fire_re, Color.RED, ChatColor.GOLD + "2 Gold Nuggets"));
                    inventory.setItem(34, getItemPotionColor(new ItemStack(Material.SPLASH_POTION), "Night Vision", night, Color.BLACK, ChatColor.GOLD + "1 Gold Nuggets"));
                    inventory.setItem(43, getItemPotionColor(new ItemStack(Material.SPLASH_POTION), "Speed", speed, Color.AQUA, ChatColor.GOLD + "2 Gold Nuggets"));
                    inventory.setItem(52, getItemPotionColor(new ItemStack(Material.SPLASH_POTION), "Speed II", speed2, Color.NAVY, ChatColor.GOLD + "2 Gold Nuggets"));

                    inventory.setItem(45, getItem(new ItemStack(Material.RED_BED), "Back To Menu", null, false, "Click To Interact"));
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals("อื่น ๆ")) {
                    Inventory inventory = Bukkit.createInventory(null, 9 * 6, "Shop-Drug | Stuffs");
                    player.openInventory(inventory);

                    inventory.setItem(3, getItem(new ItemStack(Material.GOLDEN_APPLE), null, null, false, ChatColor.GOLD + "10 Gold Nuggets"));
                    inventory.setItem(5, getItem(new ItemStack(Material.SNOWBALL, 16), null, null, false, ChatColor.GOLD + "1 Gold Nuggets"));

                    inventory.setItem(45, getItem(new ItemStack(Material.RED_BED), "Back To Menu", null, false, "Click To Interact"));
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals("Back To Menu")) {
                    player.openInventory(showInventory("Shop-Drug"));
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals("Exit From Shop")) {
                    player.closeInventory();
                    player.teleport(new Location(StaticLocations.fukieWorld, 301, -34, -161));
                }

            }

            if (event.getView().getTitle().startsWith("Shop-Mysterious")) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals("ชุดเกราะ")) {
                    Inventory inventory = Bukkit.createInventory(null, 9 * 6, "Shop-Mysterious | Armors");
                    player.openInventory(inventory);

                    EnchantList leather_cap = new EnchantList();
                    leather_cap.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4));
                    leather_cap.addEnchant(new Enchant(Enchantment.OXYGEN, 1));

                    EnchantList chain_helmet = new EnchantList();
                    chain_helmet.addEnchant(new Enchant(Enchantment.PROTECTION_EXPLOSIONS, 1));
                    chain_helmet.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5));
                    chain_helmet.addEnchant(new Enchant(Enchantment.OXYGEN, 1));

                    EnchantList iron_helmet = new EnchantList();
                    iron_helmet.addEnchant(new Enchant(Enchantment.PROTECTION_EXPLOSIONS, 1));
                    iron_helmet.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 7));

                    EnchantList diamond_helmet = new EnchantList();
                    diamond_helmet.addEnchant(new Enchant(Enchantment.PROTECTION_EXPLOSIONS, 1));
                    diamond_helmet.addEnchant(new Enchant(Enchantment.PROTECTION_FIRE, 1));
                    diamond_helmet.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 1));
                    diamond_helmet.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 7));

                    EnchantList leather_chest = new EnchantList();
                    leather_chest.addEnchant(new Enchant(Enchantment.PROTECTION_EXPLOSIONS, 1));
                    leather_chest.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2));

                    EnchantList chain_chest = new EnchantList();
                    chain_chest.addEnchant(new Enchant(Enchantment.PROTECTION_FIRE, 1));
                    chain_chest.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 1));
                    chain_chest.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3));

                    EnchantList iron_chest = new EnchantList();
                    iron_chest.addEnchant(new Enchant(Enchantment.PROTECTION_FIRE, 2));
                    iron_chest.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 3));
                    iron_chest.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2));
                    iron_chest.addEnchant(new Enchant(Enchantment.THORNS, 1));

                    EnchantList diamond_chest = new EnchantList();
                    diamond_chest.addEnchant(new Enchant(Enchantment.PROTECTION_FIRE, 2));
                    diamond_chest.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 2));
                    diamond_chest.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3));
                    diamond_chest.addEnchant(new Enchant(Enchantment.THORNS, 3));

                    EnchantList leather_pant = new EnchantList();
                    leather_pant.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 1));
                    leather_pant.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2));

                    EnchantList chain_pant = new EnchantList();
                    chain_pant.addEnchant(new Enchant(Enchantment.PROTECTION_FIRE, 1));
                    chain_pant.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 1));
                    chain_pant.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2));

                    EnchantList iron_pant = new EnchantList();
                    iron_pant.addEnchant(new Enchant(Enchantment.PROTECTION_FIRE, 1));
                    iron_pant.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 2));
                    iron_pant.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2));

                    EnchantList diamond_pant = new EnchantList();
                    diamond_pant.addEnchant(new Enchant(Enchantment.PROTECTION_FIRE, 2));
                    diamond_pant.addEnchant(new Enchant(Enchantment.PROTECTION_PROJECTILE, 2));
                    diamond_pant.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2));

                    EnchantList leather_boot = new EnchantList();
                    leather_boot.addEnchant(new Enchant(Enchantment.PROTECTION_FALL, 1));
                    leather_boot.addEnchant(new Enchant(Enchantment.PROTECTION_EXPLOSIONS, 1));
                    leather_boot.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1));

                    EnchantList chain_boot = new EnchantList();
                    chain_boot.addEnchant(new Enchant(Enchantment.PROTECTION_FALL, 2));
                    chain_boot.addEnchant(new Enchant(Enchantment.PROTECTION_EXPLOSIONS, 1));
                    chain_boot.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1));

                    EnchantList iron_boot = new EnchantList();
                    iron_boot.addEnchant(new Enchant(Enchantment.DEPTH_STRIDER, 1));
                    iron_boot.addEnchant(new Enchant(Enchantment.PROTECTION_FALL, 2));
                    iron_boot.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2));

                    EnchantList diamond_boot = new EnchantList();
                    diamond_boot.addEnchant(new Enchant(Enchantment.DEPTH_STRIDER, 2));
                    diamond_boot.addEnchant(new Enchant(Enchantment.PROTECTION_FALL, 3));
                    diamond_boot.addEnchant(new Enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3));


                    inventory.setItem(1, getItem(new ItemStack(Material.LEATHER_HELMET), null, leather_cap, true, ChatColor.GOLD + "6 Gold Nuggets"));
                    inventory.setItem(3, getItem(new ItemStack(Material.CHAINMAIL_HELMET), null, chain_helmet, true, ChatColor.GOLD + "11 Gold Nuggets"));
                    inventory.setItem(5, getItem(new ItemStack(Material.IRON_HELMET), null, iron_helmet, true, ChatColor.GOLD + "23 Gold Nuggets"));
                    inventory.setItem(7, getItem(new ItemStack(Material.DIAMOND_HELMET), null, diamond_helmet, true, ChatColor.GOLD + "36 Gold Nuggets"));
                    inventory.setItem(10, getItem(new ItemStack(Material.LEATHER_CHESTPLATE), null, leather_chest, true, ChatColor.GOLD + "10 Gold Nuggets"));
                    inventory.setItem(12, getItem(new ItemStack(Material.CHAINMAIL_CHESTPLATE), null, chain_chest, true, ChatColor.GOLD + "17 Gold Nuggets"));
                    inventory.setItem(14, getItem(new ItemStack(Material.IRON_CHESTPLATE), null, iron_chest, true, ChatColor.GOLD + "28 Gold Nuggets"));
                    inventory.setItem(16, getItem(new ItemStack(Material.DIAMOND_CHESTPLATE), null, diamond_chest, true, ChatColor.GOLD + "40 Gold Nuggets"));
                    inventory.setItem(19, getItem(new ItemStack(Material.LEATHER_LEGGINGS), null, leather_pant, true, ChatColor.GOLD + "8 Gold Nuggets"));
                    inventory.setItem(21, getItem(new ItemStack(Material.CHAINMAIL_LEGGINGS), null, chain_pant, true, ChatColor.GOLD + "12 Gold Nuggets"));
                    inventory.setItem(23, getItem(new ItemStack(Material.IRON_LEGGINGS), null, iron_pant, true, ChatColor.GOLD + "26 Gold Nuggets"));
                    inventory.setItem(25, getItem(new ItemStack(Material.DIAMOND_LEGGINGS), null, diamond_pant, true, ChatColor.GOLD + "35 Gold Nuggets"));
                    inventory.setItem(28, getItem(new ItemStack(Material.LEATHER_BOOTS), null, leather_boot, true, ChatColor.GOLD + "6 Gold Nuggets"));
                    inventory.setItem(30, getItem(new ItemStack(Material.CHAINMAIL_BOOTS), null, chain_boot, true, ChatColor.GOLD + "14 Gold Nuggets"));
                    inventory.setItem(32, getItem(new ItemStack(Material.IRON_BOOTS), null, iron_boot, true, ChatColor.GOLD + "25 Gold Nuggets"));
                    inventory.setItem(34, getItem(new ItemStack(Material.DIAMOND_BOOTS), null, diamond_boot, true, ChatColor.GOLD + "34 Gold Nuggets"));

                    inventory.setItem(45, getItem(new ItemStack(Material.RED_BED), "Back To Menu", null, false, "Click To Interact"));
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals("อาวุธ")) {
                    Inventory inventory = Bukkit.createInventory(null, 9 * 6, "Shop-Mysterious | Combats");
                    player.openInventory(inventory);

                    EnchantList hua_ma = new EnchantList();
                    hua_ma.addEnchant(new Enchant(Enchantment.KNOCKBACK, 2));
                    AttributeList hua_ma_att = new AttributeList();
                    hua_ma_att.addAttribute(new Attribute(org.bukkit.attribute.Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(UUID.randomUUID(), "", 0.02, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND)));

                    EnchantList gold_shovel = new EnchantList();
                    gold_shovel.addEnchant(new Enchant(Enchantment.DAMAGE_ARTHROPODS, 1));
                    gold_shovel.addEnchant(new Enchant(Enchantment.DAMAGE_ALL, 10));
                    gold_shovel.addEnchant(new Enchant(Enchantment.DURABILITY, 3));

                    EnchantList iron_shovel = new EnchantList();
                    iron_shovel.addEnchant(new Enchant(Enchantment.KNOCKBACK, 2));
                    iron_shovel.addEnchant(new Enchant(Enchantment.DAMAGE_ALL, 11));
                    iron_shovel.addEnchant(new Enchant(Enchantment.DAMAGE_UNDEAD, 1));
                    iron_shovel.addEnchant(new Enchant(Enchantment.DURABILITY, 3));

                    EnchantList diamond_shovel = new EnchantList();
                    diamond_shovel.addEnchant(new Enchant(Enchantment.KNOCKBACK, 2));
                    diamond_shovel.addEnchant(new Enchant(Enchantment.DAMAGE_ALL, 11));
                    diamond_shovel.addEnchant(new Enchant(Enchantment.DAMAGE_UNDEAD, 2));

                    EnchantList nether_shovel = new EnchantList();
                    nether_shovel.addEnchant(new Enchant(Enchantment.FIRE_ASPECT, 1));
                    nether_shovel.addEnchant(new Enchant(Enchantment.KNOCKBACK, 2));
                    nether_shovel.addEnchant(new Enchant(Enchantment.DAMAGE_ALL, 11));
                    nether_shovel.addEnchant(new Enchant(Enchantment.DAMAGE_UNDEAD, 2));

                    inventory.setItem(3, getItemAttribute(new ItemStack(Material.STICK), "ไม้ตีหัวหมา", hua_ma, hua_ma_att, ChatColor.GOLD + "8 Gold Nuggets"));
                    inventory.setItem(12, getItem(new ItemStack(Material.ENDER_PEARL), null, null, false, ChatColor.GOLD + "6 Gold Nuggets"));
                    inventory.setItem(21, getItem(new ItemStack(Material.ENCHANTED_BOOK), "ระเบิดเวลา (คลิกขวาอย่างระวัง)", null, false, ChatColor.GOLD + "6 Gold Nuggets"));
                    inventory.setItem(30, getItem(new ItemStack(Material.FISHING_ROD), null, null, false, ChatColor.GOLD + "5 Gold Nuggets"));
                    inventory.setItem(39, getItem(new ItemStack(Material.GOAT_HORN), "อันเชิญผู้พิทักษ์", null, false, ChatColor.GOLD + "15 Gold Nuggets"));

                    inventory.setItem(5, getItem(new ItemStack(Material.GOLDEN_SHOVEL), "พลั่วมุงตุย", gold_shovel, false, ChatColor.GOLD + "10 Gold Nuggets"));
                    inventory.setItem(14, getItem(new ItemStack(Material.IRON_SHOVEL), "พลั่วไม่ได้ใช้ตักดิน", iron_shovel, false, ChatColor.GOLD + "16 Gold Nuggets"));
                    inventory.setItem(23, getItem(new ItemStack(Material.DIAMOND_SHOVEL), "พลั่วตีหัวคนอ่าน", diamond_shovel, false, ChatColor.GOLD + "30 Gold Nuggets"));
                    inventory.setItem(32, getItem(new ItemStack(Material.NETHERITE_SHOVEL), "พลั่วผัวผัว", nether_shovel, false, ChatColor.GOLD + "39 Gold Nuggets"));
                    inventory.setItem(41, getItem(new ItemStack(Material.SNOWBALL, 16), null, null, false, ChatColor.GOLD + "1 Gold Nuggets"));

                    inventory.setItem(45, getItem(new ItemStack(Material.RED_BED), "Back To Menu", null, false, "Click To Interact"));
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals("อื่น ๆ")) {
                    Inventory inventory = Bukkit.createInventory(null, 9 * 6, "Shop-Mysterious | Stuffs");
                    player.openInventory(inventory);

                    EffectList regen = new EffectList();
                    regen.addEffect(new Effect(PotionEffectType.REGENERATION, 1800, 0));

                    EffectList jump = new EffectList();
                    jump.addEffect(new Effect(PotionEffectType.JUMP, 1800, 0));

                    inventory.setItem(3, getItem(new ItemStack(Material.GOLDEN_APPLE), null, null, false, ChatColor.GOLD + "11 Gold Nuggets"));
                    inventory.setItem(5, getItem(new ItemStack(Material.FIREWORK_ROCKET), null, null, false, ChatColor.GOLD + "1 Gold Nuggets"));
                    inventory.setItem(21, getItemPotionColor(new ItemStack(Material.POTION), "Regeneration Potion", regen, Color.PURPLE, ChatColor.GOLD + "10 Gold Nuggets"));
                    inventory.setItem(23, getItemPotionColor(new ItemStack(Material.POTION), "Jump Potion", jump, Color.MAROON, ChatColor.GOLD + "5 Gold Nuggets"));

                    inventory.setItem(45, getItem(new ItemStack(Material.RED_BED), "Back To Menu", null, false, "Click To Interact"));
                }

                if (event.getCurrentItem().getItemMeta().getDisplayName().equals("Back To Menu")) {
                    player.openInventory(showInventory("Shop-Mysterious"));
                }
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals("Exit From Shop")) {
                    player.closeInventory();
                    player.teleport(new Location(StaticLocations.fukieWorld, 310, -34, -161));
                }

            }

            if (event.getView().getTitle().contains("|")) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals("Back To Menu")) return;
                List<String> lores = event.getCurrentItem().getItemMeta().getLore();
                String costs[] = lores.get(0).split(" ");
                int cost = Integer.parseInt(costs[0].substring(2));

                Inventory inventoryPlayer = event.getWhoClicked().getInventory();
                int playerGolds = 0;
                for (ItemStack itemStack : inventoryPlayer.getContents()) {
                    if (itemStack == null) continue;
                    if (itemStack.getType() == Material.GOLD_NUGGET) {
                        playerGolds += itemStack.getAmount();
                    }
                }
                ItemStack itemStack = event.getCurrentItem();
                int am = itemStack.getAmount();

                if (cost <= playerGolds) {
                    playerGolds -= cost;
                    inventoryPlayer.remove(Material.GOLD_NUGGET);
                    inventoryPlayer.addItem(new ItemStack(Material.GOLD_NUGGET, playerGolds));
                    inventoryPlayer.addItem(itemStack);
                    itemStack.setAmount(am);
                }
            }

        } catch (NullPointerException ignored) {
        }
    }

    private ItemStack getItem(ItemStack itemStack, String name, EnchantList enchantList, boolean unBreak, String...
            lore) {
        ItemMeta meta = itemStack.getItemMeta();

        if (name != null) meta.setDisplayName(name);

        if (lore != null) {

            List<String> lores = new ArrayList<>(Arrays.asList(lore));

            meta.setLore(lores);
        }

        if (enchantList != null) {
            for (Enchant enchant : enchantList.getEnchantList()) {
                meta.addEnchant(enchant.getEnchantment(), enchant.getLevel(), true);
            }
        }

        if (unBreak) meta.setUnbreakable(true);

        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private ItemStack getItemAttribute(ItemStack itemStack, String name, EnchantList enchantList, AttributeList attributeList, String...
            lore) {
        ItemMeta meta = itemStack.getItemMeta();

        if (name != null) meta.setDisplayName(name);

        if (lore != null) {

            List<String> lores = new ArrayList<>(Arrays.asList(lore));

            meta.setLore(lores);
        }

        if (enchantList != null) {
            for (Enchant enchant : enchantList.getEnchantList()) {
                meta.addEnchant(enchant.getEnchantment(), enchant.getLevel(), true);
            }
        }

        if (attributeList != null) {
            for (Attribute attribute : attributeList.getAttributes()) {
                meta.addAttributeModifier(attribute.getAttribute(), attribute.getAttributeModifier());
            }
        }

        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private ItemStack getItemDurability(ItemStack itemStack, String name, EnchantList enchantList, int durability, String...
            lore) {
        ItemMeta meta = itemStack.getItemMeta();

        if (name != null) meta.setDisplayName(name);

        if (lore != null) {

            List<String> lores = new ArrayList<>(Arrays.asList(lore));

            meta.setLore(lores);
        }

        if (enchantList != null) {
            for (Enchant enchant : enchantList.getEnchantList()) {
                meta.addEnchant(enchant.getEnchantment(), enchant.getLevel(), true);
            }
        }

        itemStack.setItemMeta(meta);

        Damageable damageable = (Damageable) itemStack.getItemMeta();
        damageable.setDamage(durability);

        itemStack.setItemMeta(damageable);
        return itemStack;
    }

    private ItemStack getItemPotion(ItemStack itemStack, String name, EffectList effectList, String... lore) {
        PotionMeta meta = (PotionMeta) itemStack.getItemMeta();

        if (name != null) meta.setDisplayName(name);

        if (lore != null) {

            List<String> lores = new ArrayList<>(Arrays.asList(lore));

            meta.setLore(lores);
        }

        if (effectList != null) {
            for (Effect effect : effectList.getEffectList()) {
                meta.addCustomEffect(effect.getEffect(), true);
            }
        }

        itemStack.setItemMeta(meta);

        return itemStack;
    }

    private ItemStack getItemPotionColor(ItemStack itemStack, String name, EffectList effectList, Color color, String... lore) {
        PotionMeta meta = (PotionMeta) itemStack.getItemMeta();

        meta.setColor(color);

        if (name != null) meta.setDisplayName(name);

        if (lore != null) {

            List<String> lores = new ArrayList<>(Arrays.asList(lore));

            meta.setLore(lores);
        }

        if (effectList != null) {
            for (Effect effect : effectList.getEffectList()) {
                meta.addCustomEffect(effect.getEffect(), true);
            }
        }

        itemStack.setItemMeta(meta);

        return itemStack;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (StaticFlags.FukieUnBreak == 0 || event.getBlock().getType() == Material.FIRE) return;
        if (event.getBlock().getWorld() != Bukkit.getWorld("world_fukie")) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (StaticFlags.FukieUnBreak == 0) return;
        if (event.getEntity().getWorld() != Bukkit.getWorld("world_fukie")) return;
        event.blockList().clear();
    }

    @EventHandler
    public void onBlockHai(EntityChangeBlockEvent event) {
        if (StaticFlags.FukieUnBreak == 0) return;
        if (event.getBlock().getWorld() != Bukkit.getWorld("world_fukie")) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockFire(BlockBurnEvent event) {
        if (StaticFlags.FukieUnBreak == 0) return;
        if (event.getBlock().getWorld() != Bukkit.getWorld("world_fukie")) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (event.getPlayer().getWorld() != StaticLocations.fukieWorld || StaticFlags.FukieCantDrop == 0) return;
        event.getPlayer().sendMessage(ChatColor.RED + "[Boss World] Can not drop item");
        event.setCancelled(true);
    }

    private Inventory showInventory(String name) {
        Inventory inventory = Bukkit.createInventory(null, 9 * 3, name);
        inventory.setItem(11, getItem(new ItemStack(Material.DIAMOND_CHESTPLATE), "ชุดเกราะ", null, false, "Click To Interact"));
        inventory.setItem(13, getItem(new ItemStack(Material.DIAMOND_SWORD), "อาวุธ", null, false, "Click To Interact"));
        inventory.setItem(15, getItem(new ItemStack(Material.LAVA_BUCKET), "อื่น ๆ", null, false, "Click To Interact"));

        inventory.setItem(26, getItem(new ItemStack(Material.CRIMSON_DOOR), "Exit From Shop", null, false, "Click To Interact"));

        return inventory;
    }

    @EventHandler
    public void playerTp(PlayerTeleportEvent event) {
        Location to = event.getTo();
        if (to == null) return;
        if (to.getWorld() != StaticLocations.fukieWorld) return;
        if (CheckInArea.check(to, 282, -160, 295, -156)) {
            Random random = new Random();
            int i = random.nextInt(0, 3);
            String sound = null;
            switch (i) {
                case 0:
                    sound = "ncr_tubu";
                    break;
                case 1:
                    sound = "ncr_beatcore";
                    break;
                case 2:
                    sound = "ncr_elektronomia";
                    break;
            }
            event.getPlayer().playSound(event.getPlayer().getLocation(), sound, 0.05f, 1);
        }
        if (CheckInArea.check(event.getFrom(), 282, -160, 295, -156)) {
            event.getPlayer().stopAllSounds();
        }
    }
}