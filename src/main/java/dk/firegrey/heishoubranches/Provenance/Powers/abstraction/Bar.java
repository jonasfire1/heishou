package dk.firegrey.heishoubranches.Provenance.Powers.abstraction;

import net.minecraft.resources.ResourceLocation;

//this is bacically just to order things
public interface Bar {

    int current();

    int max();

    ResourceLocation back();

    ResourceLocation front();
}
