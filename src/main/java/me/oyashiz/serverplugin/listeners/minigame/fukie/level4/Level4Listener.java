package me.oyashiz.serverplugin.listeners.minigame.fukie.level4;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.tasks.*;
import me.oyashiz.serverplugin.utils.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.*;

public class Level4Listener implements Listener {

    private final MainPlugin plugin;

    private static boolean bossAppear = false;
    private static boolean bossDead = false;
    private static ItemStack gold = new ItemStack(Material.GOLD_NUGGET);
    private final Location finalByrorgot = new Location(StaticLocations.fukieWorld, 260, -37, -272);

    ScoreboardManager manager = Bukkit.getScoreboardManager();
    Scoreboard board = manager.getNewScoreboard();
    Objective objective = board.registerNewObjective("test", "dummy");

    public Level4Listener(MainPlugin plugin) {

        this.plugin = plugin;
    }

    public static void reset() {
        bossAppear = false;
        bossDead = false;
    }

    @EventHandler
    public void onAllCommonDead(EntityDeathEvent event) {
        if (StaticFlags.performingLevel != 4 || StaticFlags.listenerOn != 4) return;
        if (!CheckInArea.check(event.getEntity().getLocation(), 290, -323, 205, -241)) return;
        if (event.getEntityType() == EntityType.ZOMBIE) {
            StaticInt.numZombie--;
            gold.setAmount(gold.getAmount() + 1);
        }
        if (event.getEntityType() == EntityType.ILLUSIONER) {
            StaticInt.numBoss--;
            gold.setAmount(gold.getAmount() + 1);
        }

        if (!bossAppear) {
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName(ChatColor.RED + "Kill All Monsters");
            Score score1 = objective.getScore("Remain Zombie: ");
            score1.setScore(StaticInt.numZombie);
            ScoreboardPlayer.set(StaticLocations.fukieWorld, board);
        }

        if (StaticInt.numZombie <= 0 && !bossAppear) {
            ScoreboardPlayer.clearWorld(StaticLocations.fukieWorld);
            bossAppear = true;
            SetTitle.set(ChatColor.RED + "Boss Appear", "", StaticLocations.fukieWorld);
            PlaySound.play(Sound.ENTITY_ELDER_GUARDIAN_AMBIENT, StaticLocations.fukieWorld);

            Illusioner a = (Illusioner) StaticLocations.fukieWorld.spawnEntity(new Location(StaticLocations.fukieWorld, 260, -22, -288), EntityType.ILLUSIONER);

            a.setMaxHealth(50);
            a.setHealth(50);

            a.setCustomName("Master");
            a.setCustomNameVisible(true);
            a.setGlowing(true);
            a.setPersistent(true);
            a.setRemoveWhenFarAway(false);

            Illusioner a1 = (Illusioner) StaticLocations.fukieWorld.spawnEntity(new Location(StaticLocations.fukieWorld, 234, -27, -275), EntityType.ILLUSIONER);

            a1.setMaxHealth(50);
            a1.setHealth(50);

            a1.setCustomName("Master");
            a1.setCustomNameVisible(true);
            a1.setGlowing(true);
            a1.setPersistent(true);
            a1.setRemoveWhenFarAway(false);

            Illusioner a2 = (Illusioner) StaticLocations.fukieWorld.spawnEntity(new Location(StaticLocations.fukieWorld, 281, -21, -313), EntityType.ILLUSIONER);

            a2.setMaxHealth(50);
            a2.setHealth(50);

            a2.setCustomName("Master");
            a2.setCustomNameVisible(true);
            a2.setGlowing(true);
            a2.setPersistent(true);
            a2.setRemoveWhenFarAway(false);

        }

        if (StaticInt.numBoss <= 0 && !bossDead) {
            SetTitle.set(ChatColor.GREEN + "Victory", "", StaticLocations.fukieWorld);
            PlaySound.play(Sound.ITEM_GOAT_HORN_SOUND_2, StaticLocations.fukieWorld);
            bossDead = true;
            finalByrorgot.getBlock().setType(Material.REDSTONE_BLOCK);
            finalByrorgot.getBlock().setType(Material.AIR);
            BukkitTask t1 = new BukkitRunnable() {
                @Override
                public void run() {
                    Location npc = new Location(StaticLocations.fukieWorld, 258.5, -13, -295.5);
                    for (int i = 0; i < 10; i++) {
                        StaticLocations.fukieWorld.spawnEntity(npc, EntityType.VILLAGER);
                    }
                    BukkitTask t1 = new BukkitRunnable() {
                        @Override
                        public void run() {
                            SendMsg.send(StaticLocations.fukieWorld, "[Villager] พวกเราขอบคุณความช่วยเหลือจากท่าน");

                            BukkitTask t2 = new BukkitRunnable() {
                                @Override
                                public void run() {
                                    SendMsg.send(StaticLocations.fukieWorld, "[Villager] ตอนนี้ได้เวลาพักของพวกท่าน");

                                    BukkitTask t3 = new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            SendMsg.send(StaticLocations.fukieWorld, "[Villager] หมู่บ้านของเราจะสงบสุขในไม่ช้า");

                                            BukkitTask t4 = new BukkitRunnable() {
                                                @Override
                                                public void run() {
                                                    SendMsg.send(StaticLocations.fukieWorld, ChatColor.RED + "หืม ชัวหรอ ?");
                                                    for(Entity c : StaticLocations.fukieWorld.getEntities()) {
                                                        if(c instanceof Villager) {
                                                            if(CheckInArea.check(c.getLocation(), 290, -323, 205, -241)) ((Villager) c).setHealth(0);
                                                        }
                                                    }

                                                    BukkitTask t5 = new BukkitRunnable() {
                                                        @Override
                                                        public void run() {
                                                            for(Player p : StaticLocations.fukieWorld.getPlayers()){
                                                                if(CheckInArea.check(p.getLocation(), 290, -323, 205, -241)) {
                                                                    p.teleport(StaticLocations.smith);
                                                                }
                                                            }
                                                            gold.setAmount(gold.getAmount() + 20);
                                                            AddItemToChest.add(gold, new Location(StaticLocations.fukieWorld, 300, -32, -156));
                                                            StaticFlags.performingLevel = 5;
                                                            plugin.getConfig().set("performing_level", StaticFlags.performingLevel);
                                                            plugin.saveConfig();
                                                            StaticInt.teamLife = 3;
                                                            SignLevel.change();



                                                        }
                                                    }.runTaskLater(plugin, 100);

                                                }
                                            }.runTaskLater(plugin, 100);

                                        }
                                    }.runTaskLater(plugin, 100);

                                }
                            }.runTaskLater(plugin, 100);

                        }
                    }.runTaskLater(plugin, 100);


                }
            }.runTaskLater(plugin, 100);
        }
    }
}
