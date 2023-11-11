package me.oyashiz.serverplugin.listeners.minigame.fukie.level3;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.tasks.*;
import me.oyashiz.serverplugin.utils.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;

public class Level3Listener implements Listener {
    private final MainPlugin plugin;

    private static boolean bossAppear = false;
    private static boolean bossDead = false;
    private static ItemStack gold = new ItemStack(Material.GOLD_NUGGET);

    private final Location finalByrorgot = new Location(StaticLocations.fukieWorld, 174, -17,-158);

    ScoreboardManager manager = Bukkit.getScoreboardManager();
    Scoreboard board = manager.getNewScoreboard();
    Objective objective = board.registerNewObjective("test", "dummy");

    public Level3Listener(MainPlugin plugin) {
        this.plugin = plugin;
    }

    public static void reset() {
        bossAppear = false;
        bossDead = false;
    }

    @EventHandler
    public void onAllCommonDead(EntityDeathEvent event) {
        if (StaticFlags.performingLevel != 3 || StaticFlags.listenerOn != 3) return;
        if (!CheckInArea.check(event.getEntity().getLocation(), 197, -153, 137, -223)) return;
        if (event.getEntityType() == EntityType.HUSK) {
            StaticInt.numZombie--;
            gold.setAmount(gold.getAmount() + 1);
        }
        if (event.getEntityType() == EntityType.ENDERMAN) {
            StaticInt.numEnder--;
            gold.setAmount(gold.getAmount() + 1);
        }
        if (event.getEntityType() == EntityType.SKELETON) {
            StaticInt.numBoss--;
            gold.setAmount(gold.getAmount() + 1);
        }
        if (event.getEntityType() == EntityType.SILVERFISH) {
            StaticInt.numBoss--;
            gold.setAmount(gold.getAmount() + 1);
        }

        if (!bossAppear) {
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName(ChatColor.RED + "Kill All Monsters");
            Score score1 = objective.getScore("Remain Husk: ");
            Score score2 = objective.getScore("Remain Ender man: ");
            score1.setScore(StaticInt.numZombie);
            score2.setScore(StaticInt.numEnder);
            ScoreboardPlayer.set(StaticLocations.fukieWorld, board);
        }

        if (StaticInt.numZombie <= 0 && StaticInt.numEnder <= 0 && !bossAppear) {
            ScoreboardPlayer.clearWorld(StaticLocations.fukieWorld);
            bossAppear = true;
            SetTitle.set(ChatColor.RED + "Boss Appear", "", StaticLocations.fukieWorld);
            PlaySound.play(Sound.ENTITY_ELDER_GUARDIAN_AMBIENT, StaticLocations.fukieWorld);

            Skeleton skeleton = (Skeleton) StaticLocations.fukieWorld.spawnEntity(new Location(StaticLocations.fukieWorld, 182, -19, -198), EntityType.SKELETON);

            ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
            ItemStack legging = new ItemStack(Material.LEATHER_LEGGINGS);
            ItemStack teen = new ItemStack(Material.LEATHER_BOOTS);

            ItemStack lectern = new ItemStack(Material.LECTERN);

            ItemMeta chestMeta = chest.getItemMeta();
            ItemMeta leggingMeta = legging.getItemMeta();
            ItemMeta teenMeta = teen.getItemMeta();
            ItemMeta lecternItemMeta = lectern.getItemMeta();

            chestMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
            leggingMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
            teenMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
            lecternItemMeta.addEnchant(Enchantment.DAMAGE_ALL, 20, true);

            chest.setItemMeta(chestMeta);
            legging.setItemMeta(leggingMeta);
            teen.setItemMeta(teenMeta);
            lectern.setItemMeta(lecternItemMeta);

            skeleton.getEquipment().setHelmet(new ItemStack(Material.PLAYER_HEAD));
            skeleton.getEquipment().setChestplate(chest);
            skeleton.getEquipment().setLeggings(legging);
            skeleton.getEquipment().setBoots(teen);
            skeleton.getEquipment().setItemInMainHand(lectern);

            skeleton.setMaxHealth(260);
            skeleton.setHealth(260);

            skeleton.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 1));
            skeleton.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3));

            skeleton.setCustomName("Pharaoh Tuu");
            skeleton.setCustomNameVisible(true);
            skeleton.setGlowing(true);
            skeleton.setPersistent(true);
            skeleton.setRemoveWhenFarAway(false);

            Silverfish m = (Silverfish) StaticLocations.fukieWorld.spawnEntity(skeleton.getLocation().add(1, 0, 0), EntityType.SILVERFISH);
            m.setMaxHealth(30);
            m.setHealth(30);
            m.setGlowing(true);
            m.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 1));
            m.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
            m.setPersistent(true);
            m.setRemoveWhenFarAway(false);

            Silverfish m1 = (Silverfish) StaticLocations.fukieWorld.spawnEntity(skeleton.getLocation().add(0, 0, 1), EntityType.SILVERFISH);
            m1.setMaxHealth(30);
            m1.setHealth(30);
            m1.setGlowing(true);
            m1.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 1));
            m1.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
            m1.setPersistent(true);
            m1.setRemoveWhenFarAway(false);
        }

        if (StaticInt.numBoss <= 0 && !bossDead) {
            SetTitle.set(ChatColor.GREEN + "Victory", "", StaticLocations.fukieWorld);
            PlaySound.play(Sound.ITEM_GOAT_HORN_SOUND_2, StaticLocations.fukieWorld);
            bossDead = true;
            Location location = new Location(StaticLocations.fukieWorld, 172, -17, -157);
            location.getBlock().setType(Material.REDSTONE_BLOCK);
            location.getBlock().setType(Material.AIR);
        }
    }

    @EventHandler
    public void onButton(PlayerInteractEvent event) {
        if (StaticFlags.performingLevel != 3 || !bossDead) return;
        List<Location> locationList = new ArrayList<>();
        locationList.add(new Location(StaticLocations.fukieWorld, 183, 1, -193));
        locationList.add(new Location(StaticLocations.fukieWorld, 184, 1, -194));
        locationList.add(new Location(StaticLocations.fukieWorld, 183, 1, -195));
        locationList.add(new Location(StaticLocations.fukieWorld, 182, 1, -194));
        Block block = event.getClickedBlock();
        if (block == null || block.getWorld() != StaticLocations.fukieWorld) return;
        if (locationList.contains(block.getLocation())) {
            BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {
                    for (Player player : StaticLocations.fukieWorld.getPlayers()) {
                        if (CheckInArea.check(player.getLocation(), 197, -153, 137, -223)) {
                            player.teleport(StaticLocations.smith);
                        }
                    }
                    gold.setAmount(gold.getAmount() + 20);
                    AddItemToChest.add(gold, new Location(StaticLocations.fukieWorld, 300, -32, -156));
                    StaticFlags.performingLevel = 4;
                    plugin.getConfig().set("performing_level", StaticFlags.performingLevel);
                    plugin.saveConfig();
                    StaticInt.teamLife = 3;
                    SignLevel.change();


                    finalByrorgot.getBlock().setType(Material.REDSTONE_BLOCK);
                    finalByrorgot.getBlock().setType(Material.AIR);

                }
            }.runTaskLater(plugin, 200);
        }
    }
}
