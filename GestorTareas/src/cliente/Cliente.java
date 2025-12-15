package cliente;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
/**
 * Clase que representa al cliente de la aplicación de gestión de proyectos y tareas.
 * Se encarga de:
 * - Mostrar un menú interactivo por consola.
 * - Solicitar los datos al usuario para crear, listar, modificar o eliminar proyectos y tareas.
 * - Validar que los datos introducidos sean correctos:
 *     - 'estado' solo puede ser "pendiente", "en progreso" o "completa".
 *     - 'urgencia' solo puede ser "alta", "media" o "baja".
 *     - Las fechas deben cumplir el formato "YYYY-MM-DD".
 *     - Los campos a modificar de una tarea solo pueden ser "fecha_fin", "estado" o "urgencia".
 * - Construir los comandos siguiendo el protocolo definido para el servidor.
 * - Enviar los comandos al servidor y mostrar la respuesta.
 *
 */
public class Cliente {

	/**
	 * Punto de entrada del cliente.
	 * - Ejecuta el bucle principal del menú.
	 * - Solicita datos al usuario y valida su formato.
	 * - Construye los comandos para el servidor según la opción elegida.
	 * - Envía el comando al servidor y muestra la respuesta.
	 */

	public static void main(String[] args) {
		final String HOST = "localhost";
		final int PUERTO = 5000;
		
		
	    try (Socket sc = new Socket(HOST, PUERTO);
	         BufferedReader in = new BufferedReader(new InputStreamReader(sc.getInputStream()));
	         BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sc.getOutputStream()));
	         Scanner scn = new Scanner(System.in)) {
	
	        boolean salir = false;
	        
	        while (!salir) {
	        	System.out.println("— GESTIÓN DE PROYECTOS Y TAREAS —");
	            System.out.println("1. Crear proyecto");     
	            System.out.println("2. Crear tarea");       
	            System.out.println("3. Listar proyectos");  
	            System.out.println("4. Listar tareas");    
	            System.out.println("5. Modificar tarea (fecha fin, estado, urgencia)"); 
	            System.out.println("6. Borrar proyecto");   
	            System.out.println("7. Borrar tarea");      
	            System.out.println("8. Salir");             
	            
	            System.out.print("Seleccione una opción: ");
	
	            String opcion = scn.nextLine();
	            String comando = ""; 
	
	            switch (opcion) {
	        	/*
	        	 * 1. Crear proyecto
	        	 * Se pide al usuario el nombre del proyecto
	        	 * Se traduce al formato:
	        	 * INSERT_PROYECTO;nombre
	        	 */
	            case "1":
	                System.out.print("Nombre proyecto: ");
	                String nombreP = scn.nextLine();
	                comando = "INSERT_PROYECTO;" + nombreP; 
	                break;
	
	            /*
	             * 2. Crear tarea
	             * Se pide al usuario los datos de la nueva tarea
	             * Se traduce al formato:
	             * INSERT_TAREA;nombre;descripcion;estado;urgencia;fecha_inicio;fecha_fin;proyecto_id
	             */
	            case "2": 
	                System.out.println("--- INTRODUCE LOS DATOS DE LA TAREA ---");
	                System.out.print("Nombre de la tarea: ");
	                String nombre = scn.nextLine();
	
	                System.out.print("Descripción: ");
	                String descripcion = scn.nextLine();
	                
	                System.out.print("Estado (pendiente/en progreso/completa): ");
	                String estado = scn.nextLine();
	                //Validar que el valor esté permitido
	                while(!estado.equalsIgnoreCase("pendiente")&&
	                		!estado.equalsIgnoreCase("en progreso")&&
	                		!estado.equalsIgnoreCase("completa")) {
	                	System.out.println("Valor inválido para estado. Introduzca uno de los valores indicados.");
	                	System.out.println("Estado (pendiente/en progreso/completa): ");
	                	estado = scn.nextLine();
	                }
	                
	                System.out.print("Urgencia (alta/media/baja): ");
	                String urgencia = scn.nextLine();
	                //Validar que el valor esté permitido
	                while(!urgencia.equalsIgnoreCase("alta") &&
	                		!urgencia.equalsIgnoreCase("media")&&
	                		!urgencia.equalsIgnoreCase("baja")) {
	                	System.out.println("Valor inválido. Introduzca uno de los valores indicados.");
	                	System.out.println("Urgencia (alta/media/baja)");
	                	urgencia = scn.nextLine();
	                }
	
	                LocalDate fechaInicioValida = null;
	                while(fechaInicioValida==null) {
	                	System.out.println("Fecha inicio(YYYY-MM-DD): ");
	                	String fechaInicio = scn.nextLine();
	                	try {
	                		fechaInicioValida = LocalDate.parse(fechaInicio);
	                	} catch (DateTimeParseException e) {
	                		System.out.println("Formato de fecha inválido. Siga el formato indicado.");
	                	}
	                }
	
	                LocalDate fechaFinValida = null;
	                while(fechaFinValida==null) {
	                	System.out.println("Fecha fin(YYYY-MM-DD): ");
	                	String fechaFin = scn.nextLine();
	                	try {
	                		fechaFinValida = LocalDate.parse(fechaFin);
	                	} catch (DateTimeParseException e) {
	                		System.out.println("Formato de fecha inválido. Siga el formato indicado.");
	                	}
	                }
	
	                System.out.print("ID del proyecto: ");
	                int idProyecto = Integer.parseInt(scn.nextLine());
	                
	                //Se concatenan los datos introducidos mediante (;)
	                comando = String.join(";", 
	                	    "INSERT_TAREA",
	                	    nombre,
	                	    descripcion,
	                	    estado,
	                	    urgencia,
	                	    fechaInicioValida.toString(),
	                	    fechaFinValida.toString(),
	                	    String.valueOf(idProyecto)
	                	); 
	                break;
	
	            /*
	             * 3. Listar proyectos
	             * Pasa directamente el comando:
	             * LIST_PROYECTOS
	             */
	            case "3":
	                comando = "LIST_PROYECTOS";
	                break;
	
	            /*
	             * 4. Listar tareas
	             * Se pide al usuario la id del proyecto 
	             * Se traduce al formato:
	             * LIST_TAREAS;proyecto_id
	             */
	            case "4":
	                System.out.print("ID de proyecto para listar tareas: ");
	                String idProyectoT = scn.nextLine();
	                comando = "LIST_TAREAS;" + idProyectoT;
	                break;
	
	            /*
	             * 5. Modificar tarea
	             * Se pide al usuario: id de la tarea, campo y valor a modificar
	             * Se traduce al formato:
	             * UPDATE_TAREA;id;campo;valor
	             */
	            case "5":
	                
	                System.out.print("ID de la tarea a modificar: ");
	                String idT = scn.nextLine();
	                System.out.print("Campo a modificar (fecha_fin, estado, urgencia): ");
	                String campo = scn.nextLine();
	                //Validar que el campo sea uno de los permitidos
	                while (!campo.equalsIgnoreCase("fecha_fin") &&
	                		!campo.equalsIgnoreCase("estado")&&
	                		!campo.equalsIgnoreCase("urgencia")) {
	                	System.out.println("Campo inválido. Introduzca uno de los campos indicados.");
	                	System.out.println("Campo a modificar (fecha_fin, estado, urgencia): ");
	                	campo = scn.nextLine();
	                }
	                System.out.print("Nuevo valor: ");
	                String valor = scn.nextLine();
	                comando = "UPDATE_TAREA;" + idT + ";" + campo + ";" + valor;
	                break;
	
	            /*
	             * 6. Borrar proyecto
	             * Se pide al usuario la id del proyecto a eliminar
	             * Se traduce al formato:
	             * DELETE_PROYECTO;proyecto_id
	             */
	            case "6":
	            	
	                System.out.print("ID del proyecto a borrar: ");
	                String idProyectoDel = scn.nextLine();
	                comando = "DELETE_PROYECTO;" + idProyectoDel;
	                break;
	
	            /*
	             * 7. Borrar tarea
	             * Se pide al usuario la id de la tarea a eliminar
	             * Se traduce al formato:
	             * DELETE_TAREA;id
	             */
	            case "7":
	                System.out.print("ID de la tarea a borrar: ");
	                String idTareaDel = scn.nextLine();
	                comando = "DELETE_TAREA;" + idTareaDel;
	                break;
	
	            case "8":
	                salir = true;
	                continue;
	                
	            default:
	                System.out.println("Opción inválida");
	                continue;
	        }
	            
	            out.write(comando + "\n");
	            out.flush();
	            String respuesta = in.readLine();
	            System.out.println("Servidor: " + respuesta);
	        }
	
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
