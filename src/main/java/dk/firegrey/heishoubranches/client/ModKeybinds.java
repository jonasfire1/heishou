package dk.firegrey.heishoubranches.client;

import net.minecraft.client.KeyMapping;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

public class ModKeybinds {

    public static final KeyMapping ABILITY_1 = new KeyMapping(
            "Provenance ability 1",
            GLFW.GLFW_KEY_G,
            "key.categories.heishoubranches"
    );

    public static final KeyMapping ABILITY_2 = new KeyMapping(
            "Provenance ability 2",
            GLFW.GLFW_KEY_H,
            "key.categories.heishoubranches"
    );

    public static void register(IEventBus modBus) {
        modBus.addListener(ModKeybinds::registerKeys);
    }

    private static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(ABILITY_1);
        event.register(ABILITY_2);
    }
}