/*
 *David Ros y Alvaro Fraidias
 *Prototipo 3
 *04/04/2020 
 * 
 */
package modelo;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

/*
 * Viaje existente en una oficina.
 */

public class Viaje {
    private int codigo;
    private Autobus autobus;
    private String lugarOrigen;
    private String lugarDestino;
    private GregorianCalendar fecha;
    
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
         inicializarFecha(fichero);
    }

    public int getCodigo() {
        return codigo;
    }
    public GregorianCalendar getFecha(){
        return fecha;
    }
                    
    
    public void inicializarFecha(Scanner fichero){
        int dia = fichero.nextInt();
        int mes = fichero.nextInt();
        int anio = fichero.nextInt();
        fecha = new GregorianCalendar(anio,mes,dia);
        
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
                      FECHA + fecha.DAY_OF_MONTH +  fecha.MONTH + 
                      fecha.YEAR  + "\n");
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