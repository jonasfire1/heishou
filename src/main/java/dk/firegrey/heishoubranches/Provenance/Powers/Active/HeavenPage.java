package dk.firegrey.heishoubranches.Provenance.Powers.Active;

import dk.firegrey.heishoubranches.Heishou;
import dk.firegrey.heishoubranches.Provenance.Powers.abstraction.ActivePower;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class HeavenPage extends ActivePower {
    int cooldown;
    final int maxcooldown = 1200;

    @Override
    public void activate(Player player) {
        if (!canActivate(player)) {
            return;
        }
        cooldown = maxcooldown;

        double radius = 8.0;
        var level = player.level();

        var targets = level.getEntitiesOfClass(
                LivingEntity.class,
                player.getBoundingBox().inflate(radius),
                entity -> entity != player
                        && entity.isAlive()
                        && entity.distanceToSqr(player) <= radius * radius
        );
        targets.forEach(entity ->
        {
            entity.addEffect(new MobEffectInstance(
                    MobEffects.MOVEMENT_SLOWDOWN,
                    100,
                    0 //amp = 1+(inserted number)
            ));
            entity.hurt(player.damageSources().magic(),2.0f);
            player.heal(1.0f);
        });
    }

    @Override
    public boolean canActivate(Player player) {
        return cooldown <= 0;
    }

    @Override
    public int current() {
        return cooldown;
    }

    @Override
    public int max() {
        return maxcooldown;
    }

    @Override
    public ResourceLocation back() {
        return ResourceLocation.fromNamespaceAndPath(Heishou.MODID, "textures/provenancebars/defaultback.png");
    }

    @Override
    public ResourceLocation front() {
        return ResourceLocation.fromNamespaceAndPath(Heishou.MODID, "textures/provenancebars/librarian_heaven.png");
    }

    @Override
    public void tick(Player player) {
        cooldown--;
    }

}
