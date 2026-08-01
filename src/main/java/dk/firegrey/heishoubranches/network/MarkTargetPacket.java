package dk.firegrey.heishoubranches.network;

import dk.firegrey.heishoubranches.Heishou;
import dk.firegrey.heishoubranches.client.HeishouClient;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record MarkTargetPacket(int entityId, Operation operation) implements CustomPacketPayload {

    public enum Operation {
        ADD,
        REMOVE
    }

    public static final Type<MarkTargetPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(
                    Heishou.MODID,
                    "mark_target"
            ));

    public static final StreamCodec<FriendlyByteBuf, MarkTargetPacket> STREAM_CODEC =
            StreamCodec.of(
                    (buf, packet) -> {
                        buf.writeInt(packet.entityId());
                        buf.writeEnum(packet.operation());
                    },
                    buf -> new MarkTargetPacket(
                            buf.readInt(),
                            buf.readEnum(Operation.class)
                    )
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final MarkTargetPacket packet, final IPayloadContext context) {
        context.enqueueWork(() -> {
            var level = Minecraft.getInstance().level;

            if (level == null) {
                return;
            }

            if (!(level.getEntity(packet.entityId()) instanceof LivingEntity entity)) {
                return;
            }

            switch (packet.operation()) {
                case ADD -> {
                    if (!HeishouClient.markedEntities.contains(entity)) {
                        HeishouClient.markedEntities.add(entity);
                    }
                }

                case REMOVE -> HeishouClient.markedEntities.remove(entity);
            }
        });
    }
}