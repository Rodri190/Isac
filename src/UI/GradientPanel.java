package UI;

import java.awt.Color;
import java.awt.Graphics2D;

import Utilities.Fuente;

public class GradientPanel {
    private Fuente fuente;
    
    public GradientPanel(){
        fuente = new Fuente();
    }

    public void crearGradientPanel(Graphics2D g, String texto, Color textColor, int ancho, int alto){
        g.setColor(textColor);
        g.setFont(fuente.getFuente("negrita", 25));

        int textWidth = g.getFontMetrics().stringWidth(texto);
        int textHeight = g.getFontMetrics().getAscent();
        int textX = (int)((ancho - textWidth) / 2);
        int textY = (int)((alto + textHeight) / 2);
        g.drawString(texto, textX, textY);
    }
}
