/*
 * Practica1.java 
 * Prototipo3
 * David Ros y alvaro Fraidias
 * 14/03/2020
 */
package prototipo0;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Prototipo0{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
                            throws FileNotFoundException, IOException { 
        Viajero viajero1 = new Viajero("ALVARO", "43739T");
        Viajero viajero2 = new Viajero ("DAVID", "472947Z");
        Viajero viajero3 = new Viajero("CARLOS", "783297P");
        Viajero viajero4 = new Viajero("ANTONIO", "324657P");
        
        String ficheroViajes = "viajes.txt";
        String ficheroAutobuses = "autobus.txt";
        Oficina miOficina = new Oficina(ficheroViajes, ficheroAutobuses);
     
        miOficina.ocuparAsiento(0, 10, viajero1);
        miOficina.ocuparAsiento(0,11,viajero2);
        miOficina.ocuparAsiento(0,12,viajero3);
        miOficina.ocuparAsiento(0,13,viajero4);

        miOficina.generarHoja(1);  
        System.out.println(miOficina.obtenerEstadoOcupacion());    
    }
}
