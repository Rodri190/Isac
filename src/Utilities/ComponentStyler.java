
package Utilities;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.SwingConstants;

public class ComponentStyler {

    public ComponentStyler() {

    }

    public void style(Component component, int width, int height, int x, int y, Color bgColor, int fontSize, Color fontColor) {
        component.setBounds(x, y, width, height);
        component.setBackground(bgColor == null ? null : bgColor);
        component.setFont(new Font("Cooper Black", Font.PLAIN, fontSize));
        component.setForeground(fontColor);
    }
}