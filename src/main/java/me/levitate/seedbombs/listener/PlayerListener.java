package me.levitate.seedbombs.listener;

import lombok.AllArgsConstructor;
import me.levitate.seedbombs.SeedBombs;
import me.levitate.seedbombs.config.Configuration;
import me.levitate.seedbombs.seedbomb.SeedManager;
import me.levitate.seedbombs.seedbomb.SeedType;
import me.levitate.seedbombs.seedbomb.SeedTypes;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

@AllArgsConstructor
public class PlayerListener implements Listener {
    private Configuration configuration;

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction().isLeftClick())
            return;

        Block block = event.getClickedBlock();
        if (event.getClickedBlock() == null)
            return;

        ItemStack item = event.getItem();
        if (item == null)
            return;

        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null)
            return;

        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        String typeName = dataContainer.get(SeedBombs.SEED_NAME_KEY, PersistentDataType.STRING);
        if (typeName == null)
            return;

        if (!event.getClickedBlock().getBlockData().getMaterial().equals(Material.FARMLAND))
            return;

        SeedType seedType = SeedTypes.getSeedTypeByName(typeName);
        if (seedType == null)
            return;

        SeedManager.plantSeeds(seedType, block);
        event.getPlayer().sendMessage(MiniMessage.miniMessage().deserialize(configuration.getMessages().get("placed")));
    }
}
