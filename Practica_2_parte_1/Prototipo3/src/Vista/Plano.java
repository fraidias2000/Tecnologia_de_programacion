/*
 *David Ros y Alvaro Fraidias
 *Prototipo 3
 *04/04/2020 
 * 
 */

package Vista;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JPanel;
import modelo.Posicion;

public class Plano extends JPanel {
    private Casilla casillas[][];
    private static final int ALTURA_FILA = 300;
    private static final int ANCHURA_COLUMNA = 60;
    private OficinaVista vista ;
    
    
    public Plano (OficinaVista vista){  
        this.vista = vista;
        crearCasillas();
        this.setPreferredSize(new Dimension
                                (vista.NUMERO_FILAS * ALTURA_FILA, 
                                 vista.NUMERO_COLUMNAS * ANCHURA_COLUMNA));
    }
    
    /**
   * Crea casillas
   */
   private void crearCasillas() {
     int filas = vista.NUMERO_FILAS;
     int columnas = vista.NUMERO_COLUMNAS;
     setLayout(new GridLayout(filas, columnas));
     casillas = new Casilla[filas][columnas];
     for(int i = 0; i < filas; i++) 
     for(int j = 0; j < columnas; j++) {
        casillas[i][j] = new Casilla(new Posicion(i, j), i);
        add(casillas[i][j]);      
    }      
  }  
  
  /**
   * Pone un icono en la casilla de la posición indicada
   * 
   */   
  void ponerNumeroCasilla(Posicion posicion, Icon icono) {     
    casillas[posicion.devolverFila()][posicion.devolverColumna()]
            .ponerIcono(icono);
  }

   
  /**
   * Devuelve el tamaño del tablero
   * 
   */  
  Dimension dimensionCasilla() {
    return casillas[0][0].getSize();
  }

  /**
   * Habilita o deshabilita tablero vista
   * 
   */    
  void habilitar(boolean habilitacion) {
    this.setEnabled(habilitacion);  
    for(int fil = 0; fil < casillas.length; fil++) {
      for(int col = 0; col < casillas[0].length; col++) {
        casillas[fil][col].habilitar(habilitacion);     
      }
    }
  }
}
