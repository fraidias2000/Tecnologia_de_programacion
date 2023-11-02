/*
 * Practica1.java 
 * Prototipo3
 * David Ros y alvaro Fraidias
 * 14/03/2020
 */
package prototipo0;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Viaje {
    private int codigo;
    private Autobus autobus;
    private String lugarOrigen;
    private String lugarDestino;
    private String fecha;

    public Viaje(Scanner fichero, String ficheroAutobus)
                 throws FileNotFoundException {
        this.autobus = new Autobus(fichero, ficheroAutobus); 
        this.codigo = fichero.nextInt();
        this.lugarOrigen = fichero.next();
        this.lugarDestino = fichero.next();
        this.fecha = fichero.next();
    }

    public int getCodigo() {
        return codigo;
    }
           
    /*
     * Ocupa un asiento de un autbus
     */
    public boolean ocuparAsiento( int numeroAsiento, Viajero viajero){
        return autobus.ocuparAsiento(numeroAsiento, viajero);  
    }
        
    
     // Desocupa un asiento de un autobus
     
    public boolean desocuparAsiento( int numeroAsiento){
        return autobus.desocuparAsiento(numeroAsiento);    
    }
        
    /*
     *  Escribe en u fichero todos los datos del viaje
     */
    public void generarHojaViaje(BufferedWriter  fichero) throws IOException{
        fichero.write("Codigo Viaje: " + codigo + "\nLugar de origen: " +
                      lugarOrigen +  "\nLugar destino: " + lugarDestino + 
                      "\nFecha: " + fecha + "\n");
        autobus.generarHojaViaje(fichero);
    }
        
    /*
    * Devuelve una cadena con toda la informacion sobre los asientos ocupados
    * y libres del viaje 
    */
    public String obtenerEstadoOcupacion() {
        String cadena = "";
        cadena = autobus.estadoOcupacion() + "\n";
        return cadena;
    } 

    
}