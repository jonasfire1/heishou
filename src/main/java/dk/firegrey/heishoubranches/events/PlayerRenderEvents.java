package dk.firegrey.heishoubranches.events;

import dk.firegrey.heishoubranches.Provenance.ProvenanceManager;
import dk.firegrey.heishoubranches.Provenance.Provenances.Wu;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;

@EventBusSubscriber(value = Dist.CLIENT)
public class PlayerRenderEvents {

    @SubscribeEvent
    public static void onRenderPlayerPre(RenderPlayerEvent.Pre event) {
        if (!(ProvenanceManager.get(event.getEntity()) instanceof Wu)) {
            return;
        }

        PlayerModel<AbstractClientPlayer> model = event.getRenderer().getModel();

        model.leftLeg.visible = false;
        model.rightLeg.visible = false;
    }

    @SubscribeEvent
    public static void onRenderPlayerPost(RenderPlayerEvent.Post event) {
        PlayerModel<AbstractClientPlayer> model = event.getRenderer().getModel();

        model.leftLeg.visible = true;
        model.rightLeg.visible = true;
    }
}
