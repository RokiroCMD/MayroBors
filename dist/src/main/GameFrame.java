
package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import static main.Game.mainFrame;


public class GameFrame extends JFrame {
    GamePanel gp;
    KeyInputs ki;

    public GameFrame() throws IOException {
       
        this.setSize(new Dimension(640,640));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int) (screenSize.getWidth()/2 - this.getSize().getWidth() /2 ), (int) (screenSize.getHeight()/2 - this.getSize().getHeight()/2 ));
        this.setResizable(false);
        this.setTitle("Mayro bors");
        this.setVisible(true);
        
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        gp = new GamePanel();
        gp.setLocation(0, 0);
        gp.setSize(this.getSize());
        gp.setBackground(Color.CYAN);
        gp.setVisible(true);
        this.add(gp);
        ki = new KeyInputs(gp);
        addKeyListener(ki);
    }
    
    
        
}
