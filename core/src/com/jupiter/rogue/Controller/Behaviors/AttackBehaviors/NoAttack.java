package com.jupiter.rogue.Controller.Behaviors.AttackBehaviors;

import com.jupiter.rogue.Utils.Enums.Direction;

/**
 * Created by Oskar on 2015-05-18.
 */
public class NoAttack implements AttackBehavior {

    //Method for enemies who can't attack at all. (Neutral npcs? Just in case.)
    public void attack(Direction direction){
        //does nothing, carry on
    }

}
