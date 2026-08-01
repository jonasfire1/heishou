package dk.firegrey.heishoubranches.Provenance.Provenances;

import dk.firegrey.heishoubranches.Provenance.ModTag;
import dk.firegrey.heishoubranches.Provenance.Powers.Active.LeashHolder;
import dk.firegrey.heishoubranches.Provenance.Powers.Passive.WuStrider;
import dk.firegrey.heishoubranches.Provenance.Powers.Resources.LineBreaker;
import dk.firegrey.heishoubranches.Provenance.Powers.abstraction.Bar;
import dk.firegrey.heishoubranches.Provenance.ProvenanceAbstract;
import dk.firegrey.heishoubranches.Provenance.ProvenanceManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.List;

public class Wu extends ProvenanceAbstract {
    int LineBreakerTimer = 0;
    public LineBreaker lineBreaker;
    LeashHolder leashHolder;
    public Wu() {
        leashHolder = new LeashHolder();
        lineBreaker = new LineBreaker();
        resources.add(lineBreaker);
        this.powers = List.of(
                new WuStrider()
        );

    }

    @Override
    public String id() {
        return "wu";
    }

    @Override
    public Component name() {
        return null;
    }

    @Override
    public ResourceLocation icon() {
        return null;
    }

    @Override
    public void tick(Player player) {
        LineBreakerTimer--;
        if (LineBreakerTimer <= 0) {
            AttributeInstance maxAbsorption = player.getAttribute(Attributes.MAX_ABSORPTION);

            if (maxAbsorption != null && maxAbsorption.getValue() < 10.0) {
                maxAbsorption.setBaseValue(10.0);
            }
            player.setAbsorptionAmount(lineBreaker.current()*2);
            LineBreakerTimer = 400;
        }
    }

    @Override
    public float TakenDamage(Player victim, float damage, DamageSource damageType, Entity Attacker) {
        if (!leashHolder.IsLeashed()) lineBreaker.change(1);
        if (damage > 10 && lineBreaker.current() > 0) {
            lineBreaker.change(lineBreaker.current());
            return (damage*0.8f);
        }
        return damage;
    }

    @Override
    public float hungerMultiplier() {
        return 2f;
    }

    @Override
    public String dietTag() {
        return "vegetarian";
    }

    @Override
    public List<Bar> bars() {
        return List.of(lineBreaker);
    }

    @Override
    public boolean agroFocus() {
        return true;
    }

    @Override
    public float SwimMultiplier() {
        return 0.2f;
    }

    @Override
    public boolean Ridable(Player rider) {
        return ProvenanceManager.get(rider) == leashHolder.leashholder;
    }
}
