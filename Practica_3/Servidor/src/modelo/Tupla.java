/*
 *David Ros y Alvaro Fraidias
 *Prototipo 3
 *01/07/2020 
 * 
 */
package modelo;

/**
 *  Tupla genérica de dos objetos
 */
public class Tupla<A, B, C> {
  public final A a;
  public final B b;
  public final C c;

  /**
   *  Construye una tupla
   */   
  public Tupla(A a, B b, C c) { 
    this.a = a; 
    this.b = b; 
    this.c = c;
  }
}  

