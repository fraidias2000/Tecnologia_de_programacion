/*
 *David Ros y Alvaro Fraidias
 *Prototipo 4.2
 *02/05/2020 
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

public class AutobusVista extends JPanel {
    private AsientoVista casillas[][];
    private OficinaVista vista ;
    private Asiento asientos[];
    
    private static final int ALTURA_FILA = 300;
    private static final int ANCHURA_COLUMNA = 60;
    public static final boolean RECIBE_EVENTOS_RATON = true;
    public static final boolean NO_RECIBE_EVENTOS_RATON = false;
    
    
    public AutobusVista (OficinaVista vista, boolean recibeEventosRaton){  
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
     casillas = new AsientoVista[filas][columnas];
     for(int i = 0; i < filas; i++) 
     for(int j = 0; j < columnas; j++) {
        casillas[i][j] = new AsientoVista(vista,new Posicion(i, j), i, 
                recibeEventosRaton);
        add(casillas[i][j]);      
    }      
  }  

   /*
   * Inicia vista del plano
   */
public void iniciarVistaPlano (Viaje viaje){
    Autobus autobus = viaje.getAutobus();
    asientos = new Asiento[autobus.AsientosTotales()];
    asientos =  autobus.obtenerAsientos(asientos);
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
}
