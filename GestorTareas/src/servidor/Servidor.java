package servidor;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import dao.ProyectoDAO;
import dao.TareaDAO;
import modelos.Proyecto;
import modelos.Tarea;
import java.time.LocalDate;

/**
 * Clase que representa el servidor.
 * Se encarga de:
 * abrir las conexiones
 * interpretar la operación CRUD que le pasa el cliente
 * ordenar a las DAO las operaciones correspondientes
 */
public class Servidor {
	
	/**
	 * Método principal del servidor
	 * Inicializa el ServerSocket en un puerto fijo
	 * Queda a la espera de conexiones de clientes
	 * Cada cliente se atiende de forma secuencial
	 */

    public static void main(String[] args) {
        final int PUERTO = 5000; 

        try (ServerSocket server = new ServerSocket(PUERTO)) {
            System.out.println("Servidor iniciado en puerto " + PUERTO);
            
            while (true) {
                Socket cliente = server.accept(); 
                System.out.println("Cliente conectado: " + cliente.getInetAddress());

                manejarCliente(cliente); // secuencial
                System.out.println("Cliente desconectado");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clase que atiende al cliente
     * Crea los canales de entrada y salida, recibe los comandos enviados por el cliente, los procesa y devuelve una respuesta
     * 
     * @param cliente socket que representa la conexión con el cliente
     */
    private static void manejarCliente(Socket cliente) {
    	try (BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(cliente.getOutputStream()))) {

            String mensaje;
            ProyectoDAO proyectoDAO = new ProyectoDAO();
            TareaDAO tareaDAO = new TareaDAO();

            /*
             * Lee una línea enviada por el cliente, la procesa, 
             * recibe un string de respuesta y lo envía al cliente
             */
            while ((mensaje = in.readLine()) != null) {
                String respuesta = procesarComando(mensaje, proyectoDAO, tareaDAO);
                out.write(respuesta + "\n");
                out.flush();
            }

        } catch (IOException e) {
            System.out.println("Error en la conexión con el cliente");
        }
    }

    /**
     * Procesa el mensaje recibido por el cliente
     * El comando se recibe como una cadena de texto con campos separados por punto y coma (;). Dependiendo del comando,
     * se ejecuta una operación CRUD sobre proyectos o tareas.
     * @param mensaje comando recibido por el cliente
     * @param proyectoDAO objeto DAO para gestionar proyectos
     * @param tareaDAO objeto DAO para gestionar tareas
     * @return respuesta en formato texto indicando el resultado de la operación
     */
    private static String procesarComando(String mensaje, ProyectoDAO proyectoDAO, TareaDAO tareaDAO) {
        try {
            // Se define el delimitador ';'
            String[] partes = mensaje.split(";"); 

            //Selección del comando a ejecutar
            switch (partes[0]) { 

                // CRUD PROYECTOS 
            
                /* 
                 * Inserta un nuevo proyecto
                 * Formato esperado:
                 * INSERT_PROYECTO;nombre
                 */
                case "INSERT_PROYECTO": 
                	proyectoDAO.crearProyecto(new Proyecto(partes[1]));
                    return "OK Proyecto creado";

                /*
                 * Obtiene la lista de proyectos
                 * Formato esperado:
                 * LIST_PROYECTOS
                 */
                case "LIST_PROYECTOS": 
                	return proyectoDAO.obtenerProyectos().toString();

                /* 
                 * Actualiza el nombre de un proyecto
                 * Formato esperado:
                 * UPDATE_PROYECTO;id;nombre
                 */
                case "UPDATE_PROYECTO": 
                    int idUpd = Integer.parseInt(partes[1]); //Selecciona la id del proyecto
                    String nuevoNombre = partes[2]; //Variable nombre asignado
                    proyectoDAO.actualizarProyecto(idUpd, nuevoNombre);
                    return "OK Proyecto actualizado";

                /* Elimina un proyecto
                 * Formato esperado:
                 * DELETE_PROYECTO;id
                 */
                case "DELETE_PROYECTO": 
                    int idDel = Integer.parseInt(partes[1]);
                    proyectoDAO.eliminarProyecto(idDel);
                    return "OK Proyecto eliminado";

                //CRUD TAREAS
                    
                /* 
                 * Inserta una nueva tarea
                 * Formato esperado:
                 * INSERT_TAREA;nombre;descripcion;estado;urgencia;fecha_inicio;fecha_fin;proyecto_id
                 */
                case "INSERT_TAREA":  
                    String nombre = partes[1];
                    String descripcion = partes[2]; 
                    String estado = partes[3];     
                    String urgencia = partes[4];    
                    LocalDate inicio = LocalDate.parse(partes[5]);
                    LocalDate entrega = LocalDate.parse(partes[6]);
                    int idProyecto = Integer.parseInt(partes[7]);

                    tareaDAO.crearTarea(new Tarea(
                    		nombre, descripcion, estado, urgencia, inicio, entrega, idProyecto
                    		)); 
                    return "OK Tarea creada";

                /*
                 * Obtiene la lista de tareas de un proyecto
                 * Formato esperado:
                 * LIST_TAREAS;proyecto_id;
                 */
                case "LIST_TAREAS": // 
                    int idProyectoT = Integer.parseInt(partes[1]);
                    return tareaDAO.obtenerTareasPorProyecto(idProyectoT).toString();

                /*
                 * Actualiza un campo de una tarea
                 * Formato esperado:
                 * UPDATE_TAREA;id;campo;valor
                 */
                case "UPDATE_TAREA":
                    int idTarea = Integer.parseInt(partes[1]);
                    String campo = partes[2]; 
                    String valor = partes[3];
                    tareaDAO.actualizarTarea(idTarea, campo, valor);
                    return "OK Tarea actualizada";

                /*
                 * Elimina una tarea
                 * Formato esperado:
                 * DELETE_TAREA;id
                 */
                case "DELETE_TAREA": 
                    int idTareaDel = Integer.parseInt(partes[1]);
                    tareaDAO.eliminarTarea(idTareaDel);
                    return "OK Tarea eliminada";

                default:
                    return "ERROR Comando desconocido";
            }

        } catch (Exception e) {
            return "ERROR " + e.getMessage();
        }
    }
}
