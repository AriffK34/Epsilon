package me.jakinda.epsilon;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import me.jakinda.epsilon.block.CustomBlockListener;
import me.jakinda.epsilon.block.blocks.TestBlock;
import me.jakinda.epsilon.command.GiveItemCommand;
import me.jakinda.epsilon.item.CustomItemListener;
import me.jakinda.epsilon.item.items.TestItem;

public class Registrar {

    private final Epsilon plugin;
    private final AddonLoader addonLoader;

    public Registrar(Epsilon plugin) {
        this.plugin = plugin;
        this.addonLoader = new AddonLoader(plugin);
    }

    private void items() {
        new TestItem();
        new TestItem();
    }

    private void blocks() {
        new TestBlock();
    }

    private void commands() {
        plugin.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            event.registrar().register(GiveItemCommand.createCommand(), "Custom item verir");
        });
    }

    private void events() {
        new CustomItemListener(plugin);
        new CustomBlockListener(plugin);
    }

    public void unRegister() {
        addonLoader.unloadAddons();
    }

    public void register() {
        addonLoader.loadAddons();
        items();
        blocks();
        commands();
        events();
    }
}
