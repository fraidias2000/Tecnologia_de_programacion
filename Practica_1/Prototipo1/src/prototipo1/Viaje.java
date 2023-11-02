/*
 * Practica1.java 
 * Prototipo1
 * David Ros y Alvaro Fraidias
 * 11/03/2020
 */
package prototipo1;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.GregorianCalendar;
import java.util.Scanner;

/*
 * Practica1.java 
 * Prototipo0
 * David Ros y �?lvaro Fraidias
 * 27/02/2020
 */

public class Viaje {
    private int codigo;
    private Autobus autobus;
    private String lugarOrigen;
    private String lugarDestino;
    private String fecha;

    public Viaje(Scanner fichero) {
        this.autobus = new Autobus(fichero); 
        this.codigo = fichero.nextInt();
        this.lugarOrigen = fichero.next();
        this.lugarDestino = fichero.next();
        this.fecha = fichero.next();
    }
        
    /*
     * Lee de un fichero la estructura de los asientos de un autobus 
     * para inicializarlo.
     */
    
    public void inicializarAutobus(String fichero) 
                                   throws FileNotFoundException{
        this.autobus.inicializarAsientos(fichero);
        
     
    } 
    /*
     * Ocupa un asiento de un autbus
     */
    public boolean ocuparAsiento(int numeroAsiento, Viajero viajero){
      return true;
    }
        
    /*
     * Desocupa un asiento de un autobus
     */
    public boolean desocuparAsiento( int numeroAsiento){
      return true;
    }
        
    /*
     *  Genera una hoja con todos los datos del viaje
     */
    public void generarHojaViaje(){}
        
    /*
     * Comprueba los asientos que estan libres y ocupados
     */
    public void comprobarEstadoOcupacion(){}
    
    
    
    public String toString(){
        String cadena = "Codigo: " + this.codigo +
                        ", Lugar de origen: " + this.lugarOrigen +
                        ", Lugar de destino: " + this.lugarDestino + 
                        ", Fecha: " + this.fecha + "\n" +
                        autobus.toString();
        return cadena;
    }
   
}
