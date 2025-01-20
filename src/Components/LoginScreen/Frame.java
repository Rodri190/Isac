package Components.LoginScreen;
import javax.swing.JFrame;

public class Frame extends JFrame {
    
    public Frame(){
        initFrame();
    }
    
    private void initFrame(){
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // add(new Panel());
        setLayout(null);
        setVisible(true);
        add(new LoginPanel());
        add(new LoginImgPanel());
    }
    
    public static void main(String[] args) {
        new Frame();
    }
}