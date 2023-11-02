/**
 * PartidaNEnRaya.java
 * ccatalan (02/2019) 
 *   
 */

package control;


import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import modelo.TableroNEnRaya;
import modelo.Posicion;
import modelo.Ficha;
import vista.JuegoVista;

/**
 *  Partida de juego de tablero de n en raya
 * 
 */
class PartidaNEnRaya {
  enum ResultadoJugada {VALIDA, INVALIDA, HAY_RAYA, HAY_EMPATE};
  private TableroNEnRaya tablero;
  private List<ReglaJugada> reglasJugada;
  private Ficha turno;
  private boolean finalizada = false;
  private boolean guardada;
  
  /**
   *  Construye una partida 
   * 
   */  
  PartidaNEnRaya(JuegoVista vista, List<ReglaJugada> reglasJugada, 
                 int filas, int columnas, int n) { 
    this.reglasJugada = reglasJugada;
    
    tablero = new TableroNEnRaya(filas, columnas, n);
    turno = Ficha.CIRCULO;
    
    guardada = false;
    
    tablero.nuevoObservador(vista);    
  }

  /**
   *  Construye una partida de un fichero
   * 
   */   
  PartidaNEnRaya(JuegoVista vista, List<ReglaJugada> reglasJugada, 
                 String nombreFichero) throws Exception {  
    this.reglasJugada = reglasJugada;
       
    recuperar(nombreFichero);
    
    guardada = true;
    
    tablero.nuevoObservador(vista);
    vista.recuperarPartida(tablero);     
  }
  
  /**
   *  Devuelve ficha que tiene turno
   * 
   */    
  Ficha devuelveTurno() {
    return turno;  
  }  
  
  /**
   *  Devuelve si partida está finalizada
   * 
   */    
  boolean finalizada() {
    return finalizada;
  }  
  
  /**
   *  Devuelve si partida está guardada
   * 
   */    
  boolean guardada() {
    return guardada;
  }  
  
  /**
   *  Devuelve verdadero si jugada es válida o falso en caso contrario
   * 
   */  
  private boolean esJugadaValida(Posicion posicion) {
    if (finalizada) {
      return false;        
    }  
    for(ReglaJugada reglaJugada: reglasJugada) {
      if (! reglaJugada.seCumple(tablero, posicion)) {
        return false;
      }
    } 
    return true;    
  }   
  
  /**
   *  Pone siguiente ficha del siguiente turno
   * 
   */   
  private void siguienteTurno() {
    if (turno == Ficha.CIRCULO) {
      turno = Ficha.CRUZ;    
    } else {
      turno = Ficha.CIRCULO;
    }
    // o también
    //turno = (turno == Ficha.CIRCULO ? Ficha.CRUZ : Ficha.CIRCULO);
  }  
  
  /**
   *  Pone una ficha del tablero.
   *  Devuelve HAY_LINEA_O_DIAGONAL, HAY_EMPATE, JUGADA_INVALIDA o JUGADA_VALIDA
   *  
   */ 
  ResultadoJugada ponerFicha(Posicion posicion) {
    if ( ! esJugadaValida(posicion)) {
      return ResultadoJugada.INVALIDA;
    }
        
    tablero.ponerFicha(turno, posicion);
      
    if (tablero.hayRaya(posicion)) {
      finalizada = true;
      return ResultadoJugada.HAY_RAYA;
    }
    else if (tablero.hayEmpate()) {
      finalizada = true;
      return ResultadoJugada.HAY_EMPATE;
    }
    siguienteTurno();
    
    guardada = false;

    return ResultadoJugada.VALIDA;
  }
  
  /**
   *  Guarda en fichero la partida actual
   * 
   */   
  void guardar(String nombreFichero) throws Exception {
    PrintWriter fichero = 
      new PrintWriter(new BufferedWriter(new FileWriter(nombreFichero)));    
    
    fichero.println(finalizada);
    fichero.println(turno.toString());
    
    tablero.guardar(fichero);    
    fichero.close();
    guardada = true;
  }
  
  /**
   *  Recupera una partida de fichero
   * 
   */   
  private void recuperar(String nombreFichero) throws Exception {
    Scanner fichero = new Scanner(new FileInputStream(nombreFichero));
    if (! fichero.hasNextBoolean()) {
      throw new Exception();
    }
    finalizada = fichero.nextBoolean();    
    turno = Ficha.nueva(fichero);

    tablero = new TableroNEnRaya(fichero);
    fichero.close();
  }
  
  /**
   *  toString
   *
   */ 
  @Override
  public String toString() {
    return tablero.toString() + turno.toString();
  }   
}
