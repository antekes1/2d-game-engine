package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class KeyHandler implements KeyListener {

    ArrayList<Integer> movementKeys = new ArrayList<>();

    public KeyHandler(ArrayList<Integer> movementKeys) {
        this.movementKeys = movementKeys;
    }

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(movementKeys.contains(code)) {
            int index = movementKeys.indexOf(code);
            switch(index) {
                case 0:
                    upPressed=true;
                    break;
                case 1:
                    downPressed=true;
                    break;
                case 2:
                    leftPressed=true;
                    break;
                case 3:
                    rightPressed=true;
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(movementKeys.contains(code)) {
            int index = movementKeys.indexOf(code);
            switch(index) {
                case 0:
                    upPressed=false;
                    break;
                case 1:
                    downPressed=false;
                    break;
                case 2:
                    leftPressed=false;
                    break;
                case 3:
                    rightPressed=false;
                    break;
            }
        }
    }
}
