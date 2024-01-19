package me.oyashiz.serverplugin.listeners;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.commands.LockJoinCommand;
import me.oyashiz.serverplugin.tasks.ResourcePack;
import me.oyashiz.serverplugin.tasks.ScoreboardPlayer;
import me.oyashiz.serverplugin.utils.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.UUID;

public class JoinAndOutServerListener implements Listener {

    private final MainPlugin plugin;

    private ArrayList<String> strings;

    public JoinAndOutServerListener(MainPlugin plugin) {
        this.plugin = plugin;
        strings = new ArrayList<>();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.isOp()) {
            event.setJoinMessage(ChatColor.GREEN + player.getName() + ChatColor.WHITE + " join this server " + ChatColor.GOLD + "[Operator]");
        } else {
            event.setJoinMessage(ChatColor.GREEN + player.getName() + ChatColor.WHITE + " join this server " + ChatColor.GOLD + "[Player]");
        }

        //if (!player.getName().equals("kanhnoonsp")) player.kick(Component.text("Server in maintenance"));
        if (LockJoinCommand.lock && !player.isOp()) player.kick(Component.text("Server in lock mode by admin"));

        ResourcePack.set(player);

        if (!strings.isEmpty()) {
            player.sendMessage(ChatColor.YELLOW + "Announcement from administrator");
            for (int i = 0; i < strings.size(); i++) {
                player.sendMessage(i + 1 + ".) " + strings.get(i));
            }
            player.sendMessage(" ");
        } else {
            player.sendMessage(ChatColor.GREEN + "No announcement from administrator");
        }

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response;
        try {
            response = Unirest.post(String.format("http://10.147.17.253:7071/produce?name=%s&uuid=%s", player.getName(), uuid)).asString();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }

        if (player.getWorld().getName().equals("world_fukie")) {
            Location hub = new Location(Bukkit.getWorld("world_fukie"), 315, -60, -162);
            player.teleport(hub);
        }

        Location location = new Location(Bukkit.getWorld("world"), 22, 33, 33);
        if (player.getLastPlayed() == 0) {
            BukkitTask task1 = new BukkitRunnable() {
                @Override
                public void run() {
                    player.teleport(location);
                    player.setGameMode(GameMode.ADVENTURE);
                }
            }.runTaskLater(plugin, 10);
        }
        BukkitTask task12 = new BukkitRunnable() {
            @Override
            public void run() {
                if (player.getWorld() == StaticLocations.world) {
                    playWelcomeMusic(player);
                }
            }
        }.runTaskLater(plugin, 30);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!response.getBody().equals("Already")) {
                    player.sendMessage(Component.newline());
                    player.sendMessage(Component.text("---------------------").color(TextColor.fromHexString("#F6ECA9")));
                    player.sendMessage(Component.text("คลิกลิงก์ด้านล่างเพื่อเข้าสู่ระบบ").color(TextColor.fromHexString("#F6ECA9")).appendNewline());
                    player.sendMessage(Component.text("-> https://sso-mc.doksakura.com").clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, "https://sso-mc.doksakura.com?uuid=" + uuid)).hoverEvent(HoverEvent.showText(Component.text("Click"))));
                    player.sendMessage(Component.text("---------------------").color(TextColor.fromHexString("#F6ECA9")).appendNewline());
                    AuthListener.addPlayer(player);
                }
            }
        }.runTaskLater(MainPlugin.getMainPlugin(), 50);

    }

    @EventHandler
    public void onPlayerOut(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Unirest.setTimeouts(0, 0);
        try {
            Unirest.post(String.format("http://10.147.17.253:7071/logout?name=%s", player.getName())).asString();
        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        event.setQuitMessage(ChatColor.GOLD + player.getDisplayName() + ChatColor.RED + " log out from this server.");
    }

    @EventHandler
    public void onPlayerDead(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        if (player.getWorld().getName().equals("world_critical")) {
            event.setRespawnLocation(new Location(Bukkit.getWorld("world_critical"), 0, 100, 0));
            return;
        }
        if (player.getWorld().getName().equals("world_intruder")) {
            event.setRespawnLocation(new Location(Bukkit.getWorld("world_intruder"), 33, 3, 26));
            return;
        }
        if (player.getWorld().getName().equals("world") && CheckInArea.check(player.getLocation(), 39, 51, 28, 50)) {
            Location location = player.getLocation();
            location.setY(6);
            event.setRespawnLocation(location);
            return;
        }

        if (player.getWorld().getName().equals("tester_world")) {
            Location location = player.getLocation();
            location.setY(100);
            event.setRespawnLocation(location);
            return;
        }

        if (player.getWorld().getName().equals("world_star")) {
            event.setRespawnLocation(new Location(Bukkit.getWorld("world_star"), 0, 51, -143));
            return;
        }

        if (player.getWorld().getName().equals("world_speaka")) {
            event.setRespawnLocation(new Location(Bukkit.getWorld("world_speaka"), -216, 4, -794));
            return;
        }

        if (player.getWorld().getName().equals("world_fukie")) {
            if (StaticInt.teamLife <= 0) {
                for (Player p : StaticLocations.fukieWorld.getPlayers()) {
                    event.setRespawnLocation(StaticLocations.smith);
                    p.teleport(StaticLocations.smith);
                    p.sendMessage("การตายของทีมครบ 3 ครั้งแล้ว");
                    p.sendMessage("จึงต้องเล่นด่านแรกใหม่");
                    p.getInventory().clear();
                }
                StaticFlags.performingLevel = 1;
                plugin.getConfig().set("performing_level", StaticFlags.performingLevel);
                plugin.saveConfig();
                StaticInt.teamLife = 3;
                return;
            }
            StaticInt.teamLife--;
            SendMsg.send(StaticLocations.fukieWorld, ChatColor.RED + "จำนวนการตายของทีมคงเหลือ: " + StaticInt.teamLife);
            player.sendMessage(ChatColor.RED + "จำนวนการตายของทีมคงเหลือ: " + StaticInt.teamLife);
            Location coolDown = new Location(Bukkit.getWorld("world_fukie"), 238, -23, -175);
            Location smith = new Location(Bukkit.getWorld("world_fukie"), 290, -33, -159, -180, 0);
            event.setRespawnLocation(coolDown);
            ScoreboardPlayer.clear(player);
            player.sendMessage("Waiting for cool down time");
            BukkitTask down = new BukkitRunnable() {
                @Override
                public void run() {
                    player.teleport(smith);

                }
            }.runTaskLater(plugin, 200);
            return;
        }
        Location location = new Location(Bukkit.getWorld("world"), 36, 5, 23);
        if (player.getWorld().getName().equals("world") || player.getBedSpawnLocation() == null || player.getBedSpawnLocation().getWorld() != StaticLocations.survivalWorld) {
            event.setRespawnLocation(location);
            player.sendMessage(ChatColor.GREEN + "You have been rescued and come back in Spaceship");
            return;
        }
    }

    @EventHandler
    public void changeWorld(PlayerChangedWorldEvent event) {
        playWelcomeMusic(event.getPlayer());
    }

    private void playWelcomeMusic(Player player) {
        player.stopAllSounds();
        if (player.getWorld().getName().equals("world")) {
            player.playSound(player, Sound.MUSIC_UNDER_WATER, 0.3f, 1);
        }
    }
}
