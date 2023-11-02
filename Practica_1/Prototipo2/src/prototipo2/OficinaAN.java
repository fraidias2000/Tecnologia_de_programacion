/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototipo2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author aculplay
 */
public class Oficina {
    private Viaje[] viajes;
    private  int numeroViajes = 0;
  
    public Oficina(String ficheroViajes, String ficheroAutobuses) throws FileNotFoundException{
        Scanner fviajes = new Scanner(new File(ficheroViajes));  
        viajes = new Viaje[fviajes.nextInt()];
        fviajes.nextLine();
        leerFicheros(fviajes, ficheroAutobuses);
          
    }
    /*
     * Ocupa un asiento de un autobus de un viaje
     */
    
    public boolean ocuparAsiento(int numeroViaje, int numeroAsiento, Viajero unViajero){
        return viajes[numeroViaje].ocuparAsiento(numeroAsiento, unViajero);
    
    }
    
    /*
     * Desocupa un asiento de un autobus de un viaje
     */
    
    public boolean desocuparAsiento(int numeroViaje, int numeroAsiento){
        return viajes[numeroViaje].desocuparAsiento(numeroAsiento);
    }
    
    /*
     * Genera un fichero con todos los datos relacionados con un viaje
     */
    
    public String generarHoja(){
        return null;      
    }
   
    /*
     * Lee de los ficheros pasados como parametros e inicializa los viajes y las posiciones de los asientos en los autobuses
     */
    
    public void leerFicheros(Scanner ficheroViajes, String ficheroAutobuses) throws FileNotFoundException{    
        while(ficheroViajes.hasNextLine()) { 
            viajes[numeroViajes] = new Viaje(ficheroViajes); 
            viajes[numeroViajes++].inicializarAutobus(ficheroAutobuses);
        }        
        ficheroViajes.close();
    }
    
    public String toString(){
        String cadena = "VIAJES:\n";
        for (int i = 0; i < numeroViajes; i++) {
            cadena = cadena + viajes[i].toString() + "\n";
        }
        return cadena;
    }
}