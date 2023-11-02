/**
 * TableroNEnRaya.java
 * ccatalan (02/2020) 
 *   
 */

package modelo;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *  Tablero de juego de n en raya
 * 
 */
public class TableroNEnRaya {
  private Ficha fichas[][];
  private int numFichas;
  private int numFichasRaya;
  private PropertyChangeSupport observadores;
  public static String NOMBRE_PROPIEDAD_FICHA = "ficha";
  
 /**
  *  Construye el tablero de juego
  *  
  */   
  public TableroNEnRaya(int filas, int columnas, int n) {
    observadores = new PropertyChangeSupport(this);

    fichas = new Ficha[filas][columnas];
    this.numFichasRaya = n;
    inicializar();
  }
  
 /**
  *  Construye el tablero de juego de un fichero
  *  
  */   
  public TableroNEnRaya(Scanner scanner) throws Exception {
    observadores = new PropertyChangeSupport(this);  
    
    fichas = new Ficha[scanner.nextInt()][scanner.nextInt()];
    numFichasRaya = scanner.nextInt();
    
    for (int f = 0; f < fichas.length; f++) {
      for (int c = 0; c < fichas[0].length; c++) {
        Ficha ficha = Ficha.nueva(scanner);
        fichas[f][c] = ficha;
        if (ficha != Ficha.VACIA) {
          numFichas++;    
        }
      }
    }
  }  

 /**
  *  Añade observador del tablero
  *  
  */     
  public void nuevoObservador(PropertyChangeListener observador) {
    this.observadores.addPropertyChangeListener(observador);
  }

 /**
  *  Inicia tablero con posiciones vacías
  *  
  */  
  private void inicializar() {
    for (int f = 0; f < fichas.length; f++) {
      for (int c = 0; c < fichas[0].length; c++) {
        fichas[f][c] = Ficha.VACIA;
      }
    }
    numFichas = 0;
  }  

 /**
  *  Guarda tablero en un fichero de texto
  *  
  */  
  public void guardar(PrintWriter pw) {
    pw.println(fichas.length + " " + fichas[0].length + " " + numFichasRaya);
    for (int f = 0; f < fichas.length; f++) {
      for (int c = 0; c < fichas[0].length; c++) {
        pw.print(fichas[f][c] + " ");
      }
      pw.println();
    }
  }  
    
  /**
   *  Devuelve número de filas del tablero
   *  
   */  
  public int devuelveNumFilas() {
    return fichas.length;
  }

  /**
   *  Devuelve número de columnas del tablero
   *  
   */   
  public int devuelveNumColumnas() {
    return fichas[0].length;
  }  
  
  /**
   *  Devuelve verdadero si posición está en tablero, o falso en caso contrario
   *  
   */  
  public boolean posicionEstaDentro(Posicion posicion) {
    int fila = posicion.devolverFila();
    int columna = posicion.devolverColumna();
      
    return (fila >= 0) && (fila < devuelveNumFilas()) && 
           (columna >= 0) && (columna < devuelveNumColumnas()); 
  }
  
  /**
   *  Pone una ficha del tablero
   *  
   */   
  public void ponerFicha(Ficha ficha, Posicion posicion) {
    if (posicionEstaDentro(posicion)) {
      fichas[posicion.devolverFila()][posicion.devolverColumna()] = ficha;
      numFichas++;

      this.observadores.firePropertyChange(NOMBRE_PROPIEDAD_FICHA, null, 
                                           new Tupla<>(ficha, posicion));
    }
  }
  
  /**
   *  Devuelve la ficha del tablero
   *  
   */  
  public Ficha devolverFicha(Posicion posicion) {
    if (posicionEstaDentro(posicion)) {
      return fichas[posicion.devolverFila()][posicion.devolverColumna()];    
    }
    return Ficha.VACIA;
  }
   
  /**
   *  Devuelve verdadero si hay diagonal descendente de n fichas o 
   *  falso en caso contrario
   * 
   */   
  private boolean hayDiagonalDescendente(Posicion posicion) {
    int numFichas = 1;
    int avanceDcha = 1;
    int avanceIzda = -1;
    int avanceAbajo = 1;
    int avanceArriba = -1;
    
    numFichas = cuentaFichasIguales(posicion, avanceDcha, avanceAbajo, numFichas);
    if (numFichas == numFichasRaya) {
      return true;
    } 
   
    numFichas = cuentaFichasIguales(posicion, avanceIzda, avanceArriba, numFichas);
    if (numFichas == numFichasRaya) {
      return true;
    } 
    
    return false; 
  }
  
  /**
   *  Devuelve verdadero si hay diagonal ascendente de n fichas o 
   *  falso en caso contrario
   * 
   */    
  private boolean hayDiagonalAscendente(Posicion posicion) {
    int numFichas = 1;
    int avanceDcha = 1;
    int avanceIzda = -1;
    int avanceAbajo = 1;
    int avanceArriba = -1;
    
    numFichas = cuentaFichasIguales(posicion, avanceDcha, avanceArriba, numFichas);
    if (numFichas == numFichasRaya) {
      return true;
    } 
   
    numFichas = cuentaFichasIguales(posicion, avanceIzda, avanceAbajo, numFichas);
    if (numFichas == numFichasRaya) {
      return true;
    } 
    
    return false;     
  }
      
  /**
   *  Devuelve verdadero si hay línea horizontal de n fichas o 
   *  falso en caso contrario
   * 
   */   
  private boolean hayLineaHorizontal(Posicion posicion) {
    int numFichas = 1;
    int avanceDcha = 1;
    int avanceIzda = -1;
    
    numFichas = cuentaFichasIguales(posicion, avanceDcha, 0, numFichas);
    if (numFichas == numFichasRaya) {
      return true;
    } 
   
    numFichas = cuentaFichasIguales(posicion, avanceIzda, 0, numFichas);
    if (numFichas == numFichasRaya) {
      return true;
    } 
    
    return false;     
    
    // o también
//    int avanceDcha = 1;
//    int avanceIzda = -1;
//    return cuentaFichasIguales(
//             posicion, avanceIzda, 0, 
//             cuentaFichasIguales(posicion, avanceDcha, 0, 1)) == numFichasRaya;
  }
    
  /**
   *  Devuelve verdadero si hay línea vertical de n fichas o 
   *  falso en caso contrario
   * 
   */      
  private boolean hayLineaVertical(Posicion posicion) {
    int numFichas = 1;
    int avanceAbajo = 1;
    int avanceArriba = -1;
    
    numFichas = cuentaFichasIguales(posicion, 0, avanceAbajo, numFichas);
    if (numFichas == numFichasRaya) {
      return true;
    } 
   
    numFichas = cuentaFichasIguales(posicion, 0, avanceArriba, numFichas);
    if (numFichas == numFichasRaya) {
      return true;
    } 

    return false;     
  }
  
  /**
   *  Cuenta número de fichas iguales que hay en un sentido
   * 
   */   
  private int cuentaFichasIguales(Posicion posicion, int avanceHorizontal,
                                  int avanceVertical, int numFichas) {
     Ficha ficha = devolverFicha(posicion);
     int fila = posicion.devolverFila() + avanceVertical;
     int columna = posicion.devolverColumna() + avanceHorizontal;
     
     while (posicionEstaDentro(new Posicion(fila, columna)) &&
            ficha == fichas[fila][columna]) {
       fila = fila + avanceVertical;
       columna = columna + avanceHorizontal;
       numFichas++;  
     }
     
     return numFichas;
  }  
     
  /**
   *  Devuelve verdadero si hay raya o falso en caso contrario
   * 
   */   
  public boolean hayRaya(Posicion posicion) {      
      return hayDiagonalDescendente(posicion) || 
             hayDiagonalAscendente(posicion) ||
             hayLineaHorizontal(posicion) || 
             hayLineaVertical(posicion);
  }
  
  /**
   *  Devuelve si hay empate
   * 
   */   
  public boolean hayEmpate() {
    return (numFichas == fichas.length * fichas[0].length);
  }  
  
  /**
   *  toString
   *
   */ 
  @Override
  public String toString() {
    String s = "";
    for (int f = 0; f < fichas.length; f++) {
      for (int c = 0; c < fichas[0].length; c++) {
        s = s + fichas[f][c].toString() + " ";
      }
      s = s + '\n';
    }
    return s;
  }  
}
