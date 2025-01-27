package Components.LoginScreen;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import Components.Admin.AdminFrame;
import UI.GradientBackground;
import UI.GradientButton;
import Utilities.ComponentAligment;
import Utilities.ComponentStyler;
import Utilities.Separador;

public class LoginPanel extends JPanel {
    private Color MAIN_COLOR = Color.decode("#ffffff");// 2103B0
    private ComponentStyler styler;
    private ComponentAligment aligment;
    private Separador esp;
    private JLabel loginLabel;
    private JLabel userLabel;
    private JLabel passLabel;
    private JTextField userField;
    private JPasswordField passField;
    private GradientButton loginBtn;
    private JTextField desviarFocusField;
    private GradientBackground gradient;
    private JFrame ventanaPrincipal;

    public LoginPanel(JFrame ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
        styler = new ComponentStyler();
        esp = new Separador();
        aligment = new ComponentAligment();
        gradient = new GradientBackground();
        setLayout(null);
        setSize(600, 800);
        setLocation(400, 0);
        // setBackground(Color.decode("#892379"));
        initComponents();
        setVisible(true);
        desviarFocusField = new JTextField();
        desviarFocusField.setFocusable(true);
        add(desviarFocusField);
        SwingUtilities.invokeLater(() -> desviarFocusField.requestFocusInWindow());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        gradient.ponerFondoGradiente(
                (Graphics2D) g,
                this.getWidth(),
                this.getHeight(),
                Color.decode("#E8113A"),
                Color.decode("#4A0075")
            );
    }

    private void initComponents() {
        initLoginLabel();
        initUserLabel();
        initUserField();
        initPassLabel();
        initPassField();
        initLoginBtn();
    }

    private void initLoginLabel() {
        loginLabel = new JLabel("Iniciar Sesión");

        styler.style(
                loginLabel,
                this.getWidth(),
                100,
                null,
                "negrita",
                45,
                Color.decode("#ffffff"));// 280BB2
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginLabel.setLocation(aligment.alignHorizontalComponent(this.getWidth(), loginLabel.getWidth()), 50);
        // loginLabel.setFont(new Font("Cooper Black", Font.BOLD, 35));
        add(loginLabel);
    }

    private void initUserLabel() {
        userLabel = new JLabel("Usuario");
        styler.style(userLabel, 400, 50, null, "normal", 25, MAIN_COLOR);
        userLabel.setLocation(aligment.alignHorizontalComponent(this.getWidth(), userLabel.getWidth()), 200);
        // userLabel.setBorder(new LineBorder(Color.decode("#ff0000"), 3));
        add(userLabel);
    }

    private void initUserField() {
        userField = new JTextField();
        styler.style(userField, 400, 50, null, "normal", 25, Color.decode("#000000"));
        userField.setLocation(aligment.alignHorizontalComponent(this.getWidth(), userField.getWidth()),
                esp.separar(userLabel, 0));
        userField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, MAIN_COLOR));
        agregarFocusListener(userField, "Ingrese su nombre de usuario");
        userField.setHorizontalAlignment(SwingConstants.CENTER);
        add(userField);
    }

    private void initPassLabel() {
        passLabel = new JLabel("Contraseña");
        styler.style(passLabel, 400, 50, null, "normal", 25, MAIN_COLOR);
        passLabel.setLocation(aligment.alignHorizontalComponent(this.getWidth(), passLabel.getWidth()),
                esp.separar(userField, 40));
        add(passLabel);
    }

    private void initPassField() {
        String placeholder = "Ingrese su contraseña";
        passField = new JPasswordField();
        styler.style(passField, 400, 50, null, "normal", 25, Color.decode("000000"));
        passField.setLocation(aligment.alignHorizontalComponent(this.getWidth(), passField.getWidth()),
                esp.separar(passLabel, 0));
        passField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, MAIN_COLOR));
        passField.addFocusListener(
                new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        if (new String(passField.getPassword()).equals(placeholder)) {
                            passField.setText("");
                            passField.setEchoChar('*');
                            passField.setForeground(Color.BLACK);
                        }
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        if (passField.getPassword().length == 0) {
                            passField.setEchoChar((char) 0);
                            passField.setText(placeholder);
                            passField.setForeground(Color.GRAY);
                        }
                    }
                });

        if (passField.getPassword().length == 0) {
            passField.setEchoChar((char) 0);
            passField.setText(placeholder);
            passField.setForeground(Color.GRAY);
        }
        passField.setHorizontalAlignment(SwingConstants.CENTER);
        add(passField);
    }

    private void agregarFocusListener(JTextField componente, String placeholder) {
        componente.addFocusListener(
                new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        if (componente.getText().equals(placeholder)) {
                            componente.setText("");
                            componente.setForeground(Color.BLACK);
                        }
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        if (componente.getText().isEmpty()) {
                            componente.setText(placeholder);
                            componente.setForeground(Color.GRAY);
                        }
                    }
                });

        if (componente.getText().isEmpty()) {
            componente.setText(placeholder);
            componente.setForeground(Color.GRAY);
        }
        // componente.setFocusable(false);
    }

    private void initLoginBtn() {
        loginBtn = new GradientButton(
                "Login",
                aligment.alignHorizontalComponent(this.getWidth(), 300),
                esp.separar(passField, 80),
                300,
                50,
                Color.decode("#F19C03"),
                Color.decode("#EF4933"),
                MAIN_COLOR,
                "normal",
                25);
        agregarEfectoHover();
        add(loginBtn);
    }

    private void agregarEfectoHover() {
        loginBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                loginBtn.setBorder(new LineBorder(Color.decode("#ffffff"), 3));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginBtn.setBorder(null);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                loginBtn.setBorder(null);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                loginBtn.setBorder(new LineBorder(Color.decode("#ffffff"), 3));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if(userField.getText().equals("admin123") && new String(passField.getPassword()).equals("123456")){
                    ventanaPrincipal.dispose();
                    new Components.Admin.AdminFrame();
                }
            }
        });
    }
}
