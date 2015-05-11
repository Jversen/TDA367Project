package com.jupiter.rogue.Utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.jupiter.rogue.Model.Map.Position;

import java.util.ArrayList;

/**
 * Created by Johan on 2015-04-19.
 */
@lombok.Data
public final class WorldConstants {

    public static final float PPM = 32f; //Pixels per meter conversion unit
    public static final Position HERO_START_POSITION = new Position(75,200);
    public static final Vector2 HERO_JUMP_VECTOR = new Vector2(0, 10f);
    public static final float GRAVITY = -10f;
    public static final Vector2 GRAVITY_VECTOR = new Vector2(0, GRAVITY);
    public static World CURRENT_WORLD = new World(GRAVITY_VECTOR, true);
    public static int WIDTH;
    public static int HEIGHT;
    public static int MAP_SIZE = 3;
    public static ArrayList<Body> BODIES = new ArrayList<>();
    public static ArrayList<Joint> JOINTS = new ArrayList<>();
}
