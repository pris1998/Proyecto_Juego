package com.mygdx.game.extra;

import static com.mygdx.game.extra.Utils.ATLAS_MAP;
import static com.mygdx.game.extra.Utils.BACKGROUND_IMAGE;
import static com.mygdx.game.extra.Utils.BLOCK;
import static com.mygdx.game.extra.Utils.ENEMY;
import static com.mygdx.game.extra.Utils.FONT_FNT;
import static com.mygdx.game.extra.Utils.FONT_PNG;
import static com.mygdx.game.extra.Utils.HIT_SOUND;
import static com.mygdx.game.extra.Utils.MUSIC_BG;
import static com.mygdx.game.extra.Utils.SOUND_FALL;
import static com.mygdx.game.extra.Utils.SOUND_FALL;
import static com.mygdx.game.extra.Utils.SUSUWATARI1;
import static com.mygdx.game.extra.Utils.SUSUWATARI2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetMan {
    private AssetManager assetManager;
    private TextureAtlas textureAtlas;

    public AssetMan() {
        this.assetManager = new AssetManager();

        assetManager.load(ATLAS_MAP,TextureAtlas.class);
        assetManager.load(MUSIC_BG, Music.class);
        assetManager.load(SOUND_FALL, Sound.class);
        assetManager.load(HIT_SOUND,Sound.class);
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

    public Music getMusicBG(){
        return this.assetManager.get(MUSIC_BG);
    }
    public Sound getFallSound(){
        return this.assetManager.get(SOUND_FALL);
    }
    public Sound getHit(){
        return this.assetManager.get(HIT_SOUND);
    }

    //iDENTIFICADORES DE LOS UTILS
    public BitmapFont getFont(){
        //A PORBAR CON UNO SOLO
        return new BitmapFont(Gdx.files.internal(FONT_FNT),Gdx.files.internal(FONT_PNG),false);
    }


}
