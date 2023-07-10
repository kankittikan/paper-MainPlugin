package me.oyashiz.serverplugin.listeners.minigame.fukie.level2;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.tasks.*;
import me.oyashiz.serverplugin.utils.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.*;

public class Level2Listener implements Listener {
    private final MainPlugin plugin;

    private static boolean bossAppear = false;
    private static boolean bossDead = false;
    private static ItemStack gold = new ItemStack(Material.GOLD_NUGGET);

    ScoreboardManager manager = Bukkit.getScoreboardManager();
    Scoreboard board = manager.getNewScoreboard();
    Objective objective = board.registerNewObjective("test", "dummy");

    public Level2Listener(MainPlugin plugin) {
        this.plugin = plugin;
    }

    public static void reset() {
        bossAppear = false;
        bossDead = false;
    }

    @EventHandler
    public void onAllCommonDead(EntityDeathEvent event) {
        if (StaticFlags.performingLevel != 2 || StaticFlags.listenerOn != 2) return;
        if (!CheckInArea.check(event.getEntity().getLocation(), 32, -156, 93, -230)) return;
        if (event.getEntityType() == EntityType.SKELETON) {
            String[] strings = event.getEntity().getName().split("-");
            if (strings[0].equals("MineLord")) StaticInt.numBoss--;
            else StaticInt.numSkeleton--;
            gold.setAmount(gold.getAmount() + 1);
        }

        if (!bossAppear) {
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName(ChatColor.RED + "Kill All Monsters");
            Score score1 = objective.getScore("Remain skeleton: ");
            score1.setScore(StaticInt.numSkeleton);
            ScoreboardPlayer.set(StaticLocations.fukieWorld, board);
        }

        if (StaticInt.numSkeleton <= 0 && !bossAppear) {
            ScoreboardPlayer.clearWorld(StaticLocations.fukieWorld);
            bossAppear = true;
            SetTitle.set(ChatColor.RED + "Boss Appear", "", StaticLocations.fukieWorld);
            PlaySound.play(Sound.ENTITY_ELDER_GUARDIAN_AMBIENT, StaticLocations.fukieWorld);
            Monster skeleton = (Monster) StaticLocations.fukieWorld.spawnEntity(new Location(StaticLocations.fukieWorld, 59, -56, -186), EntityType.SKELETON);

            ItemStack chest = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
            ItemStack legging = new ItemStack(Material.CHAINMAIL_LEGGINGS);
            ItemStack bow = new ItemStack(Material.BOW);

            ItemMeta chestMeta = chest.getItemMeta();
            ItemMeta leggingMeta = legging.getItemMeta();
            ItemMeta bowMeta = bow.getItemMeta();

            chestMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
            leggingMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
            bowMeta.addEnchant(Enchantment.ARROW_KNOCKBACK, 2, true);

            chest.setItemMeta(chestMeta);
            legging.setItemMeta(leggingMeta);
            bow.setItemMeta(bowMeta);

            skeleton.getEquipment().setChestplate(chest);
            skeleton.getEquipment().setLeggings(legging);
            skeleton.getEquipment().setItemInMainHand(bow);

            skeleton.setMaxHealth(250);
            skeleton.setHealth(250);

            skeleton.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 1));
            skeleton.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));

            skeleton.setCustomName("MineLord");
            skeleton.setCustomNameVisible(true);
            skeleton.setGlowing(true);
            skeleton.setPersistent(true);
            skeleton.setRemoveWhenFarAway(false);

            Monster spider = (Monster) StaticLocations.fukieWorld.spawnEntity(new Location(StaticLocations.fukieWorld, 59, -56, -186), EntityType.SPIDER);
            spider.setMaxHealth(150);
            spider.setHealth(150);
            spider.setGlowing(true);
            spider.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 1));
            spider.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
            spider.addPassenger(skeleton);
            spider.setPersistent(true);
            spider.setRemoveWhenFarAway(false);
        }

        if (StaticInt.numBoss <= 0 && !bossDead) {
            SetTitle.set(ChatColor.GREEN + "Victory", "", StaticLocations.fukieWorld);
            PlaySound.play(Sound.ITEM_GOAT_HORN_SOUND_2, StaticLocations.fukieWorld);
            SendMsg.send(StaticLocations.fukieWorld, "กดปุ่มบนเสากลางแผนที่ เพื่อจบด่าน");
            bossDead = true;
        }
    }

    @EventHandler
    public void onButton(PlayerInteractEvent event) {
        if (StaticFlags.performingLevel != 2 || !bossDead) return;
        Block block = event.getClickedBlock();
        if (block == null || block.getWorld() != StaticLocations.fukieWorld) return;
        if (block.getX() == 58 && block.getY() == -30 && block.getZ() == -191) {
            for (Player player : StaticLocations.fukieWorld.getPlayers()) {
                if (CheckInArea.check(player.getLocation(), 32, -156, 93, -230)) {
                    player.teleport(StaticLocations.smith);
                }
            }
            gold.setAmount(gold.getAmount() + 30);
            AddItemToChest.add(gold, new Location(StaticLocations.fukieWorld, 300, -32, -156));
            StaticFlags.performingLevel = 3;
            plugin.getConfig().set("performing_level", StaticFlags.performingLevel);
            plugin.saveConfig();
            StaticInt.teamLife = 3;
            SignLevel.change();

        }
    }
}
