package me.jakinda.epsilon;


import me.jakinda.epsilon.block.CustomBlockRegistry;
import me.jakinda.epsilon.item.CustomItemRegistry;
import org.bukkit.plugin.java.JavaPlugin;

public final class Epsilon extends JavaPlugin {

    private static Epsilon instance;
    private Registrar registrar;

    @Override
    public void onEnable() {
        instance = this;
        registrar = new Registrar(this);
    }

    @Override
    public void onDisable() {
        CustomItemRegistry.clear();
        CustomBlockRegistry.clear();
        registrar.unRegister();
    }

    public static Epsilon getInstance() {
        return instance;
    }
}
