package es.nom.juanfranciscoruiz.ejemplos.usoscanner.opciones;

import java.util.Scanner;

/**
 *
 * @author juanf
 */
public class SampleScannerSentinel {

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
