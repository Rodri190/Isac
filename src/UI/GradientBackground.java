package UI;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

import Utilities.Fuente;


public class GradientBackground {
    private Fuente fuente;
    
    public GradientBackground(){
        fuente = new Fuente();
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

    public void ponerFondoGradienteRedondeado(Graphics g, int ancho, int alto, int xComp, int yComp, int xImg, int yImg, String textoUno, String textoDos, Color colorUno, Color colorDos, String path, ImageObserver observer){ 
        Graphics2D g2d = (Graphics2D) g.create();

        

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        RoundRectangle2D rectangulo = new RoundRectangle2D.Double(xComp, yComp, ancho, alto, 20, 20);

        GradientPaint gradiente = new GradientPaint(0, 0, colorUno, ancho, alto, colorDos);
        g2d.setPaint(gradiente);

        g2d.fill(rectangulo);

        g2d.setColor(Color.WHITE);
        Font fuenteTitulo = fuente.getFuente("negrita", 25);
        Font fuenteSubtitulo = fuente.getFuente("normal", 20);
        g2d.setFont(fuenteTitulo);

        int xTexto = xComp + 20; 
        int yTextoUno = (alto / 2 - 10) + yComp + 40; 
        int yTextoDos = (alto / 2 + 20) + yComp + 40; 

        g2d.drawString(textoUno, xTexto, yTextoUno);

        g2d.setFont(fuenteSubtitulo);

        g2d.drawString(textoDos, xTexto, yTextoDos);

        //para la imagen
        Image imagen = new ImageIcon(path).getImage();
        if (imagen != null) {
            g.drawImage(imagen, xImg, yImg, 80, 80, observer);
        }

        //para el circulo
        g2d.setColor(new Color(255, 255, 255, 64));

        g2d.fillOval(xComp + ancho - 150, yComp + alto - 100, 200, 200);
        g2d.fillOval(xComp + ancho - 100, yComp + alto - 180, 200, 200);

        g2d.dispose();
    }
}
