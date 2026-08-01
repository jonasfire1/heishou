package dk.firegrey.heishoubranches.mixin;

import dk.firegrey.heishoubranches.Provenance.ProvenanceManager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FoodData.class)
public abstract class FoodDataMixin {

    @Redirect(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/food/FoodData;addExhaustion(F)V",
                    ordinal = 0
            )
    )
    private void modifyFastRegenExhaustion(FoodData foodData, float exhaustion, Player player) {
        foodData.addExhaustion(applyProvenanceMultiplier(exhaustion, player));
    }

    @Redirect(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/food/FoodData;addExhaustion(F)V",
                    ordinal = 1
            )
    )
    private void modifySlowRegenExhaustion(FoodData foodData, float exhaustion, Player player) {
        foodData.addExhaustion(applyProvenanceMultiplier(exhaustion, player));
    }

    @Redirect(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;heal(F)V",
                    ordinal = 0
            )
    )
    private void allowFastNaturalRegen(Player healedPlayer, float amount) {
        if (allowNaturalRegen(healedPlayer)) {
            healedPlayer.heal(amount);
        }
    }

    @Redirect(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;heal(F)V",
                    ordinal = 1
            )
    )
    private void allowSlowNaturalRegen(Player healedPlayer, float amount) {
        if (allowNaturalRegen(healedPlayer)) {
            healedPlayer.heal(amount);
        }
    }

    private float applyProvenanceMultiplier(float exhaustion, Player player) {
        if (!allowNaturalRegen(player)) {
            return 0f;
        }

        var provenance = ProvenanceManager.get(player);
        if (provenance != null) {
            return exhaustion * provenance.hungerMultiplier();
        }
        return exhaustion;
    }

    private boolean allowNaturalRegen(Player player) {
        var provenance = ProvenanceManager.get(player);
        return provenance == null || provenance.NaturalRegen();
    }
}
