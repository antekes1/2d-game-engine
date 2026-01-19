package object;

import main.GamePanel;
import main.objects.Chest;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
//        gp.obj[0] = new SuperObject("chest.png", "chest", 100, 100, true);
        gp.obj[0] = new Chest(gp, 0);
        gp.obj[0].worldX = 100;
        gp.obj[0].worldY = 150;
    }

}
