package dk.firegrey.heishoubranches.Provenance.Provenances;

import dk.firegrey.heishoubranches.Provenance.ModTag;
import dk.firegrey.heishoubranches.Provenance.Powers.Active.HealPrayer;
import dk.firegrey.heishoubranches.Provenance.Powers.Active.IceGrave;
import dk.firegrey.heishoubranches.Provenance.Powers.Passive.Scared;
import dk.firegrey.heishoubranches.Provenance.Powers.Resources.TensionPoints;
import dk.firegrey.heishoubranches.Provenance.Powers.abstraction.Bar;
import dk.firegrey.heishoubranches.Provenance.ProvenanceAbstract;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.util.List;

public class WhiteRobe extends ProvenanceAbstract {
    private TensionPoints tensionPoints = new TensionPoints();
    public WhiteRobe() {
        ability_1 = new HealPrayer();
        ability_2 = new IceGrave();
        resources.add(tensionPoints);
        this.powers = List.of(
            new Scared(),
            ability_1,
            ability_2
        );
    }

    @Override
    public String id() {
        return "whiterobe";
    }

    @Override
    public Component name() {
        return Component.literal("Deer");
    }

    @Override
    public ResourceLocation icon() {
        return null;
    }

    @Override
    public void tick(Player player) {
        Level level = player.level();
        var targets = level.getEntitiesOfClass(
                LivingEntity.class,
                player.getBoundingBox().inflate(3.5),
                entity -> entity != player
                        && entity.isAlive()
                        && entity.distanceToSqr(player) <= 3.5 * 3.5
        );
        for  (LivingEntity target : targets) {
            if (target instanceof Enemy) {
                tensionPoints.change(1);
                break;
            }
        }
    }

    @Override
    public String dietTag() {
        return "vegetarian";
    }

    @Override
    public float jumpMultiplier() {
        return 1.2f;
    }

    @Override
    public void ability1(Player player) {
        if (tensionPoints.current() > 32) {
            ability_1.activate(player);
            tensionPoints.change(-32);
        }
    }

    @Override
    public void ability2(Player player) {
        if (tensionPoints.current() > 16) {
            ability_2.activate(player);
            tensionPoints.change(-16);
        }
    }

    @Override
    public List<Bar> bars() {
        return List.of(tensionPoints, ability_2);
    }
}
