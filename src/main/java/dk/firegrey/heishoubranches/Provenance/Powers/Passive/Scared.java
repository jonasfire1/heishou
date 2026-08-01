package dk.firegrey.heishoubranches.Provenance.Powers.Passive;

import dk.firegrey.heishoubranches.Provenance.Powers.abstraction.Power;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class Scared extends Power {
    private static final ResourceLocation MODIFIER_ID =
            ResourceLocation.fromNamespaceAndPath("heishoubranches", "mao_speed");

    @Override
    public void onAdded(Player player) {
        AttributeInstance attributeSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (attributeSpeed != null) {
            AttributeModifier modifier = attributeSpeed.getModifier(MODIFIER_ID);

            if (modifier == null || modifier.amount() != 0.1) {
                if (modifier != null) {
                    attributeSpeed.removeModifier(MODIFIER_ID);
                }

                attributeSpeed.addPermanentModifier(
                        new AttributeModifier(
                                MODIFIER_ID,
                                0.1,
                                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                        )
                );
            }
        }
        AttributeInstance attributeHealth = player.getAttribute(Attributes.MAX_HEALTH);
        if (attributeHealth != null) {
            AttributeModifier modifier = attributeHealth.getModifier(MODIFIER_ID);

            if (modifier == null || modifier.amount() != -0.1) {
                if (modifier != null) {
                    attributeHealth.removeModifier(MODIFIER_ID);
                }

                attributeHealth.addPermanentModifier(
                        new AttributeModifier(
                                MODIFIER_ID,
                                -0.1,
                                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                        )
                );
            }
        }
    }

    @Override
    public void onRemoved(Player player) {
        AttributeInstance attributeSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);
        AttributeInstance attributeHealth = player.getAttribute(Attributes.MAX_HEALTH);
        if (attributeSpeed != null) {
            attributeSpeed.removeModifier(MODIFIER_ID);
        }
        if (attributeHealth != null) {
            attributeHealth.removeModifier(MODIFIER_ID);
        }
    }

}
