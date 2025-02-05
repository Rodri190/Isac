package UI;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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

    private int cornerRadius = 20; // Radio de los bordes redondeados
    private Color borderColor = Color.decode("#FDDE46"); // Color del borde
    private int borderThickness = 5;         // Grosor del borde

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
        setOpaque(false); // Necesario para los bordes redondeados
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Mejora la calidad de renderizado
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fondo con gradiente
        GradientPaint gradientColor = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
        g2d.setPaint(gradientColor);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        // Borde redondeado
        // g2d.setColor(borderColor);
        // g2d.setStroke(new java.awt.BasicStroke(borderThickness)); // Grosor del borde
        // g2d.drawRoundRect(borderThickness / 2, borderThickness / 2, getWidth() - borderThickness, getHeight() - borderThickness, cornerRadius, cornerRadius);

        // Texto centrado
        g2d.setColor(textColor);
        g2d.setFont(fuente.getFuente(tipoFuente, tamanioFuente));

        int textWidth = g2d.getFontMetrics().stringWidth(text);
        int textHeight = g2d.getFontMetrics().getAscent();
        int textX = (getWidth() - textWidth) / 2;
        int textY = (getHeight() + textHeight) / 2 - 4;

        g2d.drawString(text, textX, textY);
    }
}
