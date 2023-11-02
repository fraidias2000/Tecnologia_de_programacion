/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototipo2;

import java.io.FileNotFoundException;

/**
 *
 * @author aculplay
 */
public class Prototipo2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
        
        
        Viajero viajero1 = new Viajero("ALVARO", "43739T");
        Viajero viajero2 = new Viajero ("DAVID", "472947Z");
        Viajero viajero3 = new Viajero("CARLOS", "783297P");
        
        String ficheroViajes = "viajes.txt";
        String ficheroAutobuses = "autobus.txt";
        Oficina miOficina = new Oficina(ficheroViajes, ficheroAutobuses);
        
        miOficina.ocuparAsiento(0, 1, viajero1);
        miOficina.ocuparAsiento(0,2,viajero2);
        miOficina.ocuparAsiento(0,3,viajero3);
        
        //Comprobamos que todo funciona correctamente
        System.out.println(miOficina.toString());
      
    }
    
}
