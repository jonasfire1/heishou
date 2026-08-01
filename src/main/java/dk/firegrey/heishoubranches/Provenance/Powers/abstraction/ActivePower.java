package dk.firegrey.heishoubranches.Provenance.Powers.abstraction;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public abstract class ActivePower extends Power implements Bar {
    protected int cooldown;
    public abstract void activate(Player player);
    public abstract boolean canActivate(Player player);

    @Override
    public abstract int current();

    @Override
    public abstract int max();

    @Override
    public abstract ResourceLocation back();

    @Override
    public abstract ResourceLocation front();

}
