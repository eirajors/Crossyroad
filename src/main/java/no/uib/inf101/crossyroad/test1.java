package no.uib.inf101.crossyroad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class test1 extends JPanel implements ActionListener {
    private int x = 0; // Current X coordinate
    private int y = 100; // Current Y coordinate
    private int speedX = 2; // Pixels to move per tick
    private Timer timer;

    public test1() {
        // Create timer that fires every 10 milliseconds
        timer = new Timer(10, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        // Draw object using current coordinates
        g.fillRect(x, y, 50, 50); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Update coordinates
        x += speedX;
        
        // Stop or reverse if it hits the edge
        if (x > getWidth()) {
            x = 0; 
        }
        
        // Refresh the screen
        repaint(); 
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Moving Object");
        frame.add(new test1());
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
