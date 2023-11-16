package hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships;

public enum Spaceship {
    MOTHER_SHIP(3,4,3,5,4,4,0),
    HUNTER      (3,2,4,2,2,2,1),
    CRUISER     (4,3,5,3,3,3,1),
    FRIGATE     (4,4,3,4,4,4,2),
    COLONY      (2,3,3,4,3,3,2),
    SUPPLIER    (1,2,3,2,1,2,2);

    public final int offensiveForce;
    public final int protectiveForce;
    public final int speed;
    public final int metalCost;
    public final int consumption;
    public final int minLevelToBuild;
    private int healthPoint;

    Spaceship(int offensiveForce, int protectiveForce,
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

    /**
     * @param defender the defender Spaceship in the battle
     * @return true if the defender is destroyed after the attack
     */
    public boolean attack(Spaceship defender) {
        defender.healthPoint -= offensiveForce;
        return defender.isDestroyed();
    }

    /**
     * @param attacker the attacker Spaceship in the battle
     * @return true if the defender is destroyed after the attack
     */
    public boolean defend(Spaceship attacker) {
        return attacker.attack(this);
    }

    /**
     * @return true if the Spaceship is destroyed
     */
    public boolean isDestroyed() {
        return healthPoint < 0;
    }
}
