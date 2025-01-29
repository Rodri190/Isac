package Components.Admin;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelCambiado extends JPanel {
    
    public PanelCambiado(){
        add(new JLabel("Este es otro panel"));
        setBounds(300, 0, 200, 200);
        setBackground(Color.decode("#ff0000"));
        setVisible(true);
    }

    
}
