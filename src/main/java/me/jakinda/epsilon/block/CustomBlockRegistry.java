package me.jakinda.epsilon.block;

import me.jakinda.epsilon.Epsilon;
import me.jakinda.epsilon.Keys;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Set;

public class CustomBlockRegistry {
    
    private static final HashMap<String, CustomBlock> cBlocks = new HashMap<>();
    
    public static void register(CustomBlock cBlock) {
        String key = cBlock.getKey().asString();

        if (cBlocks.containsKey(key)) {
            Epsilon.getInstance().getLogger().warning("Custom block " + key + " is already registered, skipping.");
            return;
        }

        cBlocks.put(key, cBlock);
    }

    public static CustomBlock get(String query) {
        return query.contains(":") ? cBlocks.get(query) : cBlocks.get("epsilon:" + query);
    }

    public static Set<String> getIds() {
        return cBlocks.keySet();
    }

    @Nullable
    public static CustomBlock identify(@Nullable Block block) {
        if (block == null) return null;
        if (!(block.getState() instanceof Skull skull)) return null;

        String key = skull.getPersistentDataContainer().get(Keys.CUSTOM_BLOCK, PersistentDataType.STRING);

        if (key == null) return null;

        return cBlocks.get(key);
    }

    public static void clear() {
        cBlocks.clear();
    }
}
