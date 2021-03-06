package com.jupiter.rogue.Model.Creatures;

/**
 * Created by Johan on 2015-04-18.
 */
@lombok.Data
public class RedDeath extends Enemy {
    /**
     * creates the enemy RedDeath
     * @param xPos position in the x axis
     * @param yPos position in the y axis
     * @param level desired level
     * @param elite elite status
     */
    public RedDeath(float xPos, float yPos, int level, boolean elite) {

        super(100, 100, 25, 25, 1, 6, true, xPos, yPos, level, elite);

        this.enemyType = "redDeath";
        this.attackHitBoxWidth = 5;
        this.attackHitBoxHeight = 5;
        this.attackHitBoxX = 9;
        this.attackHitBoxY = 5;

        this.bodyWidth = 9;
        this.bodyHeight = 14;
        this.bodyY = 0;

        this.xpValue = 50;

        this.movementSpeed = 2.5f;
    }
}
