package dk.firegrey.heishoubranches.Provenance.Provenances;

import dk.firegrey.heishoubranches.Provenance.ProvenanceAbstract;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class Human extends ProvenanceAbstract {

    @Override
    public String id() {
        return "human";
    }

    @Override
    public Component name() {
        return Component.literal("Human");
    }

    @Override
    public ResourceLocation icon() {
        return null;
    }

}
