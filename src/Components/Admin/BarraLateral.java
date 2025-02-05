package Components.Admin;

import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.MouseInputAdapter;

import UI.GradientBackground;
import Utilities.ComponentAligment;
import Utilities.ComponentStyler;
import Utilities.Separador;
import database.Query;

public class BarraLateral extends JPanel {
    private String path;
    private JLabel logoLabel;
    private ComponentStyler styler;
    private ComponentAligment aligment;
    private GradientBackground gradient;
    private Separador esp;
    private ArrayList<BotonOpcion> botonesOpciones;
    private JPanel panelActual;
    private JFrame adminFrame;
    private JLabel[] mascaras;
    private Query query;

    public BarraLateral(int altura, AdminFrame adminFrame, JPanel panelActual) {
        this.adminFrame = adminFrame;
        query = new Query();
        path = "src/Resources/img/";
        styler = new ComponentStyler();
        aligment = new ComponentAligment();
        gradient = new GradientBackground();
        botonesOpciones = new ArrayList<>();
        mascaras = new JLabel[4];
        this.panelActual = panelActual;
        esp = new Separador();
        setBounds(0, 0, 300, altura);
        setBackground(Color.decode("#ff0000"));
        setLayout(null);
        initComponents();
    }

    private void initComponents() {
        initLogoLabel();
        initBotones();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        gradient.ponerFondoGradiente(
                (Graphics2D) g, // lo mismo
                this.getWidth(), // lo mismo
                this.getHeight(), // lo mismo
                Color.decode("#E8113A"), // color 1 el que quieras
                Color.decode("#4A0075") // color 2 el que quieras
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

    private void initBotones() {
        String[] texto = { "Vista General", "Agregar Docente", "Agregar Estudiante"};
        String[] imgPath = { "vistaGeneral.png", "registrarDocente.png", "registrarEstudiante.png"};
        BotonOpcion botonOpcion;
        for (int i = 0; i < 3; i++) {
            botonOpcion = new BotonOpcion(texto[i],
                    path + imgPath[i],
                    getWidth() - 40,
                    50,
                    aligment.alignHorizontalComponent(this.getWidth(),
                            getWidth() - 40),
                    esp.separar(i == 0 ? logoLabel : botonesOpciones.get(i - 1), 20));
            botonesOpciones.add(botonOpcion);

            // para las mascaras
            JLabel mask = new JLabel();
            mask.setOpaque(true);
            mask.setBackground(new Color(255, 255, 255, 128));
            mask.setBounds(botonOpcion.getX(), botonOpcion.getY(), botonOpcion.getWidth(), botonOpcion.getHeight());

            add(botonOpcion);
            mascaras[i] = mask;

            if (i == 0) {
                add(mask);
            }
        }
        agregarEventosAOpciones();
        agregarVistaGeneral();
    }

    private void agregarEventosAOpciones() {
        for (BotonOpcion boton : botonesOpciones) {
            boton.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    // panelDifuminado.setBounds(boton.getBounds());
                    if(!boton.isActive()){
                        boton.setBorder(new LineBorder(Color.decode("#ffffff"), 3));
                    }
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    // panelDifuminado.setBounds(0, 0, 0, 0);
                    if(!boton.isActive()){
                        boton.setBorder(null);
                    }
                }

                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    // panelDifuminado.setBounds(boton.getBounds());
                    // boton.setOpaque(false);
                    // boton.
                    boton.setActive(true);
                    boton.setBorder(new LineBorder(Color.white, 3));
                    for (BotonOpcion btn : botonesOpciones) {
                        if (btn != boton) {
                            boton.setActive(false);
                            btn.setBorder(null);
                            remove(mascaras[botonesOpciones.indexOf(btn)]);
                        }else{
                            add(mascaras[botonesOpciones.indexOf(btn)]);
                        }
                    }
                }
            });
        }
    }

    private void agregarVistaGeneral() {
        botonesOpciones.get(0).addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                adminFrame.remove(panelActual);
                panelActual = new AdminPanel(getHeight(), query.selectTodasLasPersonas());
                adminFrame.add(panelActual);
                adminFrame.repaint();
                adminFrame.revalidate();
                System.out.println("se cambio a admin");
            }
        });

        botonesOpciones.get(1).addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                adminFrame.remove(panelActual);
                panelActual = (RegistroDocente)new RegistroDocente(getThis(), query);
                adminFrame.add(panelActual);
                adminFrame.repaint();
                adminFrame.revalidate();
                System.out.println("se cambio a docente");
            }
        });

        botonesOpciones.get(2).addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                adminFrame.remove(panelActual);
                panelActual = (RegistroEstudiante)new RegistroEstudiante(getThis(), query);
                adminFrame.add(panelActual);
                adminFrame.repaint();
                adminFrame.revalidate();
                System.out.println("se cambio a estudiante");
            }
        });
    }

    private BarraLateral getThis(){
        return this;
    }
}
