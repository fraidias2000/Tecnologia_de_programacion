/*
 * Practica1.java 
 * Prototipo3
 * David Ros y alvaro Fraidias
 * 14/03/2020
 */
package prototipo3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Oficina {
    private Viaje[] viajes;
    private  int numeroViajes = 0;
    private int max_viajes = 0;
  
    public Oficina(String ficheroViajes, String ficheroAutobuses) 
                   throws FileNotFoundException{
        Scanner fviajes = new Scanner(new File(ficheroViajes)); 
        max_viajes = fviajes.nextInt();
        viajes = new Viaje[max_viajes];
        fviajes.nextLine();
        recuperarFicheroViajes(fviajes, ficheroAutobuses);       
    }
    
    public Viaje buscarViaje(int codigoViaje){
        for (int i = 0; i < max_viajes; i++) {
            if(viajes[i].getCodigo() == codigoViaje){
                return viajes[i];
            }
        }
        return null;
    }
    
    /*
     * Ocupa un asiento de un autobus de un viaje
     */
    public boolean ocuparAsiento(int codigoViaje,
                                 int numeroAsiento, Viajero unViajero){
        Viaje viajeAux = buscarViaje(codigoViaje);
        if(viajeAux != null){
            viajeAux.ocuparAsiento(numeroAsiento, unViajero);
            return true;
        }
        return false;
    }
    
    /*
     * Desocupa un asiento de un autobus de un viaje
     */
    public boolean desocuparAsiento(int codigoViaje, int numeroAsiento){
      Viaje viajeAux = buscarViaje(codigoViaje);
        if(viajeAux != null){
            viajeAux.desocuparAsiento(numeroAsiento);
            return true;
        }
        return false;
    }
    
    /*
     * Genera un fichero con todos los datos relacionados con un viaje
     */
    public void generarHoja(int codigoViaje) throws IOException{
        String sFichero = "HojaViaje.txt";
        File fichero = new File(sFichero);
        BufferedWriter hojaViaje = new BufferedWriter(new FileWriter(sFichero));
        Viaje viajeAux = buscarViaje(codigoViaje);
        if(viajeAux != null){  
            if (fichero.exists()) {
                viajeAux.generarHojaViaje(hojaViaje);
                hojaViaje.close();
            }      
        }  
    } 
    
    /*
     * Lee de los ficheros pasados como parametros e inicializa los
     * viajes y las posiciones de los asientos en los autobuses
     */
    public void recuperarFicheroViajes(Scanner ficheroViajes,
                                       String ficheroAutobuses) 
                                       throws FileNotFoundException{    
        while(ficheroViajes.hasNextLine()) { 
            viajes[numeroViajes++] = new Viaje(ficheroViajes,ficheroAutobuses); 
        }        
        ficheroViajes.close();
    }
    
    public String comprobarEstadoOcupacion(){
        String cadena = "";
        for (int i = 0; i < max_viajes; i++) {
            cadena = cadena + "Codigo viaje: " + viajes[i].getCodigo() +
                     viajes[i].comprobarEstadoOcupacion();
        } 
        return cadena;
    }
    
    /*public String toString(){
        String cadena = "VIAJES:\n";
        for (int i = 0; i < max_viajes; i++) {
            cadena = cadena + viajes[i].toString() + "\n";
        }
        return cadena;
    }
    */  
}