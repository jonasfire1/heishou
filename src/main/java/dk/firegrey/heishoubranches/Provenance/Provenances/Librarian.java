package dk.firegrey.heishoubranches.Provenance.Provenances;

import dk.firegrey.heishoubranches.Provenance.ModTag;
import dk.firegrey.heishoubranches.Provenance.Powers.Active.FerventBeats;
import dk.firegrey.heishoubranches.Provenance.Powers.Active.HeavenPage;
import dk.firegrey.heishoubranches.Provenance.Powers.Resources.LibraryLight;
import dk.firegrey.heishoubranches.Provenance.Powers.abstraction.Bar;
import dk.firegrey.heishoubranches.Provenance.Powers.abstraction.Power;
import dk.firegrey.heishoubranches.Provenance.ProvenanceAbstract;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import java.util.ArrayList;
import java.util.List;

public class Librarian extends ProvenanceAbstract {
    LibraryLight light;
    FerventBeats ferventBeats;

    public Librarian() {
        light = new LibraryLight();
        resources.add(light);
        ferventBeats = new FerventBeats();
        ability_1 = ferventBeats;
        ability_2 = new HeavenPage();
        this.powers = List.of(
                ability_1,
                ability_2
        );

    }

    @Override
    public String id() {
        return "librarian";
    }

    @Override
    public Component name() {
        return Component.literal("Certain Librarians");
    }

    @Override
    public ResourceLocation icon() {
        return null;
    }

    @Override
    public List<Power> powers() {
        if (powers != null) return powers;
        else powers = new ArrayList<>();
        return powers;
    }

    @Override
    public String dietTag() {
        return "none";
    }

    @Override
    public float hungerMultiplier() {
        return 0f;
    }

    @Override
    public float jumpMultiplier() {
        if (ferventBeats.getDuration() > 0) return 1.5f;
        return 1f;
    }

    @Override
    public float AttackDamage(Player player, float damage, Entity victim) {
        if  (ferventBeats.getDuration() > 0) return damage*1.2f;
        return damage;
    }

    @Override
    public float TakenDamage(Player victim, float damage, DamageSource damageType, Entity Attacker) {
        if (damage > victim.getHealth()) {
            light.change(-light.current());
            return 0f;
        }
        return damage;
    }

    @Override
    public boolean NaturalRegen() {
        return false;
    }

    @Override
    public boolean CanSleep(ServerPlayer player, BlockPos pos) {
        var level = player.serverLevel();
        // Check the block the player is standing on and the 4 horizontal neighbors
        BlockPos[] checks = {
                pos,
                pos.north(),
                pos.south(),
                pos.east(),
                pos.west(),
                pos.below()
        };

        for (BlockPos p : checks) {
            if (level.getBlockState(p).is(Blocks.BOOKSHELF)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Bar> bars() {
        return List.of(light,  ability_1, ability_2);
    }

    @Override
    public void onUse(ItemStack stack) {
        if (stack.getItem() == Items.BOOK) {
            light.change(25);
        }
    }

    @Override
    public void ability1(Player player) {
        if (ability_1 != null && ability_1.canActivate(player) && light.current() >= 20)
        {
            light.change(-20);
            ability_1.activate(player);
        }
    }

    @Override
    public void ability2(Player player) {
        if (ability_2 != null && ability_2.canActivate(player) && light.current() > 20)
        {
            light.change(-20);
            ability_2.activate(player);
        }
    }
}
