/*
 *David Ros y Alvaro Fraidias
 *Prototipo 4.4
 *10/05/2020  
 */

package Vista;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;
import modelo.Asiento;
import modelo.Autobus;
import modelo.Posicion;
import modelo.Viaje;

public class AutobusVista extends JPanel {
    private AsientoVista asientosVista[][];
    private OficinaVista vista ;
    
    private static final int ALTURA_FILA = 300;
    private static final int ANCHURA_COLUMNA = 60;
    public static final boolean RECIBE_EVENTOS_RATON = true;
    public static final boolean NO_RECIBE_EVENTOS_RATON = false;
       
    public AutobusVista (OficinaVista vista, boolean recibeEventosRaton){  
        this.vista = vista;
        crearAsientos(recibeEventosRaton);
        this.setPreferredSize(new Dimension
                                (vista.NUMERO_FILAS * ALTURA_FILA, 
                                 vista.NUMERO_COLUMNAS * ANCHURA_COLUMNA));
    }
    
    /*
     * Crea Asientos
     */
    private void crearAsientos( boolean recibeEventosRaton) {
        int filas = vista.NUMERO_FILAS;
        int columnas = vista.NUMERO_COLUMNAS;
        setLayout(new GridLayout(filas, columnas));
        asientosVista = new AsientoVista[filas][columnas];
        for(int i = 0; i < filas; i++) 
            for(int j = 0; j < columnas; j++) {
                asientosVista[i][j] = new AsientoVista
                                          (vista,new Posicion(i, j), i, 
                                           recibeEventosRaton);
                add(asientosVista[i][j]);      
            }      
    }  
   
   /*
   * Inicia vista del plano
   */
    public void iniciarVistaPlano (Viaje viaje){
        Autobus autobus = viaje.getAutobus();
        Asiento asiento;
        for(int i = 0; i < autobus.AsientosTotales(); i++){
            asiento = autobus.obtenerAsiento(i);
            asientosVista[asiento.getFila()]
                         [asiento.getColumna()]
                         .iniciar();
        }
    }
   /*
    * Pone asientos a un autobus de un viaje concreto
    */
    public void ponerAsientos(Viaje viaje){
        Autobus autobus = viaje.getAutobus();
        Asiento asiento;
        iniciarVistaPlano(viaje);
        for(int i = 0; i < autobus.AsientosTotales(); i++){
            asiento = autobus.obtenerAsiento(i);
            asientosVista[asiento.getFila()]
                         [asiento.getColumna()]
                         .ponerNumero(asiento.getNumero());
        }
    }
}