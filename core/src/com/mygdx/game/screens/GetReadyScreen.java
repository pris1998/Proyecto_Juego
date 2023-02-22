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
    private Image getReady;

    private Stage stage;


    public GetReadyScreen(MainGame mainGame) {
        super(mainGame);
        FitViewport fitViewport = new FitViewport(WORLD_WIDTH,WORLD_HEIGHT);
        this.stage = new Stage(fitViewport);

    }

    public void addBackground(){
        this.background = new Image(mainGame.assetManager.getBackground());
        this.background.setPosition(0,0);
        this.background.setSize(WORLD_WIDTH,WORLD_HEIGHT);
        this.stage.addActor(this.background);
    }

    public void addTouch(){
        this.touch = new Image(mainGame.assetManager.getTouch());
        this.touch.setPosition(WORLD_WIDTH/3f,4f);
        this.touch.setSize(WORLD_WIDTH/3f,2f);
        this.stage.addActor(touch);
    }
    public void addGetReady(){
        this.getReady = new Image(mainGame.assetManager.getGetReady());
        this.getReady.setPosition(WORLD_WIDTH/4.5f,2f);
        this.getReady.setSize(WORLD_WIDTH/1.5f,1.5f);
        this.stage.addActor(getReady);
    }

    @Override
    public void show() {
        super.show();
        addBackground();
        addTouch();
        addGetReady();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.act();
        this.stage.draw();
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
