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
    private Oficina oficina;

    public Agencia() throws FileNotFoundException {
        
        oficina = new Oficina(ficheroViajes,ficheroAutobuses);
        vista = OficinaVista.devolverInstancia(this,oficina); 
        oficina.nuevoObservador(vista);
    }
    
    public static void main(String[] args)
                            throws FileNotFoundException, IOException { 
        new Agencia();
        
    }

    
    public void eventoProducido(Evento evento, int idViaje, Viajero viajero, int numeroAsiento) {
    switch(evento) {
      case OCUPAR_ASIENTO:
          oficina.ocuparAsiento(idViaje, numeroAsiento, viajero);                
        break;

      case DESOCUPAR_ASIENTO: 
          oficina.desocuparAsiento(idViaje, numeroAsiento);
       
        break;                         
      case SALIR: 
        System.exit(0);
        break;
        
          
        
    }  
  }
}
