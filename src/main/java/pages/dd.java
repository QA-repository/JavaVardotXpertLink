package pages;

import java.awt.*;
import java.awt.event.*;

public class dd {
    public static void main(String[] args) {
        try {
            Robot robot = new Robot();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

            while (true) {
                int x = (int) (Math.random() * screenSize.getWidth());
                int y = (int) (Math.random() * screenSize.getHeight());

                robot.mouseMove(x, y);

                // Adjust the sleep time based on your preference
                Thread.sleep(5000); // Move the mouse every 5 seconds
            }
        } catch (AWTException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
