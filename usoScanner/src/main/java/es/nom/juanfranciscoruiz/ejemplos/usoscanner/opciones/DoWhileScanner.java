package es.nom.juanfranciscoruiz.ejemplos.usoscanner.opciones;

import java.util.Scanner;

import es.nom.juanfranciscoruiz.utiles.IO;

/**
 * Se usa un bucle do-while como bucle, para, al menos, que se ejecute el bucle
 * al menos una vez. Usa un valor centinela para salir, la palabra 'salir'.
 *
 */
public class DoWhileScanner {

  public DoWhileScanner() {
    IO.prtln(2, this.getClass().getSimpleName());
    IO.prtln(1, "Para terminar el bucle escriba 'salir'");
    IO.prtln(1, " ");
  }

  public void ejecuta() {
    Scanner sc = new Scanner(System.in);
    String input;
    do {
      input = sc.nextLine();
      IO.prtln(1, "Lo introducido fue: ","'",input,"'");
    } while (!input.equals("salir"));
    // Normalmente cerraríamos el flujo como se debe hacer con cada flujo que 
    // se utiliza, pero si cerramos aquí Scanner cerramos el flujo estándar de 
    // entrada del sistema y después no se podrá volver a abrir, lo que 
    // provocará una excepción en la clase UsoScanner al intentar leer la 
    // entrada del usuario en el menú.
    // Además, la clase Menu utiliza una clase wrapper sobre System.in que NO 
    // cierra el flujo precisamente para evitar el problema de cerrar el flujo 
    // estándar de entrada del sistema que no se podría volver a abrir.
    //sc.close();
  }
}
