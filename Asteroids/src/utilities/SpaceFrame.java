package utilities;

// code copied from Simon Lucas
// code copied by Udo Kruschwitz
// code edited by Edan Bradbury

import Listeners.ControlListener;
import Listeners.GameMouseListener;

import javax.swing.*;
import java.awt.*;

public class SpaceFrame extends JFrame {
    public Component comp;
    static public int width;
    static public int height;
    public SpaceFrame(Component comp, String title){
        super(title);
        this.comp = comp;
        getContentPane().setBackground(Color.black);
        getContentPane().add(BorderLayout.CENTER, comp);
        //setSize(800,600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        width = Toolkit.getDefaultToolkit().getScreenSize().width;
        height = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.addKeyListener(new ControlListener());
        this.addMouseListener(new GameMouseListener());

        this.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        //repaint();
    }
}
