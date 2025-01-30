package Components.Admin;

import javax.swing.JFrame;
import java.awt.Color;

public class AdminFrame extends JFrame {
    private static final int ALTURA = 900;
    private static final int ANCHO = 300 + 1040;
    
    public AdminFrame(){
        AdminPanel adminPanel = new AdminPanel(ALTURA);
        add(new BarraLateral(ALTURA, this, adminPanel));
        add(adminPanel);
        setSize(ANCHO, ALTURA);
        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setBackground(Color.decode("#191c31"));
    }

    public static void main(String[] args) {
        new AdminFrame();
    }
    

}
