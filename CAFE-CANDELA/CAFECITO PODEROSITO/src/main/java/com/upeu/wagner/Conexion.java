package com.upeu.sysventas;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Datos
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:sqlite:ventas.db";
    private static Connection conn = null;

    // Constructor privado para evitar instanciación
    private Conexion() {}

    public static Connection getConexion() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(URL);
                System.out.println("Conectado a SQLite.");
            } catch (SQLException e) {
                System.out.println("Error de conexión: " + e.getMessage());
            }
        }
        return conn;
    }
}
