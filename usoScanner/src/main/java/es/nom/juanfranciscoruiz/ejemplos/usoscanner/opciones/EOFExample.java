package es.nom.juanfranciscoruiz.ejemplos.usoscanner.opciones;

import java.util.Scanner;
import es.nom.juanfranciscoruiz.utiles.IOable;
import es.nom.juanfranciscoruiz.utiles.impl.IO;

/**
 * Se controla la salida del bucle mediante el carácter de control de final de
 * fichero (EOF).
 *
 */
public class EOFExample {

  public EOFExample() {
    IOable.prtln(1, this.getClass().getSimpleName());
    IOable.prtln(1, "Para terminar el bucle pulse la combinación CTRL+D en Unix/Mac o CTRL+Z en Windows para terminar)");
  }

  public void ejecuta() {
    Scanner scan = new Scanner(System.in);
    try {
      while (scan.hasNextLine()) {
        String line = scan.nextLine();
        IO.prtln(1, "Lo introducido fue: ","'",line,"'");
      }
      IO.prtln(2, "Final de entrada detectado. Programa terminado.");
    } finally {
      // Normalmente cerraríamos el flujo como se debe hacer con cada flujo que 
      // se utiliza, pero si cerramos aquí Scanner cerramos el flujo estándar de 
      // entrada del sistema y después no se podrá volver a abrir, lo que 
      // provocará una excepción en la clase UsoScanner al intentar leer la 
      // entrada del usuario en el menú.
      // Además, la clase Menu utiliza una clase wrapper sobre System.in que NO 
      // cierra el flujo precisamente para evitar el problema de cerrar el flujo 
      // estándar de entrada del sistema que no se podría volver a abrir.
      //scan.close();
    }
  }
}
