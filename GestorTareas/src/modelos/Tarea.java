package modelos;

import java.time.LocalDate;

/**
 * Representa una tarea en la aplicación de gestión de tareas
 * Contiene los atributos básicos de la tarea (id, nombre, descripción, estado, urgencia, inicio, entrega e id del proyecto asociado)
 * Se utiliza por el servidor y los DAO para almacenar y obtener información de la base de datos
 */
public class Tarea {
	private int id;
	private String nombre;
	private String descripcion;
	private String estado;
	private String urgencia;
	private LocalDate inicio;
	private LocalDate entrega;
	private int idProyecto;
    
	//Constructores
	public Tarea(int id, String nombre, String descripcion, String estado, String urgencia, 
            LocalDate inicio, LocalDate entrega, int idProyecto) {
	   this.id = id;
	   this.nombre = nombre;
	   this.descripcion = descripcion; // Nuevo campo
	   this.estado = estado;
	   this.urgencia = urgencia;
	   this.inicio = inicio;
	   this.entrega = entrega;
	   this.idProyecto = idProyecto;
	}
	
    public Tarea(String nombre, String descripcion, String estado, String urgencia, 
            LocalDate inicio, LocalDate entrega, int idProyecto) {
	   this.nombre = nombre;
	   this.descripcion = descripcion;
	   this.estado = estado;
	   this.urgencia = urgencia;
	   this.inicio = inicio;
	   this.entrega = entrega;
	   this.idProyecto = idProyecto;
	}
    
	// Getters y setters
	public int getId() { 
		return id; 
		}
	public String getNombre() { 
		return nombre; 
		}
	public String getDescripcion() {
		return descripcion;
	}
	public String getEstado() { 
		return estado; 
		}
	public String getUrgencia() { 
		return urgencia; 
		}
	public LocalDate getInicio() { 
		return inicio; 
		}
	public LocalDate getEntrega() { 
		return entrega; 
		}
	public int getIdProyecto() { 
		return idProyecto; 
		}
	
	public void setNombre(String nombre) { 
		this.nombre = nombre; 
		}
	public void setEstado(String estado) { 
		this.estado = estado; 
		}
	public void setUrgencia(String urgencia) { 
		this.urgencia = urgencia; 
		}
	public void setInicio(LocalDate inicio) { 
		this.inicio = inicio; 
		}
	public void setEntrega(LocalDate entrega) { 
		this.entrega = entrega; 
		}
	public void setIdProyecto(int idProyecto) { 
		this.idProyecto = idProyecto; 
		}
	
	@Override
		public String toString() {
	        return "Tarea{id=" + id + ", nombre='" + nombre + "', descripcion='" + descripcion + 
	               "', estado='" + estado + "', urgencia=" + urgencia + ", inicio=" + inicio + 
	               ", entrega=" + entrega + ", idProyecto=" + idProyecto + "}";
	    }
	}