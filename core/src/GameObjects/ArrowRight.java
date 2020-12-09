package GameObjects;

import com.badlogic.gdx.math.Vector2;

public class ArrowRight {

    private int width;
    private int height;
    private Vector2 position;

    public ArrowRight(int width, int height, float x, float y){
            this.width = width;
            this.height = height;
            position = new Vector2(x, y);
        }

    public void onClick() {
        //WOFF!
        this.width = width/2;
        this.height = height/2;
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
