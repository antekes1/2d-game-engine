package main.objects;

import main.GamePanel;
import object.SuperObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Chest extends SuperObject {
    private GamePanel gp;
    private int id;

    public Chest(GamePanel gp, int id) {
        this.gp = gp;
        this.id = id;
        name = "Chest";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/tiles/objects/chest.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;

        int tileSize = 48;

        int solidWidth = 32;
        int solidHeight = 40;

        int solidX = (tileSize - solidWidth) / 2;
        int solidY = tileSize - solidHeight;

        solidArea = new Rectangle(solidX, solidY, solidWidth, solidHeight);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

    }

    @Override
    public void action() {
        gp.player.speed += 2;
        gp.obj[id] = null;
        gp.playSE(1);
    }

}
