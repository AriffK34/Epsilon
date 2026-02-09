package me.jakinda.epsilon.item.items;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.registry.keys.DataComponentTypeKeys;
import me.jakinda.epsilon.item.CustomItem;
import me.jakinda.epsilon.item.components.RightClickable;
import me.jakinda.epsilon.util.Text;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

public class TestItem extends CustomItem implements RightClickable {

    public TestItem() {
        super(Material.STICK, item -> {
            item.setData(DataComponentTypes.ITEM_NAME, Text.of("<green>Test Item</green>"));

        });
    }

    @Override
    public void onRightClick(PlayerInteractEvent event) {
        event.getPlayer().sendMessage("Test");
    }
}
