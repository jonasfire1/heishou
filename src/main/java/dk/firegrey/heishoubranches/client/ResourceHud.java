package dk.firegrey.heishoubranches.client;

import dk.firegrey.heishoubranches.Provenance.Powers.abstraction.Bar;
import dk.firegrey.heishoubranches.Provenance.ProvenanceAbstract;
import dk.firegrey.heishoubranches.Provenance.ProvenanceManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;

public class ResourceHud {
    private static ResourceLocation RESOURCE_BAR = ResourceLocation.withDefaultNamespace("textures/gui/icons.png");
    public static void register(RegisterGuiLayersEvent event) {
        event.registerAbove(
                VanillaGuiLayers.HOTBAR,
                RESOURCE_BAR,
                ResourceHud::render
        );
    }

    private static void render( GuiGraphics guiGraphics, net.minecraft.client.DeltaTracker deltaTracker )
    {
        Minecraft mc = Minecraft.getInstance();
        int screenWidth = guiGraphics.guiWidth();
        int screenHeight = guiGraphics.guiHeight();
        if (mc.player == null) { return; }

        ProvenanceAbstract provenance = ProvenanceManager.get(mc.player);

        if (provenance == null) { return; }

        ArrayList<Bar> bars = new ArrayList<Bar>(provenance.bars());

        if (bars.isEmpty()) { return; };

        for (int i = 0; i < bars.size(); i++) { Bar resource = bars.get(i);
            ResourceLocation backBar = resource.back();
            ResourceLocation frontBar = resource.front();
            guiGraphics.blit(backBar, screenWidth / 2 + 10, screenHeight - 50 + (i * (-10)), 0, 0, 80, 8);
            guiGraphics.blit(frontBar, screenWidth / 2 + 10, screenHeight - 50 + (i * -10), 0, 0, (int)(80 * (resource.current() / (float) resource.max())), 8);

        }
    }
}
