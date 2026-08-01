package dk.firegrey.heishoubranches.Provenance.Powers.Active;

import dk.firegrey.heishoubranches.Heishou;
import dk.firegrey.heishoubranches.Provenance.Powers.Passive.LeashHolderPower;
import dk.firegrey.heishoubranches.Provenance.Powers.abstraction.ActivePower;
import dk.firegrey.heishoubranches.Provenance.Powers.abstraction.Power;
import dk.firegrey.heishoubranches.Provenance.ProvenanceAbstract;
import dk.firegrey.heishoubranches.Provenance.ProvenanceManager;
import dk.firegrey.heishoubranches.Provenance.Provenances.Wu;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import javax.print.attribute.Attribute;

public class LeashHolder extends ActivePower {
    public ProvenanceAbstract leashholder = null;
    ProvenanceAbstract previusLeashholder = null;
    Wu powerowner;
    private static final ResourceLocation MODIFIER_ID_ATTACK_SPEED =
            ResourceLocation.fromNamespaceAndPath("heishoubranches", "wu_attack_speed");
    private static final ResourceLocation MODIFIER_ID_MINING_SPEED =
            ResourceLocation.fromNamespaceAndPath("heishoubranches", "wu_mining_speed");

    @Override
    public void activate(Player player) {
        powerowner = (Wu) ProvenanceManager.get(player);
        double range = 10.0;
        Vec3 start = player.getEyePosition();
        Vec3 direction = player.getLookAngle();
        // Stop the entity ray at the first solid block.
        BlockHitResult blockHit = player.level().clip(new ClipContext(
                start,
                start.add(direction.scale(range)),
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                player
        ));

        Vec3 end = blockHit.getType() == BlockHitResult.Type.MISS
                ? start.add(direction.scale(range))
                : blockHit.getLocation();

        EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(
                player,
                start,
                end,
                player.getBoundingBox().expandTowards(direction.scale(range)).inflate(1.0),
                entity -> entity instanceof Player target
                        && target != player
                        && target.isAlive()
                        && !target.isSpectator(),
                range * range
        );

        leashholder = entityHit == null ? null : ProvenanceManager.get((Player) entityHit.getEntity());
        if (leashholder == null) {
            rampage(player);
        } else leashed(player); {
            AttributeInstance AttackSpeedAttribute = player.getAttribute(Attributes.ATTACK_SPEED);

            if (AttackSpeedAttribute != null) {
                AttackSpeedAttribute.removeModifier(MODIFIER_ID_ATTACK_SPEED);
            }

            AttributeInstance MiningSpeedAttribute = player.getAttribute(Attributes.BLOCK_BREAK_SPEED);

            if (MiningSpeedAttribute != null) {
                MiningSpeedAttribute.removeModifier(MODIFIER_ID_MINING_SPEED);
            }

            if (previusLeashholder != null) {
                previusLeashholder.powers.removeIf(item -> item instanceof LeashHolderPower);
                previusLeashholder.onHitPowers.removeIf(item -> item instanceof LeashHolderPower);
                previusLeashholder.Highlights.remove(leashholder);
            }
        }
        previusLeashholder = leashholder;
    }

    private void rampage(Player player) {
        AttributeInstance AttackSpeedAttribute = player.getAttribute(Attributes.ATTACK_SPEED);

        if (AttackSpeedAttribute != null) {
            AttributeModifier modifier = AttackSpeedAttribute.getModifier(MODIFIER_ID_ATTACK_SPEED);

            if (modifier == null || modifier.amount() != 0.5) {
                if (modifier != null) {
                    AttackSpeedAttribute.removeModifier(MODIFIER_ID_ATTACK_SPEED);
                }

                AttackSpeedAttribute.addPermanentModifier(
                        new AttributeModifier(
                                MODIFIER_ID_ATTACK_SPEED,
                                0.5,
                                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                        )
                );
            }
        }

        AttributeInstance MiningSpeedAttribute = player.getAttribute(Attributes.BLOCK_BREAK_SPEED);

        if (MiningSpeedAttribute != null) {
            AttributeModifier modifier = MiningSpeedAttribute.getModifier(MODIFIER_ID_MINING_SPEED);

            if (modifier == null || modifier.amount() != 0.5) {
                if (modifier != null) {
                    MiningSpeedAttribute.removeModifier(MODIFIER_ID_MINING_SPEED);
                }

                MiningSpeedAttribute.addPermanentModifier(
                        new AttributeModifier(
                                MODIFIER_ID_MINING_SPEED,
                                0.5,
                                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                        )
                );
            }
        }


    }

    private void leashed(Player player) {
        LeashHolderPower leashHolderPower = new LeashHolderPower(powerowner);
        leashholder.powers.add(leashHolderPower);
        leashholder.Highlights.add(player);
        leashholder.onHitPowers.add(leashHolderPower);
    }

    public boolean IsLeashed() {
        return leashholder != null;
    }

    @Override
    public void onAdded(Player player) {
        rampage(player);
    }

    @Override
    public boolean canActivate(Player player) {
        return true;
    }

    @Override
    public int current() {
        return leashholder != null ? 1 : 0;
    }

    @Override
    public int max() {
        return 1;
    }

    @Override
    public ResourceLocation back() {
        return ResourceLocation.fromNamespaceAndPath(Heishou.MODID, "textures/provenancebars/wu_leashholder_back.png");
    }

    @Override
    public ResourceLocation front() {
        return ResourceLocation.fromNamespaceAndPath(Heishou.MODID, "textures/provenancebars/wu_leashholder_front.png");
    }
}
