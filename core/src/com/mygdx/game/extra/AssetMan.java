package com.mygdx.game.extra;

import static com.mygdx.game.extra.Utils.ATLAS_MAP;
import static com.mygdx.game.extra.Utils.BACKGROUND_IMAGE;

import static com.mygdx.game.extra.Utils.ENEMY;
import static com.mygdx.game.extra.Utils.FONT_FNT;
import static com.mygdx.game.extra.Utils.FONT_PNG;
import static com.mygdx.game.extra.Utils.GAMEOVER;
import static com.mygdx.game.extra.Utils.GAMEOVER_SOUND;
import static com.mygdx.game.extra.Utils.GETREADY;
import static com.mygdx.game.extra.Utils.HIT_SOUND;
import static com.mygdx.game.extra.Utils.MUSIC_BG;
import static com.mygdx.game.extra.Utils.SUSUWATARI1;
import static com.mygdx.game.extra.Utils.SUSUWATARI2;
import static com.mygdx.game.extra.Utils.TOUCH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
//Esta clase AssetMan se encarga de cargar todos los recursos que necesita el juego

public class AssetMan {
    private AssetManager assetManager;
    private TextureAtlas textureAtlas;
    //Todo.El constructor se encarga de leer el ATLAS (donde se encuentran las imagenes)
    // como la música y los sonidos
    public AssetMan() {
        this.assetManager = new AssetManager();
        //Cargamos el ATLAS
        this.assetManager.load(ATLAS_MAP,TextureAtlas.class);
        //Carga la musica y los sonidos
        this.assetManager.load(MUSIC_BG, Music.class);
        this.assetManager.load(HIT_SOUND,Sound.class);
        this.assetManager.load(GAMEOVER_SOUND,Sound.class);
        //hacemos que para de cargar
        this.assetManager.finishLoading();
        //a la variable le pasamos el ATLAS_MAP
        this.textureAtlas = assetManager.get(ATLAS_MAP);
    }

    /**
     * Método que se encarga de carga el fondo del juego
     * @return TextureRegion de la imagen de fondo
     */
    public TextureRegion getBackground(){
        return this.textureAtlas.findRegion(BACKGROUND_IMAGE);}

    /**
     * Método que se encarga de devolver la animación del personaje
     * @return Animation<TextureRegion> la animacion del personaje
     */
    public Animation<TextureRegion> getSusuwatariAnimation(){
        return new Animation<TextureRegion>(0.5f,//Tiempo acumulado en delta
                textureAtlas.findRegion(SUSUWATARI2),
                textureAtlas.findRegion(SUSUWATARI1)
                );
    }

    /**
     * Método que se encarga de obtener al Enemigo
     * @return TextureRegion del enemigo
     */
    public TextureRegion getEnemy(){
        return this.textureAtlas.findRegion(ENEMY);
    }

    /**
     * Método getMusicBG
     * @return la música de fondo del juego
     */
    public Music getMusicBG(){
        return this.assetManager.get(MUSIC_BG);
    }


    /**
     * Método getHit()
     * @return el sonido del personaje cuando ha sido golpeado
     */
    public Sound getHit(){
        return this.assetManager.get(HIT_SOUND);
    }



    public TextureRegion getGameOverImage(){
        return this.textureAtlas.findRegion(GAMEOVER);
    }
    /**
     * Método getFont()
     * @return la fuente personalizada
     */
    public BitmapFont getFont(){
        return new BitmapFont(Gdx.files.internal(FONT_FNT),Gdx.files.internal(FONT_PNG),false);
    }

    /**
     * Método getGetReady()
     * @return la imagen GETREADY
     */
    public TextureRegion getGetReady(){
        return this.textureAtlas.findRegion(GETREADY);
    }
    /**
     * Método getTouch()
     * @return TextureRegion de la imagen de la mano
     */
    public TextureRegion getTouch(){
        return this.textureAtlas.findRegion(TOUCH);
    }

    /**
     * Método getGameOver()
     * @return el sonido del personaje cuando una vez que ha sido golpeado se termina la partida
     */
    public Sound getGameOver() {
        return this.assetManager.get(GAMEOVER_SOUND);
    }
}
