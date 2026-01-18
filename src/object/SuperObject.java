package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SuperObject {

    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;

    public SuperObject(String image, String name, int worldX, int worldY){
        try {
            this.image = ImageIO.read(getClass().getResource("/tiles/objects/" + image));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.name = name;
        this.worldX = worldX;
        this.worldY = worldY;
    }

    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (screenX + gp.tileSize > 0 &&
                screenX - gp.tileSize < gp.screenWidth &&
                screenY + gp.tileSize > 0 &&
                screenY - gp.tileSize < gp.screenHeight) {

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }

    }

}
