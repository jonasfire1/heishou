package dk.firegrey.heishoubranches.Provenance.Provenances;

import dk.firegrey.heishoubranches.Provenance.ModTag;
import dk.firegrey.heishoubranches.Provenance.Powers.Active.Indulgence;
import dk.firegrey.heishoubranches.Provenance.Powers.Active.Procuration;
import dk.firegrey.heishoubranches.Provenance.Powers.Passive.PrescriptMark;
import dk.firegrey.heishoubranches.Provenance.ProvenanceAbstract;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.Item;
import java.util.List;

public class FuriosoRabbitca extends ProvenanceAbstract {
    public PrescriptMark mark;
    private final Indulgence indulgence;
    public FuriosoRabbitca() {
        mark = new PrescriptMark();
        indulgence = new Indulgence();
        onHitPowers.add(indulgence);
        ability_1 = new Procuration();
        ability_2 = indulgence;
        this.powers = List.of(
                ability_1,
                ability_2,
                mark
        );
    }
    @Override
    public String id() {
        return "rabbitca";
    }

    @Override
    public Component name() {
        return Component.literal("Mr. Furioso Rabbitca");
    }

    @Override
    public ResourceLocation icon() {
        return null;
    }


    @Override
    public boolean negateFallDamage() {
        return true;
    }

    @Override
    public String dietTag() {
        return "vegetarian";
    }

    @Override
    public float TakenDamage(Player victim, float damage, DamageSource damageType, Entity Attacker) {
        if (Attacker instanceof LivingEntity) {
            if (indulgence.duration > 0) damage = damage * 1.5f;
            if (Attacker != mark.target) damage = damage + 1f;
        }
        if (damageType.is(DamageTypeTags.IS_FIRE)) damage = damage * 2f;
        return damage;
    }

    @Override
    public float AttackDamage(Player player, float damage, Entity victim) {
        if (victim instanceof LivingEntity) if (victim != mark.target) {
            damage = damage - 2;
        } else {
            damage = damage + 1f;
        }
        return damage;
    }

    @Override
    public void ability1(Player player) {
        if (ability_1 != null && ability_1.canActivate(player)) {
            ability_1.activate(player);
        }
    }

    @Override
    public void ability2(Player player) {
        if (ability_2 != null && ability_2.canActivate(player)) {
            ability_2.activate(player);
        }
    }
}
