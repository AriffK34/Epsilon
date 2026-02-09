package me.jakinda.epsilon.item.components;

import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public interface RightClickable {

    default void onRightClick(PlayerInteractEvent event) {}

    default void onRightClickEntity(PlayerInteractEntityEvent event) {}
}
