/*
 *David Ros y Alvaro Fraidias
 *Prototipo 3
 *01/07/2020 
 * 
 */ 

package control;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import modelo.Asiento;
import modelo.Oficina;
import modelo.Viaje;
import modelo.Viajero;

/**
 * Servidor de oficinas
 * 
 */
public class ServidorConexiones extends Thread {
    private ServidorConexiones servidorConexiones;

    public static String VERSION = "Conexion Server 1.0.2";
    private static String ARG_DEBUG = "-d";

    private static String FICHERO_CONFIG_ERRONEO = 
            "Configuración de fichero erronea. "
            + "Se establecen valores por defecto";
    private static String ESPERANDO_SOLICITUD_VIAJE = 
            "Esperando solicitud viaje...";
    private static String ERROR_EJECUCION_SERVIDOR = 
            "Error: ejecucion servidor ";   
    private static String ERROR_CREANDO_CONEXION_VIAJE = 
            "Error creando conexion";

    private static int TIEMPO_TEST_CONEXIONES = 10 * 1000;
    public static int TIEMPO_ESPERA_CLIENTE = 1000;   

    private static boolean modoDebug = false;

    private Map<String, ConexionPushOficina> conexionesPushOficinas;
    private int numConexion = 0;

    /* Configuración */  
    private Properties propiedades; 
    private static final String FICHERO_CONFIG = "config.properties";

    private static final String NUM_THREADS = "threadsNumber";
    private int numThreads = 16;
    private static final String PUERTO_SERVIDOR = "serverPort";
    private int puertoServidor = 15000;
    
    Oficina oficina;
      
    public ServidorConexiones() {
        servidorConexiones = this;
        conexionesPushOficinas = new ConcurrentHashMap<>();

        leerConfiguracion();
        
        oficina = new Oficina();

        envioTestPeriodicosConexionesPushOficinas();

        start();
    }
  
    /**
     * Indica si aplicación está en modo debug
     * 
     */  
    public static boolean esModoDebug() {
        return modoDebug;
    }
  
    /**
     * Lee configuración
     * 
     */ 
    private void leerConfiguracion() {
        try {
            propiedades = new Properties();
            propiedades.load(new FileInputStream(FICHERO_CONFIG));

            numThreads = Integer.parseInt(propiedades.getProperty
                                                        (NUM_THREADS));
            puertoServidor = Integer.parseInt(propiedades.getProperty(
                    PUERTO_SERVIDOR));
        } 
        catch (Exception e) {
            System.out.println(FICHERO_CONFIG_ERRONEO);
            System.out.println(NUM_THREADS + " = " + numThreads);
            System.out.println(PUERTO_SERVIDOR + " = " + puertoServidor);

            if (esModoDebug()) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Ejecuta bucle espera conexiones
     * 
     */   
    @Override
    public void run() { 
        System.out.println(VERSION);  
        try {
            ExecutorService poolThreads = Executors.newFixedThreadPool(
                    numThreads);

            ServerSocket serverSocket = new ServerSocket(puertoServidor);

            while(true) {
                System.out.println(ESPERANDO_SOLICITUD_VIAJE);  

                Socket socket = serverSocket.accept();
                poolThreads.execute(new ServidorConexion(this, socket));
            }
        } 
        catch (BindException e) {
            System.out.println(ERROR_EJECUCION_SERVIDOR + puertoServidor);
            if (esModoDebug()) {
                e.printStackTrace();
            }
        } 
        catch (IOException e) {
            System.out.println(ERROR_CREANDO_CONEXION_VIAJE);
            if (esModoDebug()) {
                e.printStackTrace();
            }
        }          
    }
   
    /**
     * Envía tests periódicos para mantener lista conexiones push 
     * con oficinas
     *  
     */
    private void envioTestPeriodicosConexionesPushOficinas() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for(ConexionPushOficina conexionPushOficina : 
                        conexionesPushOficinas.values()) {
                    try {
                        conexionPushOficina.enviarSolicitud(
                            PrimitivaComunicacion.TEST, 
                            TIEMPO_TEST_CONEXIONES);                       
                    } 
                    catch (IOException e1) {
                        System.out.println(ServidorConexion.
                                ERROR_CONEXION_CLIENTE + " " + 
                                conexionPushOficina.toString());
                        
                        conexionesPushOficinas.remove(
                               conexionPushOficina.obtenerIdConexion());
                        try {
                            conexionPushOficina.cerrar();
                        } 
                        catch (IOException e2) {
                            // No hacemos nada, ya hemos cerrado conexión 
                        } 
                        if (ServidorConexiones.esModoDebug()) {
                            e1.printStackTrace();
                        } 
                    }
                }
            }          
        }, TIEMPO_TEST_CONEXIONES, TIEMPO_TEST_CONEXIONES);              
    }  
  
    /**
     * Genera nuevo identificador de conexión push oficina de viajes
     * 
     */
    synchronized String generarIdConexionPushOficina() {      
        return String.valueOf(++numConexion); 
    }
    
    /**
     * Obtiene conexión push oficina por id conexión
     * 
     */
    ConexionPushOficina obtenerConexionPushOficina(String idConexion) {
        ConexionPushOficina conexionPushOficina = 
                conexionesPushOficinas.get(idConexion);

        if (conexionPushOficina != null) {
            return conexionPushOficina;
        }

        return null;
    }
  
    /**
     * Nueva conexión push de oficina de viajes
     * 
     */   
    synchronized void nuevaConexionPushOficina(
            ConexionPushOficina conexionPushOficina) throws Exception {
        conexionesPushOficinas.put(conexionPushOficina.obtenerIdConexion(), 
                conexionPushOficina); 
    }
  
    /**
     * Elimina conexión push de oficina de viajes
     * 
     */   
    synchronized boolean eliminarConexionPushOficina(String idConexion) 
            throws IOException {
        ConexionPushOficina conexionPushOficina = conexionesPushOficinas.
                                                    get(idConexion);

        if (conexionPushOficina == null) {
          return false;
        }            

        conexionPushOficina.cerrar();
        conexionesPushOficinas.remove(idConexion); 

        return true;
    }
  
    /**
     * Obtiene los viajes de una fecha concreta
     * 
     */ 
    synchronized List<String> buscarViajes(GregorianCalendar fecha) {
        return oficina.obtenerViajes(fecha);
    }
    
    /**
     * Obtiene los asientos de un viaje
     * 
     */ 
    synchronized String verAsientos(int codigoViaje) {
        return oficina.obtenerAsientosViaje(codigoViaje);
    }
    
    /**
     * Notifica cambio en un viaje al resto de oficinas con el mismo id
     * 
     */ 
    private void notificiarOficinasPush(
            PrimitivaComunicacion primitivaComunicacion, String parametros)
            throws IOException {
        for(ConexionPushOficina conexionPushOficina : 
                                        conexionesPushOficinas.values()) { 
                conexionPushOficina.enviarSolicitud(primitivaComunicacion, 
                    TIEMPO_ESPERA_CLIENTE, parametros);        
            
        }
    }
    
    /**
     * Ocupa asiento y notifica al resto de usuarios
     * 
     */
    synchronized boolean ocuparAsiento(int codigoViaje, Viajero viajero, 
                                       int numeroAsiento) 
                                       throws IOException {
        if (! oficina.ocuparAsiento(codigoViaje, viajero, numeroAsiento)){
            return false;
        }
        
        String resultados =servidorConexiones.verAsientos(codigoViaje);
        
        notificiarOficinasPush(PrimitivaComunicacion.OCUPAR_ASIENTO, resultados);
        return true;
    }
    
    /**
     * Desocupa asiento y notifica al resto de usuarios
     *
     */
   synchronized boolean desocuparAsiento(int codigoViaje,int numeroAsiento) 
                                            throws IOException {
        if (! oficina.desocuparAsiento(codigoViaje,numeroAsiento)) {
            return false;
        }     
        String resultados = codigoViaje + " " + numeroAsiento;       
        notificiarOficinasPush( 
                PrimitivaComunicacion.DESOCUPAR_ASIENTO, resultados);
        return true;
    }
    
    /**
     * Genera hoja de viaje 
     * 
     */ 
    synchronized boolean generarHojaViaje(int codigoViaje) throws IOException {
        return oficina.generarHoja(codigoViaje);
    }
    
    /**
     * Obtiene el viajero de un asiento
     * 
     */ 
    synchronized Viajero obtenerViajero(int codigoViaje,int numeroAsiento){
        return oficina.obtenerViajero(codigoViaje,numeroAsiento);
    }
  
    /**
     * Procesa argumentos de main
     * 
     */  
   private static void procesarArgsMain(String[] args) {
      List<String> argumentos = new ArrayList<String>(Arrays.asList(args));  

      if (argumentos.contains(ARG_DEBUG)) {
            modoDebug = true;    
      }
   }
  
    /**
     * Método main
     * 
     */ 
    public static void main(String args[]) { 
        procesarArgsMain(args);  

        new ServidorConexiones();
    }
}
