/*
 *David Ros y Alvaro Fraidias
 *Prototipo 4
 *04/04/2020 
 * 
 */

package Vista;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JPanel;
import modelo.Asiento;
import modelo.Autobus;
import modelo.Posicion;
import modelo.Viaje;

public class Plano extends JPanel {
    private Casilla casillas[][];
    private static final int ALTURA_FILA = 300;
    private static final int ANCHURA_COLUMNA = 60;
    private OficinaVista vista ;
    private Asiento asientos[];
    
    public static final boolean RECIBE_EVENTOS_RATON = true;
    public static final boolean NO_RECIBE_EVENTOS_RATON = false;
    
    
    public Plano (OficinaVista vista, boolean recibeEventosRaton){  
        this.vista = vista;
        crearCasillas(recibeEventosRaton);
        this.setPreferredSize(new Dimension
                                (vista.NUMERO_FILAS * ALTURA_FILA, 
                                 vista.NUMERO_COLUMNAS * ANCHURA_COLUMNA));
    }
    
    /**
   * Crea casillas
   */
   private void crearCasillas( boolean recibeEventosRaton) {
     int filas = vista.NUMERO_FILAS;
     int columnas = vista.NUMERO_COLUMNAS;
     setLayout(new GridLayout(filas, columnas));
     casillas = new Casilla[filas][columnas];
     for(int i = 0; i < filas; i++) 
     for(int j = 0; j < columnas; j++) {
        casillas[i][j] = new Casilla(vista,new Posicion(i, j), i, recibeEventosRaton);
        add(casillas[i][j]);      
    }      
  }  

  
public void iniciarPlano (Viaje viaje){
    Autobus autobus = viaje.getAutobus();
    asientos = new Asiento[autobus.AsientosTotales()];
    asientos =  autobus.rellenarVector(asientos);
    for(int i = 0; i < autobus.AsientosTotales(); i++){
           casillas[asientos[i].getFila()]
                   [asientos[i].getColumna()]
                   .ponerNumero(asientos[i].getNumero());
    }
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
  /*void habilitar(boolean habilitacion) {
    this.setEnabled(habilitacion);  
    for(int fil = 0; fil < casillas.length; fil++) {
      for(int col = 0; col < casillas[0].length; col++) {
        casillas[fil][col].habilitar(habilitacion);     
      }
    }
  }*/
}
