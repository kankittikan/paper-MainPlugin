package me.oyashiz.serverplugin.utils;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class CustomMapRenderer extends MapRenderer {

    private BufferedImage bufferedImage;
    private boolean done;

    public CustomMapRenderer() {
        done = false;
    }

    public CustomMapRenderer(String url) {
        load(url);
        done = false;
    }

    public boolean load(String url) {
        BufferedImage bufferedImage = null;
        try {
            if (url.startsWith("https://content.doksakura.com")) {
                String fileUrl = "/home/kan/WebService/Content" + url.substring(29);
                bufferedImage = ImageIO.read(new File(fileUrl));
            } else {
                bufferedImage = ImageIO.read(new URL(url));
            }
            bufferedImage = MapPalette.resizeImage(bufferedImage);
        } catch (IOException e) {
            return false;
        }
        this.bufferedImage = bufferedImage;
        return true;
    }

    public boolean load(BufferedImage bufferedImage) {
        bufferedImage = MapPalette.resizeImage(bufferedImage);

        this.bufferedImage = bufferedImage;
        return true;
    }

    @Override
    public void render(MapView map, MapCanvas canvas, Player player) {
        if (done) return;

        canvas.drawImage(0, 0, bufferedImage);

        map.setTrackingPosition(false);
        done = true;
    }
}
