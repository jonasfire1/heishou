package dk.firegrey.heishoubranches.network;

import dk.firegrey.heishoubranches.Heishou;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class NetworkHandler {

    public static final ResourceLocation CHANNEL =
            ResourceLocation.fromNamespaceAndPath(
                    Heishou.MODID,
                    "main"
            );

    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");

        registrar.playToServer(
                AbilityPacket.TYPE,
                AbilityPacket.STREAM_CODEC,
                AbilityPacket::handle
        );

        registrar.playToClient(
                MarkTargetPacket.TYPE,
                MarkTargetPacket.STREAM_CODEC,
                MarkTargetPacket::handle
        );
    }

    public static void sendToServer(CustomPacketPayload packet) {
        PacketDistributor.sendToServer(packet);
    }

    public static void sendToPlayer(ServerPlayer player, CustomPacketPayload packet) {
        PacketDistributor.sendToPlayer(player, packet);
    }
}