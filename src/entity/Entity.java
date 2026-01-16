package entity;

import java.awt.*;

public class Entity {

    public int worldX, worldY;
    public int speed;

    public boolean isMoving;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public boolean hitboxVisible=false;

    public Rectangle solidArea;
    public boolean collisionOn = false;

}
