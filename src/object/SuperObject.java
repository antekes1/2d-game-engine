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
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public int solidAreaDefaultX = solidArea.x;
    public int solidAreaDefaultY = solidArea.y;

    public SuperObject(String image, String name, int worldX, int worldY, boolean collision){
        try {
            this.image = ImageIO.read(getClass().getResource("/tiles/objects/" + image));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.name = name;
        this.worldX = worldX;
        this.worldY = worldY;
        this.collision = collision;
    }

    public SuperObject() {
    }

    public void action() {}

    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (screenX + gp.tileSize > 0 &&
                screenX - gp.tileSize < gp.screenWidth &&
                screenY + gp.tileSize > 0 &&
                screenY - gp.tileSize < gp.screenHeight) {

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

            if(gp.debugView) {
                g2.setColor(new Color(255, 0, 0, 100)); // semi-transparent red
                g2.fillRect(
                        screenX + solidArea.x,
                        screenY + solidArea.y,
                        solidArea.width,
                        solidArea.height
                );
                g2.setColor(Color.RED);
                g2.drawRect(
                        screenX + solidArea.x,
                        screenY + solidArea.y,
                        solidArea.width,
                        solidArea.height
                );
            }
        }

    }
}
