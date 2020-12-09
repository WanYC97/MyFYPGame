package GameObjects;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Pet {

    private int width;
    private int height;
    private Rectangle boundingRectangle;
    private Vector2 position;

    public Pet(int width, int height, float x, float y) {
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);
        boundingRectangle = new Rectangle();
        boundingRectangle.set(position.x, position.y, width, height);
    }


    public void onClick() {
        //WOFF!
        this.width = width / 2;
        this.height = height / 2;
    }

    public void update() {

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

    public Rectangle getBoundingRectangle() {
        return boundingRectangle;
    }

}
