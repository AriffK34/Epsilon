package me.jakinda.epsilon.item.components;

import org.bukkit.event.block.BlockPlaceEvent;

public interface Placeable {

    void onPlace(BlockPlaceEvent event);
}
