package me.oyashiz.serverplugin.listeners.minigame.fukie.level5;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.tasks.*;
import me.oyashiz.serverplugin.utils.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;

public class Level5Listener implements Listener {
    private final MainPlugin plugin;

    private static boolean bossAppear = false;
    private static boolean bossDead = false;
    private static ItemStack gold = new ItemStack(Material.GOLD_NUGGET);
    ScoreboardManager manager = Bukkit.getScoreboardManager();
    Scoreboard board = manager.getNewScoreboard();
    Objective objective = board.registerNewObjective("test", "dummy");

    public Level5Listener(MainPlugin plugin) {

        this.plugin = plugin;
    }

    public static void reset() {
        bossAppear = false;
        bossDead = false;
    }

    @EventHandler
    public void onAllCommonDead(EntityDeathEvent event) {
        if (StaticFlags.performingLevel != 5 || StaticFlags.listenerOn != 5) return;
        if (!CheckInArea.check(event.getEntity().getLocation(), 290, -323, 205, -241)) return;
        if (event.getEntityType() == EntityType.ZOMBIE_VILLAGER) {
            StaticInt.numZombie--;
            gold.setAmount(gold.getAmount() + 1);
        }
        if (event.getEntityType() == EntityType.PILLAGER) {
            StaticInt.numPillager--;
            gold.setAmount(gold.getAmount() + 1);
        }
        if(event.getEntityType() == EntityType.RAVAGER || event.getEntityType() == EntityType.EVOKER) {
            StaticInt.numBoss--;
            gold.setAmount(gold.getAmount() + 1);
        }

        if (!bossAppear) {
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName(ChatColor.RED + "Kill All Monsters");
            Score score1 = objective.getScore("Remain Zombie: ");
            Score score2 = objective.getScore("Remain Pillager: ");
            score1.setScore(StaticInt.numZombie);
            score2.setScore(StaticInt.numPillager);
            ScoreboardPlayer.set(StaticLocations.fukieWorld, board);
        }

        if (StaticInt.numZombie <= 0 && StaticInt.numPillager <= 0 && !bossAppear) {
            ScoreboardPlayer.clearWorld(StaticLocations.fukieWorld);
            bossAppear = true;
            SetTitle.set(ChatColor.RED + "Boss Appear", "", StaticLocations.fukieWorld);
            PlaySound.play(Sound.ENTITY_ELDER_GUARDIAN_AMBIENT, StaticLocations.fukieWorld);

            Evoker evoker = (Evoker) StaticLocations.fukieWorld.spawnEntity(new Location(StaticLocations.fukieWorld, 250, -23, -278), EntityType.EVOKER);
            evoker.setMaxHealth(100);
            evoker.setHealth(100);
            evoker.setGlowing(true);
            evoker.setPersistent(true);
            evoker.setCustomName("Head Bandit");
            evoker.setCustomNameVisible(true);
            evoker.setRemoveWhenFarAway(false);

            Ravager ravager = (Ravager) StaticLocations.fukieWorld.spawnEntity(new Location(StaticLocations.fukieWorld, 250, -23, -278), EntityType.RAVAGER);
            ravager.setMaxHealth(150);
            ravager.setHealth(150);
            ravager.setGlowing(true);
            ravager.setPersistent(true);
            ravager.addPassenger(evoker);
            ravager.setRemoveWhenFarAway(false);

            Evoker evoker1 = (Evoker) StaticLocations.fukieWorld.spawnEntity(new Location(StaticLocations.fukieWorld, 269, -22, -286), EntityType.EVOKER);
            evoker1.setMaxHealth(100);
            evoker1.setHealth(100);
            evoker1.setGlowing(true);
            evoker1.setPersistent(true);
            evoker1.setCustomName("Head Bandit");
            evoker1.setCustomNameVisible(true);
            evoker1.setRemoveWhenFarAway(false);

            Ravager ravager1 = (Ravager) StaticLocations.fukieWorld.spawnEntity(new Location(StaticLocations.fukieWorld, 269, -22, -286), EntityType.RAVAGER);
            ravager1.setMaxHealth(150);
            ravager1.setHealth(150);
            ravager1.setGlowing(true);
            ravager1.setPersistent(true);
            ravager1.addPassenger(evoker1);
            ravager1.setRemoveWhenFarAway(false);
        }

        if (StaticInt.numBoss <= 0 && !bossDead) {
            SetTitle.set(ChatColor.GREEN + "Victory", "", StaticLocations.fukieWorld);
            SendMsg.send(StaticLocations.fukieWorld, "เหยียบแผ่นทองที่กลางหมู่บ้าน เพื่อจบเกม");
            PlaySound.play(Sound.ITEM_GOAT_HORN_SOUND_2, StaticLocations.fukieWorld);
            bossDead = true;
        }
    }

    @EventHandler
    public void onButton(PlayerInteractEvent event) {
        if (StaticFlags.performingLevel != 5 || !bossDead) return;
        List<Location> locationList = new ArrayList<>();
        locationList.add(new Location(StaticLocations.fukieWorld, 258, -22, -296));
        Block block = event.getClickedBlock();
        if (block == null || block.getWorld() != StaticLocations.fukieWorld) return;
        if (locationList.contains(block.getLocation())) {
            BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {
                    for (Player player : StaticLocations.fukieWorld.getPlayers()) {
                        if (CheckInArea.check(player.getLocation(), 290, -323, 205, -241)) {
                            player.teleport(StaticLocations.smith);
                        }
                    }
                    gold.setAmount(gold.getAmount() + 20);
                    AddItemToChest.add(gold, new Location(StaticLocations.fukieWorld, 300, -32, -156));
                    StaticFlags.performingLevel = 1;
                    plugin.getConfig().set("performing_level", StaticFlags.performingLevel);
                    plugin.saveConfig();
                    StaticInt.teamLife = 3;
                    SignLevel.change();

                }
            }.runTaskLater(plugin, 10);
        }
    }
}
