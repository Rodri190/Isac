package Utilities;

import java.awt.Component;

public class ComponentAligment {
    
    public ComponentAligment(){

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
}
