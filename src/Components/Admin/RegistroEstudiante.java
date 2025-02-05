package Components.Admin;

import javax.swing.*;
import javax.swing.border.LineBorder;

import UI.BotonesUI;
import UI.GradientBackground;
import UI.GradientButton;
import UI.GradientComboBox;
import UI.GradientLabel;
import UI.GradientTextField;
import database.Query;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

public class RegistroEstudiante extends JPanel {
    GradientBackground gradien;
    JLabel titulo, lblNombre, lblApellidos, lblCi, lblFacultad, lblCorreo, lblCel;
    JTextField txtNombre, txtApellidos, txtCi, txtCorreo, txtCel;
    JButton btnGuardar, btnCancelar;
    JComboBox<String> cbFacultad;
    private static final int ALTURA = 850;
    private static final int ANCHO = 1040;
    private Query query;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        gradien.ponerFondoGradiente(g, getWidth(), getHeight(), Color.decode("#afa4a6"), Color.white);
    }

    public RegistroEstudiante(Query query) {
        this.query = query;
        gradien = new GradientBackground();
        setLocation(300, 0);
        setLayout(null);
        setSize(ANCHO, ALTURA);

        titulo = agregarElemento("Registro De Estudiante", 60, 150, 80, 800, 75, "#17afd6", "#092b66");
        lblNombre = agregarElemento("Nombre", 50, 150, 200, 400, 55);
        txtNombre = agregarText(250, 260, 200, 35);

        lblApellidos = agregarElemento("Apellidos", 50, 450, 200, 500, 55);
        txtApellidos = agregarText(600, 260, 200, 35);

        lblCi = agregarElemento("C.I", 50, 250, 300, 100, 40);
        txtCi = agregarText(250, 350, 200, 35);

        lblCorreo = agregarElemento("Correo", 50, 550, 300, 300, 40);
        txtCorreo = agregarText(600, 350, 200, 35);

        cbFacultad = new GradientComboBox(new String[] { "Tecnología", "Economía" }, Color.RED, Color.RED);
        cbFacultad.setBounds(600, 450, 200, 35);
        add(cbFacultad);

        lblFacultad = agregarElemento("Facultad", 50, 550, 400, 300, 40);

        lblCel = agregarElemento("Cel/telf", 50, 250, 400, 200, 40);
        txtCel = agregarText(250, 450, 200, 35);

        // // Botones
        // btnCancelar = agregarBoton("Cancelar", 450, 600, 200, 50, Color.RED);
        // btnCancelar.addMouseListener(new MouseAdapter() {
        //     @Override
        //     public void mouseClicked(MouseEvent e) {
        //         limpiarCampos();
        //     }
        // });

        GradientButton boton = new GradientButton("Guardar", 700, 600, 200, 50, Color.green, Color.decode("#0dc143"), Color.white, "negrita", 30);
        add(boton);
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                guardarEstudiante();
            }
        });
    }

    private JLabel agregarElemento(String texto, int tamanio, int x, int y, int ancho, int alto) {
        GradientLabel label = new GradientLabel(texto, Color.black, Color.blue, tamanio);
        label.setBounds(x, y, ancho, alto);
        add(label);
        return label;
    }

    private JLabel agregarElemento(String texto, int tamanio, int x, int y, int ancho, int alto, String color1,
            String color2) {
        GradientLabel label = new GradientLabel(texto, Color.decode(color1), Color.decode(color2), tamanio);
        label.setBounds(x, y, ancho, alto);
        add(label);
        return label;
    }

    private JTextField agregarText(int x, int y, int ancho, int alto) {
        GradientTextField textField = new GradientTextField(Color.decode("#60026c"), Color.decode("#e4103b"), 5);
        textField.setBounds(x, y, ancho, alto);
        textField.setFont(new Font("Arial", Font.PLAIN, 20));
        textField.setForeground(Color.BLACK);
        add(textField);
        return textField;
    }

    private void guardarEstudiante() {
        String nombre = txtNombre.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String ci = txtCi.getText().trim();
        String celular = txtCel.getText().trim();
        String correo = txtCorreo.getText().trim();
        String facultad = (String) cbFacultad.getSelectedItem();

        if (nombre.isEmpty() || apellidos.isEmpty() || ci.isEmpty() || celular.isEmpty() || correo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        query.insertarPersona(nombre, apellidos, ci, celular, correo, "Estudiante", facultad, LocalDate.now());
        JOptionPane.showMessageDialog(this, "Estudiante registrado correctamente.", "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtApellidos.setText("");
        txtCi.setText("");
        txtCel.setText("");
        txtCorreo.setText("");
        cbFacultad.setSelectedIndex(0);
    }
}
