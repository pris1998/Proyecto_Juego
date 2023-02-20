package com.mygdx.game;

import static com.mygdx.game.actores.Susuwatari.STATE_NORMAL;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.mygdx.game.extra.AssetMan;
import com.mygdx.game.screens.GameOverScreen;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.GetReadyScreen;

public class MainGame extends Game {

    public GameOverScreen gameOverScreen;
    public GameScreen gameScreen;
    public GetReadyScreen getReadyScreen;


    public AssetMan assetManager;
    @Override
    public void create() {
        this.assetManager = new AssetMan();

        this.gameScreen = new GameScreen(this);

        this.gameOverScreen = new GameOverScreen(this);
        this.getReadyScreen = new GetReadyScreen(this);
        //insertar en la pantalla lo q hay en el gameScreen
        setScreen(this.gameScreen);
    }

}
