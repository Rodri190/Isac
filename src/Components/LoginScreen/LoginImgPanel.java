package Components.LoginScreen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class LoginImgPanel extends JPanel {
    private Image image;

    public LoginImgPanel() {
        setSize(400, 800);
        setBackground(Color.white);
        setVisible(true);
        image = new ImageIcon("src/img/isaacLogin.png").getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int x = (int) ((getWidth() - image.getWidth(this)) / 2);
        int y = (int) ((getHeight() - image.getHeight(this)) / 2) - 25;

        g.drawImage(image, x, y, image.getWidth(this), image.getHeight(this), this);
    }
}
