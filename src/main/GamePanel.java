package main;

import entity.CollisionChecker;
import entity.Player;
import tile.TileData;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class GamePanel extends JPanel implements Runnable {
    /// Settings for game engine
    final int BaseTileSize = 16; // 16x16 default
    final int scale = 3;
    final int FPS = 60;
    ////////////////////
    public boolean debugView=false;

    final public int tileSize = BaseTileSize * scale;
    final public int maxScreenCol = 24 ;
    final public int maxScreenRow = 18;
    final public int screenWidth = tileSize * maxScreenCol;
    final public int screenHeight = tileSize * maxScreenRow;
    final ArrayList<Integer> movementKeys = new ArrayList<>(
            Arrays.asList(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D)
    );

    private LinkedHashMap<String, TileData> tilesList = new LinkedHashMap<String, TileData>();
    {
        tilesList.put("grass_texture.png", new TileData("grass_dark", 0, false));
        tilesList.put("grass_dry.png", new TileData("grass_dry", 1, false));
        tilesList.put("rock_on_grass.png", new TileData("rock_on_grass", 2, true));
        tilesList.put("tree_texture_grass.png", new TileData("rock_on_grass", 3, true));
    }

    // WORLD SETTINGS
    public int maxWorldCol = 50;
    public int maxWorldRow = 50;
    public int worldWidth = tileSize * maxWorldCol;
    public int worldHeight = tileSize * maxWorldRow;

    public CollisionChecker cChecker = new CollisionChecker(this);
    public TileManager tileM = new TileManager(this, tilesList, "maps");
    KeyHandler keyH = new KeyHandler(this, movementKeys);
    Thread gameThread;
    public Player player = new Player(this, keyH);

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        while(gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
//            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;
//                drawCount++;
            }
//            if(timer > 1000000000) {
//                System.out.println("FPS: " + drawCount);
//                drawCount = 0;
//                timer = 0;
//            }
    }}
    public void update() {

        player.update();

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);
        player.draw(g2);

        g2.dispose();
    }
}
