package hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships;

import hu.elte.inf.szofttech2023.team3.spacewar.model.game.Player;
import hu.elte.inf.szofttech2023.team3.spacewar.model.space.objects.SpaceObject;

import java.util.ArrayList;

public final class Fleet extends SpaceObject {
    private static int idCounter = -1;
    private final int id;
    private final ArrayList<Spaceship> spaceships;
    private Player owner;
    private int totalConsumption = 0;
    private int minSpeed = Integer.MAX_VALUE;
    private int transportedResources = 0;

    public Fleet(int x, int y, Player owner) {
        super(x, y);
        this.owner = owner;
        ++idCounter;
        this.id = idCounter;
        spaceships = new ArrayList<>();
    }

    public boolean addShip(Spaceship ship) {
        /*
        if(getShip(ship.spaceship) != null) {
            return false;
        }
         */
        if ( ship == null ) return false;
        //spaceships[ship.spaceship.ordinal()] = ship;
        spaceships.add( ship );
        totalConsumption += ship.spaceship.consumption;
        if(minSpeed > ship.spaceship.speed) {
            minSpeed = ship.spaceship.speed;
        }
        return true;
    }

    public boolean mergeFleet(Fleet fleet) {
        boolean retVal = true;
        for(final var ship : fleet.getSpaceships()) {
            retVal = retVal && addShip(ship);
        }
        return retVal;
    }

    /*
    public boolean canAttack() {
        return getShip(SpaceshipEnum.MOTHER_SHIP) != null;
    }

    //public boolean canColonize() {
        return getShip(SpaceshipEnum.COLONY) != null;
    }

    //public boolean canCarryResources() {
        return getShip(SpaceshipEnum.SUPPLIER) != null;
    }

    public Spaceship getShip(SpaceshipEnum ship) {
        return spaceships[ship.ordinal()];
    }
     */

    public int getMinSpeed() {
        return minSpeed;
    }

    public int getTotalConsumption() {
        return totalConsumption;
    }

    /*
    public Spaceship[] getSpaceships() {
        return spaceships.clone();
    }
     */
    public ArrayList<Spaceship> getSpaceships(){ return (ArrayList<Spaceship>) spaceships.clone(); }
    /*
    public int getTotalShipNumber() { return spaceships.length; }
     */
    public int getTotalShipNumber() { return spaceships.size(); }
    public int getTransportedResources(){ return transportedResources; }

    public void modifyTransportedResources( int ammountOfChange )
    {
        this.transportedResources += ammountOfChange;
    }

    public int getMaxTransportedResources()
    {
        int capacity = 0;
        for ( int iship = 0 ; iship < spaceships.size(); ++iship )
        {
            capacity += spaceships.get(iship).spaceship.transportCapacity;
        }
        return capacity;
    }

    public int getNumberOf( SpaceshipEnum ship )
    {
        int numberOfShip = 0;
        for( int iship = 0 ; iship < spaceships.size() ; ++iship )
        {
            if( spaceships.get(iship).spaceship == ship ) ++numberOfShip;
        }
        return numberOfShip;
    }

    public Player getOwner() {
        return owner;
    }

    public int getId(){ return this.id; }
    public int getTotalHP()
    {
        int totalHP = 0;
        for( int iship = 0 ; iship < spaceships.size() ; ++iship )
        {
            totalHP += spaceships.get( iship ).spaceship.getHealthPoint();
        }
        return totalHP;
    }
    public int getTotalOffense()
    {
        int totalOffense = 0;
        for( int iship = 0 ; iship < spaceships.size() ; ++iship )
        {
            totalOffense += spaceships.get( iship ).spaceship.offensiveForce;
        }
        return totalOffense;
    }
    public int getTotalDefense()
    {
        int totalDefense = 0;
        for( int iship = 0 ; iship < spaceships.size() ; ++iship )
        {
            totalDefense += spaceships.get( iship ).spaceship.protectiveForce;
        }
        return totalDefense;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public static void main(String[] args) {
        var fleet = new Fleet(1, 3, new Player(1, "John"));
        fleet.addShip(new Spaceship(SpaceshipEnum.MOTHER_SHIP));
        fleet.replace(2,3);
    }
}
