package com.jupiter.rogue.Controller.Behaviors.AttackedBehaviors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.jupiter.rogue.Utils.Enums.Direction;
import com.jupiter.rogue.Controller.Behaviors.Behavior;

/**
 * Created by Oskar on 2015-05-19.
 */
public class Impact extends Behavior implements AttackedBehavior {

    public Impact(Body body) {
        this.body = body;
    }

    public void impact(Direction heroDir){
        if(heroDir == Direction.LEFT){
            body.applyLinearImpulse(new Vector2(-6f, 0f), body.getPosition(), false);
            body.setLinearVelocity(3, body.getLinearVelocity().y);
            body.setLinearVelocity(-3, body.getLinearVelocity().x);
        } else {
            body.applyLinearImpulse(new Vector2(6f, 0f), body.getPosition(), false);
            body.setLinearVelocity(3, body.getLinearVelocity().y);
            body.setLinearVelocity(3, body.getLinearVelocity().x);
        }
    }
}
