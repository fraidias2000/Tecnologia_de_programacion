/*
 *David Ros y Alvaro Fraidias
 *Prototipo 5
 *10/05/2020 
 * 
 */
package modelo;
import java.beans.PropertyChangeSupport;
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
    private PropertyChangeSupport observadores;
    
    static final String CASTELLANO = "Castellano";
    static final String INGLES = "Ingles";  
      //Idioma castellano
    private final String CODIGO_VIAJE = "Codigo Viaje: ";
    private final String LUGAR_ORIGEN = "\nLugar de origen: ";
    private final String LUGAR_DESTINO= "\nLugar destino: ";
    private final String FECHA = "\nFecha: ";
     //Idioma ingles
    private final String TRAVEL_CODE = "Travel code: ";
    private final String PLACE_OF_ORIGIN = "\nPlace of origin: ";
    private final String DESTINATION_PLACE= "\nDestination place: ";
    private final String DATE = "\nDate: ";

    public Viaje(Scanner fichero, String ficheroAutobus,
                 PropertyChangeSupport observador )
                 throws FileNotFoundException {
        this.observadores = observador;
        this.autobus = new Autobus(fichero, ficheroAutobus, observadores); 
        this.codigo = fichero.nextInt();
        this.lugarOrigen = fichero.next();
        this.lugarDestino = fichero.next();
        inicializarFecha(fichero);
    }
    /*
    * Devuelve el codigo del viaje
    */
    public int getCodigo() {
        return codigo;
    }

    /*
    * Devuelve el autobus
    */
    public Autobus getAutobus() {
        return autobus;
    }
    /*
    * Devuelve la fecha
    */
    public GregorianCalendar getFecha(){
        return fecha;
    }                  
    
    /*
     * Inicializa una fecha
     */
    public void inicializarFecha(Scanner fichero){
        int dia = fichero.nextInt();
        int mes = fichero.nextInt();
        int anio = fichero.nextInt();
        fecha = new GregorianCalendar(anio, mes, dia);   
    }
   
    /*
     * Ocupa un asiento de un autbus
     */
    public boolean ocuparAsiento( int numeroAsiento, Viajero viajero){
        return autobus.ocuparAsiento(numeroAsiento, viajero);  
    }
        
    /*
     *Desocupa un asiento de un autobus
     */
    public boolean desocuparAsiento( int numeroAsiento){
        return autobus.desocuparAsiento(numeroAsiento);    
    }
        
    /*
     *  Escribe en u fichero todos los datos del viaje
     */
    public void generarHojaViaje(BufferedWriter  fichero, String idioma) 
                                                 throws IOException{
        switch(idioma){
            case CASTELLANO:
                fichero.write(CODIGO_VIAJE + codigo + LUGAR_ORIGEN +
                    lugarOrigen +  LUGAR_DESTINO + lugarDestino + 
                    FECHA + fecha.DAY_OF_MONTH +  fecha.MONTH + 
                    fecha.YEAR  + "\n");
                break;
            case INGLES:
                 fichero.write(TRAVEL_CODE + codigo + PLACE_OF_ORIGIN +
                    lugarOrigen +  DESTINATION_PLACE + lugarDestino + 
                    DATE + fecha.DAY_OF_MONTH +  fecha.MONTH + 
                    fecha.YEAR  + "\n");
                break;         
        }   
        autobus.generarHojaViaje(fichero,idioma);
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
    
    public Viaje buscarViaje(GregorianCalendar fecha){
        Viaje viaje = null;   
        if(fecha.get(Calendar.YEAR) == this.fecha.get(Calendar.YEAR) &&
             fecha.get(Calendar.MONTH) == this.fecha.get(Calendar.MONTH) &&
                fecha.get(Calendar.DAY_OF_MONTH) == this.fecha
                        .get(Calendar.DAY_OF_MONTH)){
                  viaje = this;
        } 
        return viaje;
    }
}