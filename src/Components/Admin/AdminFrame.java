package Components.Admin;

import javax.swing.JFrame;

import database.Query;

import java.awt.Color;

public class AdminFrame extends JFrame {
    private static final int ALTURA = 900;
    private static final int ANCHO = 300 + 1040;
    private Query query;
    
    public AdminFrame(){
        query = new Query();
        AdminPanel adminPanel = new AdminPanel(ALTURA, query.selectTodasLasPersonas());
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
