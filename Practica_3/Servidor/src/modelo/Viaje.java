/*
 *David Ros y Alvaro Fraidias
 *Prototipo 3
 *01/07/2020 
 * 
 */
package modelo;
import java.beans.PropertyChangeSupport;
import java.io.BufferedWriter;
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
  
    private final String CODIGO_VIAJE = "Codigo Viaje: ";
    private final String LUGAR_ORIGEN = "\nLugar de origen: ";
    private final String LUGAR_DESTINO= "\nLugar destino: ";
    private final String FECHA = "\nFecha: ";
    private static final String ESPACIO = " ";
    private static final String BARRA = "/";
    

    public Viaje(Scanner fichero, String ficheroAutobus,
                 PropertyChangeSupport observador )
                 throws FileNotFoundException, IOException{
        this.observadores = observador;
        this.autobus = new Autobus(fichero, ficheroAutobus);      
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
    public String generarHojaViaje(Autobus autobus){       
        return obtenerEstadoOcupacion();
    }
    
     public Viajero obtenerViajero(int numeroAsiento) {
        return autobus.obtenerViajero(numeroAsiento);
    }
     
    public boolean existe(GregorianCalendar fechaCliente) {
        return (fecha.get(Calendar.YEAR) == fechaCliente.get(Calendar.YEAR)
                && fecha.get(Calendar.DAY_OF_MONTH) == fechaCliente.
                get(Calendar.DAY_OF_MONTH)
                && fecha.get(Calendar.MONTH) == fechaCliente.get(Calendar.MONTH) + 1);
    }
    
    public String obtenerDatos() {
        return codigo  + ESPACIO + lugarOrigen + ESPACIO + lugarDestino;
 }

    public String obtenerDatosViaje() {
        return CODIGO_VIAJE + codigo  + LUGAR_ORIGEN + lugarOrigen + LUGAR_DESTINO + lugarDestino + FECHA;
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
    /*
     *  Escribe en u fichero todos los datos del viaje
     */
    public void generarHojaViaje(BufferedWriter  fichero)  
                throws FileNotFoundException, IOException{       
        fichero.write(CODIGO_VIAJE + codigo + LUGAR_ORIGEN +
                lugarOrigen +  LUGAR_DESTINO + lugarDestino + 
                FECHA + fecha.get(Calendar.DAY_OF_MONTH) + BARRA + 
                fecha.get(Calendar.MONTH) +  BARRA + 
                fecha.get(Calendar.YEAR)  + "\n");                   
        autobus.generarHojaViaje(fichero);
    }
}