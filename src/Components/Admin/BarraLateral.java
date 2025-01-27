package Components.Admin;

import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import UI.GradientBackground;
import Utilities.ComponentAligment;
import Utilities.ComponentStyler;
import Utilities.Separador;

public class BarraLateral extends JPanel {
    private String path;
    private JLabel logoLabel;
    private ComponentStyler styler;
    private ComponentAligment aligment;
    private GradientBackground gradient;
    private Separador esp;
    private ArrayList<JLabel> botonesOpciones;

    public BarraLateral(int altura) {
        path = "src/Resources/img/";
        styler = new ComponentStyler();
        aligment = new ComponentAligment();
        gradient = new GradientBackground();
        botonesOpciones = new ArrayList<>();
        esp = new Separador();
        setBounds(0, 0, 300, altura);
        setBackground(Color.decode("#ff0000"));
        setLayout(null);
        initComponents();
    }

    private void initComponents() {
        initLogoLabel();
        initBotonVistaGeneral();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        gradient.ponerFondoGradiente(
            (Graphics2D)g, 
            this.getWidth(), 
            this.getHeight(), 
            Color.decode("#E8113A"), 
            Color.decode("#4A0075")
        );
    }

    private void initLogoLabel() {
        logoLabel = new JLabel() {
            @Override
            public void setText(String text) {
                super.setText("Preuniversitario");
            }
            @Override
            public void setSize(int width, int height) {
                super.setSize(250, 100);
            }
            @Override
            protected void paintComponent(Graphics g) {
                Image imagen = new ImageIcon("src/Resources/img/logoAdminPanel.png").getImage();
                super.paintComponent(g);
                if (imagen != null) {
                    g.drawImage(imagen, 0, 0, logoLabel.getWidth() / 3, logoLabel.getHeight(), logoLabel);
                }
            }
        };
        styler.style(logoLabel, this.getWidth(), 100, null, "negrita", 20, Color.white);
        logoLabel.setLocation(aligment.alignHorizontalComponent(this.getWidth(), logoLabel.getWidth()), 30);
        logoLabel.setLayout(null);
        logoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        // logoLabel.setBorder(new LineBorder(Color.white, 3));
        add(logoLabel);
    }

    private void initBotonVistaGeneral(){
        String[] texto = {"Vista General", "Agregar Docente", "Agregar Estudiante", "Agregar Materia"};
        String[] imgPath = {"vistaGeneral.png", "registrarDocente.png", "registrarDocente.png", "registrarDocente.png"};
        for(int i = 0; i < 4; i++){
            botonesOpciones.add(new BotonOpcion(texto[i], 
                path + imgPath[i], 
                getWidth() - 40, 
                50,
                aligment.alignHorizontalComponent(this.getWidth(), 
                getWidth() - 40),
                esp.separar(i == 0 ? logoLabel : botonesOpciones.get(i - 1), 20))
            );
            add(botonesOpciones.get(i));
        }
        agregarEventosAOpciones();
    }

    private void agregarEventosAOpciones(){
        for(JLabel boton : botonesOpciones){
            boton.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    boton.setBorder(new LineBorder(Color.decode("#ffffff"), 3));
                }
                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    boton.setBorder(null);
                }

                // @Override
                // public void mouseClicked(java.awt.event.MouseEvent evt) {
                //     boton.setOpaque(true);
                //     boton.setBackground(new Color(255, 255, 255, 128));
                //     for (JLabel otro : botonesOpciones) {
                //         if (!otro.equals(boton)) {
                //             otro.setOpaque(false);
                //             // otro.setBackground(null);
                //         }
                        
                //     }
                // }
            });
        }
    }
}
