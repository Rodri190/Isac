package UI;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;
import javax.swing.JLabel;

import Utilities.Fuente;

public class GradientButton extends JLabel {
    private String text;
    private Color color1;
    private Color color2;
    private Color textColor;
    private Fuente fuente;
    private String tipoFuente;
    private int tamanioFuente;

    public GradientButton(String text, int x, int y, int width, int height, Color color1, Color color2, Color textColor, String tipoFuente, int tamanioFuente) {
        this.text = text;
        this.color1 = color1;
        this.color2 = color2;
        this.textColor = textColor;
        fuente = new Fuente();
        this.tipoFuente = tipoFuente;
        this.tamanioFuente = tamanioFuente;
        setLocation(x, y);
        setSize(width, height);
        // setContentAreaFilled(false);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // for gradient color
        GradientPaint gradientColor = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
        g2d.setPaint(gradientColor);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // for text
        g2d.setColor(textColor);
        g2d.setFont(fuente.getFuente(tipoFuente, tamanioFuente));

        int textWidth = g2d.getFontMetrics().stringWidth(text);
        int textHeight = g2d.getFontMetrics().getAscent();
        int textX = (int)((getWidth() - textWidth) / 2);
        int textY = (int)((getHeight() + textHeight) / 2);
        g2d.drawString(text, textX, textY);
    }
}
