import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Ficheros {
    
    /* MÃ©todo que lee el contenido del fichero ingredientes.dat y lo vuelca en
    las variables de cla clase principal */
    
    public static void leerIngredientes(String ficheroIngredientes){
    File archivo = null;
    FileReader fr = null;
    BufferedReader br = null;

        try {
         archivo = new File (ficheroIngredientes);
         fr = new FileReader (archivo);
         br = new BufferedReader(fr);

         //Lectura del fichero
         String linea;
         
         while((linea=br.readLine())!=null){
            String linea2=linea;
            String[] parts = linea2.split(";");
            
            if(parts[0].equals("10")) {
            	ImplServer.pan= Integer.parseInt(parts[1]);
            }
            else if(parts[0].equals("11")){
            	ImplServer.carne= Integer.parseInt(parts[1]);
            }
            else if(parts[0].equals("12")){
            	ImplServer.lechuga= Integer.parseInt(parts[1]);
            }
            else if(parts[0].equals("13")){
            	ImplServer.tomate= Integer.parseInt(parts[1]);
            }
            else if(parts[0].equals("14")){
            	ImplServer.salsa= Integer.parseInt(parts[1]);
            }
            else if(parts[0].equals("15")){
            	ImplServer.cebolla= Integer.parseInt(parts[1]);
            }
            else if(parts[0].equals("16")){
            	ImplServer.garbanzos= Integer.parseInt(parts[1]);
            }
            else if(parts[0].equals("17")){
            	ImplServer.basePizza= Integer.parseInt(parts[1]);
            }
            else if(parts[0].equals("18")){
            	ImplServer.pimiento= Integer.parseInt(parts[1]);
            }
            else if(parts[0].equals("19")){
            	ImplServer.ajo= Integer.parseInt(parts[1]);
            }
            else if(parts[0].equals("20")){
            	ImplServer.tortilla= Integer.parseInt(parts[1]);
            }

         }
        } 
      catch(Exception e){
         e.printStackTrace();
      }finally{
         try{                    
            if( null != fr ){   
               fr.close();     
            }                  
         }catch (Exception e2){ 
            e2.printStackTrace();
         }
      }
    }
    
    /* Metodo que coge el contenido de la clase principal (servidor) y lo vuelca
    sobre el fichero con los datos (para actualizar su contenido).*/
    static void actualizarIngredientesFichero(String ficheroIngredientes){
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter(ficheroIngredientes);
            pw = new PrintWriter(fichero);


                pw.println("10;" + ImplServer.pan);
                pw.println("11;" + ImplServer.carne);
                pw.println("12;" + ImplServer.lechuga);
                pw.println("13;" + ImplServer.tomate);
                pw.println("14;" + ImplServer.salsa);
                pw.println("15;" + ImplServer.cebolla);
                pw.println("16;" + ImplServer.garbanzos);
                pw.println("17;" + ImplServer.basePizza);
                pw.println("18;" + ImplServer.pimiento);
                pw.println("19;" + ImplServer.ajo);
                pw.println("20;" + ImplServer.tortilla);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }
    
    /* Metodo que lee el fichero de platos.dat y para que el programa servidor
    conozca los ingredientes y cantidades de cada plato */
    public static void leerPlatos(String ficheroPlatos){
    File archivo = null;
    FileReader fr = null;
    BufferedReader br = null;

        try {
         archivo = new File (ficheroPlatos);
         fr = new FileReader (archivo);
         br = new BufferedReader(fr);

         //Lectura del fichero
         String linea;
         
         while((linea=br.readLine())!=null){
            String linea2=linea;
            
            //Primero se iddentifica que tipo de plato es
            String[] parts = linea2.split("-");
            
            if (parts[0].equals("00")) { //Si es 00 es que se trata del plato: Kebab
                 
                String[] ingredientes = parts[1].split(";"); /* Se coge la segunda parte de la cadena que
                tiene los ingredientes y cantidades separados por ';' */
                 
                for (int i = 0; i < ingredientes.length; i++) {
                    if (ingredientes[i].equals("10")) {
                        Kebab.pan= Integer.parseInt(ingredientes[i+1]);
                    }
                    else if (ingredientes[i].equals("11")) {
                        Kebab.carne= Integer.parseInt(ingredientes[i+1]);
                    }
                    else if (ingredientes[i].equals("12")) {
                        Kebab.lechuga= Integer.parseInt(ingredientes[i+1]);
                    }
                    else if (ingredientes[i].equals("13")) {
                        Kebab.tomate= Integer.parseInt(ingredientes[i+1]);
                    }
                    else if (ingredientes[i].equals("14")) {
                        Kebab.salsa= Integer.parseInt(ingredientes[i+1]);
                    }
                }
            }
             
            else if(parts[0].equals("01")){ //Si es 00 es que se trata del plato: Durum
                   String[] ingredientes = parts[1].split(";"); /* Se coge la segunda parte de la cadena que
                tiene los ingredientes y cantidades separados por ';' */
                 
                for (int i = 0; i < ingredientes.length; i++) {
                    if (ingredientes[i].equals("20")) {
                        Durum.tortilla= Integer.parseInt(ingredientes[i+1]);
                    }
                    else if (ingredientes[i].equals("11")) {
                        Durum.carne= Integer.parseInt(ingredientes[i+1]);
                    }
                    else if (ingredientes[i].equals("12")) {
                        Durum.lechuga= Integer.parseInt(ingredientes[i+1]);
                    }
                    else if (ingredientes[i].equals("13")) {
                        Durum.tomate= Integer.parseInt(ingredientes[i+1]);
                    }
                    else if (ingredientes[i].equals("14")) {
                        Durum.salsa= Integer.parseInt(ingredientes[i+1]);
                    }
                }
            }

            else if(parts[0].equals("02")){ //Si es 00 es que se trata del plato: Falafel
                String[] ingredientes = parts[1].split(";"); /* Se coge la segunda parte de la cadena que
                tiene los ingredientes y cantidades separados por ';' */

                for (int i = 0; i < ingredientes.length; i++) {
                if (ingredientes[i].equals("15")) {
                    Falafel.cebolla= Integer.parseInt(ingredientes[i+1]);
                }
                else if (ingredientes[i].equals("16")) {
                    Falafel.garbanzos= Integer.parseInt(ingredientes[i+1]);
                }
                else if (ingredientes[i].equals("18")) {
                    Falafel.pimiento= Integer.parseInt(ingredientes[i+1]);
                }
                else if (ingredientes[i].equals("19")) {
                    Falafel.ajo= Integer.parseInt(ingredientes[i+1]);
                }
                }
            }

            else if(parts[0].equals("03")){ //Si es 00 es que se trata del plato: Lahmacun
                String[] ingredientes = parts[1].split(";"); /* Se coge la segunda parte de la cadena que
                tiene los ingredientes y cantidades separados por ';' */

                for (int i = 0; i < ingredientes.length; i++) {
                if (ingredientes[i].equals("13")) {
                    Lahmacun.tomate= Integer.parseInt(ingredientes[i+1]);
                }
                else if (ingredientes[i].equals("15")) {
                    Lahmacun.cebolla= Integer.parseInt(ingredientes[i+1]);
                }
                else if (ingredientes[i].equals("17")) {
                    Lahmacun.basePizza= Integer.parseInt(ingredientes[i+1]);
                }
                else if (ingredientes[i].equals("18")) {
                    Lahmacun.pimiento= Integer.parseInt(ingredientes[i+1]);
                }
                }
            }
             }
        } 
      catch(Exception e){
         e.printStackTrace();
      }finally{
         try{                    
            if( null != fr ){   
               fr.close();     
            }                  
         }catch (Exception e2){ 
            e2.printStackTrace();
         }
      }
    }
}