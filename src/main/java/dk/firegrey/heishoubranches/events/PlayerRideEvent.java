package dk.firegrey.heishoubranches.events;

import dk.firegrey.heishoubranches.Heishou;
import dk.firegrey.heishoubranches.Provenance.ProvenanceManager;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = Heishou.MODID)
public class PlayerRideEvent {
    @SubscribeEvent
    public static void onInteract(PlayerInteractEvent.EntityInteractSpecific event) {
        if (event.getEntity().level().isClientSide()) return;
        if (!(event.getTarget() instanceof Player vehicle)) return;

        Player rider = event.getEntity();
        if (!(ProvenanceManager.get(vehicle).Ridable(rider))) return;

        if (rider.startRiding(vehicle, true)) {
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);
        }
    }
}