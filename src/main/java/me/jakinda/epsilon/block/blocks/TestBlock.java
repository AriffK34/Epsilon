package me.jakinda.epsilon.block.blocks;

import io.papermc.paper.datacomponent.DataComponentTypes;
import me.jakinda.epsilon.block.CustomBlock;
import me.jakinda.epsilon.block.components.Breakable;
import me.jakinda.epsilon.block.components.Interactable;
import me.jakinda.epsilon.util.Text;
import org.bukkit.Location;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class TestBlock extends CustomBlock implements Breakable, Interactable {

    public TestBlock() {
        super(item -> {
            item.setData(DataComponentTypes.ITEM_NAME, Text.of("<green>Test Block</green>"));
        });
    }

    @Override
    public String getTextureId() {
        return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWJjMTIzIn19fQ==";
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.OFF_HAND) return;
        event.getPlayer().sendMessage("Test");
    }

    @Override
    public void onBreak(BlockBreakEvent event) {
        event.setDropItems(false);
        Location loc = event.getBlock().getLocation().add(.5,.5,.5);
        for (ItemStack drop : getDrops())
            loc.getWorld().dropItemNaturally(loc, drop);
    }

    @Override
    public List<ItemStack> getDrops() {
        return List.of(getItem().getItem());
    }
}
