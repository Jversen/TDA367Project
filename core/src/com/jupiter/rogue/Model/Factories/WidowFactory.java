package com.jupiter.rogue.Model.Factories;

import com.jupiter.rogue.Controller.EnemyController;
import com.jupiter.rogue.Controller.WidowController;
import com.jupiter.rogue.Model.Creatures.Enemy;
import com.jupiter.rogue.Model.Creatures.Widow;

/**
 * Created by Johan on 2015-05-16.
 */
public class WidowFactory implements EnemyFactory {

    String enemyType = "widow";

    @Override
    /**
     * creates an enemy
     * @see implemented interface
     */
    public Enemy createEnemy(float xPos, float yPos, int level, boolean elite){
        Enemy enemy = new Widow(xPos, yPos, level, elite);
        return enemy;
    }
    /**
     * gets the enemy type
     * @return a string with the enemy type
     */
    public String getEnemyType(){
        return enemyType;
    }
}
