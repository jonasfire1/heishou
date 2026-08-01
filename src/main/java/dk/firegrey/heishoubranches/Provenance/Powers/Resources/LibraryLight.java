package dk.firegrey.heishoubranches.Provenance.Powers.Resources;

import dk.firegrey.heishoubranches.Heishou;
import dk.firegrey.heishoubranches.Provenance.Powers.abstraction.Resource;
import net.minecraft.resources.ResourceLocation;

public class LibraryLight implements Resource {
    int resource;
    final int maxresource = 400;
    public LibraryLight() {
        resource = 0;
    }

    @Override
    public int current() {
        return resource;
    }

    @Override
    public int max() {
        return maxresource;
    }

    @Override
    public void change(int change) {
        if (resource + change >= maxresource) {
            resource = maxresource;
        } else resource = resource + change;
    }

    @Override
    public ResourceLocation back() {
        return ResourceLocation.fromNamespaceAndPath(Heishou.MODID, "textures/provenancebars/defaultback.png");
    }

    @Override
    public ResourceLocation front() {
        return ResourceLocation.fromNamespaceAndPath(Heishou.MODID, "textures/provenancebars/librarian_light.png");
    }
}
