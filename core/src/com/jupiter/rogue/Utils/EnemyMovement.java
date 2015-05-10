package com.jupiter.rogue.Utils;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.jupiter.rogue.Controller.HeroController;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Model.Enums.Direction;
import com.jupiter.rogue.Model.Map.Position;
import java.lang.Math;
import com.jupiter.rogue.Model.Creatures.Enemy;

import static com.jupiter.rogue.Utils.WorldConstants.PPM;


/**
 * Created by oskar on 05/05/2015.
 */
public class EnemyMovement implements Movement {

    private Body body;

    public EnemyMovement(Body body) {
        this.body = body;
    }

    public Body getBody() {
        return body;
    }

    public Position getPosition() {
        if(body != null) {
            return new Position(body.getPosition().x, body.getPosition().y);
        }
        return null;
    }


    @Override
    public void jump(){
        body.setLinearVelocity(body.getLinearVelocity().x, 6);
    }


    public void walk(Direction direction, float moveSpeed) {
        if(direction == Direction.RIGHT) {
            body.applyLinearImpulse(new Vector2(moveSpeed*3,0f), body.getPosition(), true);
            if(body.getLinearVelocity().x > moveSpeed) {
                body.setLinearVelocity(moveSpeed, body.getLinearVelocity().y);
            }
        } else {
            body.applyLinearImpulse(new Vector2(-moveSpeed*3,0f), body.getPosition(), true);
            if(body.getLinearVelocity().x < -moveSpeed) {
                body.setLinearVelocity(-moveSpeed, body.getLinearVelocity().y);
            }
        }
    }
    //Method for flying enemy movements.
    public void fly(Direction direction, float angle, float moveSpeed) {
        if (direction == Direction.RIGHT) {
            body.applyLinearImpulse(new Vector2((float)Math.cos(angle)*moveSpeed, (float)Math.sin(angle)*moveSpeed), body.getPosition(), true);

        } else {
            body.applyLinearImpulse(new Vector2(-(float)Math.cos(angle)*moveSpeed, (float)Math.sin(angle)*moveSpeed), body.getPosition(), true);
        }
    }

    public void attack(){

    }

    public void attack(Direction direction){
        body.setLinearVelocity(0.0f, body.getLinearVelocity().y);
        if(direction == Direction.LEFT){
            if(Math.abs(this.getBody().getPosition().y - (Hero.getInstance().getY())) <= 20/PPM){
                WorldConstants.BODIES.get(0).applyLinearImpulse(new Vector2(-40f, 0f), WorldConstants.BODIES.get(0).getPosition(), false);
                if(WorldConstants.BODIES.get(0).getLinearVelocity().x < -6) {
                    WorldConstants.BODIES.get(0).setLinearVelocity(-6, body.getLinearVelocity().y);
                }
            }
            else{
                WorldConstants.BODIES.get(0).applyLinearImpulse(new Vector2(-40f, 0f), WorldConstants.BODIES.get(0).getPosition(), false);
                if(WorldConstants.BODIES.get(0).getLinearVelocity().x < -6) {
                    WorldConstants.BODIES.get(0).setLinearVelocity(-6, body.getLinearVelocity().y);
                }
            }
        }
        else{
            if(Math.abs(this.getBody().getPosition().y - (Hero.getInstance().getY())) <= 20/PPM){
                WorldConstants.BODIES.get(0).applyLinearImpulse(new Vector2(40f, 0f), WorldConstants.BODIES.get(0).getPosition(), false);
                if(WorldConstants.BODIES.get(0).getLinearVelocity().x > 6) {
                    WorldConstants.BODIES.get(0).setLinearVelocity(6, body.getLinearVelocity().y);
                }
            }
            else{
                WorldConstants.BODIES.get(0).applyLinearImpulse(new Vector2(40f, 0f), WorldConstants.BODIES.get(0).getPosition(), false);
                if(WorldConstants.BODIES.get(0).getLinearVelocity().x > 6) {
                    WorldConstants.BODIES.get(0).setLinearVelocity(6, body.getLinearVelocity().y);
                }
            }
        }
    }
}
