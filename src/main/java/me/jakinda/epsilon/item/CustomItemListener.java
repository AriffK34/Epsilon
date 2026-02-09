package me.jakinda.epsilon.item;

import me.jakinda.epsilon.Epsilon;
import me.jakinda.epsilon.item.components.LeftClickable;
import me.jakinda.epsilon.item.components.Placeable;
import me.jakinda.epsilon.item.components.RightClickable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CustomItemListener implements Listener {

    public CustomItemListener(Epsilon plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (!event.getAction().isRightClick()) return;
        CustomItem cItem = CustomItemRegistry.identify(item);
        if (cItem instanceof RightClickable r) r.onRightClick(event);
    }

    @EventHandler
    public void onRightClickEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        CustomItem cItem = CustomItemRegistry.identify(item);
        if (cItem instanceof RightClickable r) r.onRightClickEntity(event);
    }

    @EventHandler
    public void onLeftClick(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (!event.getAction().isLeftClick()) return;
        CustomItem cItem = CustomItemRegistry.identify(item);
        if (cItem instanceof LeftClickable l) l.onLeftClick(event);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        CustomItem cItem = CustomItemRegistry.identify(item);
        if (cItem instanceof Placeable p) p.onPlace(event);
    }
}
