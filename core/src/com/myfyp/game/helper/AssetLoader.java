package com.myfyp.game.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class AssetLoader {
    public static Texture shiba;
    public static BitmapFont font;
    public static BitmapFont shadow;
    public static Texture arrowLeft;
    public static Texture arrowRight;
    public static Texture toy;

    public static void load(){
        shiba = new Texture(Gdx.files.internal("shiba.png"));

        font = new BitmapFont(Gdx.files.internal("text.fnt"));
        font.getData().setScale(0.025f, 0.05f);

        shadow = new BitmapFont(Gdx.files.internal("shadow.fnt"));
        shadow.getData().setScale(0.025f, 0.05f);

        arrowLeft = new Texture((Gdx.files.internal("arrow_left.png")));
        arrowRight = new Texture((Gdx.files.internal("arrow_right.png")));

        toy = new Texture((Gdx.files.internal("toy.png")));

    }
    public static void dispose(){
        shiba.dispose();
        font.dispose();
        shadow.dispose();
    }
}
