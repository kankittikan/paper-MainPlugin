package me.oyashiz.serverplugin.listeners;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.commands.OutHubLightCommand;
import me.oyashiz.serverplugin.commands.SpaceshipLightCommand;
import me.oyashiz.serverplugin.utils.*;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class SurvivalListener implements Listener {

    private List<Player> playerInRocket = new ArrayList<>();
    private final MainPlugin plugin;

    public SurvivalListener(MainPlugin plugin) {
        this.plugin = plugin;
    }

    private void rocketLaunch() {

        final int[] count = {30};
        BukkitTask countDown = new BukkitRunnable() {
            @Override
            public void run() {
                if (count[0] == 0) cancel();
                for (Player p : StaticLocations.survivalWorld.getPlayers()) {
                    if (CheckInArea.check(p.getLocation(), -22, 38, 164, -115)) {
                        p.sendTitle("Rocket Launch", ChatColor.RED + String.valueOf(count[0]));
                        p.playSound(p, Sound.BLOCK_NOTE_BLOCK_BANJO, 1, 1);
                    }
                }
                count[0]--;
            }
        }.runTaskTimer(plugin, 50, 20);

        BukkitTask go = new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : StaticLocations.survivalWorld.getPlayers()) {
                    if (CheckInArea.check(p.getLocation(), -22, 38, 164, -115)) {
                        p.sendTitle("Ignition", "");
                        p.playSound(p, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 3, 1);
                        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10, 5));
                    }
                }
                OutHubLightCommand outHubLightCommand = new OutHubLightCommand(plugin);
                SpaceshipLightCommand spaceshipLightCommand = new SpaceshipLightCommand(plugin);
                spaceshipLightCommand.setLight("on");
                outHubLightCommand.setLight("on");
                SpaceshipHatch.closeHatch();
                BukkitTask goSpace = new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Player p : playerInRocket) {
                            p.teleport(StaticLocations.arrivalOut);
                            p.setGameMode(GameMode.ADVENTURE);
                        }
                    }
                }.runTaskLater(plugin, 50);
                BukkitTask goSpaceShip = new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Player p : playerInRocket) {
                            p.teleport(StaticLocations.spaceship);
                        }
                        SendMsg.sendList(playerInRocket, "Welcome to Space Hub");
                        SendMsg.sendList(playerInRocket, ChatColor.YELLOW + "Hatch will be opened in a minute");
                        BukkitTask hatchOpen = new BukkitRunnable() {
                            @Override
                            public void run() {
                                SpaceshipHatch.openHatch();
                                SendMsg.sendList(playerInRocket, ChatColor.GREEN + "Opening Hatch!!");

                                StaticFlags.TravelFlag = 0;
                                playerInRocket.clear();
                                spaceshipLightCommand.setLight("off");
                                outHubLightCommand.setLight("off");
                            }
                        }.runTaskLater(plugin, 100);
                    }
                }.runTaskLater(plugin, 300);
            }
        }.runTaskLater(plugin, 700);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getPlayer().getWorld() != StaticLocations.survivalWorld || event.getClickedBlock() == null) return;

        Location location = event.getClickedBlock().getLocation();
        Player player = event.getPlayer();

        if (location.equals(new Location(StaticLocations.survivalWorld, 79, 86, -29))) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 320, 10));
        }
        if (location.equals(new Location(StaticLocations.survivalWorld, 87, 208, -3))) {
            if (StaticFlags.TravelFlag == 1 && playerInRocket.isEmpty()) {
                player.sendMessage(ChatColor.RED + "Gate in using");
                return;
            }
            StaticFlags.TravelFlag = 1;
            player.sendMessage(ChatColor.GREEN + "Request for go space approved");
            player.teleport(new Location(StaticLocations.survivalWorld, 84, 207, -3));
            player.stopAllSounds();
            player.playSound(player, Sound.MUSIC_DISC_OTHERSIDE, 0.3f, 1);
            if (playerInRocket.isEmpty()) {
                rocketLaunch();
            }
            playerInRocket.add(player);
        }

        if (location.equals(new Location(StaticLocations.survivalWorld, 58, 84, -12))) {
            Location home = MainPlugin.homeConfig.getConfig().getLocation(player.getName());
            if (home == null) {
                player.sendMessage(ChatColor.RED + "ไม่พบพิกัดบ้าน");
                player.sendMessage(ChatColor.RED + "ใช้ /sethome หน้าบ้านของท่านก่อน");
                return;
            }
            player.teleport(home);
        }

        if(location.equals(new Location(StaticLocations.survivalWorld, 176, 67, -20))) {
            player.sendMessage(" ");
            player.sendMessage("กดปุ่ม go home บนฐานปล่อยจรวจเพื่อไปพิกัดบ้าน");
            player.sendMessage(" ");
            player.sendMessage("ใช้คำสั่ง /sethome เพื่อบันทึกพิกัดบ้าน");
            player.sendMessage(" ");
            player.sendMessage("ใช้คำสั่ง /gorocket เพื่อมายังฐานปล่อยจรวจ");
            player.setGameMode(GameMode.SURVIVAL);
        }
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        if (event.getBlock().getWorld() != StaticLocations.survivalWorld || !StaticFlags.SurvivalUnBreak) return;
        if (CheckInArea.check(event.getBlock().getLocation(), 200, -80, -10, 30)) {
            event.getPlayer().sendMessage(ChatColor.RED + "เขตห้ามทำลาย");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getWorld() != StaticLocations.survivalWorld || !StaticFlags.SurvivalUnBreak) return;
        if (CheckInArea.check(event.getBlock().getLocation(), 200, -80, -10, 30)) {
            event.getPlayer().sendMessage(ChatColor.RED + "เขตห้ามวางบล็อค");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void spawn(EntitySpawnEvent event) {
        if (event.getLocation().getWorld() != StaticLocations.survivalWorld) return;
        if (CheckInArea.check(event.getLocation(), 200, -80, -10, 30)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.getEntity().getWorld() != StaticLocations.survivalWorld || !StaticFlags.SurvivalUnBreak) return;
        if (CheckInArea.check(event.getLocation(), 200, -80, -10, 30)) {
            event.blockList().clear();
        }
    }

    @EventHandler
    public void onBlockHai(EntityChangeBlockEvent event) {
        if (event.getBlock().getWorld() != StaticLocations.survivalWorld || !StaticFlags.SurvivalUnBreak) return;
        if (CheckInArea.check(event.getBlock().getLocation(), 200, -80, -10, 30)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockFire(BlockBurnEvent event) {
        if (event.getBlock().getWorld() != StaticLocations.survivalWorld) return;
        if (CheckInArea.check(event.getBlock().getLocation(), 200, -80, -10, 30)) {
            event.setCancelled(true);
        }
    }
}
