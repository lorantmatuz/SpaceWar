package hu.elte.inf.szofttech2023.team3.spacewar.model.space.construction;

public abstract class Constructable {
    protected int timeLeftOfConstruction;

    protected abstract void construct();

    public boolean isEndOfConstruction() {
        if(--timeLeftOfConstruction <= 0) {
            construct();
            return true;
        }
        return false;
    }

    public int getTimeLeftOfConstruction(){ return this.timeLeftOfConstruction; }
}