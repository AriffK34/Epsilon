package me.jakinda.epsilon.item;

import me.jakinda.epsilon.Keys;
import me.jakinda.epsilon.util.Text;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.function.Consumer;

public abstract class CustomItem {

//    private final String id;
    private final ItemStack item;
    private final NamespacedKey key;

    public CustomItem(String id, Material material, Consumer<ItemStack> itemEditor) {
        this(null, id, material, itemEditor);
    }

    public CustomItem(Material material, Consumer<ItemStack> itemEditor) {
        this(null, material, itemEditor);
    }

    public CustomItem(String id, Material material) {
        this(id, material, null);
    }

    public CustomItem(Material material) {
        this(null, material, null);
    }

    protected CustomItem(String namespace, String id, Material material, Consumer<ItemStack> itemEditor) {
        String itemId = id == null || id.isEmpty() ? Text.toSnakeCase(this.getClass().getSimpleName()) : id;
        String ns = namespace != null ? namespace : Text.extractNamespace(this.getClass());

        this.key = new NamespacedKey(ns, itemId);
        this.item = new ItemStack(material);

        item.editMeta(meta -> {
            meta.getPersistentDataContainer().set(Keys.CUSTOM_ITEM, PersistentDataType.STRING, key.asString());
        });

        if (itemEditor != null) itemEditor.accept(item);

        CustomItemRegistry.register(this);
    }

    public ItemStack getItem() {
        return item.clone();
    }

    public NamespacedKey getKey() {
        return key;
    }
}