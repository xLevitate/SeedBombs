package me.levitate.seedbombs.seedbomb;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SeedTypes {
    @Getter
    private static final List<SeedType> seedTypeList = new ArrayList<>();

    public static void addSeedType(SeedType seedType) {
        seedTypeList.add(seedType);
    }

    public static SeedType getSeedTypeByName(String typeName) {
        for (SeedType seedType : seedTypeList) {
            if (typeName.equals(seedType.getTypeName()))
                return seedType;
        }

        return null;
    }

    public static ImmutableList<String> getSeedTypeImmutableList() {
        List<String> seedTypeNames = seedTypeList.stream()
                .map(SeedType::getTypeName)
                .collect(Collectors.toList());

        return ImmutableList.copyOf(seedTypeNames);
    }
}
