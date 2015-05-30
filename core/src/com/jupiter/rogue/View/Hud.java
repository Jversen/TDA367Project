package com.jupiter.rogue.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.jupiter.rogue.Model.Creatures.Hero;
import com.jupiter.rogue.Model.Map.Map;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Johan on 2015-05-20.
 */
public class Hud extends Actor {

    private static Hud instance = null;
    private Texture texture;
    private Sprite healthBar;
    private Sprite expBar;
    private BitmapFont font;
    private BitmapFont heroLevelFont;
    private BitmapFont meleeWeaponFont;
    private BitmapFont rangeWeaponFont;
    float currentHP;
    float maxHP;
    float currentExp;
    float expRoof;
    int startGfxWidth;
    int startGfxHeight;
    String level;
    String heroLevel;
    String meleeWeapon;
    String rangeWeapon;

    private Hud(){

        //startGfxWidth = 320;    //A hack. Set this to the actual game resolution later.
       // startGfxHeight = 180;

        font = new BitmapFont();    //Creates new font
        font.setColor(Color.WHITE);
        font.setScale(0.5f);

        heroLevelFont = new BitmapFont();
        heroLevelFont.setColor(Color.WHITE);
        heroLevelFont.setScale(0.25f);

        meleeWeaponFont = new BitmapFont();
        meleeWeaponFont.setColor(Color.WHITE);
        meleeWeaponFont.setScale(0.4f);

        rangeWeaponFont = new BitmapFont();
        rangeWeaponFont.setColor(Color.WHITE);
        rangeWeaponFont.setScale(0.4f);

        texture = new Texture(Gdx.files.internal("Data/HUD/healthBar.png"));
        healthBar = new Sprite(texture, 0, 0, 25, 3);
        healthBar.setPosition(10, 170);
        healthBar.setOrigin(0, 0);

        texture = new Texture(Gdx.files.internal("Data/HUD/expBar.png"));
        expBar = new Sprite(texture, 0, 0, 25, 3);
        expBar.setPosition(10, 160);
        expBar.setOrigin(0, 0);
    }

    public static Hud getInstance() {
        if(instance == null) {
            instance = new Hud();
        }
        return instance;
    }

    @Override
    public void draw(Batch batch, float alpha){

        updateHud();
        healthBar.draw(batch);
        expBar.draw(batch);
        font.draw(batch, level, 250, 170);
        heroLevelFont.draw(batch, heroLevel, 10, 168);
        meleeWeaponFont.draw(batch, meleeWeapon, 50, 175);
        rangeWeaponFont.draw(batch, rangeWeapon, 50, 165);
    }

    public void updateHud(){

        currentHP = (float)Hero.getInstance().getCurrentHealthPoints();
        maxHP = (float)Hero.getInstance().getMaxHealthPoints();
        currentExp = (float)Hero.getInstance().getExperiencePoints();
        expRoof = (float)Hero.getInstance().getExperienceRoof();

        if(currentHP >= 0) {
            healthBar.setScale(currentHP / maxHP, 1);
        }
        expBar.setScale(currentExp / expRoof, 1);

        /* Just some number for show. Should discuss this design of showing current room.*/
        level = ("Room: " + Map.getInstance().getCurrentRoomNbr());
        heroLevel = ("Hero level : " + Hero.getInstance().getLevel());
        meleeWeapon = ("Melee Weapon: " + Hero.getInstance().getMeleeWeapon().getWeaponName());
        if(Hero.getInstance().getRangedWeapon() != null) {
            rangeWeapon = ("Range Weapon: " + Hero.getInstance().getRangedWeapon().getWeaponName());
        }
        else{
            rangeWeapon = "Range Weapon: none equipped";
        }
    }
}
