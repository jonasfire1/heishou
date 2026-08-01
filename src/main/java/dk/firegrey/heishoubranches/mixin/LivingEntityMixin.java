package dk.firegrey.heishoubranches.mixin;

import dk.firegrey.heishoubranches.Provenance.ProvenanceManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @ModifyArg(
            method = "travel",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;moveRelative(FLnet/minecraft/world/phys/Vec3;)V"
            ),
            index = 0
    )
    private float modifySwimSpeed(float speed) {
        LivingEntity entity = (LivingEntity)(Object)this;

        if (entity.isInWater() && entity instanceof Player) {
            return speed * (ProvenanceManager.get((Player) entity)).SwimMultiplier();
        }

        return speed;
    }
}