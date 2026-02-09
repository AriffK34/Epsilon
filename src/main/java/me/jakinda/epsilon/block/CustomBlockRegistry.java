package me.jakinda.epsilon.block;

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
        if (cBlocks.containsKey(cBlock.getId())) {
            throw new IllegalArgumentException("Custom block with ID " + cBlock.getId() + " is already registered.");
        }
        cBlocks.put(cBlock.getId(), cBlock);
    }

    public static CustomBlock get(String id) {
        return cBlocks.get(id);
    }

    public static Set<String> getIds() {
        return cBlocks.keySet();
    }

    @Nullable
    public static CustomBlock identify(@Nullable Block block) {
        if (block == null) return null;
        if (!(block.getState() instanceof Skull skull)) return null;

        String id = skull.getPersistentDataContainer().get(Keys.CUSTOM_BLOCK, PersistentDataType.STRING);

        if (id == null) return null;

        return cBlocks.get(id);
    }

    public static void clear() {
        cBlocks.clear();
    }
}
