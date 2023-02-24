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
    //asignamos imagenes para el fondo,el touch y el rotulo de getReady
    private Image background;
    private Image touch;
    private Image getReady;
    //variable del stage para el apartado fisico
    private Stage stage;


    public GetReadyScreen(MainGame mainGame) {
        super(mainGame);
        //FitViewport encargado de ajustar el mundo segun el ancho y el alto
        FitViewport fitViewport = new FitViewport(WORLD_WIDTH,WORLD_HEIGHT);
        //le paso al stage el fitViewport
        this.stage = new Stage(fitViewport);

    }

    /**
     * Método que añade el fondo
     */
    public void addBackground(){
        this.background = new Image(mainGame.assetManager.getBackground());
        this.background.setPosition(0,0);
        this.background.setSize(WORLD_WIDTH,WORLD_HEIGHT);
        this.stage.addActor(this.background);
    }

    /**
     * Método que añade un touch sirve para cuando lo selecciones puedas
     * empezar la partida
     */
    public void addTouch(){
        this.touch = new Image(mainGame.assetManager.getTouch());
        this.touch.setPosition(WORLD_WIDTH/3f,4f);
        this.touch.setSize(WORLD_WIDTH/3f,2f);
        this.stage.addActor(touch);
    }

    /**
     * Metodo que muestra el rótulo de GetReady para comenzar la partida
     */
    public void addGetReady(){
        this.getReady = new Image(mainGame.assetManager.getGetReady());
        this.getReady.setPosition(WORLD_WIDTH/4.5f,2f);
        this.getReady.setSize(WORLD_WIDTH/1.5f,1.5f);
        this.stage.addActor(getReady);
    }

    @Override
    public void show() {
        super.show();
        //Llama a los metodos para poder mostrar su contenido desde el show
        addBackground();
        addTouch();
        addGetReady();
    }

    @Override
    public void render(float delta) {
        //Limpia el buffer de bits para evitar que haya errores
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //le pedimos al stage que actuen todos los actores
        this.stage.act();
        //dibujamos en el stage
        this.stage.draw();
        //si la pantalla ha sido tocada ,llama al mainGame y comienza el juego
        if (Gdx.input.justTouched()) {
            mainGame.setScreen(new GameScreen(mainGame));
        }
    }

    /**
     * Se ejecuta cuando la apliacacion este en segundo plano
     */
    @Override
    public void hide() {
        super.hide();
    }
    /**
     * Método se ejecuta al cerrar la aplicación
     * y elimina memeoria del stage y del world
     */    @Override
    public void dispose() {
        super.dispose();
    }
}
