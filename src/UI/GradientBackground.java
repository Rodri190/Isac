package UI;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.GradientPaint;


public class GradientBackground {
    
    public GradientBackground(){

    }

    public void ponerFondoGradiente(Graphics g, int ancho, int alto, Color color1, Color color2){
        Graphics2D g2d = (Graphics2D) g;

        GradientPaint gradient = new GradientPaint(
                0, 0, color1,
                ancho, alto, color2
        );

        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, ancho, alto);
    }
}
