package UI;

import javax.swing.*;
import java.awt.*;

public class GradientLabel extends JLabel {
    private Color colorInicio;
    private Color colorFin;

    public GradientLabel(String text, Color colorInicio, Color colorFin, int size) {
        super(text);
        this.colorInicio = colorInicio;
        this.colorFin = colorFin;
        setFont(new Font("Serif", Font.BOLD, size)); 
        setOpaque(false); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        int width = getWidth();
        int height = getHeight();

        GradientPaint gradient = new GradientPaint(0, height, colorInicio, 0, 0, colorFin);
        g2d.setPaint(gradient);

        g2d.setFont(getFont());
        FontMetrics fm = g2d.getFontMetrics();
        int x = (width - fm.stringWidth(getText())) / 2; 
        int y = (height + fm.getAscent() - fm.getDescent()) / 2; 
        g2d.drawString(getText(), x, y);
        
        g2d.dispose();
    }
}
