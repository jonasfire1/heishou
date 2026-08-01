package dk.firegrey.heishoubranches.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

import net.minecraft.world.entity.Entity;

public class HorseLowerBodyModel<T extends Entity> extends EntityModel<T> {

    private final ModelPart body;

    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;

    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;

    private final ModelPart tail;


    public HorseLowerBodyModel(ModelPart root) {
        this.body = root.getChild("body");

        this.rightHindLeg = root.getChild("right_hind_leg");
        this.leftHindLeg = root.getChild("left_hind_leg");

        this.rightFrontLeg = root.getChild("right_front_leg");
        this.leftFrontLeg = root.getChild("left_front_leg");

        this.tail = body.getChild("tail");
    }


    public static LayerDefinition createBodyLayer() {

        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();


        PartDefinition body = root.addOrReplaceChild(
                "body",
                CubeListBuilder.create()
                        .texOffs(0, 32)
                        .addBox(
                                -5.0F,
                                -8.0F,
                                -17.0F,
                                10.0F,
                                10.0F,
                                22.0F,
                                new CubeDeformation(0.05F)
                        ),
                PartPose.offset(0.0F, 11.0F, 5.0F)
        );


        root.addOrReplaceChild(
                "left_hind_leg",
                CubeListBuilder.create()
                        .texOffs(48, 21)
                        .mirror()
                        .addBox(
                                -3.0F,
                                -1.01F,
                                -1.0F,
                                4.0F,
                                11.0F,
                                4.0F
                        ),
                PartPose.offset(4.0F, 14.0F, 7.0F)
        );


        root.addOrReplaceChild(
                "right_hind_leg",
                CubeListBuilder.create()
                        .texOffs(48, 21)
                        .addBox(
                                -1.0F,
                                -1.01F,
                                -1.0F,
                                4.0F,
                                11.0F,
                                4.0F
                        ),
                PartPose.offset(-4.0F, 14.0F, 7.0F)
        );


        root.addOrReplaceChild(
                "left_front_leg",
                CubeListBuilder.create()
                        .texOffs(48, 21)
                        .mirror()
                        .addBox(
                                -3.0F,
                                -1.01F,
                                -1.9F,
                                4.0F,
                                11.0F,
                                4.0F
                        ),
                PartPose.offset(4.0F, 14.0F, -12.0F)
        );


        root.addOrReplaceChild(
                "right_front_leg",
                CubeListBuilder.create()
                        .texOffs(48, 21)
                        .addBox(
                                -1.0F,
                                -1.01F,
                                -1.9F,
                                4.0F,
                                11.0F,
                                4.0F
                        ),
                PartPose.offset(-4.0F, 14.0F, -12.0F)
        );


        body.addOrReplaceChild(
                "tail",
                CubeListBuilder.create()
                        .texOffs(42, 36)
                        .addBox(
                                -1.5F,
                                0.0F,
                                0.0F,
                                3.0F,
                                14.0F,
                                4.0F
                        ),
                PartPose.offsetAndRotation(
                        0.0F,
                        -5.0F,
                        2.0F,
                        (float)Math.PI / 6,
                        0.0F,
                        0.0F
                )
        );


        return LayerDefinition.create(mesh, 64, 64);
    }


    @Override
    public void setupAnim(
            T entity,
            float limbSwing,
            float limbSwingAmount,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
        float speed = 0.6662F;
        float swing = 1.4F * limbSwingAmount;

        rightFrontLeg.xRot = net.minecraft.util.Mth.cos(limbSwing * speed) * swing;
        leftFrontLeg.xRot = net.minecraft.util.Mth.cos(limbSwing * speed + (float)Math.PI) * swing;

        rightHindLeg.xRot = net.minecraft.util.Mth.cos(limbSwing * speed + (float)Math.PI) * swing;
        leftHindLeg.xRot = net.minecraft.util.Mth.cos(limbSwing * speed) * swing;
        tail.xRot = (float)Math.PI / 6.0F + limbSwingAmount * 0.2F;
    }


    @Override
    public void renderToBuffer(
            PoseStack poseStack,
            VertexConsumer buffer,
            int packedLight,
            int packedOverlay,
            int color
    ) {
        poseStack.pushPose();

        // Move front legs only
        poseStack.pushPose();

        poseStack.translate(0.0D, 0.0D, 0.1);

        rightFrontLeg.render(
                poseStack,
                buffer,
                packedLight,
                packedOverlay,
                color
        );

        leftFrontLeg.render(
                poseStack,
                buffer,
                packedLight,
                packedOverlay,
                color
        );

        poseStack.popPose();


        // Render the rest normally
        body.render(
                poseStack,
                buffer,
                packedLight,
                packedOverlay,
                color
        );

        rightHindLeg.render(
                poseStack,
                buffer,
                packedLight,
                packedOverlay,
                color
        );

        leftHindLeg.render(
                poseStack,
                buffer,
                packedLight,
                packedOverlay,
                color
        );

        poseStack.popPose();
    }
}