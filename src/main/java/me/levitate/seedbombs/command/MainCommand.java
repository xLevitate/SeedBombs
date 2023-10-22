package me.levitate.seedbombs.command;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import lombok.AllArgsConstructor;
import me.levitate.seedbombs.config.Configuration;
import me.levitate.seedbombs.seedbomb.SeedItem;
import me.levitate.seedbombs.seedbomb.SeedType;
import me.levitate.seedbombs.seedbomb.SeedTypes;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@CommandAlias("seedbomb|seedbombs|sb")
@AllArgsConstructor
public class MainCommand extends BaseCommand {
    private Configuration configuration;

    @Default
    public void onHelp(CommandSender sender, CommandHelp commandHelp) {
        commandHelp.showHelp();
    }

    @Subcommand("reload")
    @CommandPermission("seedbombs.reload")
    public void onReload(CommandSender sender) {
        configuration.reloadConfig();
        sender.sendMessage(MiniMessage.miniMessage().deserialize("<green>Reloaded the config."));
    }

    @Subcommand("give")
    @Syntax("<player> <tier> <amount>")
    @CommandCompletion("@players @seedtypes @range:1-64")
    @CommandPermission("seedbombs.give")
    public void onGive(CommandSender sender, OnlinePlayer onlinePlayer, String typeName, Integer amount) {
        final Player player = onlinePlayer.player;
        if (player == null) {
            sender.sendMessage(MiniMessage.miniMessage().deserialize(configuration.getMessages().get("error-give")));
            return;
        }

        SeedType seedType = SeedTypes.getSeedTypeByName(typeName);
        if (seedType == null || !seedType.isValid()) return;

        SeedItem seedItem = new SeedItem(seedType);
        ItemStack seedItemStack = seedItem.getItemStack(amount);
        if (seedItemStack == null) return;

        player.getInventory().addItem(seedItemStack);
        sender.sendMessage(MiniMessage.miniMessage().deserialize(configuration.getMessages().get("give")
                        .replace("%player%", player.getName())));

        player.sendMessage(MiniMessage.miniMessage().deserialize(configuration.getMessages().get("given")));
    }
}
