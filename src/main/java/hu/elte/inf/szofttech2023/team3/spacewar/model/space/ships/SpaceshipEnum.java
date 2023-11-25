package hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships;

public enum SpaceshipEnum {
    MOTHER_SHIP(3,4,3,5,4,4,0),
    HUNTER      (3,2,4,2,2,2,1),
    CRUISER     (4,3,5,3,3,3,1),
    FRIGATE     (4,4,3,4,4,4,2),
    COLONY      (2,3,3,4,3,3,2),
    SUPPLIER    (1,2,3,2,1,2,2);

    public final int offensiveForce;
    public final int protectiveForce;
    public final int speed;
    private int healthPoint;
    public final int metalCost;
    public final int consumption;
    public final int minLevelToBuild;

    SpaceshipEnum(int offensiveForce, int protectiveForce,
                  int speed, int healthPoint,
                  int metalCost, int consumption, int minLevelToBuild) {
        this.offensiveForce = offensiveForce;
        this.protectiveForce = protectiveForce;
        this.speed = speed;
        this.healthPoint = healthPoint;
        this.metalCost = metalCost;
        this.consumption = consumption;
        this.minLevelToBuild = minLevelToBuild;
    }

    public void decreaseHealthPoint(int point) {
        healthPoint -= point;
    }

    public int getHealthPoint() {
        return healthPoint;
    }
}
