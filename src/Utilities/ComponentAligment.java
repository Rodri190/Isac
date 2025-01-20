package Utilities;

import java.awt.Component;

public class ComponentAligment {
    
    public ComponentAligment(){

    }

    public int alignHorizontalComponent(Component container, Component component){
        if (container == null || component == null) {
            throw new IllegalArgumentException("El contenedor o el componente no pueden ser nulos.");
        }
        
        if (container.getWidth() <= 0 || component.getWidth() <= 0) {
            throw new IllegalArgumentException("El ancho del contenedor y del componente deben ser mayores a 0.");
        }
        
        int aligment = (int) ((container.getWidth() - component.getWidth()) / 2);
        return aligment;
    }

    public int alignVerticalComponent(Component container, Component component){
        if (container == null || component == null) {
            throw new IllegalArgumentException("El contenedor o el componente no pueden ser nulos.");
        }
        
        if (container.getWidth() <= 0 || component.getWidth() <= 0) {
            throw new IllegalArgumentException("El ancho del contenedor y del componente deben ser mayores a 0.");
        }
        
        int aligment = (int) ((container.getHeight() - component.getHeight()) / 2);
        return aligment;
    }
}
