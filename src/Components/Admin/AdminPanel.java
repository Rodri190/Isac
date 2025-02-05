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
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
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
import database.Query;
import database.modeloTablas.Persona;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AdminPanel extends JPanel {
    private GradientBackground gradient;
    private JLabel totalEstudiantesPanel;
    private JLabel totalDocentesPanel;
    private BotonesUI boton;
    private GradientPanel gradientPanel;
    private JLabel tituloTabla;
    private ComponentStyler styler;
    private Fuente fuente;
    private DefaultTableModel modelo;
    private JScrollPane scrollPane;
    private JTable tabla;
    private Query query;

    public AdminPanel(int alto, ArrayList<Persona> personas) {
        gradientPanel = new GradientPanel();
        gradient = new GradientBackground();
        styler = new ComponentStyler();
        fuente = new Fuente();
        query = new Query();
        setBackground(Color.decode("#ffffff"));
        setBounds(300, 0, 1040, alto);
        setLayout(null);
        initTabla(personas);
        initComponents();
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
        String[] imagenes = { "registrarDocente.png", "registrarEstudiante.png", "registrarMateria.png" };
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

    private ArrayList<String> getArrayDeTotales(){
        ArrayList<String> totales = new ArrayList<>();
        totales.add(query.selectTotalEstudiantes());
        totales.add(query.selectTotalDocentes());
        totales.add(query.selectTotalMaterias());
        return totales;
    }

    private void initComponents() {
        initTituloTabla();
    }

    private void initTituloTabla() {
        tituloTabla = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Crear un Graphics2D para dibujar con gradiente
                Graphics2D g2d = (Graphics2D) g;

                // Definir el texto y la fuente
                String texto = "Personas de la intitucion";
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
        styler.style(tituloTabla, 300, 50, null, "negrita", 25, Color.decode("#710466"));
        tituloTabla.setLocation(30, 260);

        add(tituloTabla);
    }


    // para la tabla
    private void initTabla(ArrayList<Persona> personas) {
        String[] columnas = { "Nombre", "E-mail", "Tipo", "Se unio", "Estado" };

        Object[][] datos = convertirDatos(personas);

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
        scrollPane.setBounds(30, 30 + 175 + 30 + 50 + 30 + 30, 960, 480);

        // para el scroll
        estilizarScrollBar();

        add(scrollPane);
    }

    private Object[][] convertirDatos(ArrayList<Persona> personas) {
        Object[][] datos = new Object[personas.size()][8];
        for (int i = 0; i < personas.size(); i++) {
            Persona persona = personas.get(i);
            datos[i][0] = persona.getNombre();
            datos[i][1] = persona.getEmail();
            datos[i][2] = persona.getTipoPersona();
            datos[i][3] = persona.getFechaRegistro();
            datos[i][4] = persona.getEstado();
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

                // Quitar todos los bordes de la celda
                celda.setBorder(BorderFactory.createEmptyBorder());

                // Solo dejar el borde inferior
                if (row == table.getRowCount() - 1) {
                    celda.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
                }

                // Alinear al centro
                celda.setHorizontalAlignment(SwingConstants.CENTER);

                // Cambiar la fuente
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
                button.setBackground(Color.DARK_GRAY); // Cambiar el color del botón de desplazamiento
                return button;
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                JButton button = super.createIncreaseButton(orientation);
                button.setBackground(Color.DARK_GRAY); // Cambiar el color del botón de desplazamiento
                return button;
            }

            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.decode("#E2103C"); // Color de la barra deslizante
                this.trackColor = Color.decode("#8F075B"); // Color de la pista
            }
        });
    }

}
