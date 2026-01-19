package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyH;
    boolean canSprint = true;
    public Map<String, BufferedImage[]> animations;
    private String directionAnimation = "idle_up";
    String previousDirectionAnimation = "";

    public int screenX;
    public int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.worldWidth/2 - (gp.tileSize/2);
        screenY = gp.worldHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle(10,16, 26, 30);
        solidArea.x = (gp.tileSize - solidArea.width) / 2;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getPlayerImgae();
        setDefaultValues();
    }

    public void setDefaultValues() {;
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 23;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImgae() {
        animations = new HashMap<>();
        String[] actions = {
                "down", "up", "left", "right",
                "idle_down", "idle_left", "idle_right", "idle_up"
        };
        for (String action : actions) {
            List<BufferedImage> frames = new ArrayList<>();
            int frameNumber = 1;

            while (true) {
                // path: np. "/tiles/player/" + "up" + "_" + 1 + ".png"
                String path = "/tiles/player/" + action + "_" + frameNumber + ".png";
                InputStream stream = getClass().getResourceAsStream(path);

                if (stream == null) {
                    break;
                }
                try {
                    frames.add(ImageIO.read(stream));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                frameNumber++;
            }

            if (!frames.isEmpty()) {
                animations.put(action, frames.toArray(new BufferedImage[0]));
            }
        }
    }

    // W pliku Player.java

    public void update() {
        // 1. Obsługa klawiszy (Ruch po świecie)
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            } else if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            }

            directionAnimation = direction;
            isMoving = true;

            collisionOn = false;
            gp.cChecker.checkTile(this);

            int objectIndex = gp.cChecker.checkObject(this, true);
            if (objectIndex != 9999){
                gp.obj[objectIndex].action();
            }

            if(!collisionOn) {
                switch (direction){
                    case "up":
                        worldY -= speed;break;
                    case "down":
                        worldY += speed;break;
                    case "left":
                        worldX -= speed;break;
                    case "right":
                        worldX += speed;break;
                }
            }
        } else {
            isMoving = false;
            directionAnimation = "idle_" + direction;
        }



        // 2. Obsługa animacji
        if (!directionAnimation.equals(previousDirectionAnimation)) {
            spriteNum = 0;
        }
        previousDirectionAnimation = directionAnimation;

        spriteCounter++;
        if (spriteCounter > 10) {
            spriteNum++;
            if (animations.get(directionAnimation) != null) {
                int maxFrames = animations.get(directionAnimation).length;
                if (spriteNum >= maxFrames) {
                    spriteNum = 0;
                }
            }
            spriteCounter = 0;
        }

        // 3. LOGIKA KAMERY
        int screenCenterX = gp.screenWidth / 2 - (gp.tileSize / 2);
        int screenCenterY = gp.screenHeight / 2 - (gp.tileSize / 2);

        // --- Oś X ---
        if (worldX < screenCenterX) {
            screenX = worldX;
        }
        else if (worldX > gp.worldWidth - screenCenterX - gp.tileSize) {
            screenX = gp.screenWidth - (gp.worldWidth - worldX);
        }
        else {
            screenX = screenCenterX;
        }

        // --- Oś Y ---
        if (worldY < screenCenterY) {
            screenY = worldY;
        }
        else if (worldY > gp.worldHeight - screenCenterY - gp.tileSize) {
            screenY = gp.screenHeight - (gp.worldHeight - worldY);
        }
        else {
            screenY = screenCenterY;
        }
    }

    public void draw(Graphics2D g2) {
//        g2.setColor(Color.WHITE);
//        g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        if (animations == null || animations.get(directionAnimation) == null || animations.get(directionAnimation)[spriteNum] == null) {
            return;
        }

        BufferedImage image = null;
        image = animations.get(directionAnimation)[spriteNum];
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

        if(gp.debugView) {
            g2.setColor(new Color(255, 0, 0, 100)); // semi-transparent red
            g2.fillRect(
                    screenX + solidArea.x,
                    screenY + solidArea.y,
                    solidArea.width,
                    solidArea.height
            );
            g2.setColor(Color.RED); // optional: outline
            g2.drawRect(
                    screenX + solidArea.x,
                    screenY + solidArea.y,
                    solidArea.width,
                    solidArea.height
            );
        }

    }

}
