/*
 *David Ros y Alvaro Fraidias
 *Prototipo 3
 *01/07/2020 
 * 
 */ 

package control;

import static control.ServidorConexiones.TIEMPO_ESPERA_CLIENTE;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Asiento;
import modelo.Viaje;
import modelo.Viajero;

/**
 * Servidor de una oficina de viajes 
 * 
 */
class ServidorConexion implements Runnable {
    public static String ERROR_CONEXION_CLIENTE = 
            "Conexion con cliente cerrada"; 
    
    private static String FORMATO_FECHA_CONEXION = "kk:mm:ss EEE d MMM yy";

    private ServidorConexiones servidorConexiones;
    private Socket socket;
    private BufferedReader entrada; 
    private PrintWriter salida;

    private static String DEBUG_SOLICITUD = "Solicitud:";
    private static String DEBUG_ERROR_SOLICITUD = 
            DEBUG_SOLICITUD + " formato incorrecto ";
  
    /**
     * Construye el servidor de oficinas de viajes
     * 
     */
    public ServidorConexion(ServidorConexiones servidorConexiones, 
            Socket socket) throws IOException {
        this.servidorConexiones = servidorConexiones;
        this.socket = socket;  

        entrada = new BufferedReader(
        new InputStreamReader(socket.getInputStream()));

        // Autoflush!!
        salida = new PrintWriter(new BufferedWriter(
        new OutputStreamWriter(socket.getOutputStream())), true);  
    }    

    /**
     * Cierra conexión
     * 
     */
    private void cerrarConexion() throws IOException {
        entrada.close();
        salida.close();
        socket.close();      
    }
    
    /**
     * Atiende solicitudes de una oficina de viajes
     * 
     */  
    @Override
    public void run() {
        try {        
            PrimitivaComunicacion solicitud = PrimitivaComunicacion.nueva(
                    new Scanner(new StringReader(entrada.readLine())));

            switch(solicitud) {
                case CONECTAR_PUSH:
                    conectarPushOficina();
                break;

                case DESCONECTAR_PUSH:
                    desconectarPushOficina();
                break;

                case OBTENER_VIAJES:
                    buscarViajes();
                break;   

                case VER_ASIENTOS:
                    verAsientos();
                break;   
                
                case OCUPAR_ASIENTO:
                    ocuparAsiento();
                break;   
                
                case DESOCUPAR_ASIENTO:
                    desocuparAsiento();
                break;   

                case GENERAR_HOJA_VIAJE:
                    generarHojaViaje();
                break;
                
                case OBTENER_VIAJERO:
                    obtenerViajero();
                break;
            }  
        } 
        catch (IOException e) {
            System.out.println(ERROR_CONEXION_CLIENTE + 
                                        ": " + e.toString());

            if (ServidorConexiones.esModoDebug()) {
                e.printStackTrace();
            }
        } 
        catch (InterruptedException e) {
            if (ServidorConexiones.esModoDebug()) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            Logger.getLogger(ServidorConexion.class.getName()).
                                        log(Level.SEVERE, null, ex);
        }
    }
    
    /**
   *  Obtiene fecha de hoy como string
   * 
   */      
    private String obtenerFechaHoy() {
        return new SimpleDateFormat(FORMATO_FECHA_CONEXION, 
                                Locale.getDefault()).format(new Date());
   }
  
    /**
     * Conecta oficina de viajes push
     * 
     */    
    private void conectarPushOficina() throws Exception {     

        CountDownLatch cierreConexion = new CountDownLatch(1);

        String idConexion = servidorConexiones.generarIdConexionPushOficina();

        ConexionPushOficina conexionPushAgencia = 
                new ConexionPushOficina(idConexion, socket, 
                                                cierreConexion); 

        PrimitivaComunicacion respuesta = 
                conexionPushAgencia.enviarSolicitud(
                        PrimitivaComunicacion.NUEVO_ID_CONEXION,
                        TIEMPO_ESPERA_CLIENTE, idConexion);

        if (respuesta.equals(PrimitivaComunicacion.OK)) {    
            servidorConexiones.nuevaConexionPushOficina(
                    conexionPushAgencia);

            // Esperamos hasta cierre conexión push agenda
            cierreConexion.await();
        } 
        else {
            conexionPushAgencia.cerrar();
        }
    }
  
    /**
     * Desconecta oficina de viajes push
     * 
     */    
    private void desconectarPushOficina() throws IOException {
        String idConexion = entrada.readLine();                     

        if (ServidorConexiones.esModoDebug()) {    
            ConexionPushOficina conexionPushOficina = 
                servidorConexiones.obtenerConexionPushOficina(idConexion);

            if (conexionPushOficina != null) {
                System.out.println(DEBUG_SOLICITUD + " " +
                PrimitivaComunicacion.DESCONECTAR_PUSH + " " +
                conexionPushOficina.toString() + " " + obtenerFechaHoy());
            }         
        }

        if (servidorConexiones.eliminarConexionPushOficina(idConexion)) {
            salida.println(PrimitivaComunicacion.OK);
        } 
        else {
            salida.println(PrimitivaComunicacion.NOK);
        }

        cerrarConexion();
    }
  
    /**
     * Parsea fecha de un string
     * 
     */
    private GregorianCalendar parsearFecha(String linea) {
        Scanner scanner = new Scanner(linea);    
   
        try {
            int dia = scanner.nextInt();
            int mes = scanner.nextInt() - 1;  // GC meses de 0 a 11
            int año = scanner.nextInt();
      
        return new GregorianCalendar(año, mes, dia);
        }
        catch(InputMismatchException e) {
            return null;
        }
    }
    
    /**
     * Obtiene los viajes para una fecha
     * 
     */
    private void buscarViajes() throws IOException {   
        GregorianCalendar fecha = parsearFecha(entrada.readLine());
        
        if (fecha != null) {
            List<String> viajes = 
                    servidorConexiones.buscarViajes(fecha);
            
            if (viajes.size() > 0) {
                salida.println(PrimitivaComunicacion.OBTENER_VIAJES);
                
                for(int i = 0; i < viajes.size(); i++) {
                    salida.println(viajes.get(i));
                }
            }
            else {
                salida.println(PrimitivaComunicacion.NOK.toString());
            }
        }
        else {
            salida.println(PrimitivaComunicacion.NOK.toString());
        }
        
        cerrarConexion();
    }
    
    /**
     * Obtiene los asientos de un viaje
     * 
     */
    private void verAsientos() throws IOException {       
        int codigoViaje = Integer.parseInt(entrada.readLine());
        
        String asientos = servidorConexiones.
                verAsientos(codigoViaje);
        
        if (asientos != null) {
            salida.println(PrimitivaComunicacion.VER_ASIENTOS);
                    salida.println(asientos);
                
                 
        }
        else {
            salida.println(PrimitivaComunicacion.NOK);
        }
        cerrarConexion();
    }
    
    /**
     * Ocupa asiento en un viaje
     * 
     */
    private void ocuparAsiento() throws IOException {
        PrimitivaComunicacion respuesta = PrimitivaComunicacion.NOK;

        Scanner scanner = new Scanner(entrada.readLine());
            
        int codigoViaje = scanner.nextInt();
        int numeroAsiento = scanner.nextInt();
        String nombreViajero = scanner.next();
        String dniViajero = scanner.next();
        
        if ((nombreViajero != null) && (dniViajero != null) 
            && (numeroAsiento >= 0) && (codigoViaje >= 0)) {
            Viajero viajero = new Viajero(nombreViajero, dniViajero);
            if (servidorConexiones.ocuparAsiento(codigoViaje, viajero, 
                numeroAsiento)) {
                respuesta = PrimitivaComunicacion.OK;
            }
        }       
        salida.println(respuesta);
        cerrarConexion();
    }
    
    /**
     * Desocupa asiento de un viaje
     * 
     */
    private void desocuparAsiento() throws IOException {
        PrimitivaComunicacion respuesta = PrimitivaComunicacion.NOK;        
        Scanner scanner = new Scanner(entrada.readLine());   
        int codigoViaje = scanner.nextInt();
        int numeroAsiento = scanner.nextInt();
               
        if ((numeroAsiento >= 0) && (codigoViaje >= 0)) {
            if (servidorConexiones.desocuparAsiento(codigoViaje ,
                                                    numeroAsiento)) {
                respuesta = PrimitivaComunicacion.OK;
            }
        }
        salida.println(respuesta);
        cerrarConexion();
    }
    
    /*
     * Obtiene la información y el estado de los asientos de un viaje
     */
    private void generarHojaViaje() throws IOException {   
        String codigoViaje = entrada.readLine();
        
        if (servidorConexiones.generarHojaViaje(Integer.parseInt(codigoViaje))) {
            salida.println(PrimitivaComunicacion.OK);     
        }
        else {
            salida.println(PrimitivaComunicacion.NOK);
        }
        cerrarConexion();
    }
    
    /*
     * Obtiene el viajero de un asiento
     * 
     */
    private void obtenerViajero() throws IOException { 
        String codigoViaje = entrada.readLine();
        int numeroAsiento = Integer.parseInt(entrada.readLine());
        
        
        if (numeroAsiento > 0 && codigoViaje != null) {
            salida.println(PrimitivaComunicacion.OBTENER_VIAJERO);
            salida.println(servidorConexiones.obtenerViajero(numeroAsiento, 
                                            Integer.parseInt(codigoViaje)));
        }
        else {
            salida.println(PrimitivaComunicacion.NOK);
        }
        cerrarConexion();
    }
}
