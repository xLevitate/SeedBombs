package me.levitate.seedbombs.utils;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class Utilities {
    public static List<Block> getBlocks(Block start, int radius) {
        int iterations = (radius * 2) + 1;

        List<Block> blocks = new ArrayList<Block>(iterations * iterations * iterations);
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    blocks.add(start.getRelative(x, y, z));
                }
            }
        }

        return blocks;
    }

    public static Material convertSeedToBlock(Material seedMaterial) {
        return switch (seedMaterial) {
            case WHEAT_SEEDS -> Material.WHEAT;
            case CARROT -> Material.CARROTS;
            case POTATO -> Material.POTATOES;
            case BEETROOT_SEEDS -> Material.BEETROOTS;
            case NETHER_WART -> Material.NETHER_WART;
            case MELON_SEEDS -> Material.MELON_STEM;
            case PUMPKIN_SEEDS -> Material.PUMPKIN_STEM;
            default -> seedMaterial;
        };
    }
}
