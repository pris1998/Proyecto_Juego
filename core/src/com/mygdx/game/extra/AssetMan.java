package com.mygdx.game.extra;

import static com.mygdx.game.extra.Utils.ATLAS_MAP;
import static com.mygdx.game.extra.Utils.BACKGROUND_IMAGE;
import static com.mygdx.game.extra.Utils.BLOCK;
import static com.mygdx.game.extra.Utils.ENEMY;
import static com.mygdx.game.extra.Utils.SUSUWATARI1;
import static com.mygdx.game.extra.Utils.SUSUWATARI2;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetMan {
    private AssetManager assetManager;
    private TextureAtlas textureAtlas;

    public AssetMan() {
        this.assetManager = new AssetManager();

        assetManager.load(ATLAS_MAP,TextureAtlas.class);
        //sonido y musica
        assetManager.finishLoading();

        this.textureAtlas = assetManager.get(ATLAS_MAP);
    }

    public TextureRegion getBackground(){
        return this.textureAtlas.findRegion(BACKGROUND_IMAGE);}
    //ANIMACION SUSUWATARI
    public Animation<TextureRegion> getSusuwatariAnimation(){
        return new Animation<TextureRegion>(0.5f,
                textureAtlas.findRegion(SUSUWATARI2),
                textureAtlas.findRegion(SUSUWATARI1)
                );
    }

    //Metodo de texture de los enemigos
    public TextureRegion getEnemy(){
        return this.textureAtlas.findRegion(ENEMY);
    }
    //Metodo de texture de los bloques
    public TextureRegion getBlock(){
        return this.textureAtlas.findRegion(BLOCK);
    }
}
