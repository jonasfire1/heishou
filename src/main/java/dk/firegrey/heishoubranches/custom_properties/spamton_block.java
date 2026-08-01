package dk.firegrey.heishoubranches.custom_properties;

import dk.firegrey.heishoubranches.Registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class spamton_block extends Block {

    public spamton_block(Properties properties) {
        super(properties);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {

        if (!level.isClientSide && entity instanceof Player player) {
            player.addItem(new ItemStack(ModItems.SPAMTON_BLOCK_ITEM.get()));
        }

        super.stepOn(level, pos, state, entity);
    }
}