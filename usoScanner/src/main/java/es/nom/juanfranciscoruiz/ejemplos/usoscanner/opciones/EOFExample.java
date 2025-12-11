package es.nom.juanfranciscoruiz.ejemplos.usoscanner.opciones;

import java.util.Scanner;
import es.nom.juanfranciscoruiz.utiles.IO;

/**
 *
 *
 */
public class EOFExample {
    public EOFExample() {
        IO.prtln(1, this.getClass().getSimpleName());
        IO.prtln(1, "Para terminar el bucle pulse la combinación CTRL+D en Unix/Mac o CTRL+Z en Windows para terminar)");
    }

    public void ejecuta() {
        Scanner scan = new Scanner(System.in);
        try {
            IO.prt("Introduzca texto :");
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                IO.prtln(1, "Usted introdujo: " + line);
            }
            IO.prtln(2,"Final de entrada detectado. Programa terminado.");
        } finally {
            scan.close();
        }
    }
}
