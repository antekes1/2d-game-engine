package tile;

import java.awt.image.BufferedImage;

public class TileData {
    public int id;
    public String name;
    public boolean collision = false;

    public TileData(String name, int id, boolean collision) {
        this.id = id;
        this.name = name;
        this.collision = collision;
    }
}
