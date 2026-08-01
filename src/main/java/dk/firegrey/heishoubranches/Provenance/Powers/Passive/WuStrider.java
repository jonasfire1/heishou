package dk.firegrey.heishoubranches.Provenance.Powers.Passive;

import dk.firegrey.heishoubranches.Provenance.Powers.abstraction.Power;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class WuStrider extends Power {
    private static final ResourceLocation MODIFIER_ID_SPEED = ResourceLocation.fromNamespaceAndPath("heishoubranches", "wu_speed");
    private static final ResourceLocation MODIFIER_ID_STEP_HEIGHT =
            ResourceLocation.fromNamespaceAndPath("heishoubranches", "wu_step_height");
    private static final ResourceLocation MODIFIER_ID_MAX_HEALTH =
            ResourceLocation.fromNamespaceAndPath("heishoubranches", "wu_max_health");

    @Override
    public void onAdded(Player player) {
        AttributeInstance speedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);

        if (speedAttribute != null) {
            AttributeModifier modifier = speedAttribute.getModifier(MODIFIER_ID_SPEED);

            if (modifier == null || modifier.amount() != 0.7) {
                if (modifier != null) {
                    speedAttribute.removeModifier(MODIFIER_ID_SPEED);
                }

                speedAttribute.addPermanentModifier(
                        new AttributeModifier(
                                MODIFIER_ID_SPEED,
                                0.7,
                                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                        )
                );
            }
        }

        AttributeInstance stepHeightAttribute = player.getAttribute(Attributes.STEP_HEIGHT);

        if (stepHeightAttribute != null) {
            AttributeModifier modifier = stepHeightAttribute.getModifier(MODIFIER_ID_STEP_HEIGHT);

            if (modifier == null || modifier.amount() != 1) {
                if (modifier != null) {
                    stepHeightAttribute.removeModifier(MODIFIER_ID_STEP_HEIGHT);
                }

                stepHeightAttribute.addPermanentModifier(
                        new AttributeModifier(
                                MODIFIER_ID_STEP_HEIGHT,
                                1,
                                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                        )
                );
            }
        }

        AttributeInstance maxHealthAttribute = player.getAttribute(Attributes.MAX_HEALTH);

        if (maxHealthAttribute != null) {
            AttributeModifier modifier = maxHealthAttribute.getModifier(MODIFIER_ID_MAX_HEALTH);

            if (modifier == null || modifier.amount() != 8) {
                if (modifier != null) {
                    maxHealthAttribute.removeModifier(MODIFIER_ID_MAX_HEALTH);
                }

                maxHealthAttribute.addPermanentModifier(
                        new AttributeModifier(
                                MODIFIER_ID_MAX_HEALTH,
                                8,
                                AttributeModifier.Operation.ADD_VALUE
                        )
                );
            }
        }
    }

    @Override
    public void onRemoved(Player player) {
        AttributeInstance speedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);

        if (speedAttribute != null) {
            speedAttribute.removeModifier(MODIFIER_ID_SPEED);
        }

        AttributeInstance stepHeightAttribute = player.getAttribute(Attributes.STEP_HEIGHT);

        if (stepHeightAttribute != null) {
            stepHeightAttribute.removeModifier(MODIFIER_ID_STEP_HEIGHT);
        }

        AttributeInstance maxHealthAttribute = player.getAttribute(Attributes.MAX_HEALTH);

        if (maxHealthAttribute != null) {
            maxHealthAttribute.removeModifier(MODIFIER_ID_MAX_HEALTH);
        }
    }

}
