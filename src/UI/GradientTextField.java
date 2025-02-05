package UI;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class GradientTextField extends JTextField {
    private Color colorInicio;
    private Color colorFin;
    private int grosor;

    public GradientTextField(Color colorInicio, Color colorFin, int grosor) {
        this.colorInicio = colorInicio;
        this.colorFin = colorFin;
        this.grosor = grosor;

        // Ajusta el margen interno para que el texto no toque el borde
        setBorder(new EmptyBorder(grosor, grosor + 5, grosor, grosor + 5));
        setOpaque(false); // Hace que el fondo del JTextField sea transparente
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        int width = getWidth();
        int height = getHeight();

        // Crear un degradado de izquierda a derecha
        GradientPaint gradient = new GradientPaint(0, 0, colorInicio, width, 0, colorFin);
        g2d.setPaint(gradient);
        g2d.setStroke(new BasicStroke(grosor)); // Ajusta el grosor del borde

        // Dibujar el borde considerando el grosor
        g2d.drawRect(grosor / 2, grosor / 2, width - grosor, height - grosor);
        g2d.dispose();
    }
}
