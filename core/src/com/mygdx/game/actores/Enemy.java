package com.mygdx.game.actores;


import static com.mygdx.game.extra.Utils.USER_ENEMY;
import static com.mygdx.game.extra.Utils.WORLD_WIDTH;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.extra.Utils;

public class Enemy extends Actor {
    //Asignación del  ancho y del alto de los enemigos
    private static final float ENEMY_WIDTH = 0.55f;
    private static final float ENEMY_HEIGHT = 1f;


    //CONTADOR DE VELOCIDAD (controlar la velocidad a la que pasan los enemigos)
    //velocidad positiva hacia arriba para que los enemigs se desplacen de abajo hacia arriba
    public static final float SPEED = 2.5f;

    //Creación las Texture,Body, fixture y mundo
    private TextureRegion enemy;

    private Body bodyEnemy;
    private Body bodyCounter;

    private Fixture fixtureEnemy;
    private Fixture fixtureCounter;


    private World world;

    public Enemy( World world,TextureRegion enemy, Vector2 position) {
        this.world = world;
        this.enemy = enemy;
        //Llamada de los metodos crean el Enemigo , la fixture y el counter
        createBodyEnemy(position);
        createFixture();
        createCounter();

    }

    /**
     * Método para crear los enemigos que se encuentra en la pantalla,
     * al ser varios solo hay que crear una variable que lo recoga y lo
     * demás se verán con un bucle (con una poscion diferente)
     * @param position
     */
    private void createBodyEnemy(Vector2 position){
        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        //Asigna el tipo
        bdef.type = BodyDef.BodyType.KinematicBody;
        //Añade al mundo
        bodyEnemy = world.createBody(bdef);
        //Asigna un identidificador
        bodyEnemy.setUserData(USER_ENEMY);
        //le asigno una velocidad
        bodyEnemy.setLinearVelocity(0,SPEED);

    }

    /**
     * Metodo que crea el Fixture del eneimgo es
     * un poligono (enemigo),la medida en setAsBox
     * se divide entre dos para adaptarlo a la pantalla
     */
    private void createFixture(){
        PolygonShape shape = new PolygonShape();
        //el tamaño del fixture
        shape.setAsBox(ENEMY_WIDTH /2, ENEMY_HEIGHT /2);
        //Asigna al ficture el body del enemigo
        this.fixtureEnemy = bodyEnemy.createFixture(shape,12);
        //Asigna un identificador
        this.fixtureEnemy.setUserData(Utils.USER_ENEMY);
        //eliminamos de la memoria
        shape.dispose();
    }

    /**
     * Metodo para la creacion del contador
     *
     */
    public void createCounter(){
        BodyDef bodyDef = new BodyDef();
        //Damos una poscion en x e y
        bodyDef.position.x = this.bodyEnemy.getPosition().x;
        bodyDef.position.y = this.bodyEnemy.getPosition().y ;
        //Asigna de que tipo es
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        //Añadimos al mundo
        this.bodyCounter = this.world.createBody(bodyDef);
        //Le decimos a la velocidad a la que tiene q ir
        this.bodyCounter.setLinearVelocity(0,SPEED);
        //Asginamos la forma que va a tener
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(WORLD_WIDTH,0.1f);
        //Asignamos el fixture tambien
        this.fixtureCounter = bodyCounter.createFixture(polygonShape,3);
        //asignamos un sensor al fixture que servirá para saber que ha pasado por ahí el
        //personaje
        this.fixtureCounter.setSensor(true);
        //asignar un identificador
        this.fixtureCounter.setUserData(Utils.USER_COUNTER);
        //eliminamos de la memoria
        polygonShape.dispose();
    }

    /**
     * Método para parar a los enemigos
     */
    public void stopEnemy(){
        bodyEnemy.setLinearVelocity(0,0);
        bodyCounter.setLinearVelocity(0,0);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
       setPosition(this.bodyEnemy.getPosition().x - (ENEMY_WIDTH /2),this.bodyEnemy.getPosition().y - (ENEMY_HEIGHT /2));
       batch.draw(this.enemy,getX(),getY(), ENEMY_WIDTH, ENEMY_HEIGHT);
    }

    //Creacion del método detach para liberar recursos y memoria
    public void detach(){
        bodyEnemy.destroyFixture(fixtureEnemy);
        world.destroyBody(bodyEnemy);
    }

    //Metodo avisa si el objeto esta fuera de la pantalla
    public boolean isOut(){
        return this.bodyEnemy.getPosition().x <= -2f;
    }


}
