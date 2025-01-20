package Components.LoginScreen;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Components.UI.GradientButton;

import Utilities.ComponentAligment;
import Utilities.ComponentStyler;

public class LoginPanel extends JPanel {
    private ComponentStyler styler;
    private ComponentAligment aligment;
    private JLabel loginLabel;
    private JLabel userLabel;
    private JLabel passLabel;
    private JTextField userField;
    private JTextField passField;
    private GradientButton loginBtn;

    public LoginPanel() {
        styler = new ComponentStyler();
        aligment = new ComponentAligment();
        setSize(600, 800);
        setLocation(400, 0);
        setBackground(Color.decode("#0083BD"));
        setLayout(null);
        initComponents();
    }

    private void initComponents() {
        initLoginLabel();
    }

    private void initLoginLabel() {
        loginLabel = new JLabel("Login");

        styler.style(
            loginLabel,
            200,
            100,
            0,
            0,
            null,
            50,
            Color.decode("#ffffff")
        );
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // meter el metodo de horizonral component dentro de los parametros se style
        loginLabel.setLocation(aligment.alignHorizontalComponent(this, loginLabel), 25);

        add(loginLabel);
    }
}
