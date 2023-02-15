package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.extra.AssetMan;
import com.mygdx.game.screens.GameScreen;

public class MainGame extends Game {

    private GameScreen gameScreen;

    public AssetMan assetManager;
    @Override
    public void create() {
        this.assetManager = new AssetMan();

        this.gameScreen = new GameScreen(this);
        //insertar en la pantalla lo q hay en el gameScreen
        setScreen(this.gameScreen);
    }
}
