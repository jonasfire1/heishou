package dk.firegrey.heishoubranches.Provenance.Powers.Active;

import dk.firegrey.heishoubranches.Heishou;
import dk.firegrey.heishoubranches.Provenance.Powers.abstraction.ActivePower;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class FerventBeats extends ActivePower {
    int cooldown;
    int maxCooldown = 600;
    int duration;
    private static final ResourceLocation MODIFIER_ID = ResourceLocation.fromNamespaceAndPath("heishoubranches", "ferventbeats");

    @Override
    public void activate(Player player) {
        if (!canActivate(player)) {
            return;
        }
        duration = 200;
        cooldown = maxCooldown;
        AttributeInstance attribute = player.getAttribute(Attributes.MOVEMENT_SPEED);

        if (attribute != null) {
            AttributeModifier modifier = attribute.getModifier(MODIFIER_ID);

            if (modifier == null || modifier.amount() != 0.2) {
                if (modifier != null) {
                    attribute.removeModifier(MODIFIER_ID);
                }

                attribute.addPermanentModifier(
                        new AttributeModifier(
                                MODIFIER_ID,
                                0.2,
                                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                        )
                );
            }
        }
    }

    @Override
    public void tick(Player player) {
        cooldown--;
        if (duration > 0) {
            duration--;
            if (duration == 0) {
                AttributeInstance attribute = player.getAttribute(Attributes.MOVEMENT_SPEED);

                if (attribute != null) {
                    attribute.removeModifier(MODIFIER_ID);
                }
                player.hurt(
                        player.damageSources().magic(),
                        8.0F
                );
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
    public boolean canActivate(Player player) {
        return cooldown <= 0 && duration <= 0;
    }

    public int  getDuration() {
        return duration;
    }

    @Override
    public int current() {
        return cooldown;
    }

    @Override
    public int max() {
        return maxCooldown;
    }

    @Override
    public ResourceLocation back() {
        return ResourceLocation.fromNamespaceAndPath(Heishou.MODID, "textures/provenancebars/defaultback.png");
    }

    @Override
    public ResourceLocation front() {
        return ResourceLocation.fromNamespaceAndPath(Heishou.MODID, "textures/provenancebars/librarian_fervent.png");
    }
}
