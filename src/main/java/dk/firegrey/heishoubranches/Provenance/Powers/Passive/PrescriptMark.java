package dk.firegrey.heishoubranches.Provenance.Powers.Passive;

import dk.firegrey.heishoubranches.Provenance.Powers.abstraction.Power;
import dk.firegrey.heishoubranches.network.MarkTargetPacket;
import dk.firegrey.heishoubranches.network.NetworkHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class PrescriptMark extends Power {
    public LivingEntity target;
    public LivingEntity previousTarget;
    final double radius = 40;
    @Override
    public void tick(Player player) {
        if (target != null && (!target.isAlive() || target.distanceToSqr(player) >= radius * radius)) {
            previousTarget  = target;
            target = null;

            if (player instanceof ServerPlayer serverPlayer) {
                NetworkHandler.sendToPlayer(
                        serverPlayer,
                        new MarkTargetPacket(
                                previousTarget.getId(),
                                MarkTargetPacket.Operation.REMOVE
                        )
                );
            }
        }

        if (target == null) {
            var level = player.level();

            var targets = level.getEntitiesOfClass(
                    LivingEntity.class,
                    player.getBoundingBox().inflate(radius),
                    entity -> entity != player
                            && !(entity instanceof ServerPlayer)
                            && entity.isAlive()
                            && entity.distanceToSqr(player) <= radius * radius
            );

            if (!targets.isEmpty()) {
                previousTarget = target;
                target = targets.get(level.random.nextInt(targets.size()));
                if (player instanceof ServerPlayer serverPlayer) {
                    NetworkHandler.sendToPlayer(
                            serverPlayer,
                            new MarkTargetPacket(target.getId(), MarkTargetPacket.Operation.ADD)
                    );
                }
            }
        }
    }

}
