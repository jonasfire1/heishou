package dk.firegrey.heishoubranches.Provenance.Powers.abstraction;

import net.minecraft.world.entity.player.Player;

public abstract class ToggleablePower extends ActivePower {
    boolean IsToggled;

    @Override
    public void activate(Player player) {
        IsToggled = !IsToggled;
    }
}

