
package Utilities;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import Utilities.ComponentAligment;

import javax.swing.SwingConstants;

public class ComponentStyler {
    private ComponentAligment aligment;
    private Fuente fuente;

    public ComponentStyler() {
        aligment = new ComponentAligment();
        fuente = new Fuente();
    }

    public void style(Component componente, int ancho, int alto, Color colorFondo,String tipoFuente, int tamanioFuente, Color colorFuente) {
        componente.setSize(ancho, alto);
        componente.setBackground(colorFondo == null ? null : colorFondo);
        componente.setForeground(colorFuente);
        componente.setFont(fuente.getFuente(tipoFuente, tamanioFuente));
    }
}