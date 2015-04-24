package com.jupiter.rogue.Model.Creatures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.jupiter.rogue.Model.Enums.MovementState;
import com.jupiter.rogue.Model.Items.MeleeWeapon;
import com.jupiter.rogue.Model.Items.RangedWeapon;

/**
 * Created by Johan on 16/04/15.
 */
@lombok.Data
public class Hero extends Creature {

    // Singleton-instance of hero
    private static Hero instance = null;

    // Inventory-spots
    private MeleeWeapon meleeWeapon;
    private RangedWeapon rangedWeapon;

    private Hero (float xPos, float yPos) {

        this.maxHealthPoints = 100;
        this.currentHealthPoints = maxHealthPoints;
        this.movementSpeed = scale*100;

        spriteBatch = new SpriteBatch();
        initAnimation();

        //TODO finish rest of stats
    }

    private void initAnimation() {
        spriteSheet = new Texture(Gdx.files.internal("Data//pixHeroAtlas.png"));
        atlas = new TextureAtlas("Data//pixHeroAtlas.atlas");

        createPhysics();

        scale = 1f;

        animation = new Animation(1/15f, atlas.getRegions());
        stateTime = 0f;
    }

    private void createPhysics() {

    }

    public void updateAnimation(float deltaTime){
        stateTime += deltaTime;
        currentFrame = animation.getKeyFrame(stateTime, true);

        spriteBatch.begin();
        spriteBatch.draw(currentFrame, getX(), getY());
        spriteBatch.end();
    }

    public static Hero getInstance() {
        if(instance == null) {
            instance = new Hero(100,100);
        }
        return instance;
    }


}
