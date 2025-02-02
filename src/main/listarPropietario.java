// package main;

// /*
//  * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
//  * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
//  */

// import javax.swing.JTable;
// import javax.swing.table.DefaultTableModel;
// import javax.swing.JOptionPane;
// import java.sql.*;
// /**
//  *
//  * @author Windows
//  */
// public class listarPropietario {
//     // Cargar datos desde la base de datos al JTable
//     public void cargarDatosEnTabla(JTable tabla) {
//         DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
//         modelo.setRowCount(0);  // Limpiar la tabla antes de llenarla

//         Connection conexion = null;
//         Statement stmt = null;
//         ResultSet rs = null;

//         try {
//             ConexionSQLServer conexionDB = new ConexionSQLServer();
//             conexion = conexionDB.obtenerConexion();
//             stmt = conexion.createStatement();
//             String sql = "SELECT Ci, Nombre_Completo, telefono, direccion, email FROM cliente";
//             rs = stmt.executeQuery(sql);

//             while (rs.next()) {
//                 modelo.addRow(new Object[]{
//                     rs.getString("Ci"),
//                     rs.getString("Nombre_Completo"),
//                     rs.getString("telefono"),
//                     rs.getString("direccion"),
//                     rs.getString("email")
//                 });
//             }

//         } catch (SQLException e) {
//             JOptionPane.showMessageDialog(null, "Error al cargar los datos: " + e.getMessage());
//         } finally {
//             try {
//                 if (rs != null) rs.close();
//                 if (stmt != null) stmt.close();
//                 if (conexion != null) conexion.close();
//             } catch (SQLException e) {
//                 e.printStackTrace();
//             }
//         }
//     }

//     // Actualizar cliente en la base de datos desde la tabla
//     public boolean actualizarClienteDesdeTabla(JTable tabla) {
//         int filaSeleccionada = tabla.getSelectedRow();

//         if (filaSeleccionada == -1) {
//             JOptionPane.showMessageDialog(null, "Por favor, seleccione una fila para editar.");
//             return false;
//         }

//         // Obtener los datos modificados de la fila seleccionada
//         String ci = (String) tabla.getValueAt(filaSeleccionada, 0);
//         String nombre = (String) tabla.getValueAt(filaSeleccionada, 1);
//         String telefono = (String) tabla.getValueAt(filaSeleccionada, 2);
//         String direccion = (String) tabla.getValueAt(filaSeleccionada, 3);
//         String correo = (String) tabla.getValueAt(filaSeleccionada, 4);

//         // Validaciones (sin restricciones estrictas en este caso)
//         if (!nombre.isEmpty() && !telefono.isEmpty() && !direccion.isEmpty() && !correo.isEmpty()) {
//             // Actualizar en la base de datos
//             Connection conexion = null;
//             PreparedStatement stmt = null;

//             try {
//                 ConexionSQLServer conexionDB = new ConexionSQLServer();
//                 conexion = conexionDB.obtenerConexion();

//                 String sql = "UPDATE cliente SET Nombre_Completo=?, telefono=?, direccion=?, email=? WHERE Ci=?";
//                 stmt = conexion.prepareStatement(sql);
//                 stmt.setString(1, nombre);
//                 stmt.setString(2, telefono);
//                 stmt.setString(3, direccion);
//                 stmt.setString(4, correo);
//                 stmt.setString(5, ci);

//                 int filasActualizadas = stmt.executeUpdate();
//                 if (filasActualizadas > 0) {
//                     JOptionPane.showMessageDialog(null, "Cliente actualizado exitosamente.");
//                     return true;
//                 }

//             } catch (SQLException e) {
//                 JOptionPane.showMessageDialog(null, "Error al actualizar cliente: " + e.getMessage());
//             } finally {
//                 try {
//                     if (stmt != null) stmt.close();
//                     if (conexion != null) conexion.close();
//                 } catch (SQLException e) {
//                     e.printStackTrace();
//                 }
//             }
//         } else {
//             JOptionPane.showMessageDialog(null, "Todos los campos deben estar llenos.");
//         }

//         return false;
//     }



//     private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {                                          
//         // Obtener datos de los JTextFields y eliminar espacios en blanco innecesarios
//         String nombre = jTextField2.getText().trim();
//         String ci = jTextField3.getText().trim();
//         String telefono = jTextField4.getText().trim();
//         String direccion = jTextField5.getText().trim();
//         String correo = jTextField6.getText().trim();

//         // Verificar si hay campos vacíos
//         StringBuilder mensaje = new StringBuilder();
//         if (nombre.isEmpty()) {
//             mensaje.append("- Debes ingresar el nombre.\n");
//         }
//         if (ci.isEmpty()) {
//             mensaje.append("- Debes ingresar el CI o NIT.\n");
//         }
//         if (telefono.isEmpty()) {
//             mensaje.append("- Debes ingresar el teléfono.\n");
//         }
//         if (direccion.isEmpty()) {
//             mensaje.append("- Debes ingresar la dirección.\n");
//         }
//         if (correo.isEmpty()) {
//             mensaje.append("- Debes ingresar el correo.\n");
//         }

//         // Mostrar alerta si hay campos vacíos
//         if (mensaje.length() > 0) {
//             JOptionPane.showMessageDialog(this, "Faltan los siguientes datos:\n" + mensaje.toString(), "Datos Incompletos", JOptionPane.WARNING_MESSAGE);
//             return;  // Detener la ejecución si faltan datos
//         }

//         // Instancia del Backend
//         registroPropietario registro = new registroPropietario();

//         // Llamar a la función de validación y registro
//         boolean resultado = registro.registrarCliente(nombre, ci, telefono, direccion, correo);

//         // Si el registro fue exitoso, limpiar los campos
//         if (resultado) {
//             jTextField2.setText("");
//             jTextField3.setText("");
//             jTextField4.setText("");
//             jTextField5.setText("");
//             jTextField6.setText("");
//         }
//     }




//     public boolean registrarCliente(String nombre, String ci, String telefono, String direccion, String correo) {
//         boolean registrado = false;

//         // 🔍 VALIDACIONES
//         if (!validarNombre(nombre)) {
//             JOptionPane.showMessageDialog(null, "Error: El nombre debe iniciar con mayúsculas, sin números ni caracteres especiales");
//             return false;
//         }
        
//         if (!validarCI(ci)) {
//             JOptionPane.showMessageDialog(null, "Error: El CI debe tener exactamente 10 caracteres y no contener símbolos o letras.");
//             return false;
//         }
        
//         if (!validarTelefono(telefono)) {
//             JOptionPane.showMessageDialog(null, "Error: El teléfono debe contener solo 8 números.");
//             return false;
//         }

//         if (!validarDireccion(direccion)) {
//             JOptionPane.showMessageDialog(null, "Error: La dirección no puede superar los 250 caracteres.");
//             return false;
//         }

//         if (!validarCorreo(correo)) {
//             JOptionPane.showMessageDialog(null, "Error: El correo debe ser válido y  debe contener un dominio valido y '@'. Máximo 50 caracteres.");
//             return false;
//         }
        
//         // 🔥 SI TODAS LAS VALIDACIONES SON EXITOSAS, REGISTRAR EN BD
//         Connection conexion = null;
//         PreparedStatement stmt = null;
        
//         try {
//             ConexionSQLServer conexionDB = new ConexionSQLServer();
//             conexion = conexionDB.obtenerConexion();

//             String sql = "INSERT INTO cliente (Nombre_Completo, Ci, telefono, direccion, email) VALUES (?, ?, ?, ?, ?)";
//             stmt = conexion.prepareStatement(sql);
//             stmt.setString(1, nombre);
//             stmt.setString(2, ci);
//             stmt.setString(3, telefono);
//             stmt.setString(4, direccion);
//             stmt.setString(5, correo);

//             int filasInsertadas = stmt.executeUpdate();
//             if (filasInsertadas > 0) {
//                 registrado = true;
//                 JOptionPane.showMessageDialog(null, "Cliente registrado exitosamente.");
//             }
            
//         } catch (SQLException e) {
//             JOptionPane.showMessageDialog(null, "Error al registrar cliente: " + e.getMessage());
//             e.printStackTrace();
//         } finally {
//             try {
//                 if (stmt != null) stmt.close();
//                 if (conexion != null) conexion.close();
//             } catch (SQLException e) {
//                 e.printStackTrace();
//             }
//         }
        
//         return registrado;
//     }
// }

