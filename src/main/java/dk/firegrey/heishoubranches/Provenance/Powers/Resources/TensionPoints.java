package dk.firegrey.heishoubranches.Provenance.Powers.Resources;

import dk.firegrey.heishoubranches.Heishou;
import dk.firegrey.heishoubranches.Provenance.Powers.abstraction.Resource;
import net.minecraft.resources.ResourceLocation;

public class TensionPoints implements Resource {
    int resource = 0;
    int maxResource = 100;
    @Override
    public int current() {
        return resource;
    }

    @Override
    public int max() {
        return maxResource;
    }

    @Override
    public ResourceLocation back() {
        return ResourceLocation.fromNamespaceAndPath(Heishou.MODID, "textures/provenancebars/defaultback.png");
    }

    @Override
    public ResourceLocation front() {
        return ResourceLocation.fromNamespaceAndPath(Heishou.MODID, "textures/provenancebars/librarian_light.png");
    }

    @Override
    public void change(int added) {
        if (resource+added > maxResource) resource = maxResource;
        else resource+=added;
    }
}
