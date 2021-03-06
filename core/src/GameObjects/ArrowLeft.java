package GameObjects;

import com.badlogic.gdx.math.Vector2;

public class ArrowLeft {

    private int width;
    private int height;
    private Vector2 position;

    public ArrowLeft(int width, int height, float x, float y){
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
