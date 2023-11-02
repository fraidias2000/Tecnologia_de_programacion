/*
 *David Ros y Alvaro Fraidias
 *Prototipo 3
 *01/07/2020 
 * 
 */
package modelo;


import Control.Agencia;
import Vista.DebugVista;
import Vista.OficinaVista;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import modelo.agendasEnlinea.Cliente;
import modelo.agendasEnlinea.OyenteServidor;
import modelo.agendasEnlinea.PrimitivaComunicacion;

/*
 * Oficina que contiene viajes.
 */
public class Oficina implements OyenteServidor{
    
    public static String PROPIEDAD_OCUPAR_ASIENTO = "Ocupar";    
    public static String PROPIEDAD_DESOCUPAR_ASIENTO = "Desocupar Asiento"; 
    public static String PROPIEDAD_CONECTADO = "Conectado";
    private Cliente cliente; 
    private boolean conectado;  
    private String idConexion;
    private OyenteServidor oyenteServidor;
    private PropertyChangeSupport observadores;
    
    private static final String ARCHIVO_HOJA_DE_VIAJE =  
                "hojaViaje.txt";
    
    private static String FICHERO_CONFIG_ERRONEO = 
          "Fichero configuración erróneo. Usando parámetros por defecto";
    private Properties configuracion;
    private static final String FICHERO_CONFIG = "config.properties";
    private static String COMENTARIO_CONFIG = 
          Agencia.VERSION + " configuración conexión Servidor"; 
    
    private static final String ERROR_GENERAR_HOJA = 
            "Error al generar la hoja de viaje";
  
    public static final String ID_USUARIO = "idUsuario";
    private String idUsuario = "<ponAquiIdUsuario>";
    public static final String URL_SERVIDOR = "URLServidor";
    private String URLServidor = "<ponAquiURLServidor>";
    public static final String PUERTO_SERVIDOR = "puertoServidor";
    private int puertoServidor = 15000; 

    private static Oficina instancia = null;
  
   public Oficina(){
    oyenteServidor = this;
    conectado = false;
    observadores = new PropertyChangeSupport(this);    
    leerConfiguracion();        
    cliente = new Cliente(URLServidor, puertoServidor);
   }
   
   /*
    * Comprueba que no se ha instanciado ningún objeto Oficina 
    * y si es así, lo instancia.
    */
   public static Oficina instancia(){
        if (instancia == null)
        instancia = new Oficina();
        return instancia;     
    }

   
   /*
   *  Lee configuración de recordatorios
   * 
   */ 
  private void leerConfiguracion() {
    try {
      configuracion = new Properties();
      configuracion.load(new FileInputStream(FICHERO_CONFIG));
      
      idUsuario = configuracion.getProperty(ID_USUARIO);
      URLServidor = configuracion.getProperty(URL_SERVIDOR);
      puertoServidor = 
      Integer.parseInt(configuracion.getProperty(PUERTO_SERVIDOR));
    } catch (Exception e) {
      configuracion.setProperty(ID_USUARIO, idUsuario);
      configuracion.setProperty(URL_SERVIDOR, URLServidor);
      configuracion.setProperty(PUERTO_SERVIDOR, 
                                Integer.toString(puertoServidor));
      
      guardarConfiguracion();
      
      if (Agencia.esModoDebug()) {
        DebugVista.devolverInstancia().mostrar(FICHERO_CONFIG_ERRONEO, e);
      }
    }
  }
  
  /**
   *  Guarda configuración de recordatorios
   * 
   */
  private void guardarConfiguracion() {
    try {
      FileOutputStream fichero = new FileOutputStream(FICHERO_CONFIG);
      configuracion.store(fichero, COMENTARIO_CONFIG);
      fichero.close();
    } catch(IOException e) {
      if (Agencia.esModoDebug()) {
         DebugVista.devolverInstancia()
           .mostrar(OficinaVista.ERROR_CONFIGURACION_NO_GUARDADA, e);
       }        
    }      
  }  
  /*
   *  Obtiene identificador conexión 
   */
  public String obtenerIdentificadorConexion() {
    return conectado ? idConexion : "---";  
  }
 
  /*
   *  Nuevo observador  
   */     
  public void nuevoObservador(PropertyChangeListener observador) {
    this.observadores.addPropertyChangeListener(observador);
  } 
  
  /**
   *  Conecta con servidor mediante long polling
   * 
   */        
  public void conectar() {
    new Thread() {
      @Override
      public void run() { 
        Cliente cliente = new Cliente(URLServidor, puertoServidor);
        
        while(true) { 
          try { 
            cliente.enviarSolicitudLongPolling(
                    PrimitivaComunicacion.CONECTAR_PUSH, 
                    Cliente.TIEMPO_ESPERA_LARGA_ENCUESTA, 
                    idUsuario,  oyenteServidor);    
          } catch (Exception e) {
            conectado = false;  
            observadores.firePropertyChange(
                    PROPIEDAD_CONECTADO, null, conectado);
            
            if (Agencia.esModoDebug()) {
              DebugVista.devolverInstancia()
                    .mostrar(OficinaVista.ERROR_CONEXION_SERVIDOR, e);
            }             
            try { 
              sleep(Cliente.TIEMPO_REINTENTO_CONEXION_SERVIDOR);
            } catch (InterruptedException e2) {
              new RuntimeException();  
            }            
          }
        }
      }
    }.start(); 
  } 
  
  /*
   *  Obtiene un viaje para una fecha
   */
    public List<String> obtenerViajes(GregorianCalendar fecha)
            throws Exception {
        if (!conectado) {
            return null;
        }

        List<String> resultados = new ArrayList<>();

        String parametros = fecha.get(Calendar.DAY_OF_MONTH) + " "
                        + (fecha.get(Calendar.MONTH) + 1) + " "
                        + fecha.get(Calendar.YEAR);

        PrimitivaComunicacion respuesta = cliente.enviarSolicitud(
                        PrimitivaComunicacion.OBTENER_VIAJES,
                        Cliente.TIEMPO_ESPERA_SERVIDOR, parametros, resultados);
        
        return resultados.isEmpty()
            || respuesta.equals(PrimitivaComunicacion.NOK.toString())
            ? null : resultados;    
        
  }
 
   /**
     * Obtiene los asientos de un viaje
     * 
     */   
    public List<String> obtenerAsientos(int codigoViaje) 
                                             throws Exception {
        if ( ! conectado) {
            return null;
        }    
        String parametros = String.valueOf(codigoViaje);       
        List<String> resultados =  new ArrayList<>();
        
        PrimitivaComunicacion respuesta = cliente.enviarSolicitud(
                PrimitivaComunicacion.VER_ASIENTOS, 
                Cliente.TIEMPO_ESPERA_SERVIDOR, 
                parametros, resultados);
        return resultados.isEmpty() || 
                respuesta.equals(PrimitivaComunicacion.NOK.toString()) ? 
                null : resultados;
    }

    /*
     * Ocupa un asiento de un autobus de un viaje
     */
    public void ocuparAsiento(String codigoViaje,Viajero unViajero,
                                 int numeroAsiento ) throws Exception{
        if ( ! conectado) {
            return;
        }
        String parametros =  codigoViaje + " " + 
                             String.valueOf(numeroAsiento) + " " + 
                             unViajero.toString();
            List<String> resultados = new ArrayList<>();
            PrimitivaComunicacion respuesta = cliente.enviarSolicitud(
                        PrimitivaComunicacion.OCUPAR_ASIENTO,
                        Cliente.TIEMPO_ESPERA_SERVIDOR, parametros, resultados);
          
         if (!parametros.isEmpty() || !respuesta.equals(
                PrimitivaComunicacion.NOK.toString())) {
            this.observadores.firePropertyChange(OficinaVista.OCUPAR,
                null, unViajero);
            
        }
    }
    
    /*
     * Desocupa un asiento de un autobus de un viaje
     */
    public void desocuparAsiento(String codigoViaje, String numeroAsiento) 
                                                   throws Exception{
         if ( ! conectado) {
            return;
        }     
        String parametros =  codigoViaje + " " + numeroAsiento;
        
       PrimitivaComunicacion respuesta =
            cliente.enviarSolicitud(PrimitivaComunicacion.DESOCUPAR_ASIENTO, 
            Cliente.TIEMPO_ESPERA_SERVIDOR, 
            parametros);
       
       if (!parametros.isEmpty() || !respuesta.equals(
                PrimitivaComunicacion.NOK.toString())) {
            this.observadores.firePropertyChange(OficinaVista.DESOCUPAR,
                null, null);
            
        }
    }
    
    /*
     * Genera un fichero con todos los datos relacionados con un viaje
     */
    public boolean generarHoja(String codigoViaje)
                                   throws Exception{
       if ( ! conectado) {
            return false;
        }    
        String parametros = codigoViaje;
        List<String> resultados =  new ArrayList<>();
        
        cliente.enviarSolicitud(
                PrimitivaComunicacion.GENERAR_HOJA_VIAJE, 
                Cliente.TIEMPO_ESPERA_SERVIDOR, 
                parametros, resultados);
        if(resultados.equals(PrimitivaComunicacion.OK)){
            return true;
        }
        return false;
    } 
    
     /**
     * Obtiene el viajero de un asiento de un viaje
     * 
     */   
    public String obtenerViajero(int codigoViaje, int numeroAsiento) 
            throws Exception{
        if ( ! conectado) {
            return null;
        }
        
        String parametros = codigoViaje + " " + numeroAsiento;
        List<String> resultados =  new ArrayList<>();       
        PrimitivaComunicacion respuesta = cliente.enviarSolicitud(
                PrimitivaComunicacion.OBTENER_VIAJERO, 
                Cliente.TIEMPO_ESPERA_SERVIDOR, 
                parametros, resultados);
        
        return resultados.isEmpty() || 
                respuesta.equals(PrimitivaComunicacion.NOK.toString()) ? 
                null : resultados.get(0);
    }
    
    
  /*
   *  Recibe solicitud del servidor de nuevo idConexion 
   */
  private boolean solicitudServidorNuevoIdConexion(List<String> resultados) 
          throws IOException {
    idConexion = resultados.get(0);
    if (idConexion == null) {
      return false;
    } 
    conectado = true; 
    
   //Se activa la etiqueta 
    observadores.firePropertyChange(PROPIEDAD_CONECTADO, null, conectado);      
    
    return true;
  }  
    
  /**
   *  Recibe solicitud de servidor de nuevo recordatorio
   * 
   */
  private boolean solicitudServidorOcuparAsiento(String propiedad, 
                                              List<String> resultados)
                                              throws IOException {
    int codigo = Integer.parseInt(resultados.get(0));
    String numeroAsiento = resultados.get(1);
    Viajero viajero = leerViajero(resultados);
    if(viajero != null){
        observadores.firePropertyChange(propiedad, null, 
                                new Tupla(codigo, numeroAsiento)); 
        return true;
    } 
    return false;
  }
  
  private Viajero leerViajero(List<String> resultados){
      Scanner scanner = new Scanner(resultados.get(2));
      String nombre;
      String dni;
      dni = scanner.next();
      nombre = scanner.next();
      
      return new Viajero(nombre, dni);
  }
  
  /**
   *  Recibe solicitud de servidor de eliminar recordatorio
   * 
   */
  private boolean solicitudServidorDesocuparAsiento(String propiedad, 
                                     List<String> resultados) 
                                     throws IOException {
    int codigo = Integer.parseInt(resultados.get(0));       
    int numeroAsiento = Integer.parseInt(resultados.get(1));
    
    if (resultados == null) {
      return false;
    }
    
    observadores.firePropertyChange(propiedad, null, 
            new Tupla(codigo, numeroAsiento));
    
    return true;
  }  
  
  
   /**
   *  Recibe solicitudes servidor agenda en línea
   *
   */
    @Override
   public boolean solicitudServidorProducida
                    (PrimitivaComunicacion solicitud, 
                     List<String> resultados) 
                     throws IOException {
        if (resultados.isEmpty()) {
            return false;
        } 
      
        switch(solicitud) {
            case NUEVO_ID_CONEXION:
                return solicitudServidorNuevoIdConexion(resultados);
        
            case OCUPAR_ASIENTO:
                return solicitudServidorOcuparAsiento(
                    PROPIEDAD_OCUPAR_ASIENTO, 
                    resultados);
        
            case DESOCUPAR_ASIENTO:
                return solicitudServidorDesocuparAsiento(
                    PROPIEDAD_DESOCUPAR_ASIENTO, 
                    resultados);
        
            default:
                return false;
        }
    }
}