package dk.firegrey.heishoubranches.client;

import dk.firegrey.heishoubranches.Heishou;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod(value = Heishou.MODID, dist = Dist.CLIENT)
public class HeishouClient {
    public static List<LivingEntity> markedEntities = new ArrayList<>(); // this is used for rendering specific targets only for a specific player
    public HeishouClient(IEventBus modBus) {
        ModKeybinds.register(modBus);
        modBus.addListener(ResourceHud::register);
        ClientEvents.init();
    }

}