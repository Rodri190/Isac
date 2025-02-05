package Components.Admin;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.metal.MetalScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import UI.BotonesUI;
import UI.GradientBackground;
import UI.GradientPanel;
import Utilities.ComponentStyler;
import Utilities.Fuente;
import Utilities.Separador;
import database.Query;
import database.model.Materia;
import database.model.Persona;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AdminPanel extends JPanel {
    private JLabel totalEstudiantesPanel;
    private JLabel totalDocentesPanel;
    private BotonesUI boton;
    private GradientPanel gradientPanel;
    private GradientBackground gradient;
    private JLabel tituloTabla;
    private ComponentStyler styler;
    private Separador esp;
    private Query query;
    private Fuente fuente;
    private DefaultTableModel modelo;
    private JScrollPane scrollPane;
    private JTable tabla;
    private JComboBox<String> opcionesFiltro;
    private JLabel filtroLabel;
    private JTextField focusFalso;

    public AdminPanel(int alto, ArrayList<Persona> personas) {
        gradientPanel = new GradientPanel();
        gradient = new GradientBackground();
        styler = new ComponentStyler();
        fuente = new Fuente();
        query = new Query();
        esp = new Separador();
        setBackground(Color.decode("#ffffff"));
        setBounds(300, 0, 1040, alto);
        setLayout(null);
        initTabla(personas);
        initComponents();
        focusFalso = new JTextField();
        focusFalso.setFocusable(true);
        add(focusFalso);
        SwingUtilities.invokeLater(() -> focusFalso.requestFocusInWindow());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int xComp = 30;
        int yComp = 30;
        int xImg = xComp + 10;
        int yImg = yComp + 10;
        ArrayList<String> totales = getArrayDeTotales();
        String[] titulos = { "Total de estudiantes", "Total de docentes", "Total de cursos" };
        Color[] colores = { Color.decode("#8787f9"), Color.decode("#B370F3"), Color.decode("#EBCB3E") };
        String path = "src/Resources/img/";
        String[] imagenes = { "registrarEstudiante.png", "registrarDocente.png", "registrarMateria.png" };
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
        totales.add(query.selectTotalEstudiantes());
        totales.add(query.selectTotalDocentes());
        totales.add(query.selectTotalMaterias());
        return totales;
    }

    private void initComponents() {
        initTituloTabla();
        initFiltroLabel();
        initFiltroOpciones();
    }

    private void initTituloTabla() {
        tituloTabla = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g;

                String texto = "Personas de la intitucion";
                Font font = fuente.getFuente("negrita", 25);
                g2d.setFont(font);

                FontMetrics metrics = g2d.getFontMetrics();
                int textoWidth = metrics.stringWidth(texto);
                int textoHeight = metrics.getHeight();

                Point2D inicio = new Point2D.Float(0, 0);
                Point2D fin = new Point2D.Float(textoWidth, textoHeight);
                float[] distancias = { 0.0f, 1.0f };
                Color[] colores = { Color.RED, Color.BLUE };

                LinearGradientPaint gradiente = new LinearGradientPaint(inicio, fin, distancias, colores);

                g2d.setPaint(gradiente);

                // Dibujar el texto en el centro del panel
                // int x = (getWidth() - textoWidth) / 2;
                int x = 0;
                int y = (getHeight() - textoHeight) / 2 + metrics.getAscent();
                g2d.drawString(texto, x, y);
            }

        };
        styler.style(tituloTabla, 300, 50, null, "negrita", 25, Color.decode("#710466"));
        tituloTabla.setLocation(30, 260);

        add(tituloTabla);
    }

    // para la tabla
    private void initTabla(ArrayList<Persona> personas) {
        String[] columnas = { "Nombre", "E-mail", "Tipo", "Se unio", "Estado" };
        String[] datosInteresados = { "Nombre", "Email", "TipoPersona", "FechaRegistro", "Estado" };
        Object[][] datos = convertirDatosPersona(personas, datosInteresados);

        modelo = new DefaultTableModel(datos, columnas);
        tabla = new JTable(modelo);

        // para el ancho de las columnas
        tabla.getColumnModel().getColumn(1).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(30);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(30);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(50);

        // para el encabezado
        estilizarTablaEncabezado();
        tabla.setRowHeight(50);
        scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(30, 30 + 175 + 30 + 50 + 30 + 30, 960, 450);

        // para el scroll
        estilizarScrollBar();

        add(scrollPane);
    }

    private Object[][] convertirDatosPersona(ArrayList<Persona> personas, String[] campos) {
        Object[][] datos = new Object[personas.size()][campos.length];

        for (int i = 0; i < personas.size(); i++) {
            Persona persona = personas.get(i);
            for (int j = 0; j < campos.length; j++) {
                String campo = campos[j];
                try {
                    String metodoGetter = "get" + campo.substring(0, 1).toUpperCase() + campo.substring(1);

                    Method metodo = Persona.class.getMethod(metodoGetter);

                    datos[i][j] = metodo.invoke(persona);
                } catch (Exception e) {
                    e.printStackTrace();
                    datos[i][j] = null;
                }
            }
        }
        return datos;
    }

    private Object[][] convertirDatosMateria(ArrayList<Materia> materias, String[] campos) {
        Object[][] datos = new Object[materias.size()][campos.length];

        for (int i = 0; i < materias.size(); i++) {
            Materia materia = materias.get(i);
            for (int j = 0; j < campos.length; j++) {
                String campo = campos[j];
                try {
                    String metodoGetter = "get" + campo.substring(0, 1).toUpperCase() + campo.substring(1);

                    Method metodo = Materia.class.getMethod(metodoGetter);

                    datos[i][j] = metodo.invoke(materia);
                } catch (Exception e) {
                    e.printStackTrace();
                    datos[i][j] = null;
                }
            }
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
        // Estilizar la barra de desplazamiento vertical
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

        // Estilizar la barra de desplazamiento horizontal
        JScrollBar horizontalScroll = scrollPane.getHorizontalScrollBar();

        horizontalScroll.setUI(new BasicScrollBarUI() {
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

    // para cambiar la visualizacion de los datos en las tablas

    private void initFiltroLabel() {
        filtroLabel = new JLabel("Mostrar por:");
        styler.style(filtroLabel, 150, 40, null, "negrita", 22, Color.decode("#762C8F"));
        filtroLabel.setLocation(esp.separarHorizontal(tituloTabla, 360), tituloTabla.getY());
        // filtroLabel.setBorder(new LineBorder(Color.red, 3));
        filtroLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(filtroLabel);
    }

    private void initFiltroOpciones() {
        opcionesFiltro = new JComboBox<>();
        opcionesFiltro.addItem("General");
        opcionesFiltro.addItem("Docentes");
        opcionesFiltro.addItem("Estudiantes");
        opcionesFiltro.addItem("Materias");
        styler.style(opcionesFiltro, 150, 40, Color.decode("#BE0C4A"), "normal", 20, Color.decode("#ffffff"));
        opcionesFiltro.setLocation(filtroLabel.getX() + filtroLabel.getWidth(), filtroLabel.getY());

        personalizarComboBox(opcionesFiltro);
        agregarEventoOpciones();
        add(opcionesFiltro);
    }

    private void agregarEventoOpciones() {
        opcionesFiltro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccionado = (String) opcionesFiltro.getSelectedItem();

                switch (seleccionado) {
                    case "General":
                        cambiarATablaGeneral(query.selectTodasLasPersonas());
                        break;
                    case "Docentes":
                        cambiarATablaDocente(query.selectTodosLosDocentes());
                        break;
                    case "Estudiantes":
                        cambiarATablaEstudiante(query.selectTodosLosEstudiantes());
                        break;
                    case "Materias":
                        cambiarATablaMateria(query.selectTodasLasMaterias());
                        break;
                    default:
                }
                repaint();
                revalidate();
            }
        });
    }

    private void personalizarComboBox(JComboBox<String> combo) {
        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
                        cellHasFocus);

                label.setHorizontalAlignment(SwingConstants.CENTER);

                if (isSelected) {
                    label.setBackground(Color.decode("#9A0957"));
                    label.setForeground(Color.WHITE);
                } else {
                    label.setBackground(Color.WHITE);
                    label.setForeground(Color.BLACK);
                }

                return label;
            }
        });
    }

    private void cambiarATablaGeneral(ArrayList<Persona> personas) {
        // tabla.setModel(new DefaultTableModel());
        remove(scrollPane);
        String[] columnas = { "Nombre", "E-mail", "Tipo", "Se unio", "Estado" };
        String[] datosInteresados = { "Nombre", "Email", "TipoPersona", "FechaRegistro", "Estado" };

        Object[][] datos = convertirDatosPersona(personas, datosInteresados);

        modelo = new DefaultTableModel(datos, columnas);
        tabla = new JTable(modelo);

        // para el ancho de las columnas
        tabla.getColumnModel().getColumn(1).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(30);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(30);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(50);

        // para el encabezado
        estilizarTablaEncabezado();
        tabla.setRowHeight(50);
        scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(30, 30 + 175 + 30 + 50 + 30 + 30, 960, 450);

        // para el scroll
        estilizarScrollBar();

        add(scrollPane);
    }

    private void cambiarATablaDocente(ArrayList<Persona> docentes) {
        remove(scrollPane);
        String[] columnas = { "Nombre", "C.I.", "Celular", "E-mail", "Tipo", "Estado", "Se unio" };
        String[] datosInteresados = { "NombreCompleto", "Ci", "Celular", "Email", "TipoPersona", "Estado",
                "FechaRegistro" };
        Object[][] datos = convertirDatosPersona(docentes, datosInteresados);

        modelo = new DefaultTableModel(datos, columnas);
        tabla = new JTable(modelo);

        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        // JScrollPane scrollPane = new JScrollPane(tabla);
        tabla.setFillsViewportHeight(true);
        // para el ancho de las columnas
        tabla.getColumnModel().getColumn(0).setPreferredWidth(250);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(250);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(6).setPreferredWidth(150);

        // para el encabezado
        estilizarTablaEncabezado();
        tabla.setRowHeight(50);
        scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(30, 30 + 175 + 30 + 50 + 30 + 30, 960, 450);

        // para el scroll
        estilizarScrollBar();

        add(scrollPane);
    }

    private void cambiarATablaEstudiante(ArrayList<Persona> estudiantes) {
        remove(scrollPane);
        String[] columnas = { "Nombre", "C.I.", "Celular", "E-mail", "Tipo", "Estado", "Se unio" };
        String[] datosInteresados = { "NombreCompleto", "Ci", "Celular", "Email", "TipoPersona", "Estado",
                "FechaRegistro" };
        Object[][] datos = convertirDatosPersona(estudiantes, datosInteresados);

        modelo = new DefaultTableModel(datos, columnas);
        tabla = new JTable(modelo);

        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        // JScrollPane scrollPane = new JScrollPane(tabla);
        tabla.setFillsViewportHeight(true);
        // para el ancho de las columnas
        tabla.getColumnModel().getColumn(0).setPreferredWidth(250);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(250);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(6).setPreferredWidth(150);

        // para el encabezado
        estilizarTablaEncabezado();
        tabla.setRowHeight(50);
        scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(30, 30 + 175 + 30 + 50 + 30 + 30, 960, 450);

        // para el scroll
        estilizarScrollBar();

        add(scrollPane);
    }

    private void cambiarATablaMateria(ArrayList<Materia> materias) {
        remove(scrollPane);

        String[] columnas = { "Nombre", "Turno", "Docente", "Facultad", "Inscritos" };
        String[] datosInteresados = { "Nombre", "Turno", "NombreDocente", "Nombre", "Inscritos" };

        Object[][] datos = convertirDatosMateria(materias, datosInteresados);

        // Revertir el comportamiento de FillsViewportHeight
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        tabla.setFillsViewportHeight(false);

        modelo = new DefaultTableModel(datos, columnas);
        tabla = new JTable(modelo);

        // para el encabezado
        estilizarTablaEncabezado();
        tabla.setRowHeight(50);
        scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(30, 30 + 175 + 30 + 50 + 30 + 30, 960, 450);

        // para el scroll
        estilizarScrollBar();
        add(scrollPane);
    }


}
