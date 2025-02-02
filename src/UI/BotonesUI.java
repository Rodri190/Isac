package UI;


import javax.swing.JButton;

import Utilities.Fuente;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BotonesUI extends JButton {
    private Color borderColor;
    private Color bgColor;
    private Color borderColorAux;
    private Color bgColorAux;
    private Color colorPressed;
    private FontMetrics metrics;
    private String text;
    private Fuente fuente;

    public BotonesUI(String text, Color borderColor, Color bgColor, Color colorPressed) {
        this.text = text;
        fuente = new Fuente();
        setContentAreaFilled(false);
        setBorderPainted(false);
        setText("Jugar");
        setFont(new Font("Cooper Black", Font.BOLD, 15));
        setForeground(Color.decode("#00ffff"));
        setBounds(100, 100, 180, 50);
        this.borderColor = borderColor;
        this.bgColor = bgColor;
        this.colorPressed = colorPressed;
        borderColorAux = borderColor;
        bgColorAux = bgColor;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // para la fuente
        g2.setFont(fuente.getFuente("normal", 20));
        metrics = g2.getFontMetrics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // para pintar el borde
        g2.setColor(borderColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 50, 50);
        
        // para el borde
        g2.setColor(bgColor);
        g2.fillRoundRect(4, 4, getWidth() - 8, getHeight() - 8, 50, 50);

        // para dibujar el texto
        g2.setColor(Color.decode("#ffffff"));
        g2.drawString(text, (getWidth() - metrics.stringWidth(text)) / 2, 33);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                borderColor = bgColorAux;
                bgColor = borderColorAux;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                borderColor = borderColorAux;
                bgColor = bgColorAux;
            }

            @Override
            public void mousePressed(MouseEvent e) {
                borderColor = borderColorAux;
                bgColor = colorPressed;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                borderColor = bgColorAux;
                bgColor = borderColorAux;
            }
        });
    }
}

