package Components.Admin;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.geom.Point2D;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.Timer;

import UI.BotonesUI;
import UI.GradientBackground;
import UI.GradientButton;
import Utilities.ComponentAligment;
import Utilities.ComponentStyler;
import Utilities.Fuente;
import Utilities.Separador;
import database.Query;
import database.model.Facultad;
import database.model.Materia;
import database.model.Persona;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegistroMateria extends JPanel {
    private Query query;
    private GradientBackground gradient;
    private Fuente fuente;
    private ComponentStyler styler;
    private Separador esp;
    private JLabel tituloRegistroLabel;
    private JLabel nombreLabel;
    private JLabel turnoLabel;
    private JLabel facultadLabel;
    private JTextField nombreField;
    private JComboBox<String> turnoBox;
    private JComboBox<String> facultadBox;
    private DefaultTableModel modelo;
    private JScrollPane scrollPane;
    private JTable tabla;
    private JTextField buscador;
    private String buscarPor;
    private JComboBox<String> opcionesFiltro;
    private ArrayList<Persona> docentesEnTabla;
    private JLabel registrarBtn;
    private ComponentAligment aligment;
    private JLabel mesajeError;
    private JLabel mensajeExistoso;
    private JLabel mensajeAlerta;

    public RegistroMateria(int altura, Query query, ArrayList<Persona> docentes) {
        query = new Query();
        gradient = new GradientBackground();
        fuente = new Fuente();
        styler = new ComponentStyler();
        aligment = new ComponentAligment();
        esp = new Separador();
        buscarPor = "Nombre";
        docentesEnTabla = new ArrayList<>();
        setLayout(null);
        // setBackground(Color.decode("#191c31"));
        setBounds(300, 0, 1040, altura);
        this.query = query;
        initComponents();
        docentesEnTabla = docentes;
        initTabla(docentes);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int xComp = 30;
        int yComp = 30;
        int xImg = xComp + 10;
        int yImg = yComp + 10;
        ArrayList<String> totales = getArrayDeTotales();
        String[] titulos = { "Total de Docentes", "Total de Facultades", "Total de Materias" };
        Color[] colores = { Color.decode("#F47725"), Color.decode("#C040D3") , Color.decode("#77D2DC")};
        String path = "src/Resources/img/";
        String[] imagenes = { "registrarDocente.png", "facultad.png" , "registrarMateria.png"};// cambiar el icono
        for (int i = 0; i < 3; i++) {
            gradient.ponerFondoGradienteRedondeado(
                    g,
                    300,
                    200,
                    xComp,
                    yComp,
                    xImg,
                    yImg,
                    titulos[i],
                    totales.get(i),
                    colores[i],
                    colores[i],
                    path + imagenes[i],
                    this);
            xComp += 330;
            xImg += 330;
        }
    }

    private ArrayList<String> getArrayDeTotales() {
        ArrayList<String> totales = new ArrayList<>();
        totales.add(query.selectTotalDocentes());
        totales.add(query.selectTotalFacultades());
        totales.add(query.selectTotalMaterias());
        return totales;
    }

    private void initComponents() {
        initTituloRegistroLabel();
        initLabels();
        initNombreField();
        initSelectFacultad();
        initTurnoBox();
        initBuscador();
        initRegistrarBoton();
        initMensajeError();
        initMensajeExitoso();
        initMesajeAlerta();
    }

    private void initTituloRegistroLabel() {
        tituloRegistroLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Crear un Graphics2D para dibujar con gradiente
                Graphics2D g2d = (Graphics2D) g;

                // Definir el texto y la fuente
                String texto = "Registro de Materia";
                Font font = fuente.getFuente("negrita", 25);
                g2d.setFont(font);

                // Obtener el tamaño del texto
                FontMetrics metrics = g2d.getFontMetrics();
                int textoWidth = metrics.stringWidth(texto);
                int textoHeight = metrics.getHeight();

                // Definir los puntos para el gradiente
                Point2D inicio = new Point2D.Float(0, 0);
                Point2D fin = new Point2D.Float(textoWidth, textoHeight);
                float[] distancias = { 0.0f, 1.0f };
                Color[] colores = { Color.RED, Color.BLUE };

                // Crear el gradiente
                LinearGradientPaint gradiente = new LinearGradientPaint(inicio, fin, distancias, colores);

                // Aplicar el gradiente al texto
                g2d.setPaint(gradiente);

                // Dibujar el texto en el centro del panel
                // int x = (getWidth() - textoWidth) / 2;
                int x = 0;
                int y = (getHeight() - textoHeight) / 2 + metrics.getAscent();
                g2d.drawString(texto, x, y);
            }

        };
        styler.style(tituloRegistroLabel, 300, 40, null, "negrita", 25, Color.decode("#710466"));
        tituloRegistroLabel.setLocation(30, 260);

        add(tituloRegistroLabel);
    }

    private void initLabels() {
        JLabel[] labels = { nombreLabel = new JLabel("Nombre"), facultadLabel = new JLabel("Facutltad"),
                turnoLabel = new JLabel("Turno") };
        int x = 30;
        for (JLabel jLabel : labels) {
            styler.style(jLabel, 300, 50, null, "negrita", 23, Color.decode("#830660"));
            jLabel.setLocation(x, esp.separar(tituloRegistroLabel, 20));
            add(jLabel);
            x = jLabel.getX() + jLabel.getWidth() + 30;
        }
    }

    private void initNombreField() {
        nombreField = new JTextField();
        styler.style(nombreField, 300, 40, Color.decode("#ffffff"), "normal", 20, Color.black);
        nombreField.setLocation(30, esp.separar(nombreLabel, 0));
        nombreField.setBorder(new LineBorder(Color.decode("#9A0957"), 3));
        nombreField.setCaretColor(Color.decode("#8F075B"));
        nombreField.setHorizontalAlignment(SwingConstants.CENTER);
        nombreField.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, Color.decode("#8F075B")));
        add(nombreField);
    }

    private void initSelectFacultad() {
        facultadBox = new JComboBox<>();
        styler.style(facultadBox, 300, 40, Color.decode("#BE0C4A"), "negrita", 20, Color.white);
        for (Facultad facultad : query.selectTodasLasFacultades()) {
            facultadBox.addItem(facultad.getNombre());
        }
        facultadBox.setBounds(esp.separarHorizontal(nombreField, 30), esp.separar(facultadLabel, 0), 300, 40);
        personalizarComboBox(facultadBox);
        add(facultadBox);
    }

    private void initTurnoBox() {
        turnoBox = new JComboBox<>();
        styler.style(turnoBox, 300, 40, Color.decode("#BE0C4A"), "negrita", 20, Color.white);
        turnoBox.addItem("Mañana");
        turnoBox.addItem("Tarde");
        turnoBox.setBounds(esp.separarHorizontal(facultadBox, 30), esp.separar(turnoLabel, 0), 300, 40);
        personalizarComboBox(turnoBox);
        add(turnoBox);
    }

    private void personalizarComboBox(JComboBox<String> combo) {
        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                // Llamar al método de la clase padre para configurar el texto y otros aspectos
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
                        cellHasFocus);

                // Alinear el texto horizontalmente (por ejemplo, a la derecha)
                label.setHorizontalAlignment(SwingConstants.CENTER);

                if (isSelected) {
                    label.setBackground(Color.decode("#9A0957")); // Color de fondo cuando está seleccionado
                    label.setForeground(Color.WHITE); // Color del texto cuando está seleccionado
                } else {
                    label.setBackground(Color.WHITE); // Color de fondo por defecto
                    label.setForeground(Color.BLACK); // Color del texto por defecto
                }

                // Devolver el JLabel personalizado
                return label;
            }
        });
    }

    private void initBuscador() {
        buscador = new JTextField() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image imagen = new ImageIcon("src/Resources/img/lupa.png").getImage();
                g.drawImage(imagen, 0, 0, 40, 40, buscador);
            }
        };
        styler.style(buscador, 960, 40, Color.decode("#ffffff"), "normal", 20, Color.decode("#000000"));
        buscador.setLocation(30, esp.separar(nombreField, 20));
        buscador.setBorder(new EmptyBorder(0, 50, 0, 0));
        agregarFocusListener(buscador, "Buscar Docente");
        agregarFiltroDeBusqueda();
        agregarEventoTeclaEnter();
        add(buscador);
    }

    private void agregarFiltroDeBusqueda() {
        JLabel filtroLabel = new JLabel("Buscar por :");
        styler.style(filtroLabel, 90, 40, null, "normal", 15, Color.decode("#762C8F"));
        filtroLabel.setLocation(760, 0);
        // filtroLabel.setBorder(new LineBorder(Color.red, 3));
        buscador.add(filtroLabel);
        agregarOpcionesFiltro(filtroLabel);

    }

    private void agregarOpcionesFiltro(JLabel filtroLabel) {
        opcionesFiltro = new JComboBox<>();
        opcionesFiltro.addItem("Nombre");
        opcionesFiltro.addItem("Apellido");
        opcionesFiltro.addItem("C.I.");
        opcionesFiltro.addItem("Celular");
        styler.style(opcionesFiltro, 100, 40, null, "normal", 15, Color.decode("#191c31"));
        opcionesFiltro.setLocation(esp.separarHorizontal(filtroLabel, 0), 0);
        opcionesFiltro.setBackground(Color.decode("#F47725"));
        opcionesFiltro.setForeground(Color.white);
        opcionesFiltro.setBorder(null);
        personalizarComboBox(opcionesFiltro);
        buscador.add(opcionesFiltro);
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

    private void agregarEventoTeclaEnter() {
        buscador.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

                modelo.setRowCount(0);

                for (Persona docente : buscarPorArray()) {
                    Object[] fila = {
                            docente.getNombre(),
                            docente.getApellido(),
                            docente.getEmail(),
                            docente.getCi(),
                            docente.getCelular()
                    };
                    modelo.addRow(fila);

                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (docentesEnTabla.size() == 0) {
                        add(mesajeError);
                        repaint();
                        initCountdown();
                    }
                }
                repaint();
                revalidate();

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }

    private void initCountdown() {
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(mesajeError);
                remove(mensajeExistoso);
                remove(mensajeAlerta);
                repaint();
                revalidate();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private ArrayList<Persona> buscarPorArray() {
        ArrayList<Persona> res = new ArrayList<>();
        String parametro = buscador.getText().trim();
        buscarPor = opcionesFiltro.getSelectedItem().toString();
        switch (buscarPor) {
            case "Nombre":
                res = query.selectDocenteCon("nombre", parametro);
                break;
            case "Apellido":
                res = query.selectDocenteCon("apellido", parametro);
                break;
            case "C.I.":
                res = query.selectDocenteCon("ci", parametro);
                break;
            case "Celular":
                res = query.selectDocenteCon("celular", parametro);
                break;
            default:
                System.out.println("Algo salio mal");
                break;
        }
        docentesEnTabla = res;
        return res;
    }

    private void initRegistrarBoton() {
        registrarBtn = new GradientButton("Registrar", 810, 730, 180, 50, Color.decode("#E31782"),
                Color.decode("#6E088C"), Color.decode("#ffffff"), "negrita", 20);
        // registrarBtn = new BotonesUI("Registrar", Color.decode("#F47725"),
        // Color.decode("#9D0956"), Color.decode("#5B026F"));
        registrarBtn.setLocation(810, 730);
        agregarEventoRegistrar();
        add(registrarBtn);
    }

    private void agregarEventoRegistrar() {
        registrarBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (nombreField.getText().equals("") || tabla.getSelectedRow() == -1) {
                    add(mensajeAlerta);
                    repaint();
                    initCountdown();
                } else {
                    query.insertIntoMateria(nombreField.getText().trim(), turnoBox.getSelectedItem().toString(),
                            facultadBox.getSelectedItem().toString(), obtenerIdDocente());
                    add(mensajeExistoso);
                    repaint();
                    initCountdown();
                }
            }
        });
    }

    private int obtenerIdDocente() {
        int idDocente = -1;
        int filaSeleccionada = tabla.getSelectedRow();

        if (filaSeleccionada != -1) {
            String nombre = String.valueOf(tabla.getValueAt(filaSeleccionada, 0));
            String apellido = String.valueOf(tabla.getValueAt(filaSeleccionada, 1));
            String ci = String.valueOf(tabla.getValueAt(filaSeleccionada, 3));
            for (Persona docente : docentesEnTabla) {
                if (docente.getNombre().equals(nombre) && docente.getApellido().equals(apellido)
                        && docente.getCi().equals(ci)) {
                    idDocente = docente.getId();
                    break;
                }
            }
        } else {
            System.out.println("No hay ninguna fila seleccionada.");
        }
        return idDocente;
    }

    private void initMensajeError() {
        mesajeError = new JLabel("No se encontró a ningún docente") {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image imagen = new ImageIcon("src/Resources/img/xMark.png").getImage();
                g.drawImage(imagen, 310, 5, 70, 40, mensajeExistoso);
            }
        };
        styler.style(mesajeError, 380, 50, null, "negrita", 20, Color.decode("#ffffff"));
        mesajeError.setLocation(30, registrarBtn.getY());
        mesajeError.setOpaque(true);
        mesajeError.setBackground(Color.decode("#FF1717"));
        mesajeError.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        // add(mensajeAlerta);
    }

    private void initMensajeExitoso() {
        mensajeExistoso = new JLabel("Materia registrada existosamente") {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image imagen = new ImageIcon("src/Resources/img/checkIcon.png").getImage();
                g.drawImage(imagen, 370, 5, 40, 40, mensajeExistoso);
            }
        };
        styler.style(mensajeExistoso, 420, 50, null, "negrita", 23, Color.decode("#ffffff"));
        mensajeExistoso.setLocation(30, registrarBtn.getY());
        mensajeExistoso.setOpaque(true);
        mensajeExistoso.setBackground(Color.decode("#00BA00"));
        mensajeExistoso.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        // add(mensajeExistoso);
    }

    private void initMesajeAlerta() {
        mensajeAlerta = new JLabel("Datos incompletos, verifique") {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image imagen = new ImageIcon("src/Resources/img/alert.png").getImage();
                g.drawImage(imagen, 320, 5, 40, 40, mensajeAlerta);
            }
        };
        styler.style(mensajeAlerta, 370, 50, null, "negrita", 23, Color.decode("#ffffff"));
        mensajeAlerta.setLocation(30, registrarBtn.getY());
        mensajeAlerta.setOpaque(true);
        mensajeAlerta.setBackground(Color.decode("#FF6C09"));
        mensajeAlerta.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        // add(mensajeAlerta);
    }

    // para la tabla
    private void initTabla(ArrayList<Persona> personas) {
        String[] columnas = { "Nombre/s", "Apellido/s", "E-mail", "C.I.", "Celular" };

        Object[][] datos = convertirDatos(personas);

        modelo = new DefaultTableModel(datos, columnas);
        tabla = new JTable(modelo);

        // para el ancho de las columnas
        tabla.getColumnModel().getColumn(0).setPreferredWidth(80);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(80);
        // tabla.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(30);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(30);

        // para el encabezado
        estilizarTablaEncabezado();
        tabla.setRowHeight(50);
        scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(30, esp.separar(buscador, 20), 960, 200);

        // para el scroll
        estilizarScrollBar();

        add(scrollPane);
    }

    private Object[][] convertirDatos(ArrayList<Persona> personas) {
        Object[][] datos = new Object[personas.size()][8];
        for (int i = 0; i < personas.size(); i++) {
            Persona persona = personas.get(i);
            datos[i][0] = persona.getNombre();
            datos[i][1] = persona.getApellido();
            datos[i][2] = persona.getEmail();
            datos[i][3] = persona.getCi();
            datos[i][4] = persona.getCelular();
        }
        return datos;
    }

    private void estilizarTablaEncabezado() {
        JTableHeader encabezado = tabla.getTableHeader();

        encabezado.setPreferredSize(new Dimension(encabezado.getWidth(), 50));
        encabezado.setFont(fuente.getFuente("negrita", 20));
        encabezado.setBackground(Color.decode("#8F075B"));
        encabezado.setForeground(Color.decode("#ffffff"));

        DefaultTableCellRenderer renderizador = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JLabel celda = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                        column);

                celda.setBorder(BorderFactory.createEmptyBorder());

                if (row == table.getRowCount() - 1) {
                    celda.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
                }

                celda.setHorizontalAlignment(SwingConstants.CENTER);

                celda.setFont(fuente.getFuente("normal", 17));

                return celda;
            }
        };

        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(renderizador);
        }
    }

    private void estilizarScrollBar() {
        JScrollBar verticalScroll = scrollPane.getVerticalScrollBar();

        verticalScroll.setUI(new BasicScrollBarUI() {
            @Override
            protected JButton createDecreaseButton(int orientation) {
                JButton button = super.createDecreaseButton(orientation);
                button.setBackground(Color.DARK_GRAY);
                return button;
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                JButton button = super.createIncreaseButton(orientation);
                button.setBackground(Color.DARK_GRAY);
                return button;
            }

            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.decode("#E2103C");
                this.trackColor = Color.decode("#8F075B");
            }
        });
    }

}
