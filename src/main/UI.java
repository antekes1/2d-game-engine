package main;

import java.awt.*;

public class UI {

    GamePanel gp;
    Font arial_40;

    public UI(GamePanel gp){
        this.gp = gp;
        arial_40 = new Font("Arial", Font.BOLD, 40);
    }

    public void draw(Graphics2D g2){
        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);

        // drawing
        g2.drawString("Speed = " + gp.player.speed, 50, 50);

    }

}
