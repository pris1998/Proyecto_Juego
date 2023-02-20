package com.mygdx.game.screens;

import static com.mygdx.game.extra.Utils.WORLD_HEIGHT;
import static com.mygdx.game.extra.Utils.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MainGame;

public class GetReadyScreen extends BaseScreen{

    private Image background;
    private Image touch;

    private Stage stage;

    private World world;
    private OrthographicCamera orthoCamera;

    public GetReadyScreen(MainGame mainGame) {
        super(mainGame);
        this.world = new World(new Vector2(0,-10),true);
        FitViewport fitViewport = new FitViewport(WORLD_WIDTH,WORLD_HEIGHT);
        this.stage = new Stage(fitViewport);
        this.orthoCamera = (OrthographicCamera) this.stage.getCamera();

    }

    public void addBackground(){
        this.background = new Image(mainGame.assetManager.getBackground());
        this.background.setPosition(0,0);
        this.background.setSize(WORLD_WIDTH,WORLD_HEIGHT);
        this.stage.addActor(this.background);
    }

    public void addTouch(){
        this.touch = new Image(mainGame.assetManager.getTouch());
        this.touch.setPosition(4f,1f);
        this.touch.setSize(1.8f,1.2f);
        this.stage.addActor(touch);
    }

    @Override
    public void show() {
        super.show();
        addBackground();
        addTouch();
    }

    @Override
    public void render(float delta) {
        //lo dejo
        super.render(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.getBatch().setProjectionMatrix(orthoCamera.combined);
        this.stage.act();
        this.world.step(delta,6,2);
        this.stage.draw();
        this.orthoCamera.update();
        if (Gdx.input.justTouched()) {
            mainGame.setScreen(new GameScreen(mainGame));
        }
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
