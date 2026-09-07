package dk.firegrey.heishoubranches.Provenance.Powers.Active;

import dk.firegrey.heishoubranches.Provenance.Powers.abstraction.ActivePower;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class Indulgence extends ActivePower {
    public int duration = 0;
    final int maxcooldown = 2400;
    int swings = 0;
    @Override
    public void activate(Player player) {
        if (!canActivate(player)) {
            return;
        }
        LivingEntity target = raycastLivingEntity(player);
        if (target == null) {
            return;
        }
        if (cooldown <= 0) {
            swings = 9;
        }
        if (duration <= 0) {
            duration = 400;
            cooldown = maxcooldown;
        }
        Vec3 facing = target.getViewVector(1.0F);
        Vec3 horizontalFacing = new Vec3(facing.x, 0.0D, facing.z).normalize();
        Vec3 behind = target.getBoundingBox().getCenter()
                .subtract(horizontalFacing.scale(2.0D));
        player.teleportTo(behind.x, behind.y, behind.z);
        player.lookAt(EntityAnchorArgument.Anchor.EYES, target.getEyePosition());
        player.resetAttackStrengthTicker();
        player.swing(InteractionHand.MAIN_HAND);
        player.attack(target);
    }

    @Override
    public boolean canActivate(Player player) {
        return cooldown <= 0 | swings > 0;
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
        }
        if (duration <= 0) {
            swings = 0;
        }
    }

    @Override
    public void onRemoved(Player player) {
        if (duration > 0) {
            duration = 0;
            tick(player);
        }
    }

    private LivingEntity raycastLivingEntity(Player player) {
        double range = 6.0D;
        Vec3 start = player.getEyePosition();
        Vec3 end = start.add(player.getViewVector(1.0F).scale(range));

        var blockHit = player.level().clip(new ClipContext(
                start, end,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                player
        ));

        if (blockHit.getType() != HitResult.Type.MISS) {
            end = blockHit.getLocation();
        }

        EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(
                player,
                start,
                end,
                player.getBoundingBox()
                        .expandTowards(end.subtract(start))
                        .inflate(1.0D),
                entity -> entity instanceof LivingEntity && entity != player,
                start.distanceToSqr(end)
        );

        return entityHit != null && entityHit.getEntity() instanceof LivingEntity living
                ? living
                : null;
    }
}
