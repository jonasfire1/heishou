package dk.firegrey.heishoubranches.events;

import dk.firegrey.heishoubranches.Heishou;
import dk.firegrey.heishoubranches.Provenance.ProvenanceManager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = Heishou.MODID)
public class ItemRIghtClickEvent {
    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();

        var provenance = ProvenanceManager.get(player);

        if (provenance == null) {
            return;
        }

        ItemStack stack = event.getItemStack();

        provenance.onUse(stack);
    }
}
