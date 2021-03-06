package com.jupiter.rogue.Controller;

import com.badlogic.gdx.physics.box2d.*;
import com.jupiter.rogue.Model.Chests.Chest;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Utils.Enums.MovementState;
import com.jupiter.rogue.Model.Map.Map;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hilden on 2015-04-26.
 */
public class MyContactListener implements ContactListener {

    private Hero hero;
    private Map map;

    //The two fixtures touching.
    private Fixture fa;
    private Fixture fb;

    public MyContactListener() {
        hero = Hero.getInstance();
        map = Map.getInstance();
    }

    @Override
    public void beginContact(Contact contact) {


        fa = contact.getFixtureA();
        fb = contact.getFixtureB();

        checkChestContact();

        if ((fa.getUserData().equals("foot") && fb.getUserData().equals("obstacle")) || (fb.getUserData().equals("foot") && fa.getUserData().equals("obstacle"))) {
            hero.setCreatureGrounded(true);
            hero.setCreatureFalling(false);
        }

        if (fa.getUserData() instanceof HeroController || fb.getUserData() instanceof HeroController) {
            Object fixtureA = fa.getUserData();
            Object fixtureB = fb.getUserData();

            if(fixtureA instanceof String && ((String)fixtureA).length() == 2) {
                map.flagRoomForDestruction((String)fixtureA);
            } else if(fixtureB instanceof String && ((String)fixtureB).length() == 2) {
                map.flagRoomForDestruction((String)fixtureB);
            }
        }

        //Removing all projectiles that hit walls.
        if (fa.getBody().getUserData().equals("projectile") && fb.getUserData().equals("obstacle")) {
            fa.getBody().setUserData("dead");
        } else if (fb.getBody().getUserData().equals("projectile") && fa.getUserData().equals("obstacle")) {
            fb.getBody().setUserData("dead");
        }

        //Checking if an enemy is taking damage from the heroes weapon.
        if (fa.getUserData().equals("weaponSensor") && (fb.getBody().getUserData() instanceof EnemyController) && (!fb.getUserData().equals("enemyHitbox"))) {
            ((EnemyController) fb.getBody().getUserData()).getEnemy().takeDamage(hero.getCurrentWeapon().getDamage());
            ((EnemyController) fb.getBody().getUserData()).getTakeDamageBehavior().impact(hero.getDirection());
        } else if ((fa.getBody().getUserData() instanceof EnemyController) && (fb.getUserData().equals("weaponSensor")) && (!fa.getUserData().equals("enemyHitbox"))) {
            ((EnemyController) fa.getBody().getUserData()).getEnemy().takeDamage(hero.getCurrentWeapon().getDamage());
            ((EnemyController) fa.getBody().getUserData()).getTakeDamageBehavior().impact(hero.getDirection());
        }

        if (fa.getUserData() instanceof HeroController && fb.getBody().getUserData() instanceof EnemyController) {
            hero.takeDamage(((EnemyController) fb.getBody().getUserData()).getEnemy().getAttackPoints());
            ((HeroController)fa.getUserData()).getTakeDamageBehavior().impact(hero.getDirection());
        } else if (fb.getUserData() instanceof HeroController && fa.getBody().getUserData() instanceof EnemyController) {
            hero.takeDamage(((EnemyController) fa.getBody().getUserData()).getEnemy().getAttackPoints());
            ((HeroController)fb.getUserData()).getTakeDamageBehavior().impact(hero.getDirection());
        }


        //Removing all projectiles that hit walls.
        if (fa.getBody().getUserData().equals("projectile") && fb.getUserData().equals("obstacle")) {
            fa.getBody().setUserData("dead");
        } else if (fb.getBody().getUserData().equals("projectile") && fa.getUserData().equals("obstacle")) {
            fb.getBody().setUserData("dead");
        }

        //Checking if an enemy is taking damage from the heroes weapon.
        if (fa.getUserData().equals("weaponSensor") && (fb.getUserData() instanceof EnemyController) && (!fb.getUserData().equals("enemyHitbox"))) {
            ((EnemyController) fb.getUserData()).getEnemy().takeDamage(hero.getCurrentWeapon().getDamage());
            ((EnemyController) fb.getUserData()).getTakeDamageBehavior().impact(hero.getDirection());
        } else if ((fa.getUserData() instanceof EnemyController) && (fb.getUserData().equals("weaponSensor")) && (!fa.getUserData().equals("enemyHitbox"))) {
            ((EnemyController) fa.getUserData()).getEnemy().takeDamage(hero.getCurrentWeapon().getDamage());
            ((EnemyController) fa.getUserData()).getTakeDamageBehavior().impact(hero.getDirection());
        }

    }

    @Override
    public void endContact(Contact contact) {

        fa = contact.getFixtureA();
        fb = contact.getFixtureB();

        //Foot sensor, keeps track of jump etc.
        if ((fa.getUserData().equals("foot") && fb.getUserData().equals("obstacle")) ||
                (fa.getUserData().equals("obstacle") && fb.getUserData().equals("foot"))) {
            hero.setCreatureGrounded(false);
            if (hero.getMovementState() != MovementState.JUMPING) {
                hero.setCreatureFalling(true);
            }
        }

        if ((fa.getUserData() instanceof HeroController && fb.getUserData() instanceof ChestController) ||
                (fa.getUserData() instanceof ChestController && fb.getUserData() instanceof HeroController)) {
            hero.setTouchingChest(false);
        }


    }

    /**
     * Checks if hero collides with a chest. If so, display the description of the Chest's item.
     */
    private void checkChestContact(){

        Chest chest;

            if (fa.getUserData() instanceof HeroController &&
                    fb.getUserData() instanceof ChestController) {
                chest = (((ChestController) fb.getUserData()).chest);
                hero.setUsableChest(chest);
                hero.setTouchingChest(true);

                if (chest.isOpened() && !chest.isEmpty()) {
                    //System.out.println(chest.getContent().getDescription());
                }

            } else if (fb.getUserData() instanceof HeroController &&
                    fa.getUserData() instanceof ChestController) {
                chest = (((ChestController) fa.getUserData()).chest);
                hero.setUsableChest(chest);
                hero.setTouchingChest(true);

                if (chest.isOpened() && !chest.isEmpty()) {
                   // System.out.println(chest.getContent().getDescription());
                }

            }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
