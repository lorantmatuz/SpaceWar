package hu.elte.inf.szofttech2023.team3.spacewar.model;

import hu.elte.inf.szofttech2023.team3.spacewar.model.Spacecraft;

public class Fighterjet implements Spacecraft {

    private final double weight;
    
    public Fighterjet(double weight) {
        this.weight = weight;
    }
    
    @Override
    public double getWeight() {
        return weight;
    }
    
}
