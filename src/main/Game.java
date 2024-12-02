
package main;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.WindowConstants;


public class Game {
     static MainFrame mainFrame;
    public static void main(String[] args) throws IOException {
                
        mainFrame = new MainFrame(new JFrame());
        
        mainFrame.setSize(new Dimension(640,640));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation((int) (screenSize.getWidth()/2 - mainFrame.getSize().getWidth() /2 ), (int) (screenSize.getHeight()/2 - mainFrame.getSize().getHeight()/2 ));
        mainFrame.setResizable(false);
        mainFrame.setTitle("Mayro bors");
        mainFrame.setVisible(true);
        
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        
                
        
    }
    
}
