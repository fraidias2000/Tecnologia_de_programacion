/**
 * Ficha.java
 * ccatalan (01/2018) 
 *   
 */

package modelo;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *  Ficha del tablero de juego
 * 
 */
public enum Ficha { 
  VACIA("-"), CRUZ ("X"), CIRCULO("O");
  private final String simbolo;

  /**
   *  Construye una ficha
   * 
   */  
  Ficha(String simbolo) {
    this.simbolo = simbolo;
  }
  
  /**
   *  Devuelve una nueva ficha
   * 
   */   
  public static Ficha nueva(Scanner scanner) throws Exception {
    String s = scanner.next(
      Pattern.compile(VACIA.toString() + "|" +
                      CRUZ.toString() + "|" +
                      CIRCULO.toString()));

    if (s.equals(CRUZ.toString())) {
      return CRUZ;    
    } else if (s.equals(CIRCULO.toString())) {
      return CIRCULO;  
    } else {
      return VACIA;
    }
  }
  
  /**
   *  toString
   *
   */  
  @Override
  public String toString() {
    return simbolo;    
  }
}