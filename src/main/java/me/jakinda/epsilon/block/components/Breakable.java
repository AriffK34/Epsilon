package me.jakinda.epsilon.block.components;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface Breakable {

    default void onBreak(BlockBreakEvent event) {}

    default List<ItemStack> getDrops() {
        return List.of();
    }
}
