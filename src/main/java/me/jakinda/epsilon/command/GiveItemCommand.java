package me.jakinda.epsilon.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import me.jakinda.epsilon.item.CustomItem;
import me.jakinda.epsilon.item.CustomItemRegistry;
import me.jakinda.epsilon.util.Text;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveItemCommand {

    public static LiteralCommandNode<CommandSourceStack> createCommand() {
        return Commands.literal("giveitem")
                .then(Commands.argument("item", ArgumentTypes.namespacedKey())
                        .suggests((ctx, builder) -> {
                            String input = builder.getRemaining().toLowerCase();
                            CustomItemRegistry.getIds().forEach(key -> {
                                String id = key.split(":")[1];

                                if (key.toLowerCase().startsWith(input) || id.toLowerCase().startsWith(input)) {
                                    builder.suggest(key);
                                }
                            });
                            return builder.buildFuture();
                        })
                        .executes(GiveItemCommand::execute))
                .build();
    }

    private static int execute(CommandContext<CommandSourceStack> ctx) {
        if (!(ctx.getSource().getSender() instanceof Player player)) {
            ctx.getSource().getSender().sendMessage(
                    Text.of("<red>Only players can use this command!</red>")
            );
            return Command.SINGLE_SUCCESS;
        }

        NamespacedKey key = ctx.getArgument("item", NamespacedKey.class);
        String fullKey = key.getNamespace().equals("minecraft")
                ? "epsilon:" + key.getKey()
                : key.asString();
        CustomItem customItem = CustomItemRegistry.get(fullKey);

        if (customItem == null) {
            player.sendMessage(Text.of(
                    "<red>Couldn't find item: <yellow><key></yellow></red>",
                    "key", fullKey
            ));
            return Command.SINGLE_SUCCESS;
        }

        ItemStack item = customItem.getItem();
        player.getInventory().addItem(item);
        player.sendMessage(Text.of(
                "<green>Gave item: <item></green>",
                "item", item.effectiveName()
        ));
        return Command.SINGLE_SUCCESS;
    }
}

