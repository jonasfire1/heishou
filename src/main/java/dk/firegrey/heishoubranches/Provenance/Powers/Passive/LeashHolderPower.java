package dk.firegrey.heishoubranches.Provenance.Powers.Passive;

import dk.firegrey.heishoubranches.Provenance.Powers.abstraction.Power;
import dk.firegrey.heishoubranches.Provenance.Provenances.Wu;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class LeashHolderPower extends Power {
    Wu leashed;
    Player leashedPlayer;
    public LeashHolderPower(Wu Leashed) {
        leashed = Leashed;
    }

    @Override
    public float TakenDamage(Player victim, float damage, DamageSource damageType, Entity Attacker) {
        leashedPlayer.hurt(Attacker.damageSources().mobAttack((LivingEntity) Attacker), damage*0.5f);
        return damage*0.5f;
    }

    @Override
    public void onHit(LivingEntity attacker, LivingEntity victim) {
        leashed.lineBreaker.change(1);
    }
}
