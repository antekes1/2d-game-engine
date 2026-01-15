package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class TileManager {
    GamePanel gp;
    Tile[] tile;
    int mapTileNum[][];
    String mapGenerationType = "maps";
    HashMap<String, String> tilesList;

    public TileManager(GamePanel gp, HashMap<String, String> tilesList, String mapGenerationType) {
        this.gp = gp;
        this.tilesList = tilesList;
        this.mapGenerationType = mapGenerationType;

        tile = new Tile[10];
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];

        getTileImage();
        loadMap("/maps/world01.txt");
    }
    public void getTileImage() {
        int index = 0;
        for (String key : tilesList.keySet()) {
            try {
                tile[index] = new Tile();
                URL url = getClass().getResource("/tiles/background/" + key);
                if (url == null) {
                    System.err.println("Resource not found: " + key);
                    continue;
                }
                tile[index].image = ImageIO.read(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            index++;
        }
    }
    public void loadMap(String filePath) {
        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;
            while(col < gp.maxScreenCol && row < gp.maxScreenRow) {
                String line = br.readLine();
                while (col < gp.maxScreenCol) {
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxScreenCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2) {

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while(col < gp.maxScreenCol && row < gp.maxScreenRow) {
            int tileNum = mapTileNum[col][row];
            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x+=gp.tileSize;
            if(col == gp.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y+=gp.tileSize;
            }
        }
    }
}
