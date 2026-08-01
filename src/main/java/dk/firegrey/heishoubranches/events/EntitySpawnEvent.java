package dk.firegrey.heishoubranches.events;

import dk.firegrey.heishoubranches.Heishou;
import dk.firegrey.heishoubranches.Provenance.ProvenanceAbstract;
import dk.firegrey.heishoubranches.Provenance.ProvenanceManager;
import dk.firegrey.heishoubranches.Provenance.Provenances.Wu;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

@EventBusSubscriber(modid = Heishou.MODID)
public class EntitySpawnEvent {
        @SubscribeEvent
        public static void onEntityJoin(EntityJoinLevelEvent event) {
            if (event.getLevel().isClientSide()) return;
            if (!(event.getEntity() instanceof Monster mob)) return;
            mob.targetSelector.addGoal(
                    0,
                    new NearestAttackableTargetGoal<>(
                            mob,
                            Player.class,
                            true,
                            player -> {
                                ProvenanceAbstract provenance = ProvenanceManager.get((ServerPlayer) player);
                                return provenance != null && provenance.agroFocus();
                            }
                    )
            );
        }
}
