package hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships;

public enum Spaceship {
    MOTHERSHIP  (3,4,3,5,4,4),
    HUNTER      (3,2,4,2,2,2),
    CRUISER     (4,3,5,3,3,3),
    FRIGATE     (4,4,3,4,4,4),
    COLONY      (2,3,3,4,3,3),
    SUPPLIER    (1,2,3,2,1,2);

    final int offensiveForce;
    final int protectiveForce;
    final int speed;
    final int consumption;
    final int metalCost;
    private int healthPoint;

    Spaceship(int offensiveForce, int protectiveForce,
              int speed, int healthPoint,
              int metalCost, int consumption) {
        this.offensiveForce = offensiveForce;
        this.protectiveForce = protectiveForce;
        this.speed = speed;
        this.healthPoint = healthPoint;
        this.metalCost = metalCost;
        this.consumption = consumption;
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
