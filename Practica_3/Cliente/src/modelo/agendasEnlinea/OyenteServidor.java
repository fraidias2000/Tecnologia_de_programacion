/*
 *David Ros y Alvaro Fraidias
 *Prototipo 3
 *01/07/2020 
 * 
 */

package modelo.agendasEnlinea;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 *  Interfaz de oyente para recibir solicitudes del servidor
 * 
 */
public interface OyenteServidor {
   /**
    *  Llamado para notificar una solicitud del servidor
    * 
    */ 
   public boolean solicitudServidorProducida(
           PrimitivaComunicacion solicitud, 
           List<String> parametros) throws IOException;
}
