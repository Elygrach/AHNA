/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.ahna.servlests;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "InicioServlet", urlPatterns = {"/inicio"})
public class InicioServlet extends HttpServlet {

    // URL de conexión a la base de datos
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/servletinicio?useSSL=false";
    // Usuario y contraseña para conectarse a la base de datos
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "password";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String usuario = request.getParameter("usuario");
        String contraseña = request.getParameter("contraseña");

        Connection conexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {
            // Cargar el driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer la conexión a la base de datos
            conexion = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            // Preparar la consulta SQL
            String query = "SELECT * FROM usuarios WHERE usuario = ? AND contrasena = ?";
            preparedStatement = conexion.prepareStatement(query);
            preparedStatement.setString(1, usuario);
            preparedStatement.setString(2, contraseña);

            // Ejecutar la consulta
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                // Usuario válido
                request.getSession().setAttribute("usuario", usuario);
                response.sendRedirect("panel.jsp");
            } else {
                // Usuario inválido
                response.sendRedirect("index.html");
            }
        } catch (ClassNotFoundException e) {
            Logger.getLogger(InicioServlet.class.getName()).log(Level.SEVERE, "Driver JDBC no encontrado", e);
            response.sendRedirect("index.html");
        } catch (SQLException e) {
            Logger.getLogger(InicioServlet.class.getName()).log(Level.SEVERE, "Excepción SQL", e);
            response.sendRedirect("index.html");
        } finally {
            // Cerrar los recursos
            try {
                if (rs != null) rs.close();
                if (preparedStatement != null) preparedStatement.close();
                if (conexion != null) conexion.close();
            } catch (SQLException ex) {
                Logger.getLogger(InicioServlet.class.getName()).log(Level.SEVERE, "Error al cerrar los recursos", ex);
            }
        }
    }

   @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    // Llama al método processRequest para manejar la solicitud GET
    processRequest(request, response);
}

@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    // Llama al método processRequest para manejar la solicitud POST
    processRequest(request, response);
}

@Override
public String getServletInfo() {
    // Proporciona una breve descripción del servlet
    return "InicioServlet maneja las solicitudes de inicio de sesión";
}

}
