/*
 * Practica1.java 
 * Prototipo1
 * David Ros y Alvaro Fraidias
 * 11/03/2020
 */
package prototipo1;

import java.io.FileNotFoundException;

/**
 *
 * @author aculplay
 */
public class Prototipo1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
        String ficheroViaje = "viaje.txt";
        String ficheroAutobus = "autobuses.txt";
        Oficina miOficina = new Oficina(ficheroViaje,ficheroAutobus);
        
        System.out.println(miOficina.toString());
        
   
        
     /*   
        System.out.println(miOficina.viajes[0].toString());
        
        miOficina.viajes[0].inicializarAutobus(ficheroAutobus);
        System.out.println(miOficina.viajes[0].toString());
       */
    }
    
}
