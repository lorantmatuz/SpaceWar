package hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships;


public class Spaceship {
    public final SpaceshipEnum spaceship;
    private int healthPoint;
    private SpaceshipEnum type;


    public Spaceship(SpaceshipEnum spaceship) {
        this.spaceship = spaceship;
        this.healthPoint = spaceship.maxHealthPoint;
    }

    /**
     * @param defender the defender Spaceship in the battle
     * @return true if the defender is destroyed after the attack
     */
    public boolean attack(Spaceship defender) {
        return defender.defend(this);
    }

    /**
     * @param attacker the attacker Spaceship in the battle
     * @return true if the defender is destroyed after the attack
     */
    public boolean defend(Spaceship attacker) {
        final var point = attacker.spaceship.offensiveForce + 2 - spaceship.protectiveForce;
        healthPoint -= point;
        if(point < 0) {
            System.exit(2);
        }
        if(healthPoint < 0) {
            healthPoint = 0;
        }
        return isDestroyed();
    }

    /**
     * @return true if the Spaceship is destroyed
     */
    public boolean isDestroyed() {
        return healthPoint <= 0;
    }

    public int getHealthPoint() {
        return healthPoint;
    }

}
