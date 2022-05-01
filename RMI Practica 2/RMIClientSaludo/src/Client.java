import java.net.*;
import java.net.UnknownHostException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.io.*;
import java.util.Random;

public class Client {
	public static void main (String args[]) throws RemoteException, NotBoundException {
		int opcion; //Para leer la opcion del menu
        Scanner sc = new Scanner(System.in);
        
        Registry registry = LocateRegistry.getRegistry(); //Localizo el registry creado por el servidor
        
        restaurante testRemote = null;
        try {
        testRemote = (restaurante) registry.lookup("RESTAURANTE"); //Creo un objeto 'testremote' de tipo 'hola', �Qu� hace lo de despu�s del igual?

        } catch (Exception e) {
        	e.printStackTrace();
            System.out.println("El restaurante no se encuentra abierto");
            System.exit(1);
        }
			
		String pedidosActivos="";  	
		
		do {
            //Menu
            System.out.println(" __________________________________________________");
            System.out.println("|                                                  |");
            System.out.println("| Introduce el numero de la opcion para ejecutar:  |");
            System.out.println("| 1. Consultar disponibilidad del plato.           |");
            System.out.println("| 2. Solicitud de plato.                           |");
            System.out.println("| 3. Anulacion de plato.                           |");
            System.out.println("| 4. Actualizacion cantidad ingredientes.          |");
            System.out.println("| 5. Consultar el menu.                            |");
            System.out.println("|__________________________________________________|");
            System.out.println("| 0. Salir del restaurante.                        |");
            System.out.println("|__________________________________________________|");
            opcion = sc.nextInt();
            
                switch (opcion) {
                    
                    case 1: //1. Consultar disponibilidad del plato
                    	int i,l=0;
	                    String cas1= consultarDisponibilidad();
	                    while(cas1.equals("04")) {
	                    	cas1= consultarDisponibilidad();
	                    }
	                    
	                    // Primero se llama al servidor para saber la composicion del plato
	                    
	                    String infoPlatos = testRemote.consulta_plato(cas1);
	                    String[] ingredientesYError = infoPlatos.split("-");
	                    
	                    /* En ingredientesYError tenemos los ingredientes + cantidades + el código de error
	                     * mientras que en ingredientes tenemos las cantidades y no el error */
	                    
	                    if(ingredientesYError[1].equals("0")) {
	                    	System.out.println("Error al consultar la disponibilidad del plato");
	                    } else {
	                    	String[] ingredientes = ingredientesYError[0].split(" ");
	                    	int numeroIngredientes = Integer.parseInt(ingredientes[0]); 
	                    	
	                    	for (i=1; i<numeroIngredientes*2; i+=2) {
	                    		/* En la posición i tenemos el ID del ingrediente y en la posición i+1 la cantidad necesaria
	                    		 Hago una llamada al servidor por cada ingrediente para comprobar su disponibilidad */
	                    		
	                    		
	                    		int idIngrediente = Integer.parseInt(ingredientes[i]);
	                    		
	                    		String disponibilidadIngrediente = testRemote.lee_ingrediente(idIngrediente);
	                    		
	                    		String[] disponibilidadYError = disponibilidadIngrediente.split(" ");
	                    		String error = disponibilidadYError[1];
	                    		int qIngredienteNecesaria = Integer.parseInt(ingredientes[i+1]);
	                    		int qIngrediente = Integer.parseInt(disponibilidadYError[0]); // la cantidad que tengo del ingrediente
	                    		
	                    		if(!error.equals("0") && qIngrediente>=qIngredienteNecesaria) {
	                    			l++; // El contador aumenta si no hay error al leer el ingrediente y la cantidad en stock es superior a la necesaria para elaborar el plato
	                    		}
	                    		
	                    	}
	                    	//Si el contador 'l' es igual al número de ingredientes significa que el plato se encuentra disponible y por tanto se avisa al usuario
	                    	if(l==numeroIngredientes) {
                    			System.out.println("El plato se encuentra disponible \n");
                    		} else {
                    			System.out.println("El plato no se encuentra disponible \n");
                    		}
	                    }    
                    break;
                    
                    case 2: //2. Solicitud de plato
                    	l=0;
                    	int b=0;
	                    String cas2 = hacerPedido();
	                    while(cas2.equals("04")) {
	                    	cas2= hacerPedido(); //En cas2 se tiene el id del plato que se desea solicitar
	                    }
	                    
	                    String pedidoCopia = cas2;
	                    
	                 // Primero se llama al servidor para saber la composición del plato
	                    infoPlatos = testRemote.consulta_plato(cas2);
	                    ingredientesYError = infoPlatos.split("-");
	                    
	                    /* En ingredientesYError tenemos los ingredientes + cantidades + el código de error
	                     * mientras que en ingredientes tenemos las cantidades y no el error */
	                    
	                    if(ingredientesYError[1].equals("0")) {
	                    	System.out.println("Error al consultar la disponibilidad del plato");
	                    } else {
	                    	String[] ingredientes = ingredientesYError[0].split(" ");
	                    	int numeroIngredientes = Integer.parseInt(ingredientes[0]); 
	                    	
	                    	for (i=1; i<numeroIngredientes*2; i+=2) {
	                    		/* En la posición i tenemos el ID del ingrediente y en la posición i+1 la cantidad necesaria
	                    		 Hago una llamada al servidor por cada ingrediente para comprobar su disponibilidad */
	                    		
	                    		
	                    		int idIngrediente = Integer.parseInt(ingredientes[i]);
	                    		
	                    		String disponibilidadIngrediente = testRemote.lee_ingrediente(idIngrediente);
	                    		
	                    		String[] disponibilidadYError = disponibilidadIngrediente.split(" ");
	                    		String error = disponibilidadYError[1];
	                    		int qIngredienteNecesaria = Integer.parseInt(ingredientes[i+1]);
	                    		int qIngrediente = Integer.parseInt(disponibilidadYError[0]); // la cantidad que tengo del ingrediente                  		
	                    		
	                    		if(!error.equals("0") && qIngrediente>=qIngredienteNecesaria) {
	                    			l++; // El contador aumenta si no hay error al leer el ingrediente y la cantidad en stock es superior a la necesaria para elaborar el plato
	                    			
	                    			
	                    			//Antes de escribir el ingrediente solicitamos el testigo al servidor
	                    			boolean TESTIGO = testRemote.solicitar_testigo();
	                            	while (TESTIGO == false) {                    		
	                            	TESTIGO = testRemote.solicitar_testigo();
	                            	
	        	                    	try {
	        								Thread.sleep(2500); //--> Se le hace una peticón al servidor del testigo cada 2,5 segundos hasta que nos pase el testigo
	        							} catch (InterruptedException e) {
	        								e.printStackTrace();
	        							}
	                            	System.out.println("Esperando a que se libere el testigo");
	                            	}
	                    			
	                    			
	                    			int idIngred = Integer.parseInt(ingredientes[i]);
	                    			String errorEscritura = testRemote.escribe_ingrediente(idIngred, qIngredienteNecesaria);
	                    			if(!errorEscritura.equals("0")) {
	                    				b++;
	                    			}
	                    		}
	                    		testRemote.devolver_testigo(); // Se devuelve el testigo
	                    	}
	                    	//Si el contador 'l' es igual al número de ingredientes significa que el plato se encuentra disponible y se ha pedido correctamente
	                    	if(l==numeroIngredientes && b ==numeroIngredientes) {
                    			System.out.println("El plato fue pedido correctamente, disfrute de su pedido. \n");
                    			pedidosActivos = pedidosActivos + pedidoCopia + " "; //Se añade el pedido a la lista de pedidosActivos
                    		} else if(b!=numeroIngredientes){
                    			System.out.println("El plato se encuentra disponible pero hubo un error al solicitarlo, pruebe de nuevo.\n");
                    		} else if(l!=numeroIngredientes) {
                    			System.out.println("No hay ingredientes suficientes para confeccionar el plato\n");
                    		}
	                    }
	                    
                    break;
                    
                    case 3: //3. Anulacion de plato
                    	
                    	String anulacion = anularPedido(pedidosActivos);
                    	if(anulacion.equals("0")) {
                    		System.out.println("Utiliza la opcion [2] del menu para solicitar un plato.");
                    	} else {        	
                    	l=0;
                    	b=0;
	                    cas2 = anulacion;
	                    
	                    
	                 // Primero se llama al servidor para saber la composición del plato
	                    infoPlatos = testRemote.consulta_plato(cas2);
	                    ingredientesYError = infoPlatos.split("-");
	                    
	                    /* En ingredientesYError tenemos los ingredientes + cantidades + el código de error
	                     * mientras que en ingredientes tenemos las cantidades y no el error */
	                    
	                    if(ingredientesYError[1].equals("0")) {
	                    	System.out.println("Error al consultar la disponibilidad del plato");
	                    } else {
	                    	String[] ingredientes = ingredientesYError[0].split(" ");
	                    	int numeroIngredientes = Integer.parseInt(ingredientes[0]); 
	                    	
	                    	for (i=1; i<numeroIngredientes*2; i+=2) {
	                    		/* En la posición i tenemos el ID del ingrediente y en la posición i+1 la cantidad necesaria
	                    		 Hago una llamada al servidor por cada ingrediente para comprobar su disponibilidad */
	                    		
	                    		
	                    		int idIngrediente = Integer.parseInt(ingredientes[i]);
	                    		
	                    		String disponibilidadIngrediente = testRemote.lee_ingrediente(idIngrediente);
	                    		
	                    		String[] disponibilidadYError = disponibilidadIngrediente.split(" ");
	                    		String error = disponibilidadYError[1];
	                    		int qIngredienteNecesaria = Integer.parseInt(ingredientes[i+1]);
	                    		int qIngrediente = Integer.parseInt(disponibilidadYError[0]); // la cantidad que tengo del ingrediente                  		
	                    		
	                    		if(!error.equals("0") && qIngrediente>=qIngredienteNecesaria) {
	                    			l++; // El contador aumenta si no hay error al leer el ingrediente y la cantidad en stock es superior a la necesaria para elaborar el plato
	                    			
	                    			
	                    			//Antes de escribir el ingrediente solicitamos el testigo
	                    			boolean TESTIGO = testRemote.solicitar_testigo();
	                            	while (TESTIGO == false) {                    		
	                            	TESTIGO = testRemote.solicitar_testigo();
	                            	
	        	                    	try {
	        								Thread.sleep(2500); //--> Se le hace una peticón al servidor del testigo cada 2,5 segundos hasta que nos pase el testigo
	        							} catch (InterruptedException e) {
	        								e.printStackTrace();
	        							}
	                            	System.out.println("Esperando a que se libere el testigo");
	                            	}
	                    			
	                    			int idIngred = Integer.parseInt(ingredientes[i]);
	                    			String errorEscritura = testRemote.escribe_ingrediente(idIngred, -qIngredienteNecesaria);
	                    			if(!errorEscritura.equals("0")) {
	                    				b++;
	                    			}
	                    		}
	                    		testRemote.devolver_testigo(); // Se devuelve el testigo
	                    	}
	                    	//Si el contador 'l' es igual al número de ingredientes significa que el plato se encuentra disponible y se ha pedido correctamente
	                    	if(l==numeroIngredientes && b ==numeroIngredientes) {
                    			System.out.println("El plato fue anulado correctamente, vuelva pronto. \n");
                    			
                    			//Se elimina el pedido borrado de los pedidosActivos
                    			pedidosActivos = borrarPlato(pedidosActivos, anulacion);
                    			
                    		} else if(b!=numeroIngredientes){
                    			System.out.println("Error al cancelar su pedido.\n");
                    		} else if(l!=numeroIngredientes) {
                    			System.out.println("Error al cancelar su pedido.\n");
                    		}
	                    }
                    	}
                    	
                    break;
                    
                    case 4: //4. Actualizacion cantidad ingredientes.
                                     	   	
                    	String cas4 = actualizarIngredientes();
                    	String[] parts = cas4.split(" ");
                    	
                    	String idIngrediente = parts[0];
                    	String cantidadIngrediente = parts[1];
                    	
                    	int idIng = Integer.parseInt(idIngrediente);
                    	int qInt = Integer.parseInt(cantidadIngrediente);
                    	
                    	
                    	
                    	boolean TESTIGO = testRemote.solicitar_testigo();
                    	while (TESTIGO == false) {                    		
                    	TESTIGO = testRemote.solicitar_testigo();
                    	
	                    	try {
								Thread.sleep(2500); //--> Se le hace una peticón al servidor del testigo cada 2,5 segundos hasta que nos pase el testigo
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
                    	System.out.println("Esperando a que se libere el testigo");
                    	}
                    	
                    	String errorAct = testRemote.escribe_ingrediente(idIng, -qInt);
                    	if(errorAct.equals("0")) {
                    		System.out.println("Error al escribir el ingrediente");
                    	}
                    	System.out.println("La cantidad del ingrediente " + idIngrediente + " fue actualizada correctamente.");
                    	
                    	testRemote.devolver_testigo();
	                    
                    break;
                    
                    case 5: //5. Consultar el menu
                    verPlatos();
                    break;
                    
                    case 0: //0. Salir del programa
                    	int error = testRemote.termina_servicio();
                    	if(error==0) {
                    		System.exit(1);
                    		opcion = 0;
                    	} else {
                    		System.out.println("Error al cerrar el servidor. ");
                    	}
                    	
                    break;
                    
                    default:
                    System.out.println("[ERROR] Introduce una opcion valida.");
                }
            } while (opcion!=0);

		
		
     }
	
	/* Metodo para comprobar la disponibilidad de los platos */
    public static String consultarDisponibilidad(){
    	String func="";
    	System.out.println("Desea ver primero los platos que ofrecemos? \n Introduce un '0' para viualizarlos, sino cualquier otro numero. ");
    	Scanner ss = new Scanner(System.in);
    	Scanner sp = new Scanner(System.in);
    	int pl = ss.nextInt();
    	if(pl==0) {
    		verPlatos();
    	}
        System.out.println("Introduce el ID del plato del que desea conocer su disponibilidad. [00-01-02-03]");
        String platoElegido = sp.nextLine();
        
        if(platoElegido.equals("00")){ //Si es 00 es Kebab
        	System.out.println("Has seleccionado: Kebab");
        	func="00";
        }
        else if(platoElegido.equals("01")){ //Si es 01 es Durum
        	System.out.println("Has seleccionado: Durum");
        	func="01";
        }
        else if(platoElegido.equals("02")){ // Si es 02 es falafel
        	System.out.println("Has seleccionado: Falafel");
        	func="02";
        }
        else if(platoElegido.equals("03")){ // Si es 03 es Lahmacun
        	System.out.println("Has seleccionado: Lahmacun");
        	func="03";
        }
        else {
        	System.out.println("[ERROR] Introduce una opcion valida.");
        	func="04";
        }
        
        return func;
    }
    
    
    /* Metodo para solicitar un plato (hacer pedido) */
    public static String hacerPedido(){
    	String func="";
    	Scanner ss = new Scanner(System.in);
    	Scanner sp = new Scanner(System.in);
    	
    	System.out.println("Desea ver primero los platos que ofrecemos? \n Introduce un '0' para viualizarlos, sino cualquier otro numero. ");
    	int pl = ss.nextInt();
    	if(pl==0) {
    		verPlatos();
    	}
        System.out.println("Introduce el ID del plato que desea solicitar. [00-01-02-03]");
        String platoElegido = sp.nextLine();
        
        if(platoElegido.equals("00")){ //Si es 00 es Kebab
        	func="00";
        }
        else if(platoElegido.equals("01")){ //Si es 01 es Durum
        	func="01";
        }
        else if(platoElegido.equals("02")){ // Si es 02 es falafel
        	func="02";
        }
        else if(platoElegido.equals("03")){ // Si es 03 es Lahmacun
        	func="03";
        }
        else {
        	System.out.println("[ERROR] Introduce una opcion valida.");
        	func="04";
        }
        System.out.println("has seleccionado el plato " + func + ". Su pedido se precesará en breve.");
        return func;
    }
    
    /* Metodo para anular un pedido */
    public static String anularPedido(String pedidosActivos) {
    	int g=0, gg=0;
    	String pedidos[] = pedidosActivos.split(" ");
    	Scanner sq = new Scanner(System.in);
    	String pedidoEliminar = "";
    	
    	int s=0;
    	
    	if(pedidosActivos.equals("") || pedidosActivos.equals("\n") || pedidosActivos.equals(" ") || pedidosActivos.equals("\n" + "\n") || pedidosActivos.equals("" + "") || pedidosActivos.equals(null)) {
    		System.out.println("No se ha realizado ningun pedido. No se puede anular nada. haz un pedido para poder anularlo.");
    		pedidoEliminar = "0";
    	}
    	else {
    	System.out.println("Los pedidos que se encuentran en proceso son los siguientes: ");
    	for (s=0; s<pedidos.length; s++) {
    		System.out.println(pedidos[s]);
    	}
	    	while (g==0) {
	    		System.out.println("Introduce el ID de un pedido de la lista: ");
	        	pedidoEliminar = sq.nextLine();
	        	
	        	for (gg=0; gg<pedidos.length; gg++) {
		        	if(pedidoEliminar.equals(pedidos[gg])) {
		        		g++;
		        	}
	        	}
	    	}
    	
    	}
    	
    	return pedidoEliminar; //Si devuelve un 0 es que no hay pedidos activos, sino devuelve el ID del pedido
    }
    
    
    
    /* Metodo para actualizar ingredientes */
    public static String actualizarIngredientes(){
    	Scanner sp = new Scanner(System.in);
    	Scanner p = new Scanner(System.in);
    	String ingrediente="";
    	
        System.out.println("Introduce el ID del ingrediente que deseas actualizar.");
        System.out.println("[10 -> Pan]  [11 -> Carne]  [12 -> lechuga]  [13 -> Tomate]  [14 -> Salsa]  [15 -> Cebolla]  [16 -> Garbanzos]  [17 -> Base para Pizza]  [18 -> Pimiento]  [19 -> Ajo]  [20 -> Pan en tortilla]");
        String IDIngrediente = sp.nextLine();
        
        System.out.println("Introduce la cantidad que vas a agregar del ingrediente: ");
        String QIngrediente = sp.nextLine();
        
        ingrediente = IDIngrediente + " " + QIngrediente;
        return ingrediente;
    }
    
    
    /* Metodo para ver los platos y su descripcion */
    public static void verPlatos() {
    	System.out.println("A continuacion se muestra el menu: ");
        System.out.println(" [KEBAB -> 00] Pan con carne, lechuga tomate y salsa.");
        System.out.println(" [DURUM -> 01] Carne, lechuga, tomate y salsa en una tortilla de pan.");
        System.out.println(" [FALAFEL -> 02] Croquetas de garbanzos, cebolla, ajo y pimiento.");
        System.out.println(" [LAHMACUN -> 03] Pizza turca con pimiento tomate y cebolla sobre una base de pan.");
		try {
			Thread.sleep(500); //Se visualiza medio segundo al menos antes de continuar
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }

    
    public static String borrarPlato(String pedActivos, String pedidoABorrar) {
    	
    	
    	String pedidosBorrados = pedActivos;
    	pedidosBorrados = pedidosBorrados.replace(pedidoABorrar, "");
    	
    	return pedidosBorrados;
    }
	
	
}