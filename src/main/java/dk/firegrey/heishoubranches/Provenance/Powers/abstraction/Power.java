package dk.firegrey.heishoubranches.Provenance.Powers.abstraction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public abstract class Power {
    public void tick(Player player) {

    }
    public void onAdded(Player player) {

    }
    public void onRemoved(Player player) {

    }
    public void onHit(LivingEntity attacker, LivingEntity victim) {

    }
    public float TakenDamage(Player victim, float damage, DamageSource damageType, Entity Attacker) {
        return damage;
    }
}
