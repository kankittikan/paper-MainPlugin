package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.utils.CustomMapRenderer;
import me.oyashiz.serverplugin.utils.MapManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class MapImageCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                String url = args[0];

                MapView view = Bukkit.createMap(player.getWorld());
                for(MapRenderer mapRenderer : view.getRenderers()) {
                    view.removeRenderer(mapRenderer);
                }

                CustomMapRenderer customMapRenderer = new CustomMapRenderer();
                if(!customMapRenderer.load(url)) {
                    player.sendMessage(ChatColor.RED + "Can not load image");
                    return true;
                }
                view.addRenderer(customMapRenderer);

                ItemStack itemStack = new ItemStack(Material.FILLED_MAP);
                MapMeta meta = (MapMeta) itemStack.getItemMeta();
                meta.setMapView(view);
                itemStack.setItemMeta(meta);

                player.getInventory().addItem(itemStack);

                MapManager manager = MapManager.getInstance();
                manager.saveImage(view.getId(), url);
            }
        }
        return false;
    }
}
