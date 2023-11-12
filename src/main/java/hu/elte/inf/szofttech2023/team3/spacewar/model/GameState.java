package hu.elte.inf.szofttech2023.team3.spacewar.model;

import hu.elte.inf.szofttech2023.team3.spacewar.model.space.GenerateSpace;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.Space;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Asteroid;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.BlackHole;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.Planet;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.SpaceObject;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships.Fleet;

import java.lang.reflect.Array;
import java.util.*;

public class GameState {

    boolean gameOver;
    Space gameSpace;
    private ArrayList<Player> players;
    private ArrayList<Fleet> fleets;
    private ArrayList<Asteroid> asteroids;
    private ArrayList<BlackHole> blackHoles;
    private ArrayList<Planet> planets;

    private ArrayList<SpaceObject> spaceObjects;

    public GameState(){}

    public void init( int mapWidth , int mapHeight,
            int numberOfPlayer , int numberOfAsteroids, int numberOfBlackHoles, int numberOfPlanets )
    {
        gameOver = false;
        players = new ArrayList<Player>();
        fleets  = new ArrayList<Fleet>();
        asteroids = new ArrayList<Asteroid>();
        planets = new ArrayList<Planet>();
        blackHoles = new ArrayList<BlackHole>();

        for( int iPlayer = 0; iPlayer < numberOfPlayer ; ++iPlayer )
        {
            players.add( new Player() );
        }

        gameSpace = Space.getInstance( mapWidth, mapHeight );
        GenerateSpace generator = new GenerateSpace(gameSpace);
        generator.run( numberOfPlanets , numberOfAsteroids , numberOfBlackHoles);
    }

    public boolean isGameOver(){ return this.gameOver; };
    public void setGameOver(boolean isReallyOver ){ this.gameOver = isReallyOver; };
//    public ArrayList<SpaceObject> getSpaceObjects(){ return this.spaceObjects ; };
    public ArrayList<Fleet> getFleets(){ return this.fleets; };
    public ArrayList<Planet> getPlanets() { return this.planets; };
    public ArrayList<Asteroid> getAsteroids() { return  this.asteroids; }
    public ArrayList<BlackHole> getBlackHoles() { return this.blackHoles ; }
    public List<SpaceObject> getSpaceObjects(){ return this.gameSpace.getSpaceObjects(); }
}
