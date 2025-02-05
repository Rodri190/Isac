package UI;
import javax.swing.*;
import java.awt.*;

public class GradientComboBox extends JComboBox<String> {
    private Color colorInicio;
    private Color colorFin;

    public GradientComboBox(String[] items, Color colorInicio, Color colorFin) {
        super(items);
        this.colorInicio = colorInicio;
        this.colorFin = colorFin;
        setOpaque(false); // Hacer el componente transparente
        setFont(new Font("Arial", Font.PLAIN, 18)); // Ajustar tamaño de letra
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (isOpaque()) {
            Graphics2D g2d = (Graphics2D) g.create();
            int width = getWidth();
            int height = getHeight();

            // Crear un degradado de arriba a abajo
            GradientPaint gradient = new GradientPaint(0, 0, colorInicio, 0, height, colorFin);
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, width, height); // Aplicar el fondo degradado

            g2d.dispose();
        }
        super.paintComponent(g); // Dibuja el contenido normal del JComboBox (texto, iconos, etc.)
    }

    @Override
    public void paintBorder(Graphics g) {
        super.paintBorder(g);  // Mantener el borde estándar
    }
}
