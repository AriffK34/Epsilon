package me.jakinda.epsilon.item;

import me.jakinda.epsilon.Keys;
import me.jakinda.epsilon.item.components.Craftable;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Set;

public class CustomItemRegistry {

    private final static HashMap<String, CustomItem> cItems = new HashMap<>();

    public static void register(CustomItem cItem) {
        String key = cItem.getKey().asString();

        if (cItems.containsKey(key))
            throw new IllegalArgumentException("Custom item " + key + " is already registered!");

        if (cItem instanceof Craftable craftable)
            Bukkit.addRecipe(craftable.getRecipe());

        cItems.put(key, cItem);
    }

    public static CustomItem get(String query) {
        return query.contains(":") ? cItems.get(query) : cItems.get("epsilon:" + query);
    }

    public static CustomItem identify(ItemStack item) {
        if (item == null) return null;
        
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;
        
        if (meta.getPersistentDataContainer().has(Keys.CUSTOM_ITEM)) {
            String key = meta.getPersistentDataContainer().get(Keys.CUSTOM_ITEM, PersistentDataType.STRING);
            return cItems.get(key);
        }
        
        return null;
    }

    public static void clear() {
        for (CustomItem cItem : cItems.values())
            if (cItem instanceof Craftable craftable)
                Bukkit.removeRecipe(((Keyed)craftable.getRecipe()).getKey());

        cItems.clear();
    }

    public static Set<String> getIds() {
        return cItems.keySet();
    }
}


