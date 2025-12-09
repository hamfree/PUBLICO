package es.nom.juanfranciscoruiz.ejemplos.usoscanner.opciones;

import java.util.Scanner;

/**
 *
 * @author juanf
 */
public class SampleScannerScan {

    public void ejecuta() {
        Scanner scan = new Scanner(System.in);
        try {
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                if (line == null) {
                    System.out.println("Saliendo del programa (comprobacion de nulo)...");
                    System.exit(0);
                }
                System.out.println("La entrada fue: " + line);
            }
        } finally {
            scan.close();
        }
    }
}
