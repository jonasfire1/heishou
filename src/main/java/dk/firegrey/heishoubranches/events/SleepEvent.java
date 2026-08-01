package dk.firegrey.heishoubranches.events;

import dk.firegrey.heishoubranches.Heishou;
import dk.firegrey.heishoubranches.Provenance.ProvenanceManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.CanPlayerSleepEvent;

@EventBusSubscriber(modid = Heishou.MODID)
public class  SleepEvent {
    @SubscribeEvent
    public static void onCanPlayerSleep(CanPlayerSleepEvent event) {
        // Keep vanilla checks first; only add your custom gate.
        if (event.getVanillaProblem() != null) return;
        boolean customSleepAllowed = false;
        var player = event.getEntity();
        var provenance = ProvenanceManager.get(player);
        if (provenance == null) return;
        BlockPos bedPos = event.getPos();
        BlockState bedState = event.getState();
        BlockPos otherHalf = bedPos.relative(bedState.getValue(BedBlock.FACING).getOpposite());
        if (provenance.CanSleep(player, bedPos) || provenance.CanSleep(player, otherHalf)) {
            customSleepAllowed = true;
        }
        if (!customSleepAllowed) {
            event.setProblem(Player.BedSleepingProblem.OTHER_PROBLEM);
        }
    }
}