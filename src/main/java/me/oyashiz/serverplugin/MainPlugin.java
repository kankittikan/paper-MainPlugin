package me.oyashiz.serverplugin;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import me.oyashiz.serverplugin.commands.*;
import me.oyashiz.serverplugin.listeners.*;
import me.oyashiz.serverplugin.listeners.minigame.MinigamesTeleportListener;
import me.oyashiz.serverplugin.listeners.minigame.fukie.FukieMonsterListener;
import me.oyashiz.serverplugin.listeners.minigame.fukie.FukieListener;
import me.oyashiz.serverplugin.listeners.minigame.fukie.level1.Level1Listener;
import me.oyashiz.serverplugin.listeners.minigame.fukie.level2.Level2Listener;
import me.oyashiz.serverplugin.listeners.minigame.fukie.level3.Level3Listener;
import me.oyashiz.serverplugin.listeners.minigame.fukie.level4.Level4Listener;
import me.oyashiz.serverplugin.listeners.minigame.fukie.level5.Level5Listener;
import me.oyashiz.serverplugin.tasks.Auth;
import me.oyashiz.serverplugin.utils.ConfigReader;
import me.oyashiz.serverplugin.utils.MapManager;
import me.oyashiz.serverplugin.utils.StaticFlags;
import me.oyashiz.serverplugin.utils.StaticLocations;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.eclipse.sisu.launch.Main;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public final class MainPlugin extends JavaPlugin {
    public static ConfigReader roleConfig;
    public static ConfigReader homeConfig;
    public static ConfigReader lightConfig;

    public static ConfigReader passwordConfig;

    public static String resourcePack;
    public static String testPack;
    private static MainPlugin mainPlugin;

    public static MainPlugin getMainPlugin(){
        return mainPlugin;
    }

    @Override
    public void onEnable() {
        mainPlugin = this;

        File theDir = new File(getDataFolder().getAbsolutePath() + "/");
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
        theDir = new File(getDataFolder().getAbsolutePath() + "/world_fukie");
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
        theDir = new File(getDataFolder().getAbsolutePath() + "/world_survival");
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
        theDir = new File(getDataFolder().getAbsolutePath() + "/world_speaka");
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
        theDir = new File(getDataFolder().getAbsolutePath() + "/world_star");
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
        theDir = new File(getDataFolder().getAbsolutePath() + "/settings");
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
        theDir = new File(getDataFolder().getAbsolutePath() + "/player_backup");
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
        theDir = new File(getDataFolder().getAbsolutePath() + "/testSave");
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
        theDir = new File(getDataFolder().getAbsolutePath() + "/settings/roleConfig.yml");
        try {
            theDir.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        theDir = new File(getDataFolder().getAbsolutePath() + "/settings/lightMatrix.yml");
        try {
            theDir.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        theDir = new File(getDataFolder().getAbsolutePath() + "/settings/echo.yml");
        try {
            theDir.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        theDir = new File(getDataFolder().getAbsolutePath() + "/world_survival/home.yml");
        try {
            theDir.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //config.yml
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        roleConfig = new ConfigReader(this, "settings/", "roleConfig.yml");
        roleConfig.saveDefaultConfig();

        homeConfig = new ConfigReader(this, "world_survival/", "home.yml");
        homeConfig.saveDefaultConfig();

        lightConfig = new ConfigReader(this, "settings/", "lightMatrix.yml");
        lightConfig.saveDefaultConfig();

        passwordConfig = new ConfigReader(this, "settings/", "echo.yml");
        passwordConfig.saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new JoinAndOutServerListener(this), this);
        getServer().getPluginManager().registerEvents(new SignClickListener(this), this);
        getServer().getPluginManager().registerEvents(new AirLockListener(this), this);
        getServer().getPluginManager().registerEvents(new MinigamesTeleportListener(this), this);
        getServer().getPluginManager().registerEvents(new GateButtonListener(), this);
        getServer().getPluginManager().registerEvents(new BookingInHubListener(this), this);
        getServer().getPluginManager().registerEvents(new OuterWorldDoorListener(), this);
        getServer().getPluginManager().registerEvents(new OuterWorldButtonListener(this), this);
        getServer().getPluginManager().registerEvents(new LevitationListener(), this);
        getServer().getPluginManager().registerEvents(new NetherPortalListener(), this);
        getServer().getPluginManager().registerEvents(new FukieListener(this), this);
        getServer().getPluginManager().registerEvents(new FukieMonsterListener(this), this);
        getServer().getPluginManager().registerEvents(new Level1Listener(this), this);
        getServer().getPluginManager().registerEvents(new Level2Listener(this), this);
        getServer().getPluginManager().registerEvents(new Level3Listener(this), this);
        getServer().getPluginManager().registerEvents(new Level4Listener(this), this);
        getServer().getPluginManager().registerEvents(new Level5Listener(this), this);
        getServer().getPluginManager().registerEvents(new SurvivalListener(this), this);
        getServer().getPluginManager().registerEvents(new MainListener(this), this);
        getServer().getPluginManager().registerEvents(new StarWarListener(this), this);
        getServer().getPluginManager().registerEvents(new SpeakaListener(this), this);
        getServer().getPluginManager().registerEvents(new SleepListener(this), this);
        getServer().getPluginManager().registerEvents(new MinecartMoveListener(this), this);
        getServer().getPluginManager().registerEvents(new AllUnBreakCommand(), this);
        getServer().getPluginManager().registerEvents(new Auth(), this);
        getServer().getPluginManager().registerEvents(new HackListener(), this);
        getServer().getPluginManager().registerEvents(new EndPortalListener(), this);
        getServer().getPluginManager().registerEvents(new WorldIntruderListener(), this);
        getServer().getPluginManager().registerEvents(new HubLiftListener(), this);
        getServer().getPluginManager().registerEvents(new AuthListener(), this);

        getCommand("sethome").setExecutor(new SetSpawnCommand(this));
        getCommand("geteffect").setExecutor(new GetEffectCommand());
        getCommand("home").setExecutor(new SpawnCommand(this));
        getCommand("ps").setExecutor(new PsCommand(this));
        getCommand("ps").setTabCompleter(new PsTabComplete());
        getCommand("hubLight").setExecutor(new HubLightingCommand(this));
        getCommand("createWorld").setExecutor(new CreateWorldCommand());
        getCommand("loadWorld").setExecutor(new LoadWorldCommand());
        getCommand("warp").setExecutor(new TeleportCommand());
        getCommand("setInHubLight").setExecutor(new InHubLightingCommand(this));
        getCommand("goControl").setExecutor(new GoToControlCommand());
        getCommand("setSpaceshipLight").setExecutor(new SpaceshipLightCommand(this));
        getCommand("setouthublight").setExecutor(new OutHubLightCommand(this));
        getCommand("freeze").setExecutor(new GoCellCommand(this));
        getCommand("unbreak").setExecutor(new UnBreakCommand());
        getCommand("spawntest").setExecutor(new SpawnTestCommand(this));
        getCommand("clearmonster").setExecutor(new ClearEntityCommand());
        getCommand("setperforminglevel").setExecutor(new SetPerformingLevelCommand(this));
        getCommand("setrocketlight").setExecutor(new RocketLightCommand(this));
        getCommand("lockplayer").setExecutor(new LockPlayerCommand());
        getCommand("eject").setExecutor(new EjectCommand(this));
        getCommand("reloadserver").setExecutor(new ReloadCommand(this));
        getCommand("playcustom").setExecutor(new PlayCustomCommand());
        getCommand("test").setExecutor(new TestCommand(this));
        getCommand("savehalllight").setExecutor(new SaveMatrixLightCommand());
        getCommand("loadlight").setExecutor(new LoadLightCommand(this));
        getCommand("setlightunder").setExecutor(new SetLightUnderCommand(this));
        getCommand("setlightunder").setTabCompleter(new SetLightUnderTabComplete());
        getCommand("opensunroof").setExecutor(new OpenSunRoofCommand(this));
        getCommand("psinarea").setExecutor(new PsInAreaCommand());
        getCommand("timer").setExecutor(new TimerCommand(this));
        getCommand("dofirework").setExecutor(new DoFireworkCommand(this));
        getCommand("cinemetic").setExecutor(new CinematicCommand(this));
        getCommand("gorocket").setExecutor(new GoRocketCommand());
        getCommand("maintenance").setExecutor(new ModeCommand());
        getCommand("pa").setExecutor(new AnnouncementCommand());
        getCommand("testworld").setExecutor(new GoTestWorldCommand(this));
        getCommand("piano").setTabCompleter(new PianoComplete());
        getCommand("showstat").setExecutor(new ShowStatCommand());
        getCommand("showstat").setTabCompleter(new ShowStatTabComplete());
        getCommand("getdistance").setExecutor(new GetDistanceCommand());
        getCommand("mapimage").setExecutor(new MapImageCommand());
        getCommand("raytrace").setExecutor(new RayTracingCommand());
        getCommand("showstatus").setExecutor(new ShowStatusCommand(this));
        getCommand("getram").setExecutor(new RamUsageCommand());
        getCommand("restore").setExecutor(new GiveBackupInventoryCommand(this));
        getCommand("password").setExecutor(new Auth());
        getCommand("resetpassword").setExecutor(new ResetPasswordCommand());
        getCommand("allunbreak").setExecutor(new AllUnBreakCommand());
        getCommand("controlplayer").setExecutor(new ControlPlayerCommand(this));
        getCommand("playscreen").setExecutor(new PlayScreenCommand());
        getCommand("findmusic").setExecutor(new FindMusicCommand());
        getCommand("setwaypoint").setExecutor(new WaypointCommand());
        getCommand("tuudance").setExecutor(new TuuDanceCommand());
        getCommand("playsidelight").setExecutor(new PlaySideLightCommand());
        getCommand("hublightintruder").setExecutor(new IntruderHubLightingCommand(this));
        getCommand("changeworld").setExecutor(new ChangeWorldCommand());
        getCommand("intruderworld").setExecutor(new IntruderWorldCommand());
        getCommand("openingplayer").setExecutor(new PlayerOpeningCommand());
        getCommand("lockserver").setExecutor(new LockJoinCommand());

        World worldCreator;
        worldCreator = new WorldCreator("world_fukie").createWorld();
        worldCreator = new WorldCreator("world_survival").createWorld();
        worldCreator = new WorldCreator("world_star").createWorld();
        worldCreator = new WorldCreator("world_speaka").createWorld();
        worldCreator = new WorldCreator("world_tester").createWorld();
        worldCreator = new WorldCreator("world_critical").createWorld();

        StaticFlags.performingLevel = getConfig().getInt("performing_level");
        resourcePack = getConfig().getString("resource_pack");
        testPack = getConfig().getString("test_pack");

        StaticLocations.fukieWorld = Bukkit.getServer().getWorld("world_fukie");
        StaticLocations.world = Bukkit.getServer().getWorld("world");
        StaticLocations.survivalWorld = Bukkit.getServer().getWorld("world_survival");

        MapManager manager = MapManager.getInstance();
        manager.init();

        new BukkitRunnable() {
            int count = 0;
            @Override
            public void run() {
                if(Bukkit.getOnlinePlayers().size() == 0) {
                    if(count == 0) {
                        Bukkit.getLogger().info("auto stop enabled");
                    }
                    count++;
                }
                if(Bukkit.getOnlinePlayers().size() != 0 && count != 0) {
                    Bukkit.getLogger().info("auto stop terminated");
                    count = 0;
                }
                if(count >= 600) {
                    Bukkit.shutdown();
                }
            }
        }.runTaskTimer(this, 0, 20);

        new BukkitRunnable() {
            @Override
            public void run() {
                getServer().dispatchCommand(getServer().getConsoleSender(), "gcr");
                getLogger().info("Loaded GravityControl");
            }
        }.runTaskLater(this, 20);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (getServer().getTPS()[0] < 15) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.sendActionBar(ChatColor.RED + "[Server] Low tps : " + (int) getServer().getTPS()[0]);
                    }
                }
                if (getServer().getTPS()[0] <= 10) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.sendMessage(ChatColor.RED + "[Server] Critical tps detection");
                        p.teleport(new Location(Bukkit.getWorld("world_critical"), 0, 100, 0));
                        p.setGameMode(GameMode.ADVENTURE);
                        p.sendMessage(ChatColor.RED + "[Server] Please report to server admin");
                    }
                }
                for(Player p : AuthListener.players) {
                    Unirest.setTimeouts(0, 0);
                    HttpResponse<String> response;
                    try {
                        response = Unirest.post(String.format("http://10.147.17.253:7071/grant?name=%s", p.getName())).asString();
                    } catch (UnirestException e) {
                        throw new RuntimeException(e);
                    }
                    if(response.getBody().equals("OK")) {
                        AuthListener.removePlayer(p);
                    }
                }
            }
        }.runTaskTimer(this, 0, 20);

        for(Player p : Bukkit.getOnlinePlayers()) {
            Unirest.setTimeouts(0, 0);
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            HttpResponse<String> response;
            try {
                response = Unirest.post(String.format("http://10.147.17.253:7071/produce?name=%s&uuid=%s", p.getName(), uuid)).asString();
            } catch (UnirestException e) {
                throw new RuntimeException(e);
            }
            if (!response.getBody().equals("Already")) {
                p.sendMessage(Component.newline());
                p.sendMessage(Component.text("---------------------").color(TextColor.fromHexString("#F6ECA9")));
                p.sendMessage(Component.text("คลิกลิงก์ด้านล่างเพื่อเข้าสู่ระบบ").color(TextColor.fromHexString("#F6ECA9")).appendNewline());
                p.sendMessage(Component.text("-> https://sso-mc.doksakura.com").clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, "https://sso-mc.doksakura.com?uuid=" + uuid)).hoverEvent(HoverEvent.showText(Component.text("Click"))));
                p.sendMessage(Component.text("---------------------").color(TextColor.fromHexString("#F6ECA9")).appendNewline());
                AuthListener.addPlayer(p);
            }
        }
    }
}
