package dk.firegrey.heishoubranches.events;

import dk.firegrey.heishoubranches.Heishou;
import dk.firegrey.heishoubranches.Provenance.ProvenanceAbstract;
import dk.firegrey.heishoubranches.Provenance.ProvenanceManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = Heishou.MODID)
public class DamageEvent {
    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        if (event.getEntity().level().isClientSide()) return;

        float damage = event.getNewDamage();
        Entity attacker = event.getSource().getEntity();
        Entity victim = event.getEntity();
        if (attacker instanceof Player) {
            ProvenanceAbstract provenance = ProvenanceManager.get((ServerPlayer) attacker);
            provenance.onHit((LivingEntity) attacker, event.getEntity());
            if (provenance != null) {
                damage = provenance.AttackDamage((Player) attacker, damage, victim);
            }
        }

        if (victim instanceof Player) {
            ProvenanceAbstract provenance = ProvenanceManager.get((ServerPlayer) victim);
            if (provenance != null) {
                damage = provenance.TakenDamage((Player) victim, damage, event.getSource(), attacker);
            }
        }
        event.setNewDamage(damage);
    }
}
