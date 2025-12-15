package dao;

import modelos.Proyecto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Clase DAO (Data access Object) para la entidad Proyecto
 * Proporciona métodos para ejecutar las operaciones CRUD sobre la tabla 'proyecto' en la base de datos.
 * Utiliza ConexionBD para obtener conexiones.
 */
public class ProyectoDAO {
	/**
	 * Crea un nuevo proyecto en la base de datos
	 * @param p objeto Proyecto con los datos del nuevo proyecto
	 * @throws SQLException si ocurre un error al conectarse o ejecutar la consulta
	 */
	public void crearProyecto(Proyecto p) throws SQLException {
	    String sql = "INSERT INTO proyecto(nombre) VALUES(?)";
	    try (Connection con = ConexionBD.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {
	
	        pst.setString(1, p.getNombre());
	        pst.executeUpdate();
	    }
	}
	
	
	/**
	 * Obtiene la lista de proyectos almacenados en la base de datos
	 * @return List de objetos Proyectos almacenados
	 * @throws SQLException si ocurre un error al conectarse o ejecutar la consulta
	 */
	public List<Proyecto> obtenerProyectos() throws SQLException {
	    String sql = "SELECT * FROM proyecto";
	    List<Proyecto> lista = new ArrayList<>();
	
	    try (Connection con = ConexionBD.getConnection();
	         Statement st = con.createStatement();
	         ResultSet rs = st.executeQuery(sql)) {
	
	        while (rs.next()) {
	            lista.add(new Proyecto(rs.getInt("id"), rs.getString("nombre")));
	        }
	    }
	    return lista;
	}
	
	/**
	 * Actualiza el nombre del proyecto seleccionado
	 * @param id identificador del proyecto seleccionado
	 * @param nuevoNombre nuevo nombre asignado al proyecto seleccionado
	 * @throws SQLException si ocurre un error al conectarse o ejecutar la consulta
	 */
	public void actualizarProyecto(int id, String nuevoNombre) throws SQLException {
	    String sql = "UPDATE proyecto SET nombre=? WHERE id=?";
	    try (Connection con = ConexionBD.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {
	
	        pst.setString(1, nuevoNombre);
	        pst.setInt(2, id);
	        pst.executeUpdate();
	    }
	}
	
	/**
	 * Elimina el proyecto seleccionado
	 * @param id identificador del proyecto a eliminar
	 * @throws SQLException si ocurre un error al conectarse o ejecutar la consulta
	 */
	public void eliminarProyecto(int id) throws SQLException {    
		String sql = "DELETE FROM proyecto WHERE id=?";
	    try (Connection con = ConexionBD.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {
	
	        pst.setInt(1, id);
	        pst.executeUpdate();
	    }
	}
}