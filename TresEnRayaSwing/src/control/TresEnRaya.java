/**
 * TresEnRaya.java
 * 
 * Versión 0 ccatalan (01/2018)
 *  - Esqueleto de código para jugar, guardar y recuperar partida
 *
 * Versión 1.0 ccatalan (01/2018)
 *  - Permite jugar una partida
 *
 * Versión 1.1 ccatalan (01/2018)
 *  - Añadido guardar y recuperar partida en fichero binario
 *
 * Versión 1.1.1 ccatalan (01/2018)
 *  - Modificado guardar y recuperar partida, ahora en fichero de texto
 *  - Modo debug
 * 
 * Versión 1.2 ccatalan (02/2019)
 *  - Cambios legibilidad
 *  - Comprobación de líneas o diagonales de n fichas para tablero de f * c
 *  - Reglas es List
 *  - Regla es interface
 *  - Procesa argumentos de main: "-d" (debug) 
 *  - Métodos toString, equals y hashcode
 *
 * Versión 2.0 ccatalan (02/2019)
 *  - Arquitectura MVC, paquetes de modelo, vista y control
 *  - Vista interfaz gráfica de ventanas con Java Swing
 * 
 * Version 2.0.0.1 ccatalan (02/2020)
 *  - Sustituido Observer y Observable por PropertyChangeSupport y
 *    PropertyChangeListener
 */ 

package control;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import modelo.Posicion;
import vista.JuegoVista;

/**
 *  Juego Tres en raya
 * 
 */
public class TresEnRaya implements OyenteVista {
  public static String VERSION = "Tres en raya 2.0.0.1";
  
  private static String ARG_DEBUG = "-d";
  private static int FILAS = 3;
  private static int COLUMNAS = 3;
  private static int FICHAS_EN_LINEA_O_DIAGONAL = 3;
  
  private PartidaNEnRaya partida;
  private List<ReglaJugada> reglasJugada;
  
  private JuegoVista vista;
  private String ficheroPartida = null;

  private static boolean modoDebug = false;
  
  /**
   *  Construye juego tres en raya
   * 
   */
  public TresEnRaya(String args[]) {
     procesarArgsMain(args);      
     
     reglasJugada = new ArrayList<>();
     reglasJugada.add(new PosicionEstaLibre());
     reglasJugada.add(new PosicionEstaDentroTablero());
  
     vista = JuegoVista.devolverInstancia(this, VERSION, FILAS, COLUMNAS);
  }
  
  /**
   *  Empieza nueva partida
   * 
   */ 
  private void nuevaPartida() { 
    guardarPartidaActual();
    ficheroPartida = null;
    vista.ponerTitulo("");      

    partida = new PartidaNEnRaya(vista, reglasJugada, 
                          FILAS, COLUMNAS, 
                          FICHAS_EN_LINEA_O_DIAGONAL);  

    vista.habilitarEvento(Evento.GUARDAR, false);
    vista.habilitarEvento(Evento.GUARDAR_COMO, false);   
    vista.habilitarEvento(Evento.PONER_FICHA, true);
    
    vista.mensajeLineaEstado(vista.TURNO + partida.devuelveTurno().toString());     
  }
  
  /**
   *  Abre partida
   * 
   */      
  private void abrirPartida() {
     guardarPartidaActual();
     ficheroPartida = vista.seleccionarFichero(JuegoVista.ABRIR_FICHERO);
     if (ficheroPartida != null) {
         try {
             partida = new PartidaNEnRaya(vista, reglasJugada, ficheroPartida);
             vista.habilitarEvento(Evento.GUARDAR, false); 
             vista.habilitarEvento(Evento.PONER_FICHA, true);    
             vista.mensajeLineaEstado(vista.TURNO + 
                                      partida.devuelveTurno().toString());        
         } catch(FileNotFoundException e1) {
           mensajeError(vista.FICHERO_NO_ENCONTRADO, e1);
         } catch(Exception e2) {
           mensajeError(vista.PARTIDA_NO_LEIDA, e2);
         }
     }
  }  
  
  /**
   *  Guarda partida
   * 
   */
  private void guardarPartida() {      
     if (ficheroPartida == null) {
       guardarPartidaComo();
     } else {
       try {
         partida.guardar(ficheroPartida);
         vista.habilitarEvento(Evento.GUARDAR, false);         
       } catch(Exception e) {
         mensajeError(vista.PARTIDA_NO_GUARDADA, e);
       }
     }
  }  
  
  /**
   *  Guarda partida como
   * 
   */      
  private void guardarPartidaComo() {
     ficheroPartida = vista.seleccionarFichero(JuegoVista.GUARDAR_FICHERO);
     if (ficheroPartida != null) { 
       guardarPartida();
     }  
  }  

  /**
   *  Guarda partida actual
   * 
   */        
  private void guardarPartidaActual() {
    if ((partida != null) && (! partida.guardada())) {
      if (vista.mensajeConfirmacion(
            vista.CONFIRMACION_GUARDAR) == vista.OPCION_SI) {               
        guardarPartida();
      }
    }      
  }
  
  /**
   *  Salir del programa
   * 
   */      
  private void salir() {
    guardarPartidaActual();
    System.exit(0);    
  }  
  
  /**
   * Pone ficha en tablero de juego
   * 
   */
  private void ponerFicha(Posicion posicion) { 
    PartidaNEnRaya.ResultadoJugada resultado;
    
    if (partida != null) {
      resultado = partida.ponerFicha(posicion);
      
      if (resultado == PartidaNEnRaya.ResultadoJugada.HAY_RAYA) {
        vista.mensajeDialogo(vista.PARTIDA_FIN_TRES_EN_RAYA);
        vista.habilitarEvento(Evento.PONER_FICHA, false);        
      } else if (resultado == PartidaNEnRaya.ResultadoJugada.HAY_EMPATE) {
        vista.mensajeDialogo(vista.PARTIDA_FIN_EMPATE);
        vista.habilitarEvento(Evento.PONER_FICHA, false);        
      } else if (resultado == PartidaNEnRaya.ResultadoJugada.VALIDA) {
        vista.mensajeLineaEstado(vista.TURNO + partida.devuelveTurno().toString());  
      }  
    }  
  }
  
  /**
   *  toString()
   *
   */
  @Override
  public String toString() {
    return partida.toString() + "\n";    
  }

  /**
   *  Recibe eventos de vista
   *
   */  
  @Override
  public void eventoProducido(Evento evento, Object obj) {
     switch(evento) {
         case NUEVA:
             nuevaPartida();
             break;

         case ABRIR: 
             abrirPartida();  
             break;             
             
         case SALIR: 
             salir();
             break;
         
         case GUARDAR: 
             guardarPartida();
             break;            

         case GUARDAR_COMO: 
             guardarPartidaComo();
             break;            
                        
         case PONER_FICHA:
             ponerFicha((Posicion)obj);
             break;
     }
  }  
  
  /**
   * Escribe mensaje error
   * 
   */
  private void mensajeError(String mensaje, Exception e) {
    if (esModoDebug()) {
      e.printStackTrace();             
    }
    vista.mensajeDialogo(mensaje);    
  }
  
  /**
   *  Indica si aplicación está en modo debug
   * 
   */ 
  public static boolean esModoDebug() {
    return modoDebug;
  }

  /**
   *  Procesa argumentos de main
   * 
   */  
  public static void procesarArgsMain(String[] args) {
    List<String> argumentos = new ArrayList<String>(Arrays.asList(args));  
    
    if (argumentos.contains(ARG_DEBUG)) {
      modoDebug = true;    
    }
  }  
  
  /**
   *  Método main
   * 
   */   
  public static void main(String[] args) {
    new TresEnRaya(args);
  }  
}
