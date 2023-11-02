/*
 *David Ros y Alvaro Fraidias
 *Prototipo 3
 *01/07/2020 
 * 
 */
package Control;

import Vista.OficinaVista;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import modelo.Asiento;
import modelo.Oficina;
import modelo.Sixtupla;
import modelo.Tripla;
import modelo.Tupla;
import modelo.Viaje;
import modelo.Viajero;

public class Agencia implements OyenteVista{
    public static final String VERSION = "Agencia 2.0";
    private OficinaVista vista;
    private Oficina oficina;
    private static String ARG_DEBUG = "-d";
    private static String ERROR_GENERAR_HOJA_VIAJES = 
            "Error al generar hoja viajes";
    private static String ERROR_OBTENER_VIAJES = 
            "Error al obtener viajes para una fecha concreta";
    private static String ERROR_OCUPAR_ASIENTO = 
            "Error al ocupar un asiento";
    private static String ERROR_DESOCUPAR_ASIENTO = 
            "Error al desocupar un asiento";
    private static boolean modoDebug = false; 
     
      
   /*
    * Construye la agencia
    */
    public Agencia(String[] args){     
        procesarArgsMain(args);
        oficina =  Oficina.instancia();
        vista = OficinaVista.devolverInstancia(this,oficina); 
        oficina.nuevoObservador(vista);
        oficina.conectar();   
    }
    
     public static boolean esModoDebug() {
        return modoDebug;
    }
    
    
     private void procesarArgsMain(String[] args) {
        List<String> argumentos =
                new ArrayList<String>(Arrays.asList(args));  
    
        if (argumentos.contains(ARG_DEBUG)) {
            modoDebug = true;    
        }
    }
    
 
    
    
    /*
     * Obtiene los viajes para una fecha dada
     */
    private void obtenerViajes(GregorianCalendar fecha) {
        List<String> viajes;
        try {
            viajes = oficina.obtenerViajes(fecha);   
            vista.rellenarViajes(viajes);
        } catch (Exception ex) {
            vista.ponerMensaje(ERROR_OBTENER_VIAJES);
        }    
    }
    
    
    /*
     * Escribe en un fichero todos los datos relacionados con un viaje
     */
      private void generarHojaViaje(String codigoViaje) {
            try {
                oficina.generarHoja(codigoViaje);
            } catch (Exception ex) {
                vista.ponerMensaje(ERROR_GENERAR_HOJA_VIAJES);
            }  
    }
      
  /*
   *  Recibe eventos de vista
   */  
    public void eventoProducido(Evento evento, Object obj){        
        switch(evento) {
            case OCUPAR_ASIENTO:   
                 Tripla<String, Viajero, Integer> tuplaOcupar = 
                (Tripla<String,Viajero, Integer>)obj;
                ocuparAsiento(tuplaOcupar);
                break;
            case DESOCUPAR_ASIENTO:
                 Tupla<String, String> tuplaDesocupar = 
                (Tupla<String,String>)obj;
                desocuparAsiento(tuplaDesocupar);
                break;                         
            case SALIR: 
                System.exit(0);
                break;    
            case BUSCAR_VIAJES:
                obtenerViajes((GregorianCalendar) obj);
                break;
             case GENERAR_HOJA_VIAJE:
                generarHojaViaje(String.valueOf(obj));
                break;
                        
        }  
    }
    
    
    /*
     * Ocupa un asiento
     */
    private void ocuparAsiento( Tripla<String,Viajero, Integer> tupla){
        try {
            oficina.ocuparAsiento(tupla.a,tupla.b,tupla.c);
        } catch (Exception ex) {
            vista.ponerMensaje(ERROR_OCUPAR_ASIENTO);
        }
    }
    
     /*
     * Desocupa un asiento
     */
    private void desocuparAsiento(Tupla<String,String> tupla){
        try {
            oficina.desocuparAsiento(tupla.a, tupla.b);
        } catch (Exception ex) {
            System.out.println(ex);
            vista.ponerMensaje(ERROR_DESOCUPAR_ASIENTO);
        }
    }
    
   /*
    *  Método main
    */ 
    public static void main(String[] args){ 
        new Agencia(args);
    }
}
