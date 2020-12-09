package com.myfyp.game.helper;

import com.badlogic.gdx.InputProcessor;

import GameObjects.ArrowLeft;
import GameObjects.ArrowRight;
import GameObjects.Pet;

public class InputHandler implements InputProcessor {
    private Pet myPet;
    private ArrowLeft arrowLeft;
    private ArrowRight arrowRight;

    //references for pet object
    public InputHandler(Pet pet){
        myPet = pet;
    }

    public InputHandler(ArrowLeft left){
        arrowLeft = left;
    }
    public InputHandler(ArrowRight right){
        arrowRight = right;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    //When user touch
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        myPet.onClick();
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
