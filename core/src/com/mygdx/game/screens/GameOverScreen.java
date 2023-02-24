package com.mygdx.game.screens;




import static com.mygdx.game.extra.Utils.WORLD_HEIGHT;
import static com.mygdx.game.extra.Utils.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MainGame;

public class GameOverScreen extends BaseScreen{
    //asignamos imagenes para el fondo,el touch y el rotulo de getReady
    private Image background;
    private Stage stage;
    private Image gameOver;


    public GameOverScreen(MainGame mainGame) {
        super(mainGame);
        //FitViewport encargado de ajustar el mundo segun el ancho y el alto
        FitViewport fitViewport = new FitViewport(WORLD_WIDTH,WORLD_HEIGHT);
        //le paso al stage el fitViewport
        this.stage = new Stage(fitViewport);
        //llamada a los metodos creados
        addBackgroundGameOver();
        addGameOver();

    }

    /**
     * Método que añade el fondo
     */
    public void addBackgroundGameOver(){
        this.background = new Image(mainGame.assetManager.getBackground());
        this.background.setPosition(0,0);
        this.background.setSize(WORLD_WIDTH,WORLD_HEIGHT);
        this.stage.addActor(this.background);
    }
    /**
     * Metodo que muestra el rótulo de GameOver para comenzar la partida
     */
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
        //si la pantalla ha sido tocada ,llama al mainGame y comienza el juego
        if (Gdx.input.justTouched()){
            mainGame.setScreen(new GetReadyScreen(this.mainGame));
        }
        //Limpia el buffer de bits para evitar que haya errores
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //le pedimos al stage que actuen todos los actores
        this.stage.draw();
        //dibujamos en el stage
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
