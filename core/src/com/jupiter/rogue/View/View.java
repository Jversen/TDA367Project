package com.jupiter.rogue.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.jupiter.rogue.Controller.RedDeathController;
import com.jupiter.rogue.Controller.WidowController;
import com.jupiter.rogue.Model.Chests.Chest;
import com.jupiter.rogue.Model.Creatures.*;
import com.jupiter.rogue.Model.Map.Map;
import com.jupiter.rogue.Utils.WorldConstants;

import java.util.ArrayList;
import java.util.List;

import static com.jupiter.rogue.Utils.WorldConstants.PPM;

/**
 * Created by oskar on 17/04/2015.
 */
@lombok.Data
public class View implements Screen{

    private static View instance = null;
    private Texture img;
    private TiledMap tiledMap;
    private OrthographicCamera camera;
    private OrthographicCamera b2dCam;
    private TiledMapRenderer tiledMapRenderer;
    private float w;
    private float h;
    private Map map;
    private SpriteBatch batch;
    private Stage stage; //Scene2d stage
    private List<EnemyView> enemyViews;
    private List<Enemy> enemies;
    private boolean showAttributeMenu = false;
    private List<ChestView> chestViews;
    private List<Chest> chests;
    private boolean showDebugInfo = true;
    private ChestView chestView;

    ExtendViewport vp;

    private HeroView heroView;

    Box2DDebugRenderer debugRenderer;
    AttributeMenu attributeMenu = AttributeMenu.getInstance();

    private View() {

        heroView = HeroView.getInstance();
        enemyViews = new ArrayList<>();
        chestViews = new ArrayList<>();

        debugRenderer = new Box2DDebugRenderer();
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        camera  = new OrthographicCamera(); //Regular camera for level
        b2dCam = new OrthographicCamera();  //Box2D camera to scale up the box2D simulation

        map = Map.getInstance();
        batch = new SpriteBatch();
        vp = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage = new Stage(vp);

        Hud hud = Hud.getInstance();
        stage.addActor(hud);
        MiniMap miniMap = MiniMap.getInstance();
        stage.addActor(miniMap);
    }

    public static View getInstance() {
        if(instance == null) {
            instance = new View();
        }
        return instance;
    }

    public void showAttributesMenu(){
        showAttributeMenu ^= true;
    }

    public void update() {
        vp.setMinWorldHeight(Gdx.graphics.getHeight());
        vp.setMinWorldWidth(Gdx.graphics.getWidth());

        stage.setViewport(vp);

        tiledMapRenderer = map.getCurrentRoom().getTiledHandler().getRenderer();
        float posX = getCamPosX();
        float posY = getCamPosY();

        camera.setToOrtho(false, w, h);
        moveCamera(posX, posY);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        heroView.updateAnimation(Gdx.graphics.getDeltaTime(), camera.combined);

        for (int i = 0; i < enemyViews.size(); i++) {
                enemyViews.get(i).updateAnimation(Gdx.graphics.getDeltaTime(), camera.combined);
            }

        for (int i = 0; i < chestViews.size(); i++) {
            chestViews.get(i).draw(camera.combined);
        }

        if(showDebugInfo) {
            camera.setToOrtho(false, w / PPM, h / PPM);
            moveB2DCamera(posX, posY);
            debugRenderer.render(WorldConstants.CURRENT_WORLD, camera.combined);
        }
        if(showAttributeMenu){
            stage.addActor(attributeMenu);
        }else{
            attributeMenu.remove();
        }

        stage.draw();
    }


    private void moveCamera(float x, float y){
        camera.position.set(x, y, 0);
        camera.update();
    }

    private void moveB2DCamera(float x, float y) {
        camera.position.set(x/PPM, y/PPM, 0);
        camera.update();
    }

    public float getCamPosX() {
        float x = Hero.getInstance().getX() * PPM;
        float roomWidth = map.getCurrentRoom().getTiledHandler().getRoomWidth();

        if(roomWidth <= 320) {
            System.out.println("roomWidth: " + roomWidth);
            return x;
        }
        else if(x < 192) {
            x = 192;
        } else if(x>roomWidth-192) {
            x = roomWidth-192;
        }
        return x;
    }

    public float getCamPosY() {
        float y = Hero.getInstance().getY() * PPM;
        float roomHeight = map.getCurrentRoom().getTiledHandler().getRoomHeight();

        if(roomHeight <= 180) {
            System.out.println("roomHeight: " + roomHeight);
            return y;
        }

        if(y < 122) {
            y = 122;
        } else if (y > roomHeight-122) {
            y = roomHeight-122;
        }
        return y;
    }


    public void remakeEnemyViews(){

        String enemyType;
        enemyViews.clear();
        enemies = map.getCurrentRoom().getEnemies();

        if(enemies != null) {
            for (int i = 0; i < enemies.size(); i++){
                enemyType = enemies.get(i).getEnemyType();

                switch (enemyType){
                    case("widow"): enemyViews.add(new WidowView((Widow) enemies.get(i)));
                        break;
                    case("redDeath"):
                        enemyViews.add(new RedDeathView((RedDeath) enemies.get(i)));
                        break;
                    case("boss"):
                        enemyViews.add(new BossView((Boss) enemies.get(i)));
                        break;
                }
            }
        }
    }

//TODO ChestViews (and EnemyViews) are instantiated both here and in ChestController(and EnemyController)!
    public void remakeChestViews(){

        chestViews.clear();
        chests = map.getCurrentRoom().getChests();

        if(chests != null) {
            for (int i = 0; i < chests.size(); i++){
                chestViews.add(new ChestView(chests.get(i)));
            }
        }
    }

    public boolean getShowDebugInfo() {
        return showDebugInfo;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}