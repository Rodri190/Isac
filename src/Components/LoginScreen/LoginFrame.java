package Components.LoginScreen;
import javax.swing.JFrame;

public class LoginFrame extends JFrame {
    
    public LoginFrame(){
        initFrame();
    }
    
    private void initFrame(){
        add(new LoginPanel(this));
        add(new LoginImgPanel());
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        // add(new Panel());
        setLayout(null);
        setVisible(true);
        setVisible(true); 
        // pack();
    }
}