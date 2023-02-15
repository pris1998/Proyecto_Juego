package com.mygdx.game.actores;

import static com.mygdx.game.extra.Utils.USER_ENEMY;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Block  extends Actor {
    //Todo. Fijarun ancho y un alto de los bloques
    private static final float BLOCK_WIDTH = 1f;
    private static final float BLOCK_HEIGHT = 4f;

    //CONTADOR DE VELOCIDAD (controlar la velocidad a la que pasan los bloques)
    public static final float SPEED = -0.2f;

    //Todo. Creación las Texture,Body, fixture y mundo
    private TextureRegion blocks;

    private Body bodyBlock;

    private Fixture fixtureBlock;


    private World world;

    public Block(World world, TextureRegion blocks, Vector2 position) {
        this.blocks = blocks;
        this.world = world;

        createBlock(position);
        createFixture();

    }

    private void createBlock(Vector2 position){
        BodyDef bdef = new BodyDef();
        bdef.position.set(position);

        bdef.type = BodyDef.BodyType.KinematicBody;
        bodyBlock = world.createBody(bdef);
        bodyBlock.setUserData(USER_ENEMY);
        //velocidad del bloque en x e Y
        bodyBlock.setLinearVelocity(SPEED,0);
    }
    private void createFixture(){
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(BLOCK_WIDTH/2,BLOCK_HEIGHT/2);

        this.fixtureBlock = bodyBlock.createFixture(shape,8);

        shape.dispose();
    }

    //Todo.Creacion método que se asegura de que el enemigo esta fuera
    public boolean isOutOfScreen(){
        return this.bodyBlock.getPosition().x <= -2f;
    }
    //Todo.Creacion método que se asegura de que el bloque esta fuera


    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(this.bodyBlock.getPosition().x -(BLOCK_WIDTH/2), this.bodyBlock.getPosition().y -(BLOCK_HEIGHT/2));
        batch.draw(this.blocks,getX(),getY(),BLOCK_WIDTH,BLOCK_HEIGHT);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    //Todo 11. Creamos detach para liberar recursos
    public void detach(){
        bodyBlock.destroyFixture(fixtureBlock);
        world.destroyBody(bodyBlock);


    }
}
