package Utilities;

import java.awt.Component;

public class Separador {
    
    public Separador(){

    }

    public int separar(Component anterior, int espaciado){
        return anterior.getY() + anterior.getHeight() + espaciado;
    }
}
