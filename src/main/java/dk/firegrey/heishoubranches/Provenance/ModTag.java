package dk.firegrey.heishoubranches.Provenance;

import dk.firegrey.heishoubranches.Heishou;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTag {

    public static final TagKey<Item> ALL_FOOD = TagKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(
                    Heishou.MODID,
                    "all_food"
            )
    );

    public static final TagKey<Item> MEAT = TagKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(
                    Heishou.MODID,
                    "meat"
            )
    );

    public static final TagKey<Item> VEGETARIAN_FOOD = TagKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(
                    Heishou.MODID,
                    "vegetarian"
            )
    );

    public static final TagKey<Item> NO_FOOD = TagKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(
                    Heishou.MODID,
                    "no_food"
            )
    );
}