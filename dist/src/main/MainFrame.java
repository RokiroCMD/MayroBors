package main;

import java.awt.Color;
import java.io.IOException;
import javax.swing.JFrame;

public class MainFrame extends JFrame {

    MouseInput titleMouse;

    
    TitlePanel tp;

    MainFrame(JFrame jFrame) throws IOException {
        
        tp = new TitlePanel(this);
        tp.setLocation(0, 0);
        tp.setSize(this.getSize());
        tp.setBackground(Color.black);
        tp.setVisible(true);
        this.add(tp);
        titleMouse = new MouseInput(tp);
        addMouseListener(titleMouse);

    }

    public void pantallaJuego() throws IOException {
        this.dispose();
        GameFrame gf = new GameFrame();
        

    }

}
