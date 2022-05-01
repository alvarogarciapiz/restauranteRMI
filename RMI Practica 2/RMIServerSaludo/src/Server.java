import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {
	public static String ficheroIngredientes;
	public static String ficheroPlatos;
	public static void main (String args[]) {
	 try {
		 
		 	// Se asocian los nombres de los ficheros pasados por argumento a dos variables
			ficheroIngredientes = args[0];
			ficheroPlatos = args[1];
			
			//Se lee el stock de ingredientes y se actualizan sus valores
			Ficheros.leerIngredientes(ficheroIngredientes);
			
			//En cada clase se actualizan los valores correspondientes a los ingredientes y cantidades 
		    Ficheros.leerPlatos(ficheroPlatos);
		 
		    
		    
		   LocateRegistry.createRegistry(1099); //Creo el registry, el cliente lo localizar�, le paso un puerto
		   
		   ImplServer RestauranteRMI = new ImplServer();	//Se crea una instancia de ImplHola que implementa la interfaz Hola		   		   
		   Naming.rebind("rmi://localhost/RESTAURANTE", RestauranteRMI); //Para acceder a la tabla
		   
		   System.out.println("El restaurante se encuentra abierto. ");
		   
		   }catch (Exception e) {
			   System.out.println("Error al abrir el restaurante: " + e);
			}
	 Ficheros.actualizarIngredientesFichero(ficheroIngredientes);
	   }
	

}