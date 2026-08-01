package dk.firegrey.heishoubranches.client;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import dk.firegrey.heishoubranches.Heishou;
import dk.firegrey.heishoubranches.Provenance.ProvenanceAbstract;
import dk.firegrey.heishoubranches.Provenance.ProvenanceManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.Locale;

@EventBusSubscriber(modid = Heishou.MODID)
public class ModCommands {

    @SubscribeEvent
    public static void register(RegisterCommandsEvent event) {

        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(
                Commands.literal("setprovenance")
                        .then(Commands.argument("player", EntityArgument.player())
                                .then(Commands.argument("provenance", StringArgumentType.word())
                                        .executes(context -> {

                                            ServerPlayer player = EntityArgument.getPlayer(context, "player");
                                            String provenanceStr = StringArgumentType.getString(context, "provenance");

                                            ProvenanceAbstract provenance = ProvenanceManager.getProvenance(provenanceStr.toLowerCase());

                                            if (provenance == null) {
                                                context.getSource().sendFailure(
                                                        Component.literal("Unknown provenance: " + provenanceStr)
                                                );
                                                return 0;
                                            }

                                            ProvenanceManager.set(player, provenance);

                                            context.getSource().sendSuccess(
                                                    () -> Component.literal(
                                                            "Set " + player.getName().getString()
                                                                    + "'s provenance to " + provenanceStr
                                                    ),
                                                    true
                                            );

                                            return 1;
                                        })
                                )
                        )
        );
    }
}