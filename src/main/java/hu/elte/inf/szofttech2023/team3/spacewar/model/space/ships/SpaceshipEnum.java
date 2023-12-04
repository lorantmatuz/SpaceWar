package hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships;

public enum SpaceshipEnum {
    MOTHER_SHIP(3,4,10,5,4,4,0, 0,3),
    HUNTER      (3,2,5,2,2,2,1, 0, 1),
    CRUISER     (4,3,1,3,3,3,1, 0, 2),
    FRIGATE     (4,4,10,4,4,4,2, 0, 2),
    COLONY      (2,3,10,4,3,3,2, 0, 1),
    SUPPLIER    (1,2,10,2,1,2,2, 50, 1);

    public final int offensiveForce;
    public final int protectiveForce;
    public final int speed;
    public final int maxHealthPoint;
    public final int metalCost;
    public final int consumption;
    public final int minLevelToBuild;
    public final int transportCapacity;
    public final int turnsToComplete;

    SpaceshipEnum(int offensiveForce, int protectiveForce,
                  int speed, int healthPoint,
                  int metalCost, int consumption, int minLevelToBuild,
                  int transportCapacity, int turnsToComplete ) {
        this.offensiveForce = offensiveForce;
        this.protectiveForce = protectiveForce;
        this.speed = speed;
        this.maxHealthPoint = healthPoint;
        this.metalCost = metalCost;
        this.consumption = consumption;
        this.minLevelToBuild = minLevelToBuild;
        this.transportCapacity = transportCapacity;
        this.turnsToComplete = turnsToComplete;
    }

    public int getHealthPoint() { return maxHealthPoint; }
    public int getOffensiveForce(){ return offensiveForce; }
    public int getProtectiveForce(){ return  protectiveForce; }
    public int getSpeed(){ return speed; }
    public int getConsumption(){ return consumption; }
    public int getMinLevelToBuild(){ return minLevelToBuild; }
    public int getTransportCapacity() { return transportCapacity; }
    public int getTurnsToComplete(){ return turnsToComplete; }
    public int getMetalCost(){ return metalCost; }
    
}
