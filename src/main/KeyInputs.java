package main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInputs extends KeyAdapter {

    GamePanel panel;

    public KeyInputs(GamePanel panel) {
        this.panel = panel;
    }

    @Override
    public void keyPressed(KeyEvent e){
        panel.keyPressed(e);
    }
    
    @Override
    public void keyReleased(KeyEvent e){
        panel.keyReleased(e);
    }
    
}
