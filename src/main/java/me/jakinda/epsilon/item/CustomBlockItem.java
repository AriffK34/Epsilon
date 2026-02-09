package me.jakinda.epsilon.item;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import me.jakinda.epsilon.Keys;
import me.jakinda.epsilon.block.CustomBlock;
import me.jakinda.epsilon.item.components.Placeable;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;
import java.util.function.Consumer;

public class CustomBlockItem extends CustomItem implements Placeable {

    private final CustomBlock cBlock;

    public CustomBlockItem(CustomBlock cBlock, Consumer<ItemStack> itemEditor) {
        super(cBlock.getId(), Material.PLAYER_HEAD, itemEditor);
        this.cBlock = cBlock;
    }


    @Override
    public void onPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        if (!(block.getState() instanceof Skull skull)) return;

        skull.getPersistentDataContainer().set(Keys.CUSTOM_BLOCK, PersistentDataType.STRING, getId());
        PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
        profile.setProperty(new ProfileProperty("textures", cBlock.getTextureId()));
        skull.setOwnerProfile(profile);
        skull.update();
    }

    public CustomBlock getBlock() {
        return cBlock;
    }
}
