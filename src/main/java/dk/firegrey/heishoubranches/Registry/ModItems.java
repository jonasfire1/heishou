package dk.firegrey.heishoubranches.Registry;

import dk.firegrey.heishoubranches.Heishou;
import net.minecraft.world.item.BlockItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;

public class ModItems {

    public static final DeferredItem<BlockItem> SPAMTON_BLOCK_ITEM =
            Heishou.ITEMS.registerSimpleBlockItem(
                    "spamton_block",
                    ModBlock.SPAMTON_BLOCK
            );

    public static void register(IEventBus eventBus) {
    }
}