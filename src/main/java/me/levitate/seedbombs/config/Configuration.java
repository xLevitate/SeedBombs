package me.levitate.seedbombs.config;

import lombok.Getter;
import me.levitate.seedbombs.seedbomb.SeedType;
import me.levitate.seedbombs.seedbomb.SeedTypes;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Configuration {
    private final Plugin plugin;
    private FileConfiguration fileConfiguration;

    @Getter
    private final Map<String, String> messages = new LinkedHashMap<>();

    @Getter
    private final Map<String, SeedType> seedTypes = new LinkedHashMap<>();

    // Settings
    //public boolean breakOther = false;

    public Configuration(Plugin plugin, FileConfiguration fileConfiguration) {
        this.plugin = plugin;
        this.fileConfiguration = fileConfiguration;

        reloadConfig();
    }

    public void reloadConfig() {
        plugin.reloadConfig();

        this.fileConfiguration = plugin.getConfig();
        fileConfiguration.options().copyDefaults(true);
        plugin.saveConfig();

        loadMessages();
        loadSettings();
        loadSeedTypesFromConfig();
    }

    private void loadMessages() {
        messages.clear();

        for (String key : fileConfiguration.getConfigurationSection("messages").getKeys(false)) {
            messages.put(key, fileConfiguration.getString("messages." + key));
        }
    }

    private void loadSettings() {
        //breakOther = fileConfiguration.getBoolean("settings.break-other");
        //System.out.println("break other is set to: " + breakOther);
    }

    private void loadSeedTypesFromConfig() {
        ConfigurationSection section = fileConfiguration.getConfigurationSection("seedTypes");
        if (section == null) return;

        SeedTypes.getSeedTypeList().clear();

        for (String typeName : section.getKeys(false)) {
            SeedType type = parseSeedTypeFromConfig(typeName, section.getConfigurationSection(typeName));
            SeedTypes.addSeedType(type);
        }
    }

    private SeedType parseSeedTypeFromConfig(String typeName, ConfigurationSection section) {
        String materialString = section.getString("material");
        int radius = section.getInt("radius");
        String name = section.getString("name");
        List<String> lore = section.getStringList("lore");

        if (radius <= 0) {
            Bukkit.getLogger().severe("The radius must be higher than 0!");
            return null;
        }

        if (materialString == null) {
            Bukkit.getLogger().severe("The material string is invalid!");
            return null;
        }

        System.out.println("material string: " + materialString);

        Material seedMaterial = Material.matchMaterial(materialString);
        if (seedMaterial == null) {
            Bukkit.getLogger().severe("The material is invalid!");
            return null;
        }

        lore.replaceAll(s -> s.replace("%radius%", String.valueOf(radius)));

        return new SeedType(typeName, seedMaterial, radius, name, lore);
    }
}
