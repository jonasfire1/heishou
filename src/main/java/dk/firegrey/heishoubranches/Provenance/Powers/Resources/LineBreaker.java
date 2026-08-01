package dk.firegrey.heishoubranches.Provenance.Powers.Resources;

import dk.firegrey.heishoubranches.Heishou;
import dk.firegrey.heishoubranches.Provenance.Powers.abstraction.Resource;
import net.minecraft.resources.ResourceLocation;

public class LineBreaker implements Resource {

    int linebreaker = 0;
    int maxLinebreaker = 5;
    @Override
    public int current() {
        return linebreaker;
    }

    @Override
    public int max() {
        return maxLinebreaker;
    }

    @Override
    public ResourceLocation back() {
        return ResourceLocation.fromNamespaceAndPath(Heishou.MODID, "textures/provenancebars/wu_linebreaker_back.png");
    }

    @Override
    public ResourceLocation front() {
        return ResourceLocation.fromNamespaceAndPath(Heishou.MODID, "textures/provenancebars/wu_linebreaker_front.png");
    }

    @Override
    public void change(int added) {
        if (linebreaker + added > maxLinebreaker) linebreaker = maxLinebreaker;
        else linebreaker += added;
    }
}
