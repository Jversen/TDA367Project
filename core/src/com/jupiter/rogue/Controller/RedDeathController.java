package com.jupiter.rogue.Controller;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.jupiter.rogue.Model.Creatures.Enemy;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Model.Creatures.RedDeath;
import com.jupiter.rogue.Model.Enums.Direction;
import com.jupiter.rogue.Model.Enums.MovementState;
import com.jupiter.rogue.Model.Map.Position;
import com.jupiter.rogue.Utils.AIBehaviors.AttackBehaviors.MeleeAttack;
import com.jupiter.rogue.Utils.AIBehaviors.JumpBehaviors.NormalJump;
import com.jupiter.rogue.Utils.AIBehaviors.MoveBehaviors.MoveBehavior;
import com.jupiter.rogue.Utils.AIBehaviors.MoveBehaviors.Walk;
import com.jupiter.rogue.Utils.WorldConstants;
import com.jupiter.rogue.View.RedDeathView;

import static com.jupiter.rogue.Utils.WorldConstants.PPM;

/**
 * Created by Johan on 17/04/15.
 */
@lombok.Data
public class RedDeathController extends EnemyController{

    private Position startPosition;

    public RedDeathController(float xPos, float yPos, int level, boolean elite) {
        RedDeath redDeath = new RedDeath(xPos, yPos, level, elite);
        this.enemy = redDeath;
        this.enemyView = new RedDeathView(redDeath);
        startPosition = enemy.getPosition();
        initBody();

    }

    @Override
    public void initBody() {

        //creates a shapeless body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(enemy.getX() / PPM, enemy.getY() / PPM);

        //creates the shape of the bodyDef
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(10 / PPM, 20 / PPM); //temporary values, should be dependent on sprite size

        // FixtureDef sets physical properties
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 100f;
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = 0.0f;

        body = WorldConstants.CURRENT_WORLD.createBody(bodyDef);
        body.setUserData(this);
        body.createFixture(fixtureDef).setUserData("enemy"); //naming the fixture

        WorldConstants.BODIES.add(body);

        enemy.setMoveBehavior(new Walk(body));
        enemy.setAttackBehavior(new MeleeAttack(body));
        enemy.setJumpBehavior(new NormalJump(body));

        //disposes shape to save memory
        shape.dispose();

    }

    @Override
    public void update(){
        updatePhysics(); //Unnecessary?

        enemy.setEnemyDirection();

        if(heroNotNear()) {
            enemy.setMovementState(MovementState.STANDING);
        }else if(!heroInRange() && !heroNotNear()){
            enemy.setMovementState(MovementState.WALKING);
            enemy.performMove();
        }
        else {
            enemy.performAttack();
        }
    }

    private void updatePhysics() {
        Position physPos = new Position(body.getPosition().x, body.getPosition().y);
        enemy.setPosition(physPos);
    }
}
