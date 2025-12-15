package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase de utilidad para manejar la conexión a la base de datos MySQL
 * Contiene los parámetros de conexión y proporciona un método estático para obtener la conexión a la base de datos
 */
public class ConexionBD {
	private static final String URL = "jdbc:mysql://localhost:3306/gestortareas";
	private static final String USER = "root";
	private static final String PASS = "root";
	
	/**
	 * Devuelve la conexión a la base de datos utilizando los parámetros definidos
	 * @return devuelve un objeto Connection para interactuar con la base de datos
	 * @throws SQLException si ocurre un error al conectarse a la base de datos
	 */
	public static Connection getConnection() throws SQLException {
	    return DriverManager.getConnection(URL, USER, PASS);
	}
}
