package dk.firegrey.heishoubranches.client;

import dk.firegrey.heishoubranches.client.model.HorseLowerBodyModel;
import dk.firegrey.heishoubranches.client.render.HorseBodyLayer;
import dk.firegrey.heishoubranches.network.AbilityPacket;
import dk.firegrey.heishoubranches.network.NetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;
import dk.firegrey.heishoubranches.Heishou;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Heishou.MODID, value = Dist.CLIENT)
public class ClientEvents {

    private static boolean initialized = false;

    public static void init() {
        if (initialized) {
            return;
        }

        initialized = true;

        net.neoforged.neoforge.common.NeoForge.EVENT_BUS.addListener(
                ClientEvents::onClientTick
        );
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        ClientEvents.init();
    }

    private static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null) {
            return;
        }

        while (ModKeybinds.ABILITY_1.consumeClick()) {
            NetworkHandler.sendToServer(new AbilityPacket(1));
        }
        while (ModKeybinds.ABILITY_2.consumeClick()) {
            NetworkHandler.sendToServer(new AbilityPacket(2));
        }
    }

    @SubscribeEvent
    static void addPlayerLayers(EntityRenderersEvent.AddLayers event) {
        for (PlayerSkin.Model skin : event.getSkins()) {
            PlayerRenderer renderer = event.getSkin(skin);
            renderer.addLayer(
                    new HorseBodyLayer(
                            renderer,
                            new HorseLowerBodyModel<>(
                                    event.getEntityModels().bakeLayer(ModelLayers.HORSE)
                            )
                    )
            );
        }
    }
}