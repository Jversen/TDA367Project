package com.jupiter.rogue.Model.Creatures;

/**
 * Created by Johan on 16/04/15.
 */
public abstract class Creature {
    protected Position position;
    protected int direction;
    protected int currentHealthPoints;
    protected int maxHealthPoints;
    protected int attackPoints;
    protected int movementSpeed;
    enum MovementState {STANDING, WALKING, JUMPING, CROUCHING}
    private MovementState movementState = MovementState.STANDING;


    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        //TODO finish method
    }

    public void setPosition(int x, int y) {
        this.position.setXPos(x);
        this.position.setYPos(y);
    }

    public MovementState getMovementState() {
        return movementState;
    }

    public void setMovementStateStanding() {
        movementState = movementState.STANDING;
    }

    public void setMovementStateWalking() {
        movementState = movementState.WALKING;
    }

    public int getCurrentHealthPoints() {
        return currentHealthPoints;
    }

    public int getMaxHealthPoints() {
        return maxHealthPoints;
    }

    public void setHealthPoints(int HP) {
        if(this.maxHealthPoints >= HP || HP >= 0) {
            this.currentHealthPoints = HP;
        }
    }

    public void decreaseHealthPoints(int HP) {
        if(HP > 0) {
            this.currentHealthPoints -= HP;
        }
        if(currentHealthPoints < 0) {
            currentHealthPoints = 0;
        }
    }

}
