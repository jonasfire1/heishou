package dk.firegrey.heishoubranches.Provenance.Provenances;

import dk.firegrey.heishoubranches.Provenance.ModTag;
import dk.firegrey.heishoubranches.Provenance.Powers.Active.MaoDash;
import dk.firegrey.heishoubranches.Provenance.Powers.Passive.MaoSpeed;
import dk.firegrey.heishoubranches.Provenance.Powers.abstraction.Bar;
import dk.firegrey.heishoubranches.Provenance.ProvenanceAbstract;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.List;

public class Mao extends ProvenanceAbstract {

    public Mao() {
        ability_1 = new MaoDash();
        this.powers = List.of(
                ability_1,
                new MaoSpeed()
        );

    }

    public String id() {
        return "mao";
    }

    @Override
    public Component name() {
        return Component.literal("Mao Branch adept");
    }

    @Override
    public ResourceLocation icon() {
        return null;
        /*return ResourceLocation.fromNamespaceAndPath(
                Heishou.MODID,
                "textures/gui/provenance/mao.png"
        );*/
    }


    @Override
    public float airSpeedMultiplier() {
        return 1.5f;
    }
    @Override
    public float jumpMultiplier() {
        return 1.5f;
    }

    @Override
    public boolean negateFallDamage() {
        return true;
    }

    @Override
    public float hungerMultiplier() {
        return 1.5f;
    }

    @Override
    public String dietTag() {
        return "vegetarian";
    }

    @Override
    public void ability1(Player player) {
        if (ability_1 != null && ability_1.canActivate(player)) {
            ability_1.activate(player);
        }
    }

}
