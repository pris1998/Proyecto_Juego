package com.mygdx.game.actores;

import static com.mygdx.game.extra.Utils.USER_SUSUWATARI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Susuwatari extends Actor {
    //Estados del Actor
    public static final int STATE_NORMAL = 0;
    public static final int STATE_DEAD = 1;

    //La potencia de la caída
    private static final float FALL_SPEED = 0.3f;
    //Estado del personaje
    private int state;
    //Guardo la animación del coche
    private Animation<TextureRegion> susuAnimation;
    //Vector para saber la posición
    private Vector2 position;
    //tiempo q marca el momento en el q se ve los fotogramas
    private float stateTime;
    //Encarga de controlar el apartado grafico
    private World world;
    //Body crea el cuerpo del personaje
     private Body body ;
     //Hitbox para guardad el fixture del personaje
     private Fixture fixture;
    //Guarda el sonido que hace el personaje en este caso al caer
     private Sound fallSound;

    public Susuwatari( World world,Animation<TextureRegion> susuAnimation,Sound sound, Vector2 position) {
        this.susuAnimation = susuAnimation;
        this.position = position;
        this.world = world;

        this.stateTime = 0f;
        this.state = STATE_NORMAL;
        this.fallSound = sound;
        //Llamada a los métodos de crear el Body y el Fixture
        createBody();
        createFixture();

    }

    /**
     * Método que crea el cuerpo del personaje
     */
    private void createBody(){
        BodyDef bodyDef = new BodyDef();
        //asigna una posicion
        bodyDef.position.set(this.position);
        //asigna un tipo en este caso Dinámico adapta al cuerpo
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        //creamos el body para que se vea en el mundo
        this.body = this.world.createBody(bodyDef);
    }
    /**
     * Método que crea el fixture del personaje
     */
    private void createFixture(){
        //En este caso es una circunferencia
        CircleShape circle = new CircleShape();
        //Adsignamos un radio
        circle.setRadius(0.20f);
        //El fixture se lo asignamos al body y le damos la forma que obtiene y su densidad
        this.fixture = this.body.createFixture(circle,6);
        //Le añadimos un identificador
        this.fixture.setUserData(USER_SUSUWATARI);
        //La eliminamos de la memoria
        circle.dispose();
    }

    /**
     * Método getState() saber el estado en el que se encuentra el personaje
     * @return el estado del personaje en este momento
     */
    public int getState(){
        return this.state;
    }

    /**
     * Método hurt() para cambiar el estado de muerto o no
     */
    public void hurt(){
        state = STATE_DEAD;
        stateTime = 0f;
    }

    /**
     * Dibujamos la animación del personaje
     * @param batch
     * @param parentAlpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        //Mover la posicion del personaje
        setPosition(body.getPosition().x-0.34f , body.getPosition().y - 0.25f);
        //Le pedimos a la gráfica que dibuje la animacion y la muestre en pantalla
        batch.draw(this.susuAnimation.getKeyFrame(stateTime,true),getX(),getY(),0.8f,0.5f);
        //Incremento stateTime el valor de delta
        stateTime += Gdx.graphics.getDeltaTime();
    }

    /**
     * Método que asigna la acción que hace el personaje
     * @param delta
     */
    @Override
    public void act(float delta) {
        //Adinamgos a fall el acelerometro en X (se va a mover en X segun si movemos el
        // movil se un lado a otro)
        float fall = Gdx.input.getAccelerometerX();
        if (fall > 1) {
            //Activamos el sonido de lacaida y la velocidad que lleva en un lateral
            this.fallSound.play();
            this.body.setLinearVelocity(-3,-FALL_SPEED);
        }else if(fall < -1 ){
            //Si es menor que -1 tambien le asignamso el sonido otro lateral y se mueve en un lateral
            this.body.setLinearVelocity(3,-FALL_SPEED);
            this.fallSound.play();
        }else{
            //Cuando se x=0 tambien se mueve en línea recta hacia abajo y se activa el sonido
            this.body.setLinearVelocity(0,-FALL_SPEED);
            this.fallSound.play();
        }
    }

    /**
     * Método de destruir el body y el world
     */
    public void detach(){
        this.body.destroyFixture(this.fixture);
        this.world.destroyBody(this.body);
    }


}
