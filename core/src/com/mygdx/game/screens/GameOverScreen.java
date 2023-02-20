package com.mygdx.game.screens;



import static com.mygdx.game.extra.Utils.WORLD_HEIGHT;
import static com.mygdx.game.extra.Utils.WORLD_WIDTH;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MainGame;

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
        this.gameOver = new Image(mainGame.assetManager.getGameOver());
        this.gameOver.setSize(3.5f,2f);
        this.stage.addActor(this.gameOver);
    }

    //añado la animacion del pajaro

    @Override
    public void render(float delta) {
        this.stage.draw();

    }

    @Override
    public void dispose() {
        super.dispose();
        this.stage.dispose();
    }
}
