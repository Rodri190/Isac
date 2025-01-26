package Components.Admin;

import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import Utilities.ComponentAligment;
import Utilities.ComponentStyler;

public class BarraLateral extends JPanel {
    private JLabel logoLabel;
    private ComponentStyler styler;
    private ComponentAligment aligment;

    public BarraLateral(int altura) {
        styler = new ComponentStyler();
        aligment = new ComponentAligment();
        setBounds(0, 0, 300, altura);
        setBackground(Color.decode("#ff0000"));
        setLayout(null);
        initComponents();
    }

    private void initComponents() {
        initLogoLabel();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        Color colorStart = Color.decode("#E8113A");
        Color colorEnd = Color.decode("#4A0075");

        GradientPaint gradient = new GradientPaint(
                0, 0, colorStart,
                getWidth(), getHeight(), colorEnd
        );

        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }

    private void initLogoLabel() {
        logoLabel = new JLabel() {
            @Override
            public void setText(String text) {
                super.setText("Preuniversitario");
            }
            @Override
            public void setSize(int width, int height) {
                super.setSize(250, 100);
            }
            @Override
            protected void paintComponent(Graphics g) {
                Image imagen = new ImageIcon("src/Resources/img/logoAdminPanel.png").getImage();
                super.paintComponent(g);
                if (imagen != null) {
                    g.drawImage(imagen, 0, 0, logoLabel.getWidth() / 3, logoLabel.getHeight(), logoLabel);
                }
            }
        };
        styler.style(logoLabel, this.getWidth(), 100, null, "negrita", 20, Color.white);
        logoLabel.setLocation(aligment.alignHorizontalComponent(this.getWidth(), logoLabel.getWidth()), 30);
        logoLabel.setLayout(null);
        logoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        // logoLabel.setBorder(new LineBorder(Color.white, 3));
        add(logoLabel);
    }
}
