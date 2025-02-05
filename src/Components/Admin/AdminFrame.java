package Components.Admin;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import database.Query;

import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminFrame extends JFrame {
    private static final int ALTURA = 850;
    private static final int ANCHO = 300 + 1040;
    private Query query;
    
    public AdminFrame(){
        query = new Query();
        AdminPanel adminPanel = new AdminPanel(ALTURA, query.selectTodasLasPersonas());
        // RegistroMateria adminPanel = new RegistroMateria(ALTURA, query, query.selectTodosLosDocentes());
        add(new BarraLateral(ALTURA, this, adminPanel));
        add(adminPanel);
        setSize(ANCHO, ALTURA);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setBackground(Color.decode("#191c31"));
        setVisible(true);
        System.out.println(getWidth());
    }

    public static void main(String[] args) {
        new AdminFrame();
    }
    

}




