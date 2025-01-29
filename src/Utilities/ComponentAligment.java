package Utilities;

import java.awt.*;
import Utilities.Fuente;
import javax.swing.*;
import java.awt.Graphics2D;


public class ComponentAligment {
    private Fuente fuente;
    
    public ComponentAligment(){
        fuente = new Fuente();
    }

    public int alignHorizontalComponent(int containerWidth, int componentWidth){
        if (containerWidth <= 1 || componentWidth <= 1) {
            throw new IllegalArgumentException("El ancho del contenedor y del componente deben ser mayores a 0.");
        }
        int aligment = (int) ((containerWidth - componentWidth) / 2);
        return aligment;
    }

    public int alignVerticalComponent(int containerWidth, int componentWidth){
        
        if (containerWidth <= 1 || componentWidth <= 1) {
            throw new IllegalArgumentException("El ancho del contenedor y del componente deben ser mayores a 0.");
        }
        
        int aligment = (int) ((containerWidth - componentWidth) / 2);
        return aligment;
    }

    public void alinearHorizontal(Graphics2D g, String texto, Color textColor, int ancho, int alto){//sobreescribir el metodo de arriba con el mismo nombre
        g.setColor(textColor);
        g.setFont(fuente.getFuente("negrita", 25));

        int textWidth = g.getFontMetrics().stringWidth(texto);
        int textHeight = g.getFontMetrics().getAscent();
        int textX = (int)((ancho - textWidth) / 2);
        int textY = (int)((alto + textHeight) / 2);
        g.drawString(texto, textX, textY);
    }
}
