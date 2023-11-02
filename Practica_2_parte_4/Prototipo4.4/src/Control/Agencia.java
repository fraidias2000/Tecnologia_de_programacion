/*
 *David Ros y Alvaro Fraidias
 *Prototipo 4.4
 *10/05/2020 
 * 
 */
package Control;

import Vista.OficinaVista;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Oficina;
import modelo.Tupla;
import modelo.Viajero;

/*
 * En esta clase se instancian una Oficina y todos los viajeros 
 * que van a realizar un viaje
 */
public class Agencia implements OyenteVista{
    private String ficheroViajes = "viajes.txt";
    private String ficheroAutobuses = "autobus.txt";
    private OficinaVista vista;
    private Oficina oficina;
      
   /*
    * Construye la agencia
    */
    public Agencia(){
        try {
            oficina = new Oficina(ficheroViajes,ficheroAutobuses);
            vista = OficinaVista.devolverInstancia(this,oficina); 
            oficina.nuevoObservador(vista);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Agencia.class.getName()).
                             log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Agencia.class.getName()).
                             log(Level.SEVERE, null, ex);
        }  
    }
    
   /*
   *  Recibe eventos de vista
   */  
    public void eventoProducido(Evento evento, Object obj) {
        Tupla<Integer,Viajero, Integer> tupla = (Tupla<Integer,Viajero, Integer>)obj; //casteo
        switch(evento) {
            case OCUPAR_ASIENTO:   
                oficina.ocuparAsiento(tupla.a,tupla.b,tupla.c);                
                break;
            case DESOCUPAR_ASIENTO: 
                oficina.desocuparAsiento(tupla.a, tupla.c);
                break;                         
            case SALIR: 
                System.exit(0);
                break;    
        }  
    }
    
   /*
    *  Método main
    */ 
    public static void main(String[] args){ 
        new Agencia();
    }
}
