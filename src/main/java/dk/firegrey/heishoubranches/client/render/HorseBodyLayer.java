package dk.firegrey.heishoubranches.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import dk.firegrey.heishoubranches.Provenance.ProvenanceManager;
import dk.firegrey.heishoubranches.Provenance.Provenances.Wu;
import dk.firegrey.heishoubranches.client.model.HorseLowerBodyModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class HorseBodyLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    private static final ResourceLocation HORSE_TEXTURE =
            ResourceLocation.withDefaultNamespace("textures/entity/horse/horse_brown.png");

    private final HorseLowerBodyModel<AbstractClientPlayer> horseModel;

    public HorseBodyLayer(
            RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> parent,
            HorseLowerBodyModel<AbstractClientPlayer> horseModel
    ) {
        super(parent);
        this.horseModel = horseModel;
    }

    @Override
    public void render(
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            AbstractClientPlayer player,
            float limbSwing,
            float limbSwingAmount,
            float partialTick,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {

        if (!(ProvenanceManager.get(player) instanceof Wu)) {
            return;
        }

        poseStack.pushPose();

        poseStack.translate(0.0D, 0.3D, 0.25D);
        poseStack.scale(0.9F, 0.9F, 0.9F);

        horseModel.setupAnim(
                player,
                limbSwing,
                limbSwingAmount,
                ageInTicks,
                netHeadYaw,
                headPitch
        );

        horseModel.renderToBuffer(
                poseStack,
                buffer.getBuffer(RenderType.entityCutout(HORSE_TEXTURE)),
                packedLight,
                OverlayTexture.NO_OVERLAY,
                -1 // white
        );

        poseStack.popPose();
    }
}