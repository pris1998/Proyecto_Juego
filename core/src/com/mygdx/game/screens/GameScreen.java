package com.mygdx.game.screens;

import static com.mygdx.game.extra.Utils.WORLD_HEIGHT;
import static com.mygdx.game.extra.Utils.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MainGame;
import com.mygdx.game.actores.Block;
import com.mygdx.game.actores.Enemy;
import com.mygdx.game.actores.Susuwatari;

public class GameScreen extends BaseScreen{
    //Todo. Constante indica el tiempo que queremos q dure el enemigo en la pantalla
    private static final float TIME_TO_SPAWN_ENEMIES = 1.5f;
    private float timeToCreateEnemy;
    private Stage stage;
    private Susuwatari susuwatari;

    private Image background;

    private World world;

    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera ortCamera;

    //Creamos un arrayList que almacene los enemigos
    private Array<Enemy> arrayenemies;

    Block block;


    public GameScreen(MainGame mainGame) {
        super(mainGame);
        //poner el personaje va hacia arriba
        this.world = new World(new Vector2(0,-10),true);
        FitViewport fitViewport = new FitViewport(WORLD_WIDTH,WORLD_HEIGHT);
        this.stage = new Stage(fitViewport);

        //Todo.Inicialización del array
        this.arrayenemies = new Array<>();
        this.timeToCreateEnemy = 1f;

        this.ortCamera = (OrthographicCamera) this.stage.getCamera();
        this.debugRenderer = new Box2DDebugRenderer();
    }

    /**
     * Metodo para crearme el fondo del juego
     */
    public void addBackground(){
        this.background = new Image(mainGame.assetManager.getBackground());
        this.background.setPosition(0,0);
        this.background.setSize(WORLD_WIDTH,WORLD_HEIGHT);
        this.stage.addActor(this.background);
    }

    public void addSusuwatari(){
        Animation<TextureRegion> susuSprite = mainGame.assetManager.getSusuwatariAnimation();
        //colocacion del actor en la pantalla x=0.5 / y=7.5
        this.susuwatari = new Susuwatari(this.world,susuSprite,new Vector2(2f,7f));
        this.stage.addActor(this.susuwatari);
    }

    public void addEnemy(float delta){
        TextureRegion oneEnemy = mainGame.assetManager.getEnemy();
        if (susuwatari.getState() == Susuwatari.STATE_NORMAL) {
            //Todo.Acumulacion de delta hasta llegar al tope de tiempo establecido
            this.timeToCreateEnemy +=delta;
            //Todo.Si el tiempo es acumulado es mayor que el prestablecido se crea una tubería
            if (this.timeToCreateEnemy >= TIME_TO_SPAWN_ENEMIES) {
                //Todo.
                this.timeToCreateEnemy -= TIME_TO_SPAWN_ENEMIES;
                //aparecen lso enemigos en x e y entre esas posiciones(0.5-4.5)
                float posRandomX = MathUtils.random(0.5f,4.5f);
                //Todo. posRandomX sale en una posicion random entre 0-2 y en y= 1
                Enemy var_enemy = new Enemy(this.world,oneEnemy,new Vector2(posRandomX,1f));
                arrayenemies.add(var_enemy);
                this.stage.addActor(var_enemy);
            }
        }
    }

    /**
     * Metodo que quita los enemigos una vez zque desaparecen de la pantalla
     */
    public void removeEnemies(){
        for (Enemy enemy : this.arrayenemies) {
            //Todo. SI el mundo no se actualiza y esta bloqueado el mundo y ...
            if (!world.isLocked()) {
                //Todo. los enemigos están fuera de la pantalla
                if (enemy.isOutOfScreen()) {
                    //Todo.eliminamos recursos
                    enemy.detach();
                    //Todo. eliminamsoescenario
                    enemy.remove();
                    //Todo.la eliminamso del array
                    arrayenemies.removeValue(enemy,false);
                }
            }
        }
    }

    @Override
    public void show() {
        addBackground();
        addSusuwatari();

        //AÑADE LA MUSICA Y EL SONIDO

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Todo . Añado los enemigos en función del tiempo, delta almacena el tiempo el cual sale
        // enemigo cuando sale al tope de tiempo se recarga a 0
        addEnemy(delta);

        this.stage.act();
        this.world.step(delta,2,2);
        this.stage.draw();

        this.debugRenderer.render(this.world,this.ortCamera.combined);

        //Todo. Llamamos al metodo de eliminar enemigos para cuando salgan de la pantalla
        removeEnemies();
    }

    @Override
    public void dispose() {
        this.stage.dispose();
        this.world.dispose();
    }

    @Override
    public void hide() {
        this.susuwatari.detach();
        this.susuwatari.remove();

        this.block.detach();
        this.block.remove();
    }
}
