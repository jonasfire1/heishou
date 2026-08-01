package dk.firegrey.heishoubranches.Provenance;

import dk.firegrey.heishoubranches.Provenance.Powers.abstraction.ActivePower;
import dk.firegrey.heishoubranches.Provenance.Powers.abstraction.Bar;
import dk.firegrey.heishoubranches.Provenance.Powers.abstraction.Power;
import dk.firegrey.heishoubranches.Provenance.Powers.abstraction.Resource;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.level.NoteBlockEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class ProvenanceAbstract {

    public List<Power> powers;
    public List<Resource> resources = new ArrayList<>();
    public List<Power> onHitPowers = new ArrayList<>();
    public List<LivingEntity> Highlights = new ArrayList<>();
    protected ActivePower ability_1;
    protected ActivePower ability_2;


    public abstract String id();

    public abstract Component name();

    public abstract ResourceLocation icon();

    public void tick(Player player) {

    }
    public float airSpeedMultiplier() {
        return 1.0f;
    }
    public float jumpMultiplier() {
        return 1.0f;
    }
    public float AttackDamage(Player player, float damage, Entity victim) {
        return damage;
    }

    public List<Power> powers() {
        if (powers != null) return powers;
        else powers = new ArrayList<>();
        return powers;
    }


    public boolean negateFallDamage() {
        return false;
    }

    public float hungerMultiplier() {
        return 1.0f;
    }

    public String dietTag() {
        return "all";
    }

    public List<LivingEntity> highlights() {
        return Highlights;
    }

    public void ability1(Player player) {}

    public void ability2(Player player) {}

    public List<Bar> bars() {
        return List.of();
    }

    public float TakenDamage(Player victim, float damage, DamageSource damageType, Entity Attacker) {
        for (Power power : powers) {
            damage = power.TakenDamage(victim, damage, damageType, Attacker);
        }
        return damage;
    }

    public boolean NaturalRegen() {
        return true;
    }
    public boolean CanSleep(ServerPlayer player, BlockPos pos) {
        return true;
    }

    public void onHit(LivingEntity attacker, LivingEntity victim) {
        for (int i = 0; i < onHitPowers.size(); i++) {
            onHitPowers.get(i).onHit(attacker, victim);
        }
    }

    public boolean agroFocus() {
        return false;
    }

    public float SwimMultiplier() {
        return 1f;
    }

    public void onUse(ItemStack stack) {
    }

    public boolean Ridable(Player rider) {
        return false;
    }
}
