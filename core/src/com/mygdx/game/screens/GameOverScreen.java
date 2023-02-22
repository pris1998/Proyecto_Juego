package com.mygdx.game.screens;



import static com.mygdx.game.extra.Utils.SCREEN_HEIGHT;
import static com.mygdx.game.extra.Utils.SCREEN_WIDTH;
import static com.mygdx.game.extra.Utils.WORLD_HEIGHT;
import static com.mygdx.game.extra.Utils.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MainGame;
import com.mygdx.game.extra.AssetMan;

public class GameOverScreen extends BaseScreen{
    private Image background;
    private Stage stage;
    private Image gameOver;


    public GameOverScreen(MainGame mainGame) {
        super(mainGame);

        FitViewport fitViewport = new FitViewport(WORLD_WIDTH,WORLD_HEIGHT);
        this.stage = new Stage(fitViewport);

        addBackgroundGameOver();
        addGameOver();

    }
    public void addBackgroundGameOver(){
        this.background = new Image(mainGame.assetManager.getBackground());
        this.background.setPosition(0,0);
        this.background.setSize(WORLD_WIDTH,WORLD_HEIGHT);
        this.stage.addActor(this.background);
    }

    private void addGameOver(){
        this.gameOver = new Image(mainGame.assetManager.getGameOverImage());
        this.gameOver.setPosition(0.75f,4f);
        this.gameOver.setSize(3.5f,1f);
        this.stage.addActor(this.gameOver);
    }

    /**
     * Render se encarga de dibujarlo todo
     * @param delta
     */
    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()){
            mainGame.setScreen(new GetReadyScreen(this.mainGame));
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.draw();
        this.stage.act();

    }

    /**
     * Método se ejecuta al cerrar la aplicación
     * y elimina memeoria del stage y del world
     */
    @Override
    public void dispose() {
        super.dispose();
        this.stage.dispose();
    }
}
