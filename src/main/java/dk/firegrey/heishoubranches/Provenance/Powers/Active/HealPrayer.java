package dk.firegrey.heishoubranches.Provenance.Powers.Active;

import dk.firegrey.heishoubranches.Provenance.Powers.abstraction.ActivePower;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class HealPrayer extends ActivePower {
    private static final double RANGE = 8.0;

    @Override
    public void activate(Player player) {
        Vec3 start = player.getEyePosition();
        Vec3 direction = player.getViewVector(1.0F);
        Vec3 end = start.add(direction.scale(RANGE));

        // Stop the ray at the first block, or at RANGE if it hits no block.
        HitResult blockHit = player.pick(RANGE, 1.0F, false);
        if (blockHit.getType() != HitResult.Type.MISS) {
            end = blockHit.getLocation();
        }
        AABB searchBox = player.getBoundingBox()
            .expandTowards(direction.scale(RANGE))
            .inflate(1.0);
        EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(
                player,
                start,
                end,
                searchBox,
                entity -> entity instanceof Player
                        && entity != player
                        && entity.isAlive(),
                RANGE * RANGE
            );

        if (entityHit != null && entityHit.getEntity() instanceof Player target) target.heal(4.0F); // heals 2 hearts
        else player.heal(2.0F);
    }

    @Override
    public boolean canActivate(Player player) {
        return true;
    }

    @Override
    public int current() {
        return 0;
    }

    @Override
    public int max() {
        return 0;
    }

    @Override
    public ResourceLocation back() {
        return null;
    }

    @Override
    public ResourceLocation front() {
        return null;
    }

}
