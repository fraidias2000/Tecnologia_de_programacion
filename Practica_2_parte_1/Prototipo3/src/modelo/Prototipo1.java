/*
 *David Ros y Alvaro Fraidias
 *Prototipo 3
 *04/04/2020 
 * 
 */
package modelo;

import Vista.OficinaVista;
import java.io.FileNotFoundException;
import java.io.IOException;
import modelo.Oficina;
import modelo.Viajero;

/*
 * En esta clase se instancian una Oficina y todos los viajeros 
 * que van a realizar un viaje
 */


public class Prototipo1{
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
        System.out.println(miOficina.obtenerEstadoOcupacion()); 
        //OficinaVista vista = OficinaVista.devolverInstancia(10, 10, miOficina.getViajes());
        OficinaVista vista = new OficinaVista(10, 10, miOficina.getViajes());
        
    }
}
