package dk.firegrey.heishoubranches.client;

import com.mojang.serialization.Codec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class Attachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, "heishoubranches");

    public static Supplier<AttachmentType<String>> PROVENANCE =
            ATTACHMENTS.register(
                    "provenance",
                    () -> AttachmentType.builder(() -> "human")
                            .serialize(Codec.STRING)
                            .copyOnDeath()
                            .sync(ByteBufCodecs.STRING_UTF8)
                            .build()
            );
}