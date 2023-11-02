/*
 *David Ros y Alvaro Fraidias
 *Prototipo 3
 *01/07/2020 
 * 
 */

package modelo;

/**
 *  Tupla genérica de dos objetos
 * 
 */
public class Tupla<A, B> {
  public final A a;
  public final B b;

  /**
   *  Construye una tupla
   * 
   */   
  public Tupla(A a, B b) { 
    this.a = a; 
    this.b = b; 
  }
}  

