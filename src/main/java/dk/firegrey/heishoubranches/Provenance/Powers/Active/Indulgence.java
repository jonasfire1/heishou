package dk.firegrey.heishoubranches.Provenance.Powers.Active;

import dk.firegrey.heishoubranches.Provenance.Powers.abstraction.ActivePower;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class Indulgence extends ActivePower {
    public int duration = 0;
    final int maxcooldown = 2400;
    private static final ResourceLocation MODIFIER_ID_SPEED = ResourceLocation.fromNamespaceAndPath("heishoubranches", "indulgence_speed");
    private static final ResourceLocation MODIFIER_ID_ATTACKSPEED = ResourceLocation.fromNamespaceAndPath("heishoubranches", "indulgence_attacjspeed");
    @Override
    public void activate(Player player) {
        if (!canActivate(player)) {
            return;
        }
        duration = 400;
        cooldown = maxcooldown;
        AttributeInstance attribute = player.getAttribute(Attributes.MOVEMENT_SPEED);

        if (attribute != null) {
            AttributeModifier modifier = attribute.getModifier(MODIFIER_ID_SPEED);

            if (modifier == null || modifier.amount() != 0.2) {
                if (modifier != null) {
                    attribute.removeModifier(MODIFIER_ID_SPEED);
                }

                attribute.addPermanentModifier(
                        new AttributeModifier(
                                MODIFIER_ID_SPEED,
                                0.2,
                                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                        )
                );
            }
        }
        attribute = player.getAttribute(Attributes.ATTACK_SPEED);

        if (attribute != null) {
            AttributeModifier modifier = attribute.getModifier(MODIFIER_ID_ATTACKSPEED);

            if (modifier == null || modifier.amount() != 0.2) {
                if (modifier != null) {
                    attribute.removeModifier(MODIFIER_ID_ATTACKSPEED);
                }

                attribute.addPermanentModifier(
                        new AttributeModifier(
                                MODIFIER_ID_ATTACKSPEED,
                                0.2,
                                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                        )
                );
            }
        }
    }

    @Override
    public boolean canActivate(Player player) {
        return cooldown <= 0 && duration <= 0;
    }

    @Override
    public int current() {
        return cooldown;
    }

    @Override
    public int max() {
        return maxcooldown;
    }

    @Override
    public ResourceLocation back() {
        return null;
    }

    @Override
    public ResourceLocation front() {
        return null;
    }

    @Override
    public void tick(Player player) {
        cooldown--;
        if (duration > 0) {
            duration--;
            if (duration == 0) {
                AttributeInstance attribute = player.getAttribute(Attributes.MOVEMENT_SPEED);

                if (attribute != null) {
                    attribute.removeModifier(MODIFIER_ID_SPEED);
                }
                attribute = player.getAttribute((Attributes.ATTACK_SPEED));
                if (attribute != null) {
                    attribute.removeModifier(MODIFIER_ID_SPEED);
                }
            }
        }
    }

    @Override
    public void onRemoved(Player player) {
        if (duration > 0) {
            duration = 0;
            tick(player);
        }
    }

    @Override
    public void onHit(LivingEntity attacker, LivingEntity victim) {
        if (duration > 0) {
            victim.addEffect(new MobEffectInstance(
                    MobEffects.MOVEMENT_SLOWDOWN,
                    20, // 1 seconds (20 ticks/sec)
                    0
            ));// Slowness I)
        }
    }
}
