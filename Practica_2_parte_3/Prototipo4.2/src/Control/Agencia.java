/*
 *David Ros y Alvaro Fraidias
 *Prototipo 4.2
 *02/05/2020 
 * 
 */
package Control;

import Vista.OficinaVista;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Oficina;
import modelo.Viajero;

/*
 * En esta clase se instancian una Oficina y todos los viajeros 
 * que van a realizar un viaje
 */


public class Agencia implements OyenteVista{
    private String ficheroViajes = "viajes.txt";
    private String ficheroAutobuses = "autobus.txt";
    private OficinaVista vista;

    public Agencia() throws FileNotFoundException {
        vista = OficinaVista.devolverInstancia(this,ficheroViajes,ficheroAutobuses);       
    }
    
    public static void main(String[] args)
                             { 
        try {
            new Agencia();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Agencia.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    
    public void eventoProducido(Evento evento, Object obj) {
    switch(evento) {
      case OCUPAR_ASIENTO:
                 
        break;

      case DESOCUPAR_ASIENTO: 
       
        break;             
             
      case SALIR: 
        System.exit(0);
        break;
        
          
        
    }  
  }
}
