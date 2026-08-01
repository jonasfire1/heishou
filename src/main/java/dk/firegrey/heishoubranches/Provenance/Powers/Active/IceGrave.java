package dk.firegrey.heishoubranches.Provenance.Powers.Active;

import dk.firegrey.heishoubranches.Heishou;
import dk.firegrey.heishoubranches.Provenance.Powers.abstraction.ActivePower;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class IceGrave extends ActivePower {
    int maxcooldown = 20;
    int windup = 0;
    int killed = 0;
    int maxkilled = 5;
    BlockPos position;
    ServerLevel level;

    private BlockPos getTargetPos(Player player) {
        Vec3 start = player.getEyePosition();
        Vec3 end = start.add(player.getLookAngle().scale(10));

        BlockHitResult hit = player.level().clip(new ClipContext(
                start,
                end,
                ClipContext.Block.COLLIDER,
                ClipContext.Fluid.NONE,
                player
        ));

        return hit.getBlockPos();
    }

    @Override
    public void activate(Player player) {
        if (canActivate(player)) {
            cooldown = maxcooldown;
            windup = 10;
            position = getTargetPos(player);
        }
    }

    @Override
    public boolean canActivate(Player player) {
        return cooldown <= 0;
    }

    @Override
    public int current() {
        return killed;
    }

    @Override
    public int max() {
        return maxkilled;
    }

    @Override
    public ResourceLocation back() {
        return ResourceLocation.fromNamespaceAndPath(Heishou.MODID, "textures/provenancebars/defaultback.png");
    }

    @Override
    public ResourceLocation front() {
        return ResourceLocation.fromNamespaceAndPath(Heishou.MODID, "textures/provenancebars/librarian_heaven.png");
    }

    @Override
    public void tick(Player player) {
        cooldown--;
        level = (ServerLevel) player.level();
        if(windup > 0) {
            windup--;
            if (killed > 4) {
                level.sendParticles(
                        ParticleTypes.SNOWFLAKE,
                        player.getX() + 0.5,
                        player.getY() + 1.0,
                        player.getZ() + 0.5,
                        3,
                        0.5,
                        0.5,
                        0.5,
                        0.05
                );
                if (windup <= 0) {
                    killed = 0;
                    level.sendParticles(
                            ParticleTypes.SNOWFLAKE,
                            player.getX(),
                            player.getY() + 1.0,
                            player.getZ(),
                            100,
                            0.25,
                            0.5,
                            0.25,
                            0.5
                    );
                    var targets = level.getEntitiesOfClass(
                            LivingEntity.class,
                            player.getBoundingBox().inflate(10),
                            entity -> entity != player
                                    && entity.isAlive()
                                    && entity.distanceToSqr(player) <= 10 * 10
                    );
                    targets.forEach(entity ->
                    {
                        entity.addEffect(new MobEffectInstance(
                                MobEffects.MOVEMENT_SLOWDOWN,
                                100,
                                0 //amp = 1+(inserted number)
                        ));
                        entity.hurt(player.damageSources().freeze(),10.0f);
                    });
                }
            } else {
                level.sendParticles(
                        ParticleTypes.SNOWFLAKE,
                        position.getX() + 0.5,
                        position.getY() + 1.0,
                        position.getZ() + 0.5,
                        3,    // particle count
                        0.5,   // X spread
                        0.5,   // Y spread
                        0.5,   // Z spread
                        0.05   // speed
                );
                if (windup <= 0) {
                    level.sendParticles(
                            ParticleTypes.SNOWFLAKE,
                            position.getX() + 0.5,
                            position.getY() + 1.0,
                            position.getZ() + 0.5,
                            40,    // particle count
                            0.5,   // X spread
                            0.5,   // Y spread
                            0.5,   // Z spread
                            0.25   // speed
                    );
                    AABB positionBB = new AABB(position);
                    var targets = level.getEntitiesOfClass(
                            LivingEntity.class,
                            positionBB.inflate(10),
                            entity -> entity != player
                                    && entity.isAlive()
                                    && entity.distanceToSqr(Vec3.atCenterOf(position)) <= 3 * 3
                    );
                    targets.forEach(entity ->
                    {
                        entity.hurt(player.damageSources().freeze(),5.0f);
                        if (!entity.isAlive()) if (!(killed == maxkilled)) killed++;
                    });
                }
            }

        }
    }

}
