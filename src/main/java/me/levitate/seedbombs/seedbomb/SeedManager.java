package me.levitate.seedbombs.seedbomb;

import me.levitate.seedbombs.utils.Utilities;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Farmland;

import java.util.List;

public class SeedManager {
    public static void plantSeeds(SeedType seedType, Block mainBlock) {
        int radius = seedType.getRadius();

        List<Block> blocks = Utilities.getBlocks(mainBlock, radius);

        for (Block block : blocks) {
            if (block.getBlockData().getMaterial().equals(Material.FARMLAND)) {
                Location blockLocation = block.getLocation();
                blockLocation.add(0, 1, 0);

                Block targetBlock = blockLocation.getWorld().getBlockAt(blockLocation);

                if (targetBlock.getType().equals(Material.AIR))
                    blockLocation.getWorld().getBlockAt(blockLocation).setType(Utilities.convertSeedToBlock(seedType.getSeedMaterial()));
            }
        }
    }
}
