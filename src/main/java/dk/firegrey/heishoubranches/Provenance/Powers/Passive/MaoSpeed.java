package dk.firegrey.heishoubranches.Provenance.Powers.Passive;

import dk.firegrey.heishoubranches.Provenance.Powers.abstraction.Power;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class MaoSpeed extends Power {
    private static final ResourceLocation MODIFIER_ID =
            ResourceLocation.fromNamespaceAndPath("heishoubranches", "mao_speed");

    @Override
    public void onAdded(Player player) {
        AttributeInstance attribute = player.getAttribute(Attributes.MOVEMENT_SPEED);

        if (attribute != null) {
            AttributeModifier modifier = attribute.getModifier(MODIFIER_ID);

            if (modifier == null || modifier.amount() != 0.5) {
                if (modifier != null) {
                    attribute.removeModifier(MODIFIER_ID);
                }

                attribute.addPermanentModifier(
                        new AttributeModifier(
                                MODIFIER_ID,
                                0.5,
                                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                        )
                );
            }
        }
    }

    @Override
    public void onRemoved(Player player) {
        AttributeInstance attribute = player.getAttribute(Attributes.MOVEMENT_SPEED);

        if (attribute != null) {
            attribute.removeModifier(MODIFIER_ID);
        }
    }

}
