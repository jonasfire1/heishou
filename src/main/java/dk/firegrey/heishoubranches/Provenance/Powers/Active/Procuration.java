package dk.firegrey.heishoubranches.Provenance.Powers.Active;

import dk.firegrey.heishoubranches.Provenance.Powers.abstraction.ActivePower;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class Procuration extends ActivePower {
    int cooldown;
    int maxcooldown = 100;
    @Override
    public void activate(Player player) {
        if (!canActivate(player)) {
            return;
        }

        Vec3 look = player.getLookAngle();

        player.setDeltaMovement(
                0,
                1,
                0
        );

        player.hurtMarked = true;
        cooldown = 300;
    }

    @Override
    public void tick(Player player) {
        cooldown--;
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
        return null;
    }

    @Override
    public ResourceLocation front() {
        return null;
    }
}
