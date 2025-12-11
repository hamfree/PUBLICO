package es.nom.juanfranciscoruiz.ejemplos.usoscanner.opciones;

import es.nom.juanfranciscoruiz.utiles.IO;

import java.util.Scanner;

/**
 *
 * @author juanf
 */
public class SampleScannerScan {
    public SampleScannerScan() {
        IO.prtln(1,this.getClass().getSimpleName());
        IO.prtln(1,"Usando Scanner y preguntando por nulo para salir. No podrás salir del bucle.");
        IO.prtln(1,"Para forzar la salida pulse la combinación CTRL+D en Unix/Mac o CTRL+Z en Windows y después INTRO para terminar el programa.");
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
                IO.prtln(1, "La entrada fue: " + line);
            }
        } finally {
            scan.close();
        }
    }
}
