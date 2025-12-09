package es.nom.juanfranciscoruiz.ejemplos.usoscanner.opciones;

import java.util.Scanner;

/**
 *
 * @author juanf
 */
public class SampleScanner {

    public void ejecuta() {
        Scanner scan = new Scanner(System.in);
        try {
            while (scan.hasNextLine()) {
                String line = scan.nextLine().toLowerCase();
                System.out.println(line);
            }
        } finally {
            scan.close();
        }
    }
}
