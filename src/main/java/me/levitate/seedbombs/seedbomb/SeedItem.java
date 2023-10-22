package me.levitate.seedbombs.seedbomb;

import me.levitate.seedbombs.SeedBombs;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class SeedItem {
    private final SeedType seedType;

    public SeedItem(SeedType seedType) {
        this.seedType = seedType;
    }

    public ItemStack getItemStack(int amount) {
        ItemStack itemStack = new ItemStack(seedType.getSeedMaterial(), amount);

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return null;

        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        dataContainer.set(SeedBombs.SEED_NAME_KEY, PersistentDataType.STRING, seedType.getTypeName());

        List<Component> translatedLore = seedType.getLore().stream()
                .map(line -> MiniMessage.miniMessage().deserialize(line).decoration(TextDecoration.ITALIC, false))
                .toList();

        itemMeta.displayName(MiniMessage.miniMessage().deserialize(seedType.getName()).decoration(TextDecoration.ITALIC, false));
        itemMeta.lore(translatedLore);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
