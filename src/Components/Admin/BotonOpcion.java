package Components.Admin;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import Utilities.ComponentStyler;
import Utilities.Fuente;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

public class BotonOpcion extends JLabel {
    private String texto;
    private String imgPath;
    private ComponentStyler styler;
    private Fuente fuente;

    public BotonOpcion(String texto, String imgPath, int ancho, int alto, int x, int y) {
        this.texto = texto;
        this.imgPath = imgPath;
        styler = new ComponentStyler();
        fuente = new Fuente();
        styler.style(this, ancho, alto, null, "normal", 0, Color.decode("#ffffff"));//el tamanio de fuente aqui no cuenta
        setLocation(x, y);
        setOpaque(false);
        // setBackground(new Color(255, 255, 255, 120));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Image imagen = new ImageIcon(imgPath).getImage();
        if (imagen != null) {
            g.drawImage(imagen, 10, 0, getWidth() / 5, getHeight(), this);
        }

        g.setColor(Color.WHITE);
        Font fuenteLetra = fuente.getFuente("normal", 20);
        g.setFont(fuenteLetra);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int espacioImagen = getWidth() / 5; 
        int espacioSobrante = getWidth() - espacioImagen; 

        FontMetrics metrics = g.getFontMetrics(fuenteLetra);
        int anchoTexto = metrics.stringWidth(texto); 
        int altoTexto = metrics.getHeight(); 

        int x = espacioImagen + (espacioSobrante - anchoTexto) / 2;

        int y = (getHeight() - altoTexto) / 2 + metrics.getAscent();

        g.drawString(texto, x, y);
    }
}