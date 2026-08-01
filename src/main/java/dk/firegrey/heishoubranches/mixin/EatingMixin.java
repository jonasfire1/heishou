package dk.firegrey.heishoubranches.mixin;

import dk.firegrey.heishoubranches.Provenance.ModTag;
import dk.firegrey.heishoubranches.Provenance.ProvenanceManager;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class EatingMixin {

    @Inject(
            method = "startUsingItem",
            at = @At("HEAD"),
            cancellable = true
    )
    private void handleItemUse(InteractionHand hand, CallbackInfo ci) {

        if (!((Object) this instanceof Player player)) {
            return;
        }

        var provenance = ProvenanceManager.get(player);

        if (provenance == null) {
            return;
        }
        ItemStack stack = player.getItemInHand(hand);
        if (stack.get(DataComponents.FOOD) != null && !canEat(provenance.dietTag(), stack)) {
            ci.cancel();
        }


    }

    private boolean canEat(String diet, ItemStack stack) {
        switch (diet) {
            case "vegetarian":
                return !stack.is(ModTag.MEAT);

            case "meat":
                return stack.is(ModTag.MEAT);

            case "none":
                return false;

            default:
                return true;
        }
    }
}
