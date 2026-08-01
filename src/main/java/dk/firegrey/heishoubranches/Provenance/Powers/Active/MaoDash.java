package dk.firegrey.heishoubranches.Provenance.Powers.Active;

import dk.firegrey.heishoubranches.Provenance.Powers.abstraction.ActivePower;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class MaoDash extends ActivePower {
    int cooldown;
    int maxcooldown = 100;

    @Override
    public void activate(Player player) {
        if (!canActivate(player)) {
            return;
        }

        Vec3 look = player.getLookAngle();

        player.setDeltaMovement(
                look.x * 1.5,
                0.3,
                look.z * 1.5
        );

        player.hurtMarked = true;
        cooldown = maxcooldown;
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
