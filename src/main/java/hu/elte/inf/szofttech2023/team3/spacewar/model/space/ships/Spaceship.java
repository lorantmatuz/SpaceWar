package hu.elte.inf.szofttech2023.team3.spacewar.model.space.ships;


public class Spaceship {
    public final SpaceshipEnum spaceship;

    public Spaceship(SpaceshipEnum spaceship) {
        this.spaceship = spaceship;
    }

    /**
     * @param defender the defender Spaceship in the battle
     * @return true if the defender is destroyed after the attack
     */
    public boolean attack(Spaceship defender) {
        defender.spaceship.decreaseHealthPoint(spaceship.offensiveForce);
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
        return spaceship.getHealthPoint() < 0;
    }
}
