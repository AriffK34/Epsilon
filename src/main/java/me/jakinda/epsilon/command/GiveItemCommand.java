package me.jakinda.epsilon.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import me.jakinda.epsilon.item.CustomItem;
import me.jakinda.epsilon.item.CustomItemRegistry;
import me.jakinda.epsilon.util.Text;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveItemCommand {

    public static LiteralCommandNode<CommandSourceStack> createCommand() {
        return Commands.literal("giveitem")
                .then(Commands.argument("item_id", StringArgumentType.word())
                        .suggests((ctx, builder) -> {
                            CustomItemRegistry.getIds().forEach(builder::suggest);
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

        String itemId = StringArgumentType.getString(ctx, "item_id");
        CustomItem customItem = CustomItemRegistry.get(itemId);

        if (customItem == null) {
            player.sendMessage(Text.of(
                    "<red>Couldn't find item: <yellow><id></yellow></red>",
                    "id", itemId
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

