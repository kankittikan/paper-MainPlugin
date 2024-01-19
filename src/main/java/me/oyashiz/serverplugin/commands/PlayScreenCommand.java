package me.oyashiz.serverplugin.commands;

import me.oyashiz.serverplugin.MainPlugin;
import me.oyashiz.serverplugin.utils.CustomMapRenderer;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PlayScreenCommand implements CommandExecutor {
    private List<ItemFrame> itemFrames;
    private List<BufferedImage> bufferedImages;
    private List<List<BufferedImage>> frames;

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 0) removeAll();
        if (strings.length == 1) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    createItemFrame();
                    try {
                        loadImg(strings[0], strings[0].startsWith("/"));
                        showScreen();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }.run();
        }
        if (strings.length == 4) {
            if (strings[0].equals("frames")) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        try {
                            loadFrame(strings[1], Integer.parseInt(strings[2]), strings[3]);
                            createItemFrame();
                            new BukkitRunnable() {
                                int i = 0;
                                @Override
                                public void run() {
                                    showFrameScreen(frames.get(i++));
                                    if(i >= frames.size()) {
                                        frames = null;
                                        bufferedImages = null;
                                        cancel();
                                    }
                                }
                            }.runTaskTimer(MainPlugin.getMainPlugin(), 0, 60);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }.run();
            }
        }
        return false;
    }

    public void createItemFrame() {
        removeAll();
        itemFrames = new ArrayList<>();
        World world = Bukkit.getWorld("world");
        for (int y = 14; y >= 7; y--) {
            for (int x = 9; x >= -5; x--) {
                Location location = new Location(world, x, y, 101);
                ItemFrame itemFrame = (ItemFrame) world.spawnEntity(location, EntityType.ITEM_FRAME);
                itemFrame.setSilent(true);
                itemFrames.add(itemFrame);
            }
        }
    }

    public void removeAll() {
        new BukkitRunnable() {
            @Override
            public void run() {
                World world = Bukkit.getWorld("world");
                for (int y = 14; y >= 7; y--) {
                    for (int x = 9; x >= -5; x--) {
                        Location location = new Location(world, x, y, 101);
                        location.getNearbyEntitiesByType(ItemFrame.class, 1).forEach(Entity::remove);
                    }
                }
            }
        }.run();
    }

    public void loadImg(String url, boolean local) throws IOException {
        Image img;
        if(local) {
            img = ImageIO.read(new File(url));
        }
        else {
            img = ImageIO.read(new URL(url));
        }
        bufferedImages = new ArrayList<>();
        int rows = 8;
        int column = 15;
        BufferedImage[] splittedImages = new BufferedImage[rows * column];
        BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        g.drawImage(img, 0, 0, null);
        int width = bi.getWidth();
        int height = bi.getHeight();
        int swidth = width / column;
        int sheight = height / rows;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < column; j++) {
                BufferedImage bimg = bi.getSubimage(j * swidth, i * sheight, swidth, sheight);
                bufferedImages.add(bimg);
            }
        }
    }

    public void loadFrame(String url, int limit, String type) throws IOException {
        frames = new ArrayList<>();
        for (int i = 1; i <= limit; i++) {
            String newUrl = url + i + type;

            loadImg(newUrl, url.startsWith("/"));

            frames.add(bufferedImages);
        }
    }

    public void showScreen() {
        World world = Bukkit.getWorld("world");

        for (int i = 0; i < itemFrames.size(); i++) {

            MapView view = Bukkit.createMap(world);
            for (MapRenderer mapRenderer : view.getRenderers()) {
                view.removeRenderer(mapRenderer);
            }
            CustomMapRenderer customMapRenderer = new CustomMapRenderer();
            if (!customMapRenderer.load(bufferedImages.get(i))) {
                return;
            }
            view.addRenderer(customMapRenderer);

            ItemStack itemStack = new ItemStack(Material.FILLED_MAP);

            MapMeta meta = (MapMeta) itemStack.getItemMeta();
            meta.setMapView(view);
            itemStack.setItemMeta(meta);

            itemFrames.get(i).setItem(itemStack);
        }
    }

    public void showFrameScreen(List<BufferedImage> bufferedImages) {
        World world = Bukkit.getWorld("world");

        for (int i = 0; i < itemFrames.size(); i++) {

            MapView view = Bukkit.createMap(world);
            for (MapRenderer mapRenderer : view.getRenderers()) {
                view.removeRenderer(mapRenderer);
            }
            CustomMapRenderer customMapRenderer = new CustomMapRenderer();
            if (!customMapRenderer.load(bufferedImages.get(i))) {
                return;
            }
            view.addRenderer(customMapRenderer);

            ItemStack itemStack = new ItemStack(Material.FILLED_MAP);

            MapMeta meta = (MapMeta) itemStack.getItemMeta();
            meta.setMapView(view);
            itemStack.setItemMeta(meta);

            itemFrames.get(i).setItem(itemStack);
        }
    }
}
