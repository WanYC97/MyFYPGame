package GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Toy {

    private int width;
    private int height;
    //private Circle boundingCircle;
    private Vector2 position;
    private Vector2 velocity;
    private float prevX;
    private float prevY;

    public Toy(int width, int height, float x, float y){
        this.width = width;
        this.height = height;
        position = new Vector2(x, y);
        //boundingCircle = new Circle();
        //velocity = new Vector2(0,0);
        //boundingCircle.set(position.x, position.y, width/2);
    }

    public void update(float delta){
        //position.add(velocity.cpy().scl(delta));
    }

    public void onClick() {
        this.width = width/2;
        this.height = height/2;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getX(){
        return position.x;
    }

    public float getY(){
        return position.y;
    }

    /*public Circle getBoundingCircle(){
        return boundingCircle;
    }*/
}
