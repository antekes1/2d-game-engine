package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];
    String mapGenerationType = "maps";
    public LinkedHashMap<String, TileData> tilesList;

    public TileManager(GamePanel gp, LinkedHashMap<String, TileData> tilesList, String mapGenerationType) {
        this.gp = gp;
        this.tilesList = tilesList;
        this.mapGenerationType = mapGenerationType;

        tile = new Tile[10];

        getTileImage();
        loadMap("/maps/world01.txt");
    }

    public void getTileImage() {
        for (String key : tilesList.keySet()) {
            TileData data = tilesList.get(key);
            try {
                tile[data.id] = new Tile();
                URL url = getClass().getResource("/tiles/background/" + key);
                if (url == null) {
                    System.err.println("Resource not found: " + key);
                    continue;
                }
                tile[data.id].image = ImageIO.read(url);
                tile[data.id].collision = data.collision;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            List<String> mapLines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                mapLines.add(line);
            }
            br.close();

            if (mapLines.isEmpty()) return;

            int maxRows = mapLines.size();

            int maxCols = 0;
            for (String mapLine : mapLines) {
                String[] numbers = mapLine.split(" ");
                if (numbers.length > maxCols) {
                    maxCols = numbers.length;
                }
            }

            mapTileNum = new int[maxCols][maxRows];

            for (int row = 0; row < maxRows; row++) {
                String rowLine = mapLines.get(row);
                String numbers[] = rowLine.split(" ");

                for (int col = 0; col < maxCols; col++) {
                    if (col < numbers.length) {
                        int num = Integer.parseInt(numbers[col]);
                        mapTileNum[col][row] = num;
                    }
                }
            }

            gp.maxWorldCol = maxCols;
            gp.maxWorldRow = maxRows;
            gp.worldWidth = gp.tileSize * gp.maxWorldCol;
            gp.worldHeight = gp.tileSize * gp.maxWorldRow;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        if (mapTileNum == null) return;

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < mapTileNum.length && worldRow < mapTileNum[0].length) {

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (screenX + gp.tileSize > 0 &&
                    screenX - gp.tileSize < gp.screenWidth &&
                    screenY + gp.tileSize > 0 &&
                    screenY - gp.tileSize < gp.screenHeight) {

                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            worldCol++;

            if (worldCol == mapTileNum.length) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
