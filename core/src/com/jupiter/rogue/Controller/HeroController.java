package com.jupiter.rogue.Controller;

import com.badlogic.gdx.Gdx;
import com.jupiter.rogue.Model.Creatures.Creature;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Model.Map.Position;
import com.jupiter.rogue.Model.Enums.Direction;
import com.jupiter.rogue.Model.Enums.MovementState;
import com.jupiter.rogue.Model.World.WorldConstants;

/**
 * Created by hilden on 2015-04-17.
 */
@lombok.Data
public class HeroController {

    private Hero hero = Hero.getInstance();
    Position heroPosition = hero.getPosition();
    private boolean isGrounded = false;
    WorldConstants constants = WorldConstants.getInstance();

    public void update(){
        
    }
    public void relax() {
        hero.setMovementState(MovementState.STANDING);
    }

    public void worldEffects() {

       // if (!isGrounded) {
                hero.setVerticalSpeed(hero.getVerticalSpeed() - constants.getGravity() * Gdx.graphics.getDeltaTime());
            hero.setPosition(hero.getPosition().getXPos(), hero.getPosition().getYPos() + hero.getVerticalSpeed());
      //  }

        //System.out.println((hero.getPosition().getYPos()));
        //System.out.println(hero.getVerticalSpeed());

        if (hero.getPosition().getYPos() < 0){
            hero.setYPos(0);
          //      System.out.println(hero.getVerticalSpeed());
            hero.setVerticalSpeed(0);
            isGrounded = true;
        }


    }
    public void walk(Direction direction) {
        hero.setMovementState(MovementState.WALKING);
        hero.setDirection(direction);
        float newPosX = 0;
        float newPosY = 0;

        if(walkIsPossible(direction, heroPosition)) {

            if(direction == Direction.RIGHT) {
                newPosX = heroPosition.getXPos() + hero.getMovementSpeed() * Gdx.graphics.getDeltaTime();
            } else {
                newPosX = heroPosition.getXPos() - hero.getMovementSpeed() * Gdx.graphics.getDeltaTime();
            }
            hero.setPosition(newPosX, heroPosition.getYPos());
        }
    }

    private boolean walkIsPossible(Direction direction, Position heroPosition) {
        return true;
    }

    public void jumpIfPossible() {
       // hero.setMovementState(MovementState.JUMPING);
       // if (!(hero.getMovementState().equals(MovementState.JUMPING))) {
            jump();
      //  }
    }

    private void jump() {
        isGrounded = false;
        hero.setVerticalSpeed(10);
    }

    public void attack() {
        //TODO finish walk() method
    }
}
