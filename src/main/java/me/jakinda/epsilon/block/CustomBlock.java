package me.jakinda.epsilon.block;

import me.jakinda.epsilon.item.CustomBlockItem;
import me.jakinda.epsilon.util.Text;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.Consumer;

public abstract class CustomBlock {

    private final String id;
    private final NamespacedKey key;
    private final CustomBlockItem cItem;

    public CustomBlock(String id, Consumer<ItemStack> itemEditor) {
        this.id = id == null ? Text.toSnakeCase(this.getClass().getSimpleName()) : id;
        this.key = new NamespacedKey("epsilon", this.id);
        CustomBlockRegistry.register(this);

        this.cItem = new CustomBlockItem(this, itemEditor);
    }

    public CustomBlock(Consumer<ItemStack> itemEditor) {
        this(null, itemEditor);
    }

    public String getId() {
        return id;
    }

    public NamespacedKey getKey() {
        return key;
    }

    public CustomBlockItem getItem() {
        return cItem;
    }

    public abstract String getTextureId();

}
