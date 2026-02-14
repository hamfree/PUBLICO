package es.nom.juanfranciscoruiz.ejemplos.usoscanner.opciones;

import es.nom.juanfranciscoruiz.utiles.impl.IO;

import java.util.Scanner;

/**
 * Se usa un valor centinela para salir del bucle de entrada
 *
 * @author juanf
 */
public class SampleScannerSentinel {

  public SampleScannerSentinel() {
    IO.prtln(1, this.getClass().getSimpleName());
    IO.prtln(1, "Usando Scanner con valor centinela. Para salir del bucle y terminar el programa escriba 'salir'.");
  }

  public void ejecuta() {
    Scanner scan = new Scanner(System.in);
    try {
      while (scan.hasNextLine()) {
        String line = scan.nextLine().toLowerCase();
        if (line.equals("salir")) {
          System.out.println("Saliendo del programa...");
          break;
        }
        IO.prtln(1, "Lo introducido fue: ","'",line,"'");
      }
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
