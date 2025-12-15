package dao;

import modelos.Tarea;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO (Data access Object) para la entidad Tarea
 * Proporciona métodos para ejecutar las operaciones CRUD sobre la tabla 'tarea' en la base de datos.
 * Utiliza ConexionBD para obtener conexiones.
 */
public class TareaDAO {
	/**
	 * Crea una nueva tarea en la base de datos
	 * @param t objeto Tarea con los datos de la nueva tarea
	 * @throws SQLException si ocurre un error al conectarse o ejecutar la consulta
	 */
	public void crearTarea(Tarea t) throws SQLException {
	    String sql = """
	        INSERT INTO tarea(nombre, descripcion, estado, urgencia, fecha_inicio, fecha_fin, proyecto_id)
	        VALUES(?,?,?,?,?,?,?)
	    """;
	
	    try (Connection con = ConexionBD.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {
	
	    	pst.setString(1, t.getNombre());
	        pst.setString(2, t.getDescripcion());
	        pst.setString(3, t.getEstado());
	        pst.setString(4, t.getUrgencia());    
	        pst.setDate(5, Date.valueOf(t.getInicio()));
	        pst.setDate(6, Date.valueOf(t.getEntrega()));
	        pst.setInt(7, t.getIdProyecto());     
	        pst.executeUpdate();
	    }
	}
	
	/**
	 * Obtener la lista de tareas asociadas a un proyecto específico
	 * @param idProyecto identificador del proyecto
	 * @return List de objetos Tarea correspondientes al proyecto
	 * @throws SQLException si falla la conexión con la base de datos
	 */
	public List<Tarea> obtenerTareasPorProyecto(int idProyecto) throws SQLException {
	    String sql = "SELECT * FROM tarea WHERE proyecto_id=?";
	    List<Tarea> lista = new ArrayList<>();
	    try (Connection con = ConexionBD.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {
	        pst.setInt(1, idProyecto);
	        ResultSet rs = pst.executeQuery();
	        while (rs.next()) {
	            lista.add(new Tarea(
	                rs.getInt("id"),
	                rs.getString("nombre"),
	                rs.getString("descripcion"), 
	                rs.getString("estado"),
	                rs.getString("urgencia"),   
	                rs.getDate("fecha_inicio").toLocalDate(),
	                rs.getDate("fecha_fin").toLocalDate(),
	                rs.getInt("proyecto_id")
	            ));
	        }
	    }
	    return lista;
	}
	
	/**
	 * Actualiza un campo específico de una tarea
	 * @param id identificador de la tarea a modificar
	 * @param campo nombre del campo a actualizar
	 * @param valor nuevo valor del campo
	 * @throws SQLException si falla la conexión con la base de datos
	 */
	public void actualizarTarea(int id, String campo, String valor) throws SQLException {

	    String sql = "UPDATE tarea SET " + campo + "=? WHERE id=?";
	
	    try (Connection con = ConexionBD.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {
	            pst.setString(1, valor);
		        pst.setInt(2, id);
		        pst.executeUpdate();
	    }
	}
	/**
	 * Elimina una tarea de la base de datos
	 * @param id identificador de la tarea a eliminar
	 * @throws SQLException si falla la conexión con la base de datos
	 */
	public void eliminarTarea(int id) throws SQLException {
	    String sql = "DELETE FROM tarea WHERE id=?";
	    try (Connection con = ConexionBD.getConnection();
	         PreparedStatement pst = con.prepareStatement(sql)) {
	
	        pst.setInt(1, id);
	        pst.executeUpdate();
	    }
	}
}