/*
 *David Ros y Alvaro Fraidias
 *Prototipo 3
 *01/07/2020 
 * 
 */

package modelo.agendasEnlinea;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Primitiva de comunicación cliente-servidor
 * 
 */
public enum PrimitivaComunicacion {
    CONECTAR_PUSH("conectar"), 
    DESCONECTAR_PUSH("desconectar"), 
    NUEVO_ID_CONEXION("nueva_id_conexion"),
    TEST("test"),
    OBTENER_VIAJES("obtener_viajes"),
    VER_ASIENTOS("ver_asientos"),
    OCUPAR_ASIENTO("ocupar_asiento"),
    DESOCUPAR_ASIENTO("desocupar_asiento"),
    GENERAR_HOJA_VIAJE("generar_hoja"),
    OBTENER_VIAJERO("obtener_viajero"),
    FIN("fin"),  
    OK("ok"),
    NOK("nok");
    
    private String simbolo;
    private static final Pattern expresionRegular =      
            Pattern.compile(CONECTAR_PUSH.toString() + "|" +
                            DESCONECTAR_PUSH.toString() + "|" +
                            NUEVO_ID_CONEXION + "|" +
                            TEST.toString() + "|" +
                            OBTENER_VIAJES.toString() + "|"+
                            VER_ASIENTOS.toString() + "|"+
                            OCUPAR_ASIENTO.toString() + "|"+
                            DESOCUPAR_ASIENTO.toString() + "|"+
                            GENERAR_HOJA_VIAJE.toString() + "|"+
                            OBTENER_VIAJERO.toString() + "|"+
                            FIN.toString() + "|" +                    
                            OK.toString() + "|" +
                            NOK.toString());

    /**
     *  Construye una primitiva
     * 
     */   
    PrimitivaComunicacion(String simbolo) {
        this.simbolo = simbolo;    
    }
  
    /**
     *  Devuelve una nueva primitiva leída de un scanner
     * 
     */   
    public static PrimitivaComunicacion nueva(Scanner scanner) 
            throws InputMismatchException {
        String token = scanner.next(expresionRegular);

        if (token.equals(CONECTAR_PUSH.toString())) {
            return CONECTAR_PUSH;
        } 
        else if (token.equals(DESCONECTAR_PUSH.toString())) {
            return DESCONECTAR_PUSH;
        } 
        else if (token.equals(NUEVO_ID_CONEXION.toString())) {
            return NUEVO_ID_CONEXION;
        }     
        else if (token.equals(TEST.toString())) {
            return TEST;
        }
        else if (token.equals(OBTENER_VIAJES.toString())) {
            return OBTENER_VIAJES;
        }
        else if (token.equals(VER_ASIENTOS.toString())) {
            return VER_ASIENTOS;
        }
        else if (token.equals(OCUPAR_ASIENTO.toString())) {
            return OCUPAR_ASIENTO;
        }
        else if (token.equals(DESOCUPAR_ASIENTO.toString())) {
            return DESOCUPAR_ASIENTO;
        }
        else if (token.equals(GENERAR_HOJA_VIAJE.toString())) {
            return GENERAR_HOJA_VIAJE;   
        }
        else if (token.equals(OBTENER_VIAJERO.toString())) {
            return OBTENER_VIAJERO;
        } 
        else if (token.equals(FIN.toString())) {
            return FIN;
        }    
        else if (token.equals(OK.toString())) {
            return OK;
        } 
        else {
            return NOK;    
        } 
    }
  
    /**
     *  toString
     *
     */  
    @Override
    public String toString() {
        return simbolo;    
    }
}
