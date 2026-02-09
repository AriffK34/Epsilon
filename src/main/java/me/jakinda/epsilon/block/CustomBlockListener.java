package me.jakinda.epsilon.block;

import me.jakinda.epsilon.Epsilon;
import me.jakinda.epsilon.block.components.Breakable;
import me.jakinda.epsilon.block.components.Interactable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class CustomBlockListener implements Listener {

    public CustomBlockListener(Epsilon plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        CustomBlock cBlock = CustomBlockRegistry.identify(event.getClickedBlock());
        if (cBlock instanceof Interactable i) i.onInteract(event);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        CustomBlock cBlock = CustomBlockRegistry.identify(event.getBlock());
        if (cBlock instanceof Breakable b) b.onBreak(event);
    }
}
