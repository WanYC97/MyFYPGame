package com.myfyp.game.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import javax.xml.soap.Text;

public class AssetLoader {
    public static Texture background;
    public static Texture background_play;
    public static Texture background_upgrade;

    public static Texture shiba;
    public static BitmapFont font;
    public static BitmapFont shadow;
    public static Texture arrowLeft;
    public static Texture arrowRight;
    public static Texture runButton;
    public static Texture toy;
    public static Texture food;
    public static Texture coin;
    public static Texture fun;
    public static Texture happiness;
    public static Texture fallingObject;
    public static Texture upgrade1;
    public static Texture upgrade2;
    public static Texture upgrade3;

    public static void load(){
        background = new Texture("background.png");
        background_play = new Texture("background_play.png");
        background_upgrade = new Texture("background_upgrade.png");

        shiba = new Texture(Gdx.files.internal("shiba.png"));

        font = new BitmapFont(Gdx.files.internal("text.fnt"));
        font.getData().setScale(0.025f, 0.05f);

        shadow = new BitmapFont(Gdx.files.internal("shadow.fnt"));
        shadow.getData().setScale(0.025f, 0.05f);

        arrowLeft = new Texture((Gdx.files.internal("arrow_left.png")));
        arrowRight = new Texture((Gdx.files.internal("arrow_right.png")));
        runButton = new Texture((Gdx.files.internal("Rocket-PNG-Clipart.png")));

        toy = new Texture((Gdx.files.internal("toy.png")));
        food = new Texture((Gdx.files.internal("food.png")));

        coin = new Texture((Gdx.files.internal("coin.png")));
        fun = new Texture((Gdx.files.internal("fun.png")));
        happiness = new Texture((Gdx.files.internal("happiness.png")));

        fallingObject = new Texture((Gdx.files.internal("toy.png")));

        upgrade1 = new Texture((Gdx.files.internal("ball1.png")));
        upgrade2 = new Texture((Gdx.files.internal("ball2.png")));
        upgrade3 = new Texture((Gdx.files.internal("ball3.png")));

    }
    public static void dispose(){
    }
}

