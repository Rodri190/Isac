package Components.Admin;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LinearGradientPaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.geom.Point2D;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
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
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.metal.MetalScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.w3c.dom.events.MouseEvent;

import UI.BotonesUI;
import UI.GradientBackground;
import UI.GradientButton;
import UI.GradientPanel;
import Utilities.ComponentStyler;
import Utilities.Fuente;
import Utilities.Separador;
import database.Query;
import database.model.Materia;
import database.model.Persona;

public class Inscripcion extends JPanel {
    private Fuente fuente;
    private GradientBackground gradient;
    private ComponentStyler styler;
    private Separador esp;
    private Query query;
    private JLabel tituloTablaEstudiantes;
    private JLabel tituloTablaMaterias;
    private DefaultTableModel modelo;
    private JScrollPane scrollPane;
    private JTable tabla;
    private JTextField buscador;
    private String buscarPor;
    private JComboBox<String> opcionesFiltro;
    private ArrayList<Persona> estudiantesEnTabla;
    private ArrayList<Materia> materiasEnTabla;
    private JLabel mesajeError;
    private JLabel mensajeExistoso;
    private JLabel mensajeAlerta;
    private JLabel registrarBtn;
    private DefaultTableModel modeloMateria;
    private JScrollPane scrollPaneMateria;
    private JTable tablaMateria;
    private JTextField buscadorMateria;
    private String buscarPorMateria;






    
    public Inscripcion(int alto){
        gradient = new GradientBackground();
        styler = new ComponentStyler();
        fuente = new Fuente();
        query = new Query();
        esp = new Separador();
        buscarPor = "Nombre";
        buscarPorMateria = "Nombre";
        setBounds(300, 0, 1040, alto);
        setLayout(null);
        initComponents();
    }

    private void initComponents(){
        initTituloTablaEstudiantes();
        initBuscador();
        initTablaEstudiantes(query.selectTodosLosEstudiantes());
        initTituloTablaMaterias();
        initBuscadorMateria();
        initTablaMateria(query.selectTodasLasMaterias());
        initRegistrarBoton();
        initMensajeError();
        initMensajeExitoso();
        initMesajeAlerta();
    }

    private void initRegistrarBoton() {
        registrarBtn = new GradientButton("Registrar", 810, 730, 180, 50, Color.decode("#844AE3"),
                Color.decode("#39D2FF"), Color.decode("#ffffff"), "negrita", 20);
        // registrarBtn = new BotonesUI("Registrar", Color.decode("#F47725"),
        // Color.decode("#9D0956"), Color.decode("#5B026F"));
        registrarBtn.setLocation(810, 730);
        // agregarEventoRegistrar();
        hoverEffectRegistrarBtn();
        agregarEventoRegistrar();
        add(registrarBtn);
    }

    private void agregarEventoRegistrar(){
        registrarBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e){
                if(tabla.getSelectedRow() == -1 || tablaMateria.getSelectedRow() == -1){
                    System.out.println("hola");
                    add(mensajeAlerta);
                    repaint();
                    initCountdown();
                }else{
                    query.insertIntoInscripcion(obtenerIdEstudiante(), obtenerIdMateria());
                    add(mensajeExistoso);
                    repaint();
                    initCountdown();
                }
            }
        });
    }

    private int obtenerIdEstudiante(){
        int idEstudiante = -1;
        int filaSeleccionada = tabla.getSelectedRow();

        if (filaSeleccionada != -1) {
            String nombre = String.valueOf(tabla.getValueAt(filaSeleccionada, 0));
            String ci = String.valueOf(tabla.getValueAt(filaSeleccionada, 1));
            for (Persona estudiante : estudiantesEnTabla) {
                if (estudiante.getNombreCompleto().equals(nombre)
                        && estudiante.getCi().equals(ci)) {
                    idEstudiante = estudiante.getId();
                    break;
                }
            }
        } else {
            System.out.println("No hay ninguna fila seleccionada.");
        }
        return idEstudiante;
    }

    private int obtenerIdMateria(){
        int idMateria = -1;
        int filaSeleccionada = tablaMateria.getSelectedRow();

        if (filaSeleccionada != -1) {
            String nombre = String.valueOf(tablaMateria.getValueAt(filaSeleccionada, 0));
            String docente = String.valueOf(tablaMateria.getValueAt(filaSeleccionada, 2));
            for (Materia materia : materiasEnTabla) {
                if (materia.getNombre().equals(nombre) && materia.getNombreDocente().equals(docente)) {
                    idMateria = materia.getId();
                    break;
                }
            }
        } else {
            System.out.println("No hay ninguna fila seleccionada.");
        }
        System.out.println(idMateria);
        return idMateria;
    }

    private void hoverEffectRegistrarBtn(){
        registrarBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                super.mouseEntered(e);
                registrarBtn.setBorder(new LineBorder(Color.decode("#F47725"), 4));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                // TODO Auto-generated method stub
                super.mouseExited(e);
                registrarBtn.setBorder(null);
            }
        });
    }

    private void initMensajeError() {
        mesajeError = new JLabel("No se encontró") {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image imagen = new ImageIcon("src/Resources/img/xMark.png").getImage();
                g.drawImage(imagen, 310, 5, 70, 40, mensajeExistoso);
            }
        };
        styler.style(mesajeError, 380, 50, null, "negrita", 20, Color.decode("#ffffff"));
        mesajeError.setLocation(30, 730);
        mesajeError.setOpaque(true);
        mesajeError.setBackground(Color.decode("#FF1717"));
        mesajeError.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        // add(mesajeError);
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
        mensajeAlerta.setLocation(30, 730);
        mensajeAlerta.setOpaque(true);
        mensajeAlerta.setBackground(Color.decode("#FF6C09"));
        mensajeAlerta.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        mensajeAlerta.setVisible(true);
        // add(mensajeAlerta);
    }

    private void initMensajeExitoso() {
        mensajeExistoso = new JLabel("Inscripcion existosa") {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image imagen = new ImageIcon("src/Resources/img/checkIcon.png").getImage();
                g.drawImage(imagen, 370, 5, 40, 40, mensajeExistoso);
            }
        };
        styler.style(mensajeExistoso, 420, 50, null, "negrita", 23, Color.decode("#ffffff"));
        mensajeExistoso.setLocation(30, 730);
        mensajeExistoso.setOpaque(true);
        mensajeExistoso.setBackground(Color.decode("#00BA00"));
        mensajeExistoso.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        // add(mensajeExistoso);
    }

    private void initTituloTablaEstudiantes() {
        tituloTablaEstudiantes = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g;

                String texto = "Estudiantes";
                Font font = fuente.getFuente("negrita", 30);
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
        styler.style(tituloTablaEstudiantes, 300, 50, null, "negrita", 25, Color.decode("#710466"));
        tituloTablaEstudiantes.setLocation(30, 10);

        add(tituloTablaEstudiantes);
    }

    private void initTituloTablaMaterias() {
        tituloTablaMaterias = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g;

                String texto = "Materias";
                Font font = fuente.getFuente("negrita", 30);
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
        styler.style(tituloTablaMaterias, 300, 50, null, "negrita", 25, Color.decode("#710466"));
        tituloTablaMaterias.setLocation(30, esp.separar(scrollPane, 20));

        add(tituloTablaMaterias);
    }


    private void initTablaEstudiantes(ArrayList<Persona> estudiantes) {
        estudiantesEnTabla = estudiantes;
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
        estilizarTablaEncabezado(tabla);
        tabla.setRowHeight(50);
        scrollPane = new JScrollPane(tabla);
        scrollPane.setBounds(30, esp.separar(buscador, 20), 960, 220);

        // para el scroll
        estilizarScrollBar(scrollPane);

        add(scrollPane);
    }

    private void initTablaMateria(ArrayList<Materia> materias) {
        materiasEnTabla = materias;
        String[] columnas = { "Nombre", "Turno", "Docente", "Facultad", "Inscritos" };
        String[] datosInteresados = { "Nombre", "Turno", "NombreDocente", "NombreFacultad", "Inscritos"};

        Object[][] datos = convertirDatosMateria(materias, datosInteresados);

        // Revertir el comportamiento de FillsViewportHeight
        
        modeloMateria = new DefaultTableModel(datos, columnas);
        tablaMateria = new JTable(modeloMateria);
        

        // para el encabezado
        estilizarTablaEncabezado(tablaMateria);
        tablaMateria.setRowHeight(50);
        scrollPaneMateria = new JScrollPane(tablaMateria);
        scrollPaneMateria.setBounds(30, esp.separar(buscadorMateria, 20), 960, 200);

        // para el scroll
        estilizarScrollBar(scrollPaneMateria);
        add(scrollPaneMateria);
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

    private void estilizarTablaEncabezado(JTable tabla) {
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

    private void estilizarScrollBar(JScrollPane scrollPane) {
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
        buscador.setLocation(30, esp.separar(tituloTablaEstudiantes, 10));
        buscador.setBorder(new EmptyBorder(0, 50, 0, 0));
        agregarFocusListener(buscador, "Buscar Estudiante");
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

    private void agregarFocusListener(JTextField componente, String placeholder) {

        componente.addFocusListener(new FocusListener() {
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

                for (Persona estudiante : buscarPorArray()) {
                    Object[] fila = {
                            estudiante.getNombre(),
                            estudiante.getApellido(),
                            estudiante.getEmail(),
                            estudiante.getCi(),
                            estudiante.getCelular()
                    };
                    modelo.addRow(fila);

                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (estudiantesEnTabla.size() == 0) {
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
                res = query.selectEstudianteCon("nombre", parametro);
                break;
            case "Apellido":
                res = query.selectEstudianteCon("apellido", parametro);
                break;
            case "C.I.":
                res = query.selectEstudianteCon("ci", parametro);
                break;
            case "Celular":
                res = query.selectEstudianteCon("celular", parametro);
                break;
            default:
                System.out.println("Algo salio mal");
                break;
        }
        estudiantesEnTabla = res;
        return res;
    }

    //para el buscador de materia

    private void initBuscadorMateria() {
        buscadorMateria = new JTextField() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image imagen = new ImageIcon("src/Resources/img/lupa.png").getImage();
                g.drawImage(imagen, 0, 0, 40, 40, buscadorMateria);
            }
        };
        styler.style(buscadorMateria, 960, 40, Color.decode("#ffffff"), "normal", 20, Color.decode("#000000"));
        buscadorMateria.setLocation(30, esp.separar(tituloTablaMaterias, 10));
        buscadorMateria.setBorder(new EmptyBorder(0, 50, 0, 0));
        agregarFocusListener(buscadorMateria, "Buscar Materia");
        agregarEventoTeclaEnterMateria();
        add(buscadorMateria);
    }

    private void agregarEventoTeclaEnterMateria() {
        buscadorMateria.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

                modeloMateria.setRowCount(0);

                materiasEnTabla = query.selectMateriasPorNombre(buscadorMateria.getText());
                for (Materia materia : materiasEnTabla) {
                    Object[] fila = {
                            materia.getNombre(),
                            materia.getTurno(),
                            materia.getNombreDocente(),
                            materia.getNombreFacultad(),
                            materia.getInscritos()
                    };
                    modeloMateria.addRow(fila);

                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (materiasEnTabla.size() == 0) {
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



    
}
