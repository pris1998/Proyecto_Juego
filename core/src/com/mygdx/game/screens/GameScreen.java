package com.mygdx.game.screens;

import static com.mygdx.game.extra.Utils.SCREEN_HEIGHT;
import static com.mygdx.game.extra.Utils.SCREEN_WIDTH;
import static com.mygdx.game.extra.Utils.USER_COUNTER;
import static com.mygdx.game.extra.Utils.USER_ENEMY;
import static com.mygdx.game.extra.Utils.USER_LEFT;
import static com.mygdx.game.extra.Utils.USER_RIGHT;
import static com.mygdx.game.extra.Utils.USER_SUSUWATARI;
import static com.mygdx.game.extra.Utils.WORLD_HEIGHT;
import static com.mygdx.game.extra.Utils.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MainGame;
import com.mygdx.game.actores.Enemy;
import com.mygdx.game.actores.Susuwatari;

public class GameScreen extends BaseScreen implements ContactListener {

    //Todo. Constante indica el tiempo que queremos q dure el enemigo en la pantalla
    private static final float TIME_TO_SPAWN_ENEMIES = 1.3f;
    //para saber cuando crear al enemigo
    private float timeToCreateEnemy;
    //variable del stage para el apartado fisico
    private Stage stage;
    //llama al actor
    private Susuwatari susuwatari;
    //variable del body y del fixture
    private Body bodyRigth;
    private Body bodyLeft;
    private Fixture fixtureRigth;
    private Fixture fixtureLeft;

    // Variable contador
    private int scoreNumber;
    //Variable del fondo
    private Image background;
    //Eencarga de controlar el apartado grafico
    private World world;

    //Creamos un arrayList que almacene los enemigos
    private Array<Enemy> arrayenemies;

    //Musica y sonidos
    private Music musicbg;
    private Sound hitSound;
    private Sound gameOverSound;

    //Depuración
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera ortCamera;

    //Score Cámara
    private OrthographicCamera fontCamera;
    private BitmapFont score;

    public GameScreen(MainGame mainGame) {
        super(mainGame);
        //poner el personaje va hacia arriba
        this.world = new World(new Vector2(0,-10),true);
        this.world.setContactListener(this);
        //FitViewport encargado de ajustar el mundo segun el ancho y el alto
        FitViewport fitViewport = new FitViewport(WORLD_WIDTH,WORLD_HEIGHT);
        //le paso al stage el fitViewport
        this.stage = new Stage(fitViewport);

        //Inicialización del array
        this.arrayenemies = new Array();
        this.timeToCreateEnemy = 0.8f;

        this.ortCamera = (OrthographicCamera) this.stage.getCamera();
        this.debugRenderer = new Box2DDebugRenderer();

        //llamar al los metodos de la musica y el sonido
        prepareScore();
        prepareGameSound();
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
    /**
     * Metodo para crearme el personaje
     */
    public void addSusuwatari(){
        Animation<TextureRegion> susuSprite = mainGame.assetManager.getSusuwatariAnimation();
        //asignamos un sonido independiente mientras está cayendo
        Sound sound = mainGame.assetManager.getFallSound();
        this.susuwatari = new Susuwatari(this.world,susuSprite,sound,new Vector2(1.5f,7.5f));
        this.stage.addActor(this.susuwatari);
    }

    /**
     * Metodo que añade la pared de la derecha
     */
    private void addRigth(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        this.bodyRigth = world.createBody(bodyDef);
        bodyDef.position.set(0,1);

        EdgeShape edge = new EdgeShape();
        edge.set(WORLD_WIDTH,0 ,WORLD_WIDTH,WORLD_HEIGHT);
        this.fixtureRigth = this.bodyRigth.createFixture(edge,1);
        this.fixtureRigth.setUserData(USER_RIGHT);
        edge.dispose();

    }

    /**
     * Metodo que añade la pared de la izquierda
     */
    private void addLeft(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        this.bodyLeft = world.createBody(bodyDef);
        bodyDef.position.set(1,1);

        EdgeShape edge = new EdgeShape();
        edge.set(0,0,0,WORLD_HEIGHT);
        this.fixtureLeft = this.bodyLeft.createFixture(edge,1);
        this.fixtureLeft.setUserData(USER_LEFT);
        edge.dispose();

    }

    /**
     * Método para añadir al enemigo
     * @param delta
     */
    public void addEnemy(float delta){
        TextureRegion oneEnemy = mainGame.assetManager.getEnemy();
        if (susuwatari.getState() == Susuwatari.STATE_NORMAL) {
            //Acumulamos de delta hasta llegar al tope de tiempo establecido
            this.timeToCreateEnemy +=delta;
            //Si el tiempo es acumulado es mayor que el prestablecido se crea una tubería
            if (this.timeToCreateEnemy >= TIME_TO_SPAWN_ENEMIES) {
                //Se le resta la variable timeToCreateEnemy a la constante
                this.timeToCreateEnemy -= TIME_TO_SPAWN_ENEMIES;
                //aparecen los enemigos en x e y entre esas posiciones(0.5-4.5)
                float posRandomX = MathUtils.random(0.5f,4.5f);
                //posRandomX sale en una posicion random
                Enemy var_enemy = new Enemy(this.world,oneEnemy,new Vector2(posRandomX,1f));
                //añadimos al array los enemigos
                arrayenemies.add(var_enemy);
                //añadimos como actor
                this.stage.addActor(var_enemy);
            }
        }
    }

    /**
     * Metodo que quita los enemigos una vez zque desaparecen de la pantalla
     */
    public void removeEnemies(){
        for (Enemy enemy : this.arrayenemies) {
            // Si el mundo no se actualiza y esta bloqueado el mundo y
            if (!world.isLocked()) {
                //los enemigos se salen de la pantalla
                if (enemy.isOut()) {
                    //Eliminacion de recursos
                    enemy.detach();
                    //Eliminacion del escenario
                    enemy.remove();
                    //Los eliminamos del array a los enemigos
                    arrayenemies.removeValue(enemy,false);
                }
            }
        }
    }

    /**
     * Método que va prepara la puntuacion
     */
    private void prepareScore(){
        this.scoreNumber = 0;
        this.score = this.mainGame.assetManager.getFont();
        this.score.getData().scale(1f);

        this.fontCamera = new OrthographicCamera();
        this.fontCamera.setToOrtho(false,SCREEN_WIDTH,SCREEN_HEIGHT);
        this.fontCamera.update();
    }

    /**
     * Método prepara los sonidos del juego  */
    private void prepareGameSound(){
        this.musicbg = this.mainGame.assetManager.getMusicBG();
        this.hitSound = this.mainGame.assetManager.getHit();
        this.gameOverSound = this.mainGame.assetManager.getGameOver();
    }

    @Override
    public void show() {
        //llamada de todos los métodos
        addBackground();
        addSusuwatari();
        addRigth();
        addLeft();

        //Ajustar la musica de fondo para que deje de sonar en forma de bucle
        this.musicbg.setLooping(true);
        this.musicbg.play();
    }

    @Override
    public void render(float delta) {
        //Comprobacion del personaje en caso de que muera salta a la pantalla de GaimOver
        if (susuwatari.getState() == susuwatari.STATE_DEAD){
            mainGame.setScreen(new GameOverScreen(this.mainGame));
        }
        //Limpia el buffer de bits para evitar que haya errores
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Añado los enemigos en función del tiempo, delta almacena el tiempo el cual sale
        // enemigo cuando sale al tope de tiempo se recarga a 0
        addEnemy(delta);
        //para una de las camaras
        this.stage.getBatch().setProjectionMatrix(ortCamera.combined);
        //le pedimos al stage que actuen todos los actores
        this.stage.act();
        //los valores dados es según la documentación
        this.world.step(delta,6,2);
        //dibujamos en el stage
        this.stage.draw();


        //Llamada al metodo de eliminar enemigos para cuando salgan de la pantalla
        removeEnemies();

        //hacer lo mismo para la otra camara
        //para decirle a la tarjeta grafica que existe otra camara
        this.stage.getBatch().setProjectionMatrix(this.fontCamera.combined);
        //inicio del batch
        this.stage.getBatch().begin();
        //le damos la posicion en la que qeuremos que nos la dibuje
        this.score.draw(this.stage.getBatch(), "" + this.scoreNumber,SCREEN_WIDTH/2, 725);
        //una vez finalizado las acciones le pedimos que termine
        this.stage.getBatch().end();


    }

    //Ejecuta cuando se cierra la apliacacion
    @Override
    public void dispose() {
        this.stage.dispose();
        this.world.dispose();
    }
    //Hide se ejecuta cuando la apliacacion se ejecute en segundo plano
    @Override
    public void hide() {

        this.susuwatari.detach();
        this.susuwatari.remove();
        for (Enemy enemy: arrayenemies) {
            enemy.detach();
            enemy.remove();}
        this.stage.dispose();
        this.world.dispose();
        this.musicbg.stop();

    }


    //--------------COLISIONES------------------\\
    //Cramos un metodo para comprobar cuando un elemento se ha chocado con otro
    private boolean areColisioner(Contact contact, Object actorA, Object actorB){
        return (contact.getFixtureA().getUserData().equals(actorA) && contact.getFixtureB().getUserData().equals(actorB)
                || contact.getFixtureA().getUserData().equals(actorB) && contact.getFixtureB().getUserData().equals(actorA));
    }

    @Override
    public void beginContact(Contact contact) {
        //En caso de haber colisionado uno de estos dos elementos entre sí
        //la variable scoreNumber suma en uno
        if (areColisioner(contact,USER_SUSUWATARI,USER_COUNTER) ) {
            this.scoreNumber++;
        }else{
            //Al chocarse con otra cosa que no sea el counter el personaje
            //llama al metodo hurt() y el sonido de choque suena
            susuwatari.hurt();
            this.hitSound.play();
            //Al chocar hacemos que la música se pare y el sonido de GameOver
            this.musicbg.stop();
            this.gameOverSound.play();
            //Recorremos con el foreach para parar los enemigos que se encuentran
            //creados en el momento
            for (Enemy enemy : arrayenemies) {
                enemy.stopEnemy();
            }

        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }



}
