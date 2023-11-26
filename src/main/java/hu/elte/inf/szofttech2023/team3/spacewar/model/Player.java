package hu.elte.inf.szofttech2023.team3.spacewar.model;

import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Planet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.Fleet;

import java.util.ArrayList;

public class Player
{
    private String name;
    ArrayList<Planet> planets;
    ArrayList<Fleet> fleets;

    public Player(String name){
        this.name = name;
    }

    public void init()
    {
    }
}
