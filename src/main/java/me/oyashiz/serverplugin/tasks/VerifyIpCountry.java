package me.oyashiz.serverplugin.tasks;

import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class VerifyIpCountry {
    public static boolean verifyCountry(String ip, Player player, String countryCheck) {
        player.sendMessage(ChatColor.GOLD + "Checking your ip");
        URL url = null;
        try {
            url = new URL("http://ip-api.com/json/" + ip);
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(new InputStreamReader(url.openStream()));
            if(json.get("status").toString().equals("fail")) {
                player.sendMessage(ChatColor.RED + "Cannot verify your ip");
                return false;
            }
            String country = (String) json.get("country");
            String isp = (String) json.get("isp");
            player.sendMessage("Country : " + country + "\n");
            player.sendMessage("ISP : " + isp + "\n");
            return true;
            //return country.equals(countryCheck);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
