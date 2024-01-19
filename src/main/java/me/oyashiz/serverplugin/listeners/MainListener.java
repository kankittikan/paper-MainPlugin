package me.oyashiz.serverplugin.listeners;

import io.papermc.paper.event.player.PlayerItemFrameChangeEvent;
import io.papermc.paper.event.player.PlayerPickItemEvent;
import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.tasks.PlaySound;
import me.oyashiz.serverplugin.tasks.ScoreboardPlayer;
import me.oyashiz.serverplugin.utils.*;
import org.bukkit.*;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.*;

import java.io.IOException;
import java.util.ArrayList;

public class MainListener implements Listener {
    private final MainPlugin plugin;

    private SaveInventory saveInventory;

    public MainListener(MainPlugin plugin) {

        this.plugin = plugin;
        saveInventory = new SaveInventory(plugin);
    }

    @EventHandler
    public void interact(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null || event.getPlayer().getWorld() != StaticLocations.world) return;
        Location location = event.getClickedBlock().getLocation();

        if (location.equals(new Location(StaticLocations.world, -3, 7, 34))) {
            ArrayList<Note> notes = new ArrayList<>();
            notes.add(new Note(0, Note.Tone.F, false));
            notes.add(new Note(0, Note.Tone.A, false));

            BukkitTask play = new BukkitRunnable() {
                int i = 0;

                @Override
                public void run() {
                    if (i == notes.size() - 1) cancel();
                    PlaySound.playNoteLocation(event.getClickedBlock().getLocation(), notes.get(i++));

                }
            }.runTaskTimer(plugin, 0, 10);
        }

        if (location.equals(new Location(StaticLocations.world, 12, 6, 84))) {
            event.getPlayer().teleport(new Location(StaticLocations.world, 9, 5, 85));
        }
        if (location.equals(new Location(StaticLocations.world, 10, 6, 84))) {
            event.getPlayer().teleport(new Location(StaticLocations.world, 13, 5, 85));
        }
        if (location.getWorld().getName().equals("world") && event.getClickedBlock().getType().toString().endsWith("BED")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void playerMove(PlayerMoveEvent event) {
        if (StaticLists.playerLock.contains(event.getPlayer())) {
            event.getPlayer().setInvulnerable(true);
            event.setCancelled(true);
        } else {
            event.getPlayer().setInvulnerable(false);
        }
    }

    @EventHandler
    public void interactEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player && event.getEntity() instanceof Player)) return;
        Player player1 = (Player) event.getDamager();
        Player player2 = (Player) event.getEntity();

        if (player1.isSneaking() && player2.isSneaking()) {
            ItemStack itemStack1 = player1.getInventory().getItemInMainHand();
            ItemStack itemStack2 = player2.getInventory().getItemInMainHand();

            player1.getInventory().setItemInMainHand(null);
            player2.getInventory().setItemInMainHand(null);

            //swap
            player1.getInventory().setItemInMainHand(itemStack2);
            player2.getInventory().setItemInMainHand(itemStack1);
        }
    }

    @EventHandler
    public void playerMount(PlayerInteractEntityEvent event) {
        if(event.getRightClicked() instanceof Player) {
            event.getRightClicked().addPassenger(event.getPlayer());
        }
    }

    @EventHandler
    public void entitySpawn(EntitySpawnEvent event) {
        if (event.getLocation().getWorld() == StaticLocations.world) {
            if (event.getEntity() instanceof Bat) {
                event.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void playerDrop(PlayerDropItemEvent event) {
        if (StaticFlags.cinematic) event.setCancelled(true);
        if (StaticLists.playerLock.contains(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void playerClickItem(InventoryClickEvent event) {
        if (StaticFlags.cinematic) event.setCancelled(true);
    }

    @EventHandler
    public void gameModeChange(PlayerGameModeChangeEvent event) {
        if (StaticFlags.canChangeMode) return;
        if (event.getNewGameMode() == GameMode.ADVENTURE) return;
        if (event.getNewGameMode() != GameMode.SURVIVAL) {
            if (!StaticFlags.maintenance) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(ChatColor.RED + "Game mode change is allow in maintenance mode only");
            }
        }
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent event) {
        if (event.getMessage().startsWith("ai ")) {

        }
    }

    @EventHandler
    public void playerChangeWorld(PlayerChangedWorldEvent event) {
        if (event.getFrom().equals(StaticLocations.fukieWorld)) {
            event.getPlayer().getScoreboard().registerNewObjective("", "");
        }
    }

    @EventHandler
    public void playerDead(PlayerDeathEvent event) {
        try {
            saveInventory.saveInventory(event.getEntity(), "player_backup");
        } catch (IOException e) {
            SendAdmin.sendMsg("backup inventory failed");
        }
    }

    @EventHandler
    public void concreteDrop(ItemSpawnEvent event) {

        Item item = event.getEntity();

        if (item.getName().endsWith("Concrete Powder")) {
            BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {
                    if (item.getLocation().getBlock().getType() == Material.WATER || item.getLocation().getBlock().getType() == Material.BUBBLE_COLUMN) {
                        Material newMat = Material.valueOf(item.getItemStack().getType().toString().replaceAll("_POWDER", ""));
                        item.getItemStack().setType(newMat);
                    }

                }
            }.runTaskLater(plugin, 10);

        }
    }

    @EventHandler
    public void playerBreakItemFrame(PlayerItemFrameChangeEvent event) {
        if (event.getItemFrame().getWorld().getName().equals("world") && !event.getPlayer().isOp())
            event.setCancelled(true);
    }
}
