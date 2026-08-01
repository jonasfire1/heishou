package dk.firegrey.heishoubranches.mixin;

import dk.firegrey.heishoubranches.Provenance.ProvenanceManager;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class FallDamageMixin {

    @Inject(
            method = "causeFallDamage",
            at = @At("HEAD"),
            cancellable = true
    )
    private void negateFallDamage(
            float fallDistance,
            float damageMultiplier,
            DamageSource damageSource,
            CallbackInfoReturnable<Boolean> cir
    ) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (!(entity instanceof Player player)) {
            return;
        }

        var provenance = ProvenanceManager.get(player);

        if (provenance == null) {
            return;
        }

        if (provenance.negateFallDamage()) {
            cir.setReturnValue(false);
        }
    }
}