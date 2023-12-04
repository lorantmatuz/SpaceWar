package hu.elte.inf.szofttech2023.team3.spacewar.controller.generator;

import hu.elte.inf.szofttech2023.team3.spacewar.model.space.Space;

@FunctionalInterface
public interface SpaceGenerator {
    
    public Space generate();

}
