package dk.firegrey.heishoubranches.Provenance;

import dk.firegrey.heishoubranches.Provenance.Provenances.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class Provenance {
    public static final Map<String, ProvenanceAbstract> REGISTRY = new LinkedHashMap<>();
    public static final ProvenanceAbstract Human = register(new Human());
    public static final ProvenanceAbstract Mao = register(new Mao());
    public static final ProvenanceAbstract Librarian = register(new Librarian());
    public static final ProvenanceAbstract FuriosoRavvitca = register(new FuriosoRabbitca());
    public static final ProvenanceAbstract WhiteMage = register(new WhiteRobe());
    public static final ProvenanceAbstract WU = register(new Wu());

    private static ProvenanceAbstract register(ProvenanceAbstract Provenance) {
        REGISTRY.put(Provenance.id(), Provenance);
        return Provenance;
    }
}
