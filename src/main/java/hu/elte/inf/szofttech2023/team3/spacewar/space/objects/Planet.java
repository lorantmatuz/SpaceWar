package hu.elte.inf.szofttech2023.team3.spacewar.space.objects;

public class Planet extends SpaceObject {
    public final int maxSize;
    public final int temperature;
    //private Player owner;
    private int energy;
    private int material;
    //private ArrayList<Building> buildings;

    public Planet(int x, int y, int size, int temp) {
        super(x,y);
        maxSize = size;
        temperature = temp;
    }

}
