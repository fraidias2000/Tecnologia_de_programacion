/*
 *David Ros y Alvaro Fraidias
 *Prototipo 3
 *01/07/2020 
 * 
 */
package modelo;

import java.beans.PropertyChangeSupport;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Oficina que contiene viajes.
 */
public class Oficina {

    private Map<Integer, Viaje> viajes;
    private PropertyChangeSupport observadores;
    private static Oficina instancia = null;
    private final String FICHEROSALIDA = "HojaViaje.txt";
    private final String CODIGO_VIAJE = "Codigo Viaje: ";
    private final String FICHERO_VIAJES = "datos_viajes.txt";
    private final String FICHERO_AUTOBUS = "autobus.txt";

    public Oficina() {
        observadores = new PropertyChangeSupport(this);
        viajes = new HashMap<Integer, Viaje>();
        recuperarFicheroViajes(FICHERO_VIAJES, FICHERO_AUTOBUS);
    }

    /*
    * Comprueba que no se ha instanciado ningún objeto Oficina 
    *y si es así, lo instancia.
     */
    public static Oficina instancia()
            throws FileNotFoundException,
            IOException {
        if (instancia == null) {
            instancia = new Oficina();
        }
        return instancia;
    }

    /*
     * Ocupa un asiento de un autobus de un viaje
     */
    public boolean ocuparAsiento(int codigoViaje, Viajero unViajero,
            int numeroAsiento) {
        Viaje viaje = viajes.get(codigoViaje);
        if (viaje != null) {
            viaje.ocuparAsiento(numeroAsiento, unViajero);
            return true;
        }
        return false;
    }

    /*
     * Desocupa un asiento de un autobus de un viaje
     */
    public boolean desocuparAsiento(int codigoViaje, int numeroAsiento) {
        Viaje viaje = viajes.get(codigoViaje);
        if (viaje != null) {
            viaje.desocuparAsiento(numeroAsiento);
            return true;
        }
        return false;
    }

    /*
     * Genera un fichero con todos los datos relacionados con un viaje
     */
    public boolean generarHoja(int codigoViaje) throws IOException {
         String sFichero = FICHEROSALIDA ;
        File fichero = new File(sFichero);
        BufferedWriter hojaViaje = new BufferedWriter
                                       (new FileWriter(sFichero));
        Viaje viaje = viajes.get(codigoViaje);
        if(viaje != null){  
            if (fichero.exists()) {
                viaje.generarHojaViaje(hojaViaje);
                hojaViaje.close();
                return true;
            }      
        }
        return false;
    }

    /*
     * Lee de los ficheros pasados como parametros e inicializa los
     * viajes y las posiciones de los asientos en los autobuses
     */
    public void recuperarFicheroViajes(String ficheroViajes, String ficheroAutobuses) {
        try {
            Scanner fviajes = new Scanner(new File(ficheroViajes));
            while (fviajes.hasNextLine()) {
                Viaje viaje = new Viaje(fviajes, ficheroAutobuses, observadores);
                viajes.put(viaje.getCodigo(), viaje);
            }
            fviajes.close();

        } catch (Exception ex) {
            Logger.getLogger(Oficina.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
    * Devuelve una cadena con toda la informacion sobre los asientos
    * ocupados y libres de todos los viajes 
     */
    public String obtenerEstadoOcupacion() {
        String cadena = "";
        for (int i = 0; i < viajes.size(); i++) {
            cadena = cadena + CODIGO_VIAJE
                    + viajes.get(i).getCodigo()
                    + viajes.get(i).obtenerEstadoOcupacion();
        }
        return cadena;
    }

    /**
     * Obtiene un viaje para una fecha
     */
    public List<String> obtenerViajes(GregorianCalendar fecha) {
        List<String> listaViajes = new ArrayList();
        for (Viaje viaje : viajes.values()) {
            if (viaje.existe(fecha)) {
                listaViajes.add(viaje.obtenerDatos());
            }
        }
        return listaViajes;
    }

    /**
     * Obtiene los asientos de un viaje
     */
    public String obtenerAsientosViaje(int codigoViaje) {
        String asientosTotales = " ";
        Autobus autobus = viajes.get(codigoViaje).getAutobus();
        for (int i = 0; i < autobus.AsientosTotales(); i++) {
             asientosTotales += autobus.getDatosAsiento(i) + "\n";           
        }
        return asientosTotales;
    }

    /**
     * Obtiene un viajero
     */
    public Viajero obtenerViajero(int codigoViaje, int numeroAsiento) {
        return viajes.get(codigoViaje).obtenerViajero(numeroAsiento);
    }

    /**
     * Comprueba si existe viaje
     */
    public boolean existeViaje(int codigoViaje) {
        Viaje viaje = viajes.get(codigoViaje);
        if (viaje == null) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return viajes.toString();
    }
}
