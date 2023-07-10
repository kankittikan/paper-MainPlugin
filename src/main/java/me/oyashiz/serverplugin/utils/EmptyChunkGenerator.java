package me.oyashiz.serverplugin.utils;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import java.util.Random;

public class EmptyChunkGenerator extends ChunkGenerator {
    public void generateSurface(WorldInfo info, Random random, int x, int z, ChunkData data) {
        for (int y = info.getMinHeight(); y < info.getMaxHeight(); y++) {
            data.setBlock(x, y, z, Material.AIR);
        }
    }

    public boolean shouldGenerateNoise() {
        return false;
    }

    public boolean shouldGenerateBedrock() {
        return false;
    }

    public boolean shouldGenerateCaves() {
        return false;
    }
}
