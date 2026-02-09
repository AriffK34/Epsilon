package me.jakinda.epsilon.block.components;

import org.bukkit.event.player.PlayerInteractEvent;

public interface Interactable {

    void onInteract(PlayerInteractEvent event);
}
