package me.oyashiz.serverplugin.listeners.minigame.fukie.level1;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.tasks.*;
import me.oyashiz.serverplugin.tasks.level1.SpawnLevel1;
import me.oyashiz.serverplugin.utils.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Spider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.*;

public class Level1Listener implements Listener {
    private final MainPlugin plugin;
    private static boolean bossAppear = false;
    private static boolean bossDead = false;

    ScoreboardManager manager = Bukkit.getScoreboardManager();
    Scoreboard board = manager.getNewScoreboard();
    Objective objective = board.registerNewObjective("test", "dummy");

    private static ItemStack gold = new ItemStack(Material.GOLD_NUGGET, 0);

    public Level1Listener(MainPlugin plugin) {
        this.plugin = plugin;
    }

    public static void reset() {
        bossAppear = false;
        bossDead = false;
    }

    @EventHandler
    public void onAllCommonDead(EntityDeathEvent event) {
        if (StaticFlags.performingLevel != 1 || StaticFlags.listenerOn != 1) return;
        if (!CheckInArea.check(event.getEntity().getLocation(), 18, -78, 83, -10)) return;
        if (event.getEntityType() == EntityType.ZOMBIE || event.getEntityType() == EntityType.DROWNED) {
            StaticInt.numZombie--;
            gold.setAmount(gold.getAmount() + 1);
        }
        if (event.getEntityType() == EntityType.SKELETON) {
            StaticInt.numSkeleton--;
            gold.setAmount(gold.getAmount() + 1);
        }
        if (event.getEntityType() == EntityType.SPIDER || event.getEntityType() == EntityType.CAVE_SPIDER) {
            String[] strings = event.getEntity().getName().split("-");
            if (strings[0].equals("Suup Pod") || strings[0].equals("Holy Suup Pod")) {
                StaticInt.numBoss--;
                gold.setAmount(gold.getAmount() + 1);
            }
        }

        if (!bossAppear) {
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName(ChatColor.RED + "Kill All Monsters");
            Score score = objective.getScore("Remain zombie: ");
            score.setScore(StaticInt.numZombie);
            Score score1 = objective.getScore("Remain skeleton: ");
            score1.setScore(StaticInt.numSkeleton);
            ScoreboardPlayer.set(StaticLocations.fukieWorld, board);
        }

        if (StaticInt.numZombie <= 0 && StaticInt.numSkeleton <= 0 && !bossAppear) {
            ScoreboardPlayer.clearWorld(StaticLocations.fukieWorld);
            bossAppear = true;
            SetTitle.set(ChatColor.RED + "Boss Appear", "", StaticLocations.fukieWorld);
            PlaySound.play(Sound.ENTITY_ELDER_GUARDIAN_AMBIENT, StaticLocations.fukieWorld);
            World world = Bukkit.getWorld("world_fukie");
            Spider c = (Spider) world.spawnEntity(new Location(world, 24, -51, -74), EntityType.SPIDER);
            c.setMaxHealth(400);
            c.setHealth(200);
            c.setGlowing(true);
            c.setCustomName("Suup Pod");
            c.setCustomNameVisible(true);
            c.setPersistent(true);
            c.setRemoveWhenFarAway(false);

            Monster cave = (Monster) world.spawnEntity(new Location(world, 24, -51, -74), EntityType.CAVE_SPIDER);
            cave.setMaxHealth(400);
            cave.setHealth(400);
            cave.setGlowing(true);
            cave.setCustomName("Holy Suup Pod");
            cave.setCustomNameVisible(true);
            cave.setPersistent(true);
            cave.setRemoveWhenFarAway(false);
        }

        if (StaticInt.numBoss <= 0 && !bossDead) {
            bossDead = true;
            SetTitle.set(ChatColor.GREEN + "Victory", "", StaticLocations.fukieWorld);
            PlaySound.play(Sound.ITEM_GOAT_HORN_SOUND_2, StaticLocations.fukieWorld);

            SpawnLevel1.locationDoor.getBlock().setType(Material.REDSTONE_BLOCK);
            SpawnLevel1.locationGlass.getBlock().setType(Material.GLASS);

            SendMsg.send(StaticLocations.fukieWorld, "เดินตามลำแสง เพื่อจบด่าน");
        }
    }

    @EventHandler
    public void onPlayerStep(PlayerInteractEvent event) {
        if (event.getPlayer().getWorld() != StaticLocations.fukieWorld || StaticFlags.performingLevel != 1) return;
        Block block = event.getClickedBlock();
        if (block == null) return;
        if (block.getX() == 23 && block.getY() == -56 && block.getZ() == -74) {
            Location smith = new Location(Bukkit.getWorld("world_fukie"), 290, -33, -159, -180, 0);
            for (Player player : StaticLocations.fukieWorld.getPlayers()) {
                if (CheckInArea.check(player.getLocation(), 18, -78, 83, -10)) {
                    player.teleport(smith);
                }
            }
            gold.setAmount(gold.getAmount() + 20);
            AddItemToChest.add(gold, new Location(StaticLocations.fukieWorld, 300, -32, -156));
            StaticFlags.performingLevel = 2;
            plugin.getConfig().set("performing_level", StaticFlags.performingLevel);
            plugin.saveConfig();
            StaticInt.teamLife = 3;
            SignLevel.change();
        }
    }
}
