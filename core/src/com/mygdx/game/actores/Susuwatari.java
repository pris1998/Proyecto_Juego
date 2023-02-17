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

    //la potencia de la caída
    private static final float FALL_SPEED = 0.3f;

    private int state;

    private Animation<TextureRegion> susuAnimation;
    private Vector2 position;
    //tiempo q marca el momento en el q se ve los fotogramas
    private float stateTime;

    private World world;

     private Body body ;
     private Fixture fixture;

     private Sound fallSound;

    public Susuwatari( World world,Animation<TextureRegion> susuAnimation,Sound sound, Vector2 position) {
        this.susuAnimation = susuAnimation;
        this.position = position;
        this.world = world;

        this.stateTime = 0f;
        this.state = STATE_NORMAL;
        this.fallSound = sound;

        createBody();
        createFixture();

    }

    private void createBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(this.position);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        this.body = this.world.createBody(bodyDef);
    }

    private void createFixture(){
        CircleShape circle = new CircleShape();
        circle.setRadius(0.20f);

        this.fixture = this.body.createFixture(circle,6);
        this.fixture.setUserData(USER_SUSUWATARI);

        circle.dispose();
    }

    public int getState(){
        return this.state;
    }

    //Método para controlar el estado del personaje
    public void hurt(){
        state = STATE_DEAD;
        stateTime = 0f;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //Mover el fixture de circunferencia en x e y
        setPosition(body.getPosition().x-0.34f , body.getPosition().y - 0.25f);
        //tamaño del fixture
        batch.draw(this.susuAnimation.getKeyFrame(stateTime,true),getX(),getY(),0.8f,0.5f);

        stateTime += Gdx.graphics.getDeltaTime();
    }

    @Override
    public void act(float delta) {
        float fall = Gdx.input.getAccelerometerX();
        if (fall > 1) {
            this.fallSound.play();
            this.body.setLinearVelocity(-3,-FALL_SPEED);
        }else if(fall < -1 ){
            //this.fallSound.play();
            this.body.setLinearVelocity(3,-FALL_SPEED);
            this.fallSound.play();
        }else{
            this.body.setLinearVelocity(0,-FALL_SPEED);
            this.fallSound.play();
        }
    }

    public void detach(){
        this.body.destroyFixture(this.fixture);
        this.world.destroyBody(this.body);
    }
}
