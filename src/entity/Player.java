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

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

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

    public void update() {
        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.leftPressed) {
                direction = "left";
                worldX -= speed;
            } else if (keyH.rightPressed) {
                direction = "right";
                worldX += speed;
            } else if (keyH.upPressed) {
                direction = "up";
                worldY -= speed;
            } else if (keyH.downPressed) {
                direction = "down";
                worldY += speed;
            }
            directionAnimation = direction;
        } else {
            isMoving = false;
            directionAnimation = "idle_" + direction;
        }
            if (!directionAnimation.equals(previousDirectionAnimation)) {
                spriteNum = 0;
            }
            previousDirectionAnimation = directionAnimation;

            spriteCounter++;
            if(spriteCounter > 10) {
                spriteNum++;
                int maxFrames = animations.get(directionAnimation).length;
                if(spriteNum >= maxFrames) {
                    spriteNum = 0;
                }
                spriteCounter = 0;
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

    }

}
