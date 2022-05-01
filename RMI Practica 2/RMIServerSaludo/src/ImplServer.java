import java.rmi.*;
import java.rmi.server.*;
 
public class ImplServer extends UnicastRemoteObject implements restaurante {
	
	//Ingredientes (al leer el fichero se actualizan sus cantidades).
    public static int pan=0, carne=0, lechuga=0, tomate=0, salsa=0, tortilla=0, garbanzos=0, cebolla=0, ajo=0, pimiento=0, basePizza=0;
    
    public static boolean TESTIGO = true; //--> true si está disponible, false si está ocupado por un cliente (en uso)
 
      public ImplServer () throws RemoteException {   }

	
	@Override
	/* Terminar el servicio ------------------------------------------------------------------------ */
	public int termina_servicio() {
		int errorExit;
		System.exit(1);
		return 0;
	}
	
	@Override
	public boolean solicitar_testigo () throws RemoteException{	
		if (TESTIGO==true) { // --> Si el testigo está disponible
			TESTIGO = false; // --> Se establece el testigo como ocupado (porque lo tendría el cliente)
			return true; 
		} else {
			return false; // --> El testigo no se encuentra disponible
		}
	}
	
	@Override
	public void devolver_testigo () throws RemoteException {
		TESTIGO=true; // --> El testigo vuelve a estar disponible
	}
	
	@Override
	 public String consulta_plato(String id){
    	int i=0, result=0;
    	String plato="", s="";
    	if(id.equals("00")) { //Kebab
    		plato = String.format("%s 10 %s 11 %s 12 %s 13 %s 14 %s", Kebab.numIngredientes, Kebab.pan, Kebab.carne, Kebab.lechuga, Kebab.tomate, Kebab.salsa);
        	i++;
        }
        else if(id.equals("01")){ //Durum
        	plato = String.format("%s 20 %s 11 %s 12 %s 13 %s 14 %s", Durum.numIngredientes, Durum.tortilla, Durum.carne, Durum.lechuga, Durum.tomate, Durum.salsa);
        	i++;
        }
        else if(id.equals("02")){ //Falafel
        	plato = String.format("%s 15 %s 16 %s 18 %s 19 %s", Falafel.numIngredientes, Falafel.cebolla, Falafel.garbanzos, Falafel.pimiento, Falafel.ajo);
        	i++;
        }
        else if(id.equals("03")){ //Lahmacun
        	plato = String.format("%s 13 %s 15 %s 17 %s 18 %s", Lahmacun.numIngredientes, Lahmacun.tomate, Lahmacun.cebolla, Lahmacun.basePizza, Lahmacun.pimiento);
        	i++;
        }
    	
    	s = plato + "-" + i; //Se obtiene el numero de ingredientes + identificadores + cantidade + error
    	return s;
    }
	
	
	@Override
	public String lee_ingrediente(int id){
    	int cantidad = 0, i=0;
    	String cantidadResult="";
    	
    	if(id==10) {
        	cantidad=pan;
        	i++;
        }
        else if(id==11){
        	cantidad=carne;
        	i++;
        }
        else if(id==12){
        	cantidad=lechuga;
        	i++;
        }
        else if(id==13){
        	cantidad=tomate;
        	i++;
        }
        else if(id==14){
        	cantidad=salsa;
        	i++;
        }
        else if(id==15){
        	cantidad=cebolla;
        	i++;
        }
        else if(id==16){
        	cantidad=garbanzos;
        	i++;
        }
        else if(id==17){
        	cantidad=basePizza;
        	i++;
        }
        else if(id==18){
        	cantidad=pimiento;
        	i++;
        }
        else if(id==19){
        	cantidad=ajo;
        	i++;
        }
        else if(id==20){
        	cantidad=tortilla;
        	i++;
        }
    	
    	cantidadResult = String.valueOf(cantidad) + " " + String.valueOf(i); //cantidad result
    	return cantidadResult;
    }
	
	
	
	@Override
	public String escribe_ingrediente(int id, int cantidad){
    	String dev="";
    	int i=0;
    	
    	if(id==10) {
        	pan-=cantidad;
        	i++;
        }
        else if(id==11){
        	carne-=cantidad;
        	i++;
        }
        else if(id==12){
        	lechuga-=cantidad;
        	i++;
        }
        else if(id==13){
        	tomate-=cantidad;
        	i++;
        }
        else if(id==14){
        	salsa-=cantidad;
        	i++;
        }
        else if(id==15){
        	cebolla-=cantidad;
        	i++;
        }
        else if(id==16){
        	garbanzos-=cantidad;
        	i++;
        }
        else if(id==17){
        	basePizza-=cantidad;
        	i++;
        }
        else if(id==18){
        	pimiento-=cantidad;
        	i++;
        }
        else if(id==19){
        	ajo-=cantidad;
        	i++;
        }
        else if(id==20){
        	tortilla-=cantidad;
        	i++;
        }
    	
    	dev = String.valueOf(i); // Si dev==0 es que hay un error, si por el contrario es 1 todo bien
    	Ficheros.actualizarIngredientesFichero(Server.ficheroIngredientes);
    	return dev;
    }	
	
	
	
	
	
	
 }