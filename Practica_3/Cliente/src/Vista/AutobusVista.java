/*
 *David Ros y Alvaro Fraidias
 *Prototipo 3
 *01/07/2020 
 * 
 */

package Vista;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.swing.JPanel;
import modelo.Asiento;
import modelo.Posicion;
import modelo.Sixtupla;
import modelo.Viajero;

public class AutobusVista extends JPanel {
    private AsientoVista asientosVista[][];
    private OficinaVista vista ;
    
    private static final int ALTURA_FILA = 300;
    private static final int ANCHURA_COLUMNA = 60;
    public static final boolean RECIBE_EVENTOS_RATON = true;
    public static final boolean NO_RECIBE_EVENTOS_RATON = false;
    private static final int LIBRE = 0;
    private static final int OCUPADO = 1;
       
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
    public void iniciarVistaPlano (){
        int filas = vista.NUMERO_FILAS ;
        int columnas = vista.NUMERO_COLUMNAS;
      for(int i = 0; i < filas; i++){
            for(int j = 0; j < columnas; j++){
            asientosVista[i][j].iniciar();
            }
        }
    }

   /*
    * Pone asientos a un autobus de un viaje concreto
    */
    public void ponerAsientos(List<String>  asientos){
       iniciarVistaPlano();
       for(int i = 0; i < asientos.size()-1; i++){ 
           Sixtupla auxiliar = parsearAsiento(asientos.get(i));
           if (((int)auxiliar.d == LIBRE) || ((int)auxiliar.d ==OCUPADO)){
               asientosVista[(int)auxiliar.b][(int)auxiliar.c].ponerNumero((int) auxiliar.a);
           }
           if((int)auxiliar.d == OCUPADO){
               Viajero viajero = new Viajero((String) auxiliar.e, (String) auxiliar.f);
               asientosVista[(int)auxiliar.b][(int)auxiliar.c].ocuparAsiento(viajero);
           
           }
        }
    } 
    
    private Sixtupla<Integer,Integer, Integer, String, String, String> parsearAsiento(String linea){    
        Scanner scaner = new Scanner(linea);
        int numero;
        int fila;
        int columna;
        int estado;
        String nombre;
        String dni;
        
        numero = scaner.nextInt();
        fila = scaner.nextInt();
        columna = scaner.nextInt();
        estado = scaner.nextInt();
        if(estado == OCUPADO){
            dni = scaner.next();
            nombre = scaner.next();
            return new Sixtupla (numero, fila, columna, estado, dni, nombre);
        }
        return new Sixtupla (numero, fila, columna, estado, null, null);
    }
}