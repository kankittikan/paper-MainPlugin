package me.oyashiz.serverplugin.tasks;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.utils.ConfigReader;
import me.oyashiz.serverplugin.utils.SendAdmin;
import me.oyashiz.serverplugin.utils.StaticLists;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Auth implements CommandExecutor, Listener {
    private String confirm = null;

    private static ArrayList<Player> playerGrant = new ArrayList<>();

    private static ArrayList<String> playerOp = new ArrayList<>();

    public Auth() {
    }

    public static String sha256(final String base) {
        try{
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(base.getBytes(StandardCharsets.UTF_8));
            final StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                final String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public boolean isPlayerRegister(Player player) {
        return MainPlugin.passwordConfig.getConfig().getString(player.getName()) != null;
    }

    public void update(Player player, String s) {
        MainPlugin.passwordConfig.getConfig().set(player.getName(), sha256(s));
        ConfigReader.save(MainPlugin.passwordConfig);
    }

    public boolean checkPasswd(Player player, String s) {
        return sha256(s).equals(MainPlugin.passwordConfig.getConfig().getString(player.getName()));
    }

    public void doAuth(Player player) {
        if (!StaticLists.playerLock.contains(player)) {
            StaticLists.playerLock.add(player);
        }
        if(player.isOp()) {
            player.setOp(false);
            if(!playerOp.contains(player.getName())) playerOp.add(player.getName());
        }
        if (isPlayerRegister(player)) {
            player.sendMessage(Component.text(ChatColor.AQUA + "Enter your password: /password <pass>"));
        } else {
            player.sendMessage(Component.text(ChatColor.AQUA + "Please register your password"));
            player.sendMessage(Component.text(ChatColor.AQUA + "Your password will be digest to SHA-256\n"));
            player.sendMessage(Component.text(ChatColor.AQUA + "Enter your new password: /password <pass>"));
        }
    }

    @EventHandler
    public void playerLeave(PlayerQuitEvent event) {
        playerGrant.remove(event.getPlayer());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1) {
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                if(playerGrant.contains(player)) {
                    player.sendMessage("You already login");
                    return false;
                }
                if (StaticLists.playerLock.contains(player)) {
                    if (isPlayerRegister(player)) {
                        if (checkPasswd(player, strings[0])) {
                            player.sendMessage(Component.text(ChatColor.GREEN + "Password correct"));
                            StaticLists.playerLock.remove(player);

                            playerGrant.add(player);

                            if(player.getResourcePackStatus().name().equals("FAILED_DOWNLOAD")) {
                                player.kick(Component.text("Cannot detect your server resource-pack"));
                            }

                            if(playerOp.contains(player.getName())) {
                                player.setOp(true);
                                playerOp.remove(player.getName());
                            }

                            return true;
                        } else {
                            player.sendMessage(Component.text(ChatColor.RED + "Password incorrect"));
                            return true;
                        }
                    } else {
                        if (confirm == null) {
                            confirm = strings[0];
                            player.sendMessage(Component.text(ChatColor.YELLOW + "Enter confirm password: /password <pass>"));
                            return true;
                        } else {
                            if (confirm.equals(strings[0])) {
                                player.sendMessage(Component.text(ChatColor.GREEN + "Password updated"));
                                StaticLists.playerLock.remove(player);
                                update(player, strings[0]);
                                return true;
                            } else {
                                player.sendMessage(Component.text(ChatColor.AQUA + "Enter your new password: /password <pass>"));
                                confirm = null;
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
