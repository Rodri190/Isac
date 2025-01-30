package Components.Admin;

import UI.GradientBackground;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

class RegistroDocente extends JPanel {
    String color1;
    String color2;
    GradientBackground gradien;

    private static final int ALTURA = 900;
    private static final int ANCHO = 1040;
    private BarraLateral barraLateral;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        gradien.ponerFondoGradiente(g, getWidth(), getHeight(), Color.decode("#688e12"), Color.decode("#222a14"));
    }

    public RegistroDocente(BarraLateral barraLateral) {
        this.barraLateral = barraLateral;
        gradien = new GradientBackground();
        setLocation(300, 0);

        setLayout(null);
        setSize(ANCHO, ALTURA);

        JLabel titulo = new JLabel("REGISTRO DE DOCENTE", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.ITALIC, 30));
        titulo.setForeground(Color.blue);
        titulo.setBounds(250, 10, 400, 200);
        add(titulo);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Serif", Font.ITALIC, 20));
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setBounds(140, 200, 100, 25);
        add(lblNombre);
        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(230, 200, 150, 25);
        add(txtNombre);

        JLabel lblCarrera = new JLabel("Materia:");
        lblCarrera.setFont(new Font("Serif", Font.ITALIC, 20));
        lblCarrera.setForeground(Color.WHITE);
        lblCarrera.setBounds(430, 200, 100, 25);
        add(lblCarrera);
        JTextField txtCarrera = new JTextField();
        txtCarrera.setBounds(530, 200, 120, 25);
        add(txtCarrera);

        JLabel lblApellidos = new JLabel("Apellidos:");
        lblApellidos.setFont(new Font("Serif", Font.ITALIC, 20));
        lblApellidos.setForeground(Color.WHITE);
        lblApellidos.setBounds(140, 250, 100, 25);
        add(lblApellidos);
        JTextField txtApellidos = new JTextField();
        txtApellidos.setBounds(230, 250, 150, 25);
        add(txtApellidos);

        JLabel lblFacultad = new JLabel("Facultad:");
        lblFacultad.setFont(new Font("Serif", Font.ITALIC, 20));
        lblFacultad.setForeground(Color.WHITE);
        lblFacultad.setBounds(430, 250, 100, 25);
        add(lblFacultad);
        String[] facultades = { "Tecnología", "Economia" };
        JComboBox<String> cbFacultad = new JComboBox<>(facultades);
        cbFacultad.setBounds(530, 250, 120, 25);
        add(cbFacultad);

        JLabel lblCi = new JLabel("CI:");
        lblCi.setFont(new Font("Serif", Font.ITALIC, 20));
        lblCi.setForeground(Color.white);
        lblCi.setBounds(140, 300, 100, 25);
        add(lblCi);
        JTextField txtCi = new JTextField();
        txtCi.setBounds(230, 300, 150, 25);
        add(txtCi);

        JLabel lblCorreo = new JLabel("Correo:");
        lblCorreo.setFont(new Font("Serif", Font.ITALIC, 20));
        lblCorreo.setForeground(Color.WHITE);
        lblCorreo.setBounds(140, 350, 100, 25);
        add(lblCorreo);
        JTextField txtCorreo = new JTextField();
        txtCorreo.setBounds(230, 350, 150, 25);
        add(txtCorreo);

        JLabel lblCel = new JLabel("Cel/telf:");
        lblCel.setFont(new Font("Serif", Font.ITALIC, 20));
        lblCel.setForeground(Color.WHITE);
        lblCel.setBounds(140, 400, 100, 25);
        add(lblCel);
        JTextField txtCel = new JTextField();
        txtCel.setBounds(230, 400, 150, 25);
        add(txtCel);

        JLabel lTurno = new JLabel("TURNO");
        lTurno.setFont(new Font("Serif", Font.ITALIC, 20));
        lTurno.setForeground(Color.WHITE);
        lTurno.setFont(new Font("Serif", Font.ITALIC, 20));
        lTurno.setBounds(480, 300, 100, 25);
        add(lTurno);

        JCheckBox chkManana = new JCheckBox("Mañana");
        chkManana.setForeground(Color.WHITE);
        chkManana.setOpaque(false);
        chkManana.setBounds(430, 340, 130, 25);
        chkManana.setFont(new Font("Serif", Font.PLAIN, 25));
        add(chkManana);

        JCheckBox chkTarde = new JCheckBox("Tarde");
        chkTarde.setForeground(Color.WHITE);
        chkTarde.setOpaque(false);
        chkTarde.setBounds(550, 340, 100, 25);
        chkTarde.setFont(new Font("Serif", Font.PLAIN, 25));
        add(chkTarde);

        chkManana.addActionListener(e -> {
            if (chkManana.isSelected()) {
                chkTarde.setSelected(false);
            }
        });
    
        chkTarde.addActionListener(e -> {
            if (chkTarde.isSelected()) {
                chkManana.setSelected(false);
            }
        });

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(250, 500, 130, 50);
        btnCancelar.setBackground(Color.RED);
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Serif", Font.PLAIN, 25));
        add(btnCancelar);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(450, 500, 130, 50);
        btnGuardar.setBackground(Color.GREEN);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Serif", Font.PLAIN, 25));
        btnGuardar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String nombre = txtNombre.getText();
                String apellidos = txtApellidos.getText();
                String ci = txtCi.getText();
                String celular = txtCel.getText();
                String carrera = txtCarrera.getText();
                String correo = txtCorreo.getText();
                // String facultad = cbFacultad.getSelectedItem().toString();
                // String turno = "";
                // if(chkManana.isSelected()){
                // turno = "Mañana";
                // }else if(chkTarde.isSelected()){
                // turno = "Tarde";
                // }
                Persona persona = new Persona(nombre, apellidos, ci, celular, carrera, correo, "Docente", "Activo");
                barraLateral.crearNuevaPersona(persona);

                txtNombre.setText("");
                txtApellidos.setText("");
                txtCi.setText("");
                txtCel.setText("");
                txtCarrera.setText("");
                txtCorreo.setText("");
                cbFacultad.setSelectedIndex(0);
                chkManana.setSelected(false);
                chkTarde.setSelected(false);

            }
        });
        add(btnGuardar);
    }

}
