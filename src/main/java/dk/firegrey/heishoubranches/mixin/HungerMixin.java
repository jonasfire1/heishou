package dk.firegrey.heishoubranches.mixin;

import dk.firegrey.heishoubranches.Provenance.ProvenanceManager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class HungerMixin {

    @Inject(
            method = "causeFoodExhaustion",
            at = @At("HEAD"),
            cancellable = true
    )
    private void modifyExhaustion(float exhaustion, CallbackInfo ci) {
        Player player = (Player)(Object)this;

        var provenance = ProvenanceManager.get(player);

        if (provenance != null) {
            ci.cancel();
            player.getFoodData().addExhaustion(
                    exhaustion * provenance.hungerMultiplier()
            );
        }
    }
}