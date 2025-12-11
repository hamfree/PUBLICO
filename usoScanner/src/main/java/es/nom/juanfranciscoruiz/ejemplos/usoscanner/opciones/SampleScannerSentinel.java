package es.nom.juanfranciscoruiz.ejemplos.usoscanner.opciones;

import es.nom.juanfranciscoruiz.utiles.IO;

import java.util.Scanner;

/**
 *
 * @author juanf
 */
public class SampleScannerSentinel {
    public SampleScannerSentinel() {
        IO.prtln(1,this.getClass().getSimpleName());
        IO.prtln(1,"Usando Scanner con valor centinela. Para salir del bucle y terminar el programa escriba 'salir'.");
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
                System.out.println(line);
            }
        } finally {
            scan.close();
        }

    }
}
