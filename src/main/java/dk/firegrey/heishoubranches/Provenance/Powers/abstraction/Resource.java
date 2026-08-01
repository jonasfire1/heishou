package dk.firegrey.heishoubranches.Provenance.Powers.abstraction;

public interface Resource extends Bar {
    public abstract int current();

    public abstract int max();

    public abstract void change(int added);
}
