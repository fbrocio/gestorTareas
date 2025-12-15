package modelos;

/**
 * Representa un proyecto en la aplicación de gestión de tareas
 * Contiene los atributos básicos del proyecto (id, nombre)
 * Se utiliza por el servidor y los DAO para almacenar y obtener información de la base de datos
 */
public class Proyecto {
	private int id;
	private String nombre;
	//Constructores
	public Proyecto(int id, String nombre) {
	    this.id = id;
	    this.nombre = nombre;
	}
	
	public Proyecto(String nombre) {
	    this.nombre = nombre;
	}
	//Getters y setters
	public int getId() { 
		return id; 
		}
	public String getNombre() { 
		return nombre; 
		}
	public void setNombre(String nombre) { 
		this.nombre = nombre;
		}
	
	@Override
	public String toString() {
	    return "Proyecto{id=" + id + ", nombre='" + nombre + "'}";
	}
}