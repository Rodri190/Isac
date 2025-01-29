package Components.LoginScreen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import Utilities.ComponentAligment;

public class LoginImgPanel extends JPanel {
    private JLabel loginImg;
    private ComponentAligment aligment;

    public LoginImgPanel() {
        setLayout(null);
        setBackground(Color.decode("#ffffff"));//2103B0
        setSize(400, 700);
        aligment = new ComponentAligment();
        // setLoginImage();
    }

    private void setLoginImage(){
        loginImg = new JLabel();
        try {
            Image imagen = new ImageIcon("src/img/logo.png").getImage();
            loginImg.setSize(imagen.getWidth(null), imagen.getHeight(null));
            loginImg.setLocation(aligment.alignHorizontalComponent(this.getWidth(), loginImg.getWidth()), aligment.alignVerticalComponent(this.getWidth(), loginImg.getWidth()));
            Icon icono = new ImageIcon(imagen.getScaledInstance(loginImg.getWidth(), loginImg.getHeight(), Image.SCALE_DEFAULT));
            loginImg.setIcon(icono);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // loginImg.setBorder(new LineBorder(Color.decode("#ffffff"), 3));
        add(loginImg);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Image imagen = new ImageIcon("src/Resources/img/logo2.jpg").getImage();
        super.paintComponent(g);
        if (imagen != null) {
            g.drawImage(imagen, aligment.alignHorizontalComponent(this.getWidth(), imagen.getWidth(null)), aligment.alignVerticalComponent(this.getHeight(), imagen.getHeight(null)) - 10, (int)(imagen.getWidth(null) ), (int)(imagen.getHeight(null) ), this);
        }
    }

}
