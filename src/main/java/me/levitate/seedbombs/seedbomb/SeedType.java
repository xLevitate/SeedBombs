package me.levitate.seedbombs.seedbomb;

import lombok.Getter;
import org.bukkit.Material;

import java.util.List;

@Getter
public class SeedType {
    private final String typeName;
    private final Material seedMaterial;
    private final int radius;
    private final String name;
    private final List<String> lore;

    public SeedType(String typeName, Material seedMaterial, int radius, String name, List<String> lore) {
        this.typeName = typeName;
        this.seedMaterial = seedMaterial;
        this.radius = radius;
        this.name = name;
        this.lore = lore;
    }

    public boolean isValid() {
        if (typeName.isEmpty())
            return false;
        else if (seedMaterial == null)
            return false;
        else if (radius <= 0)
            return false;

        return true;
    }
}
