/*
 * Practica2.java 
 * Prototipo0
 * David Ros y alvaro Fraidias
 * 29/03/2020
 */
package prototipo1;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/*
 * Viaje existente en una oficina.
 */

public class Viaje {
    private int codigo;
    private Autobus autobus;
    private String lugarOrigen;
    private String lugarDestino;
    private String fecha;
    
    private final String CODIGO_VIAJE = "Codigo Viaje: ";
    private final String LUGAR_ORIGEN = "\nLugar de origen: ";
    private final String LUGAR_DESTINO= "\nLugar destino: ";
    private final String FECHA = "\nFecha: ";

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
    public void generarHojaViaje(BufferedWriter  fichero) 
                                                 throws IOException{
        fichero.write(CODIGO_VIAJE + codigo + LUGAR_ORIGEN +
                      lugarOrigen +  LUGAR_DESTINO + lugarDestino + 
                      FECHA + fecha + "\n");
        autobus.generarHojaViaje(fichero);
    }
        
    /*
    * Devuelve una cadena con toda la informacion sobre los asientos
    * ocupados y libres del viaje 
    */
    public String obtenerEstadoOcupacion() {
        String cadena = "";
        cadena = autobus.estadoOcupacion() + "\n";
        return cadena;
    } 

    
}