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
    //Todo. Fijarun ancho y un alto de los bloques
    private static final float ENEMY_WIDTH = 0.55f;
    private static final float ENEMY_HEIGHT = 1f;



    //CONTADOR DE VELOCIDAD (controlar la velocidad a la que pasan los bloques)
    //velocidad positiva hacia arriba
    public static final float SPEED = 2.5f;

    //Todo. Creación las Texture,Body, fixture y mundo
    private TextureRegion enemy;

    private Body bodyEnemy;
    private Body bodyCounter;

    private Fixture fixtureEnemy;
    private Fixture fixtureCounter;


    private World world;

    public Enemy( World world,TextureRegion enemy, Vector2 position) {
        this.world = world;
        this.enemy = enemy;

        createBodyEnemy(position);
        createFixture();
        createCounter();

    }

    /**
     * Método para crear los bloques que se encuentra en la pantalla,
     * al ser varios solo hay que crear una variable que lo recoga y lo
     * demás se verán con un bucle (con una poscion diferente)
     * @param position
     */
    private void createBodyEnemy(Vector2 position){
        BodyDef bdef = new BodyDef();
        bdef.position.set(position);

        bdef.type = BodyDef.BodyType.KinematicBody;
        bodyEnemy = world.createBody(bdef);
        bodyEnemy.setUserData(USER_ENEMY);

        //al cambiar la y el muñeco va hacia arriba
        bodyEnemy.setLinearVelocity(0,SPEED);

    }
    //Todo. Creacion del método para la fixture
    //Mi fixture tambien es un poligono (enemigo),la medida en setAsBox
    //se divide entre dos para adaptarlo a la pantalla
    private void createFixture(){
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(ENEMY_WIDTH /2, ENEMY_HEIGHT /2);
        //densidad de 12 (como solo es uno )
        this.fixtureEnemy = bodyEnemy.createFixture(shape,12);
        this.fixtureEnemy.setUserData(Utils.USER_ENEMY);

        shape.dispose();
    }

    public void createCounter(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.x = this.bodyEnemy.getPosition().x;
        bodyDef.position.y = this.bodyEnemy.getPosition().y ;
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        this.bodyCounter = this.world.createBody(bodyDef);
        this.bodyCounter.setLinearVelocity(0,SPEED);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(WORLD_WIDTH,0.1f);

        this.fixtureCounter = bodyCounter.createFixture(polygonShape,3);
        this.fixtureCounter.setSensor(true);
        this.fixtureCounter.setUserData(Utils.USER_COUNTER);
        polygonShape.dispose();
    }


    //Todo. Parar los enemigos
    public void stopEnemy(){
        bodyEnemy.setLinearVelocity(0,0);
        bodyCounter.setLinearVelocity(0,0);
    }
    //Todo. Sobrecarga de metodos
    @Override
    public void act(float delta) {
        super.act(delta);
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
       setPosition(this.bodyEnemy.getPosition().x - (ENEMY_WIDTH /2),this.bodyEnemy.getPosition().y - (ENEMY_HEIGHT /2));
       batch.draw(this.enemy,getX(),getY(), ENEMY_WIDTH, ENEMY_HEIGHT);
    }

    //Todo. Creacion del método detach para liberar recursos
    public void detach(){
        bodyEnemy.destroyFixture(fixtureEnemy);
        world.destroyBody(bodyEnemy);
    }

    //Todo.Metodo avisa si el objeto esta fuera de la pantalla
    public boolean isOut(){
        return this.bodyEnemy.getPosition().x <= -2f;
    }


}
