/*
 *David Ros y Alvaro Fraidias
 *Prototipo 3
 *04/04/2020 
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
    private Oficina miOficina;
    private OficinaVista vista;

    public Agencia() throws FileNotFoundException {
        miOficina = Oficina.instancia(ficheroViajes, 
                                              ficheroAutobuses);
        vista = OficinaVista.devolverInstancia(this, 0, 0, miOficina.getViajes());       
    }
    
    public static void main(String[] args)
                            throws FileNotFoundException, IOException { 
        Viajero viajero1 = new Viajero("ALVARO", "43739T");
        Viajero viajero2 = new Viajero ("DAVID", "472947Z");
        Viajero viajero3 = new Viajero("CARLOS", "783297P");
        Viajero viajero4 = new Viajero("ANTONIO", "324657P");
        
        String ficheroViajes = "viajes.txt";
        String ficheroAutobuses = "autobus.txt";
        Oficina miOficina = Oficina.instancia(ficheroViajes, 
                                              ficheroAutobuses);
     
        miOficina.ocuparAsiento(0, 10, viajero1);
        miOficina.ocuparAsiento(0,11,viajero2);
        miOficina.ocuparAsiento(0,12,viajero3);
        miOficina.ocuparAsiento(0,13,viajero4);

        miOficina.generarHoja(1);  
        //System.out.println(miOficina.obtenerEstadoOcupacion()); 
        //OficinaVista vista = OficinaVista.devolverInstancia(this, 5, 10, miOficina.getViajes());
        new Agencia();
        
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
