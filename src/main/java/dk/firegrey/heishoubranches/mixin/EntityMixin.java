package dk.firegrey.heishoubranches.mixin;

import dk.firegrey.heishoubranches.client.HeishouClient;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static dk.firegrey.heishoubranches.client.HeishouClient.markedEntities;

@Mixin(Entity.class) // not LivingEntity.class
public abstract class EntityMixin {
    @Inject(method = "isCurrentlyGlowing", at = @At("HEAD"), cancellable = true)
    private void provenance$forceGlow(CallbackInfoReturnable<Boolean> cir) {
        Entity entity = (Entity) (Object) this;

        if (HeishouClient.markedEntities.contains(entity)) {
            cir.setReturnValue(true);
        }
    }
}