package com.myfyp.game.GameWorld;

import GameObjects.ArrowLeft;
import GameObjects.ArrowRight;
import GameObjects.Coin;
import GameObjects.Food;
import GameObjects.Fun;
import GameObjects.Happiness;
import GameObjects.Pet;
import GameObjects.RunButton;
import GameObjects.Toy;

public class GameWorld {
    private ArrowLeft arrowLeft;
    private ArrowRight arrowRight;
    private RunButton runButton;
    private Pet pet;
    private Toy toy;
    private Food food;
    private Coin coin;
    private Fun fun;
    private Happiness happiness;

    public GameWorld(int gameWidth, int gameHeight, int midPointY){
        arrowLeft = new ArrowLeft(2, 3, 0, gameHeight/2);
        arrowRight = new ArrowRight(2, 3, gameWidth - 3,gameHeight/2);
        runButton = new RunButton(3,3, gameWidth/2 - 2, 3);
        pet = new Pet(11, 15, gameWidth/2 - 11/2, midPointY - 15/2);
        toy = new Toy(4, 4, gameWidth/2 - 2, 9);
        food = new Food(6, 4, gameWidth/2 - 3, 9);
        coin = new Coin(2,2, 0,gameHeight -2);
        fun = new Fun(2,2 , 0, gameHeight -4.5f);
        happiness = new Happiness(2,2 , 0, gameHeight -7.0f);
    }

    public void update(float delta){
    }

    public Pet getPet(){
        return pet;
    }

    public Toy getToy(){
        return toy;
    }

    public ArrowLeft getArrowLeft(){
        return arrowLeft;
    }

    public ArrowRight getArrowRight(){
        return arrowRight;
    }

    public RunButton getRunButton(){return runButton;}

    public Coin getCoin(){
        return coin;
    }

    public Fun getFun(){
        return fun;
    }

    public Happiness getHappiness(){
        return happiness;
    }

    public Food getFood(){return food;}
}
