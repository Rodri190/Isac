package database;

import java.sql.Connection;
import java.sql.DriverManager;


public class Conection {

    public Conection() {
        
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/preuniversitario", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    

}

/*
 * package NewDB;
 * 
 * import java.sql.*;
 * 
 * public class Registro {
 * 
 * public Registro(){
 * 
 * }
 * 
 * public void start(){
 * try {
 * Connection connection =
 * DriverManager.getConnection("jdbc:mysql://localhost/netfliss", "root", "");
 * PreparedStatement pst =
 * connection.prepareStatement("select * from pelicula where idPelicula = 10");
 * ResultSet res = pst.executeQuery();
 * 
 * if(res.next()){
 * System.out.println(res.getString("nombre_pelicula"));
 * }else{
 * System.out.println("No hay nada");
 * }
 * connection.close();
 * pst.close();
 * } catch (Exception e) {
 * e.printStackTrace();
 * }
 * }
 * 
 * 
 * public static void main(String[] args) {
 * Registro registro = new Registro();
 * registro.start();
 * }
 * }
 */
