/*
 * Practica1.java 
 * Prototipo1
 * David Ros y Alvaro Fraidias
 * 11/03/2020
 */
package prototipo1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author aculplay
 */
public class Oficina {
    private Viaje[] viajes;
    private  int numeroViajes = 0;
    
  
    public Oficina(String ficheroViajes, String ficheroAutobuses) 
                   throws FileNotFoundException{
        Scanner fviajes = new Scanner(new File(ficheroViajes));  
        viajes = new Viaje[fviajes.nextInt()];
        fviajes.nextLine();
        recuperarFicheros(fviajes, ficheroAutobuses);
          
    }
    
    /*
     * Ocupa un asiento de un autobus de un viaje
     */
    
    public void ocuparAsiento(int numeroViaje, int numeroAsiento, 
                              Viajero unViajero){}
    
    /*
     * Desocupa un asiento de un autobus de un viaje
     */
    
    public void desocuparAsiento(int numeroViaje, int numeroAsiento){}
    
    /*
     * Genera un fichero con todos los datos relacionados con un viaje
     */
    
    public void generarHoja(){}
   
    /*
     * Lee de los ficheros pasados como parametros e inicializa los viajes 
     * y las posiciones de los asientos en los autobuses
     */
    
    public void recuperarFicheros(Scanner ficheroViajes, String ficheroAutobuses) 
                             throws FileNotFoundException{
        while(ficheroViajes.hasNextLine()) { 
            viajes[numeroViajes] = new Viaje(ficheroViajes); 
            viajes[numeroViajes++].inicializarAutobus(ficheroAutobuses);
            
        }        
        ficheroViajes.close();
    
    }
    public String toString(){
        String cadena = "VIAJES\n";
        for (int i = 0; i < numeroViajes; i++) {
            cadena = cadena + viajes[i].toString() + "\n";
        }
        return cadena;
    }
}