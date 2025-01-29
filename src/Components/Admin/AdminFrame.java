package Components.Admin;

import javax.swing.JFrame;

public class AdminFrame extends JFrame {
    private static final int ALTURA = 900;
    private static final int ANCHO = 1200;
    
    public AdminFrame(){
        add(new BarraLateral(ALTURA));
        add(new RegistroEstudiante());
        setSize(ANCHO, ALTURA);
        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }

    public static void main(String[] args) {
        new AdminFrame();
    }

}
