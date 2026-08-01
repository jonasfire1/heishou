package dk.firegrey.heishoubranches.Provenance;

import dk.firegrey.heishoubranches.Provenance.Powers.abstraction.Power;
import dk.firegrey.heishoubranches.client.Attachments;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import static dk.firegrey.heishoubranches.Provenance.Provenance.Human;
import static dk.firegrey.heishoubranches.Provenance.Provenance.Mao;
import static dk.firegrey.heishoubranches.Provenance.Provenance.Librarian;
import static dk.firegrey.heishoubranches.Provenance.Provenance.FuriosoRavvitca;
import static dk.firegrey.heishoubranches.Provenance.Provenance.WhiteMage;


public class ProvenanceManager {

    public static void tick(ServerLevel level) {
        for (ServerPlayer player : level.players()) {

            ProvenanceAbstract provenance = get(player);

            if (provenance == null) continue;
            provenance.tick(player);
            if (provenance.powers() == null) continue;

            for (Power power : provenance.powers()) {
                power.tick(player);
            }
        }
    }

    public static ProvenanceAbstract get(ServerPlayer player) {
        // Read player's chosen provenance
        return getProvenanceAbstract(player);
    }

    public static ProvenanceAbstract get(Player player) {
        return getProvenanceAbstract(player);
    }

    public static ProvenanceAbstract getProvenance(String provenance) {
        return Provenance.REGISTRY.get(provenance.toLowerCase());
    }

    @Nullable
    private static ProvenanceAbstract getProvenanceAbstract(Player player) {
        String provenance = player.getData(Attachments.PROVENANCE);

        if (provenance == null) {
            return null;
        }

        return Provenance.REGISTRY.get(provenance.toLowerCase());
    }

    public static String toString(ProvenanceAbstract provenance) {
        return provenance.id();
    }

    public static void set(ServerPlayer player, ProvenanceAbstract provenance) {

        ProvenanceAbstract old = get(player);

        if (old != null) if (old.powers != null) if (!old.powers.isEmpty()) {
                for (Power power : old.powers()) {
                    power.onRemoved(player);
                }
        }

        player.setData(Attachments.PROVENANCE, provenance.id());
        if (provenance.powers() != null)
            for (Power power : provenance.powers()) {
                power.onAdded(player);
            }
    }

    public static void useAbility1(ServerPlayer player) {
        ProvenanceAbstract provenance = get(player);

        if (provenance == null) {
            return;
        }
        provenance.ability1(player);
    }

    public static void useAbility2(ServerPlayer player) {
        ProvenanceAbstract provenance = get(player);

        if (provenance == null) {
            return;
        }
        provenance.ability2(player);
    }
}