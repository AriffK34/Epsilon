package me.jakinda.epsilon.block;

import me.jakinda.epsilon.item.CustomBlockItem;
import me.jakinda.epsilon.util.Text;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public abstract class CustomBlock {

    private final NamespacedKey key;
    private final CustomBlockItem cItem;

    public CustomBlock(String id, Consumer<ItemStack> itemEditor) {
        String blockId = id == null ? Text.toSnakeCase(this.getClass().getSimpleName()) : id;
        String namespace = Text.extractNamespace(this.getClass());

        this.key = new NamespacedKey(namespace, blockId);
        this.cItem = new CustomBlockItem(this, itemEditor);

        CustomBlockRegistry.register(this);
    }

    public CustomBlock(Consumer<ItemStack> itemEditor) {
        this(null, itemEditor);
    }

    public NamespacedKey getKey() {
        return key;
    }

    public CustomBlockItem getItem() {
        return cItem;
    }

    public abstract String getTextureId();

}
