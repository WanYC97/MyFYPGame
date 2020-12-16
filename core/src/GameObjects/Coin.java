package GameObjects;

import com.badlogic.gdx.math.Vector2;

public class Coin {

    private int width;
    private int height;
    private Vector2 position;

    public Coin(int width, int height, float x, float y){
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}