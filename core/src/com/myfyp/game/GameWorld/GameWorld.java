package com.myfyp.game.GameWorld;

import GameObjects.ArrowLeft;
import GameObjects.ArrowRight;
import GameObjects.Pet;
import GameObjects.Toy;

public class GameWorld {
    private ArrowLeft arrowLeft;
    private ArrowRight arrowRight;
    private Pet pet;
    private Toy toy;

    public GameWorld(int gameWidth, int gameHeight, int midPointY){
        pet = new Pet(8, 10, gameWidth/2 - 8/2, midPointY - 10/2);
        arrowLeft = new ArrowLeft(3, 2, 0, gameHeight/2);
        arrowRight = new ArrowRight(3, 2, gameWidth - 3,gameHeight/2);
        toy = new Toy(1, 1, gameWidth/2, 5);
    }
/*
    public boolean collides(Toy toy){
        return(pet.collides(toy));
    }
*/
    public void update(float delta){
       //toy.update(delta);
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
}
