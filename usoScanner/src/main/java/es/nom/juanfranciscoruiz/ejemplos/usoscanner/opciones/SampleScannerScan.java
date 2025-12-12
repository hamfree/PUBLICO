package es.nom.juanfranciscoruiz.ejemplos.usoscanner.opciones;

import es.nom.juanfranciscoruiz.utiles.IO;

import java.util.Scanner;

/**
 * Se intenta controlar la condición de salida del bucle mediante el uso 
 * incorrecto del operador de igualdad == y el valor null.
 * @author juanf
 */
public class SampleScannerScan {

  public SampleScannerScan() {
    IO.prtln(1, this.getClass().getSimpleName());
    IO.prtln(1, "Usando Scanner y preguntando por nulo para salir. No podrás salir del bucle.");
    IO.prtln(1, "Para forzar la salida pulse la combinación CTRL+D en Unix/Mac o CTRL+Z en Windows y después INTRO para terminar el programa.");
  }

  public void ejecuta() {
    Scanner scan = new Scanner(System.in);
    try {
      while (scan.hasNextLine()) {
        String line = scan.nextLine();
        if (line == null) {
          IO.prt("Saliendo del programa (comprobacion de nulo)...");
          System.exit(0);
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
