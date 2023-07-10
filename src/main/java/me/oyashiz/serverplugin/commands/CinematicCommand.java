package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.utils.StaticFlags;
import me.oyashiz.serverplugin.utils.StaticLocations;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.EulerAngle;

import java.util.ArrayList;
import java.util.Random;

public class CinematicCommand implements CommandExecutor {
    private final MainPlugin plugin;

    private OutHubLightCommand outHubLightCommand;
    private SpaceshipLightCommand spaceshipLightCommand;

    private static boolean tuuDance = false;

    private boolean skip = false;

    public CinematicCommand(MainPlugin plugin) {
        this.plugin = plugin;
        outHubLightCommand = new OutHubLightCommand(plugin);
        spaceshipLightCommand = new SpaceshipLightCommand(plugin);
    }

    public void play(Player player) {


        player.sendTitle("Space Hub", "");
        BukkitTask sideWorld = new BukkitRunnable() {

            float x = -22;
            float yaw = -6;

            @Override
            public void run() {
                Location location = new Location(StaticLocations.world, x, 16, -21, yaw, 16);
                player.teleport(location);
                x += 0.4;
                yaw += 0.15;
                if (x >= 51 || skip) {
                    cancel();
                    BukkitTask upTop = new BukkitRunnable() {
                        float y = 136;

                        @Override
                        public void run() {
                            Location location = new Location(StaticLocations.world, 21, y, 33, 0, 90);
                            player.teleport(location);
                            y -= 0.3;
                            if (y <= 90 || skip) {
                                cancel();
                                BukkitTask smith = new BukkitRunnable() {
                                    float x = 283;
                                    float yaw = -130;
                                    boolean play = false;

                                    @Override
                                    public void run() {
                                        Location location = new Location(StaticLocations.fukieWorld, x, -33, -159, yaw, 0);
                                        player.teleport(location);
                                        x += 0.05;
                                        yaw -= 0.1;
                                        if (!play) {
                                            BukkitTask p = new BukkitRunnable() {
                                                @Override
                                                public void run() {
                                                    player.sendTitle("Minigames", "");
                                                }
                                            }.runTaskLater(plugin, 50);

                                            play = true;
                                        }
                                        if (x >= 290 || skip) {
                                            cancel();
                                            BukkitTask level1 = new BukkitRunnable() {
                                                float y = -22;
                                                float pitch = 50;
                                                boolean play = false;

                                                @Override
                                                public void run() {
                                                    Location location = new Location(StaticLocations.fukieWorld, 72, y, -45, 90, pitch);
                                                    player.teleport(location);
                                                    y -= 0.3;
                                                    pitch -= 0.3;
                                                    if (!play) {
                                                        player.playSound(player, Sound.MUSIC_DISC_PIGSTEP, 0.5f, 1);
                                                        play = true;
                                                    }
                                                    if (y <= -45 || skip) {
                                                        cancel();
                                                        BukkitTask level2 = new BukkitRunnable() {
                                                            float z = -159;

                                                            @Override
                                                            public void run() {
                                                                Location location = new Location(StaticLocations.fukieWorld, 55, -44, z, 180, 30);
                                                                player.teleport(location);
                                                                z -= 0.3;
                                                                if (z <= -180 || skip) {
                                                                    cancel();
                                                                    BukkitTask level3 = new BukkitRunnable() {
                                                                        float y = 26;
                                                                        float pitch = 45;

                                                                        @Override
                                                                        public void run() {
                                                                            Location location = new Location(StaticLocations.fukieWorld, 193, y, -187, 90, pitch);
                                                                            player.teleport(location);
                                                                            y -= 0.3;
                                                                            pitch -= 0.3;
                                                                            if (y <= 15 || skip) {
                                                                                cancel();
                                                                                BukkitTask level3In = new BukkitRunnable() {
                                                                                    float x = 189;

                                                                                    @Override
                                                                                    public void run() {
                                                                                        Location location = new Location(StaticLocations.fukieWorld, x, -13, -189, 175, 30);
                                                                                        player.teleport(location);
                                                                                        x -= 0.2;
                                                                                        if (x <= 178 || skip) {
                                                                                            cancel();
                                                                                            BukkitTask level4 = new BukkitRunnable() {
                                                                                                float y = 36;

                                                                                                @Override
                                                                                                public void run() {
                                                                                                    Location location = new Location(StaticLocations.fukieWorld, 243, y, -286, 180, 90);
                                                                                                    player.teleport(location);
                                                                                                    y -= 0.3;
                                                                                                    if (y <= 20 || skip) {
                                                                                                        cancel();

                                                                                                        BukkitTask speaka = new BukkitRunnable() {
                                                                                                            float y = 57;

                                                                                                            boolean play = false;

                                                                                                            @Override
                                                                                                            public void run() {
                                                                                                                Location location = new Location(Bukkit.getWorld("world_speaka"), -266, y, -814, 136, 34);
                                                                                                                player.teleport(location);
                                                                                                                y -= 0.3;
                                                                                                                if (!play) {
                                                                                                                    player.playSound(player.getLocation(), "park", 0.2f, 1);
                                                                                                                    player.sendMessage("By Trevor");
//                                                                                                                    TextComponent textComponent = new TextComponent("https://www.minecraftmaps.com/amusement-park-maps/unspeaka-land");
//                                                                                                                    textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.minecraftmaps.com/amusement-park-maps/unspeaka-land"));
//                                                                                                                    player.spigot().sendMessage(textComponent);
                                                                                                                    play = true;
                                                                                                                }
                                                                                                                if (y <= 25 || skip) {
                                                                                                                    cancel();
                                                                                                                    BukkitTask speaka2 = new BukkitRunnable() {
                                                                                                                        float y = 61;

                                                                                                                        @Override
                                                                                                                        public void run() {
                                                                                                                            Location location = new Location(Bukkit.getWorld("world_speaka"), -311, y, -804, -90, 90);
                                                                                                                            player.teleport(location);
                                                                                                                            y += 0.3;
                                                                                                                            if (y >= 90 || skip) {
                                                                                                                                cancel();
                                                                                                                                BukkitTask speaka3 = new BukkitRunnable() {
                                                                                                                                    float x = -321;
                                                                                                                                    float yaw = -157;

                                                                                                                                    @Override
                                                                                                                                    public void run() {
                                                                                                                                        Location location = new Location(Bukkit.getWorld("world_speaka"), x, 33, -794, yaw, 16);
                                                                                                                                        player.teleport(location);
                                                                                                                                        x += 0.3;
                                                                                                                                        yaw -= 0.2;
                                                                                                                                        if (x >= -268 || skip) {
                                                                                                                                            cancel();
                                                                                                                                            BukkitTask speaka4 = new BukkitRunnable() {
                                                                                                                                                float x = -269;
                                                                                                                                                float yaw = 60;

                                                                                                                                                @Override
                                                                                                                                                public void run() {
                                                                                                                                                    Location location = new Location(Bukkit.getWorld("world_speaka"), x, 22, -788, yaw, 30);
                                                                                                                                                    player.teleport(location);
                                                                                                                                                    x -= 0.3;
                                                                                                                                                    yaw -= 0.2;
                                                                                                                                                    if (x <= -332 || skip) {
                                                                                                                                                        cancel();
                                                                                                                                                        BukkitTask starWar = new BukkitRunnable() {
                                                                                                                                                            float y = 58;
                                                                                                                                                            float pitch = 30;

                                                                                                                                                            boolean play = false;

                                                                                                                                                            @Override
                                                                                                                                                            public void run() {
                                                                                                                                                                Location location = new Location(Bukkit.getWorld("world_star"), -6, y, -73, 0, pitch);
                                                                                                                                                                player.teleport(location);
                                                                                                                                                                y += 0.3;
                                                                                                                                                                pitch -= 0.1;
                                                                                                                                                                if (!play) {
                                                                                                                                                                    player.playSound(player.getLocation(), "starwar", 0.1f, 1);
                                                                                                                                                                    player.sendMessage("By John Ireland");
//                                                                                                                                                                    TextComponent textComponent = new TextComponent("https://www.minecraftmaps.com/amusement-park-maps/star-wars-space-world");
//                                                                                                                                                                    textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.minecraftmaps.com/amusement-park-maps/star-wars-space-world"));
//                                                                                                                                                                    player.spigot().sendMessage(textComponent);
                                                                                                                                                                    play = true;
                                                                                                                                                                }
                                                                                                                                                                if (y >= 155 || skip) {
                                                                                                                                                                    cancel();
                                                                                                                                                                    BukkitTask starWar2 = new BukkitRunnable() {
                                                                                                                                                                        float z = -138;

                                                                                                                                                                        @Override
                                                                                                                                                                        public void run() {
                                                                                                                                                                            Location location = new Location(Bukkit.getWorld("world_star"), -12, 90, z, -60, 27);
                                                                                                                                                                            player.teleport(location);
                                                                                                                                                                            z += 0.3;
                                                                                                                                                                            if (z >= -50 || skip) {
                                                                                                                                                                                cancel();
                                                                                                                                                                                BukkitTask starWar3 = new BukkitRunnable() {
                                                                                                                                                                                    float x = -66;

                                                                                                                                                                                    @Override
                                                                                                                                                                                    public void run() {
                                                                                                                                                                                        Location location = new Location(Bukkit.getWorld("world_star"), x, 100, 36, -59, 34);
                                                                                                                                                                                        player.teleport(location);
                                                                                                                                                                                        x += 0.3;
                                                                                                                                                                                        if (x >= 11 || skip) {
                                                                                                                                                                                            cancel();

                                                                                                                                                                                            BukkitTask starWar4 = new BukkitRunnable() {
                                                                                                                                                                                                float y = 93;

                                                                                                                                                                                                @Override
                                                                                                                                                                                                public void run() {
                                                                                                                                                                                                    Location location = new Location(Bukkit.getWorld("world_star"), 2, y, -124, 0, 50);
                                                                                                                                                                                                    player.teleport(location);
                                                                                                                                                                                                    y += 0.3;
                                                                                                                                                                                                    if (y >= 150 || skip) {
                                                                                                                                                                                                        cancel();
                                                                                                                                                                                                        BukkitTask survival = new BukkitRunnable() {
                                                                                                                                                                                                            float y = 70;
                                                                                                                                                                                                            float pitch = 23;

                                                                                                                                                                                                            boolean play = false;

                                                                                                                                                                                                            @Override
                                                                                                                                                                                                            public void run() {
                                                                                                                                                                                                                Location location = new Location(StaticLocations.survivalWorld, 176, y, -2, 120, pitch);
                                                                                                                                                                                                                player.teleport(location);
                                                                                                                                                                                                                y += 0.2;
                                                                                                                                                                                                                pitch -= 0.2;
                                                                                                                                                                                                                if (!play) {
                                                                                                                                                                                                                    BukkitTask task = new BukkitRunnable() {
                                                                                                                                                                                                                        @Override
                                                                                                                                                                                                                        public void run() {
                                                                                                                                                                                                                            player.sendTitle("Survival", "");
                                                                                                                                                                                                                            player.playSound(player, Sound.MUSIC_DISC_OTHERSIDE, 0.5f, 1);
                                                                                                                                                                                                                        }
                                                                                                                                                                                                                    }.runTaskLater(plugin, 50);
                                                                                                                                                                                                                    play = true;
                                                                                                                                                                                                                }
                                                                                                                                                                                                                if (y >= 110 || skip) {
                                                                                                                                                                                                                    cancel();
                                                                                                                                                                                                                    BukkitTask survival2 = new BukkitRunnable() {
                                                                                                                                                                                                                        float x = 39;

                                                                                                                                                                                                                        boolean play = false;

                                                                                                                                                                                                                        @Override
                                                                                                                                                                                                                        public void run() {
                                                                                                                                                                                                                            Location location = new Location(StaticLocations.survivalWorld, x, 155, -122, 30, 36);
                                                                                                                                                                                                                            player.teleport(location);
                                                                                                                                                                                                                            x -= 0.3;
                                                                                                                                                                                                                            if (!play) {
                                                                                                                                                                                                                                BukkitTask task = new BukkitRunnable() {
                                                                                                                                                                                                                                    @Override
                                                                                                                                                                                                                                    public void run() {
                                                                                                                                                                                                                                        player.sendTitle("Credits", "");
                                                                                                                                                                                                                                        BukkitTask task1 = new BukkitRunnable() {
                                                                                                                                                                                                                                            @Override
                                                                                                                                                                                                                                            public void run() {
                                                                                                                                                                                                                                                player.sendTitle("Map Creator", ChatColor.GREEN + "rorgot, Emma8956, NicoTin3060");
                                                                                                                                                                                                                                                BukkitTask task2 = new BukkitRunnable() {
                                                                                                                                                                                                                                                    @Override
                                                                                                                                                                                                                                                    public void run() {
                                                                                                                                                                                                                                                        player.sendTitle("Server Developer", ChatColor.GREEN + "kanhnoonsp");
//                                                                                                                                                                                                                                                        TextComponent textComponent = new TextComponent("source code: https://github.com/kankittikan/Minecraft-1.19.2-Spigot-plugin");
//                                                                                                                                                                                                                                                        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/kankittikan/Minecraft-1.19.2-Spigot-plugin"));
//                                                                                                                                                                                                                                                        player.spigot().sendMessage(textComponent);
                                                                                                                                                                                                                                                        BukkitTask task3 = new BukkitRunnable() {
                                                                                                                                                                                                                                                            @Override
                                                                                                                                                                                                                                                            public void run() {
                                                                                                                                                                                                                                                                player.sendTitle("Thank for join our server", "have fun!!");
                                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                                        }.runTaskLater(plugin, 150);

                                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                                }.runTaskLater(plugin, 150);

                                                                                                                                                                                                                                            }
                                                                                                                                                                                                                                        }.runTaskLater(plugin, 150);
                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                }.runTaskLater(plugin, 100);
                                                                                                                                                                                                                                play = true;
                                                                                                                                                                                                                            }
                                                                                                                                                                                                                            if (x <= -100) {
                                                                                                                                                                                                                                cancel();
                                                                                                                                                                                                                                player.teleport(new Location(StaticLocations.world, 0, 7, 88));
                                                                                                                                                                                                                                player.sendTitle("Welcome", "Doksakura Server");
                                                                                                                                                                                                                                BukkitTask task = new BukkitRunnable() {
                                                                                                                                                                                                                                    @Override
                                                                                                                                                                                                                                    public void run() {
                                                                                                                                                                                                                                        player.sendTitle("Happy Birthday", "Emma8956");
                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                }.runTaskLater(plugin, 50);

                                                                                                                                                                                                                                player.stopAllSounds();
                                                                                                                                                                                                                                player.playSound(player.getLocation(), "hbd", 0.1f, 1);
                                                                                                                                                                                                                                spaceshipLightCommand.setLight("off");
                                                                                                                                                                                                                                outHubLightCommand.setLight("off");
                                                                                                                                                                                                                                player.setInvisible(false);
                                                                                                                                                                                                                                StaticFlags.cinematic = false;
                                                                                                                                                                                                                                Location location1 = new Location(StaticLocations.world, 4, 7, 115);
                                                                                                                                                                                                                                location1.getBlock().setType(Material.REDSTONE_BLOCK);
                                                                                                                                                                                                                                dance(true);
                                                                                                                                                                                                                                BukkitTask task1 = new BukkitRunnable() {
                                                                                                                                                                                                                                    @Override
                                                                                                                                                                                                                                    public void run() {
                                                                                                                                                                                                                                        player.setGameMode(GameMode.ADVENTURE);
                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                }.runTaskLater(plugin, 10);

                                                                                                                                                                                                                                BukkitTask task2 = new BukkitRunnable() {
                                                                                                                                                                                                                                    @Override
                                                                                                                                                                                                                                    public void run() {
                                                                                                                                                                                                                                        dance(false);
                                                                                                                                                                                                                                    }
                                                                                                                                                                                                                                }.runTaskLater(plugin, 2040);
                                                                                                                                                                                                                            }
                                                                                                                                                                                                                        }
                                                                                                                                                                                                                    }.runTaskTimer(plugin, 0, 2);
                                                                                                                                                                                                                }
                                                                                                                                                                                                            }
                                                                                                                                                                                                        }.runTaskTimer(plugin, 0, 2);

                                                                                                                                                                                                    }
                                                                                                                                                                                                }
                                                                                                                                                                                            }.runTaskTimer(plugin, 0, 2);

                                                                                                                                                                                        }
                                                                                                                                                                                    }
                                                                                                                                                                                }.runTaskTimer(plugin, 0, 2);

                                                                                                                                                                            }
                                                                                                                                                                        }
                                                                                                                                                                    }.runTaskTimer(plugin, 0, 2);

                                                                                                                                                                }
                                                                                                                                                            }
                                                                                                                                                        }.runTaskTimer(plugin, 0, 2);

                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                            }.runTaskTimer(plugin, 0, 2);

                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                }.runTaskTimer(plugin, 0, 2);

                                                                                                                            }
                                                                                                                        }
                                                                                                                    }.runTaskTimer(plugin, 0, 2);

                                                                                                                }
                                                                                                            }
                                                                                                        }.runTaskTimer(plugin, 0, 2);
                                                                                                    }
                                                                                                }
                                                                                            }.runTaskTimer(plugin, 0, 2);
                                                                                        }
                                                                                    }
                                                                                }.runTaskTimer(plugin, 0, 2);
                                                                            }
                                                                        }
                                                                    }.runTaskTimer(plugin, 0, 2);
                                                                }
                                                            }
                                                        }.runTaskTimer(plugin, 0, 2);
                                                    }
                                                }
                                            }.runTaskTimer(plugin, 0, 2);
                                        }
                                    }
                                }.runTaskTimer(plugin, 0, 2);
                            }
                        }
                    }.runTaskTimer(plugin, 0, 2);
                }
            }
        }.runTaskTimer(plugin, 0, 2);
    }

    public void dance(boolean play) {
        if (tuuDance) return;
        if (play) {
            ArmorStand armorStand = null;
            for (Entity entity : StaticLocations.world.getEntities()) {
                if (entity instanceof ArmorStand && entity.getName().equals("Lung Tuu")) {
                    armorStand = (ArmorStand) entity;
                    break;
                }
            }
            if (armorStand == null) return;

            ArmorStand finalArmorStand = armorStand;

            finalArmorStand.setCustomNameVisible(true);
            BukkitTask task = new BukkitRunnable() {

                Random random = new Random();

                float yaw = 0;

                @Override
                public void run() {
                    finalArmorStand.setLeftArmPose(new EulerAngle(random.nextDouble(0, 1), random.nextDouble(0, 2), 0));
                    finalArmorStand.setRightArmPose(new EulerAngle(random.nextDouble(0, 1), random.nextDouble(0, 2), 0));
                    finalArmorStand.setBodyPose(new EulerAngle(random.nextDouble(0, 0.3), 0, 0));
                    finalArmorStand.setHeadPose(new EulerAngle(random.nextDouble(0, 1), 0, 0));
                    finalArmorStand.setRotation(yaw += 10, 0);
                    if (tuuDance) {
                        cancel();
                        finalArmorStand.setRotation(180, 0);
                        finalArmorStand.setCustomNameVisible(false);
                    }
                }
            }.runTaskTimer(plugin, 0, 5);
        }

        if (!play) {
            tuuDance = true;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 1) {
            ArrayList<Player> players = new ArrayList<>();
            if (args[0].equals("@a")) {
                players.addAll(Bukkit.getOnlinePlayers());
            } else {
                Player player = Bukkit.getPlayerExact(args[0]);
                if (player == null || !player.isOnline()) return false;
                players.add(player);
            }

            spaceshipLightCommand.setLight("on");
            outHubLightCommand.setLight("on");

            StaticFlags.cinematic = true;

            for (Player p : players) {
                p.setGameMode(GameMode.CREATIVE);
                p.setInvisible(true);
                p.getInventory().clear();
                play(p);
            }
        }


        return false;
    }
}
