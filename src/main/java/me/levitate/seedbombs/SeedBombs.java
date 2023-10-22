package me.levitate.seedbombs;

import co.aikar.commands.PaperCommandManager;
import com.google.common.collect.ImmutableList;
import me.levitate.seedbombs.command.MainCommand;
import me.levitate.seedbombs.config.Configuration;
import me.levitate.seedbombs.listener.PlayerListener;
import me.levitate.seedbombs.seedbomb.SeedTypes;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class SeedBombs extends JavaPlugin {
    public static NamespacedKey SEED_NAME_KEY;

    @Override
    public void onEnable() {
        SEED_NAME_KEY = new NamespacedKey(this, "seed_name");

        getConfig().options().copyDefaults(true);
        saveConfig();

        Configuration configuration = new Configuration(this, getConfig());

        final PaperCommandManager commandManager = new PaperCommandManager(this);
        commandManager.getCommandCompletions().registerCompletion("seedtypes", c -> SeedTypes.getSeedTypeImmutableList());

        getServer().getPluginManager().registerEvents(new PlayerListener(configuration), this);

        commandManager.enableUnstableAPI("help");
        commandManager.registerCommand(new MainCommand(configuration));
    }
}