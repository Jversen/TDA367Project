package com.jupiter.rogue.Controller.Behaviors.AttackedBehaviors;

import com.jupiter.rogue.Model.Enums.Direction;

/**
 * Created by Oskar on 2015-05-19.
 */
//Interface which is implemented by all AttackedBehaviors.
public interface AttackedBehavior {

    void impact(Direction direction);

}
