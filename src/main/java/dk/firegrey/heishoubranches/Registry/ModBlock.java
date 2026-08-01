package dk.firegrey.heishoubranches.Registry;

import dk.firegrey.heishoubranches.Heishou;
import dk.firegrey.heishoubranches.custom_properties.spamton_block;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlock {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(Heishou.MODID);

    public static final DeferredBlock<Block> SPAMTON_BLOCK =
            BLOCKS.register(
                    "spamton_block",
                    () -> new spamton_block(
                            BlockBehaviour.Properties.of()
                                    .strength(5.0F, 6.0F)
                                    .sound(SoundType.METAL)
                                    .requiresCorrectToolForDrops()
                    )
            );

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}