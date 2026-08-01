package dk.firegrey.heishoubranches.network;

import dk.firegrey.heishoubranches.Heishou;
import dk.firegrey.heishoubranches.Provenance.ProvenanceManager;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public record AbilityPacket(int abilityId) implements CustomPacketPayload {

    public static final Type<AbilityPacket> TYPE =
            new Type<>(
                    ResourceLocation.fromNamespaceAndPath(
                            Heishou.MODID,
                            "power"
                    )
            );

    public static final StreamCodec<RegistryFriendlyByteBuf, AbilityPacket> STREAM_CODEC =
            StreamCodec.of(
                    (buf, packet) -> buf.writeInt(packet.abilityId()),
                    buf -> new AbilityPacket(buf.readInt())
            );


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }


    public static void handle(AbilityPacket packet, net.neoforged.neoforge.network.handling.IPayloadContext context) {

        context.enqueueWork(() -> {

            ServerPlayer player = (ServerPlayer) context.player();

            switch (packet.abilityId()) {
                case 1 -> ProvenanceManager.useAbility1(player);
                case 2 -> ProvenanceManager.useAbility2(player);
            }

        });
    }
}