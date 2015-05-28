package com.jupiter.rogue.Model.Factories;

import com.jupiter.rogue.Controller.EnemyController;
import com.jupiter.rogue.Controller.RedDeathController;
import com.jupiter.rogue.Model.Creatures.Enemy;
import com.jupiter.rogue.Model.Creatures.RedDeath;


/**
 * Created by Johan on 2015-05-17.
 */
public class RedDeathFactory implements EnemyFactory {

    String enemyType = "redDeath";

    @Override
    public Enemy createEnemy(float xPos, float yPos, int level, boolean elite) {
        Enemy enemy = new RedDeath(xPos, yPos, level, elite);
        return enemy;
    }

    public String getEnemyType() {
        return enemyType;
    }
}