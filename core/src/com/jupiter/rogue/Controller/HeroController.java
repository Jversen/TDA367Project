package com.jupiter.rogue.Controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.*;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Model.Map.Position;
import com.jupiter.rogue.Model.Enums.Direction;
import com.jupiter.rogue.Model.Enums.MovementState;
import com.jupiter.rogue.Model.Map.WorldConstants;
import com.jupiter.rogue.Model.Map.WorldHolder;

import static com.jupiter.rogue.Model.Map.WorldConstants.PPM;
import java.util.ArrayList;

/**
 * Created by hilden on 2015-04-17.
 */
@lombok.Data
public class HeroController {

    private Hero hero;
    private WorldHolder worldHolder;

    public HeroController() {
        initHero();
    }

    public void initHero() {

        hero = hero.getInstance();

        Position startPosition = WorldConstants.HERO_START_POSITION;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;

        bodyDef.position.set(startPosition.getXPos() / PPM, startPosition.getYPos() / PPM);

        PolygonShape boundingBox = new PolygonShape();
        boundingBox.setAsBox(10 / PPM, 10 / PPM); //temporary values, should be dependent on sprite size

        // FixtureDef sets physical properties
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = boundingBox;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.95f;
        fixtureDef.restitution = 0.0f;

        Body body = worldHolder.getInstance().getWorld().createBody(bodyDef);
        body.createFixture(fixtureDef).setUserData("hero"); //naming the herofixture hero.


        hero.setBody(body);

        boundingBox.dispose();
    }

    public void update(ArrayList<Integer> keys){
        checkJumping();
        updateMoves(keys);
    }


    //TODO: rewrite when box2d-ground is properly implemented
    private void checkJumping() {

/*        if(hero.getY() <= 0f) {
            hero.setGrounded(true);
            hero.setY(0);
        }*/
    }

    private void updateMoves(ArrayList<Integer> keys) {
        if(keys.contains(Input.Keys.LEFT) && !keys.contains(Input.Keys.RIGHT)) {
            hero.walk(Direction.LEFT);
        }
        if(!keys.contains(Input.Keys.LEFT) && keys.contains(Input.Keys.RIGHT)) {
            hero.walk(Direction.RIGHT);
        }
        if(keys.contains(Input.Keys.SPACE)) {
            hero.jump();
        }
        if(keys.isEmpty()) {
            hero.relax();
        }
    }


}
