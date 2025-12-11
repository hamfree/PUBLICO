package es.nom.juanfranciscoruiz.ejemplos.usoscanner.opciones;

import es.nom.juanfranciscoruiz.utiles.IO;

import java.util.Scanner;

/**
 *
 * @author juanf
 */
public class SampleScanner {

    public SampleScanner() {
        IO.prtln(1,this.getClass().getSimpleName());
        IO.prtln(1,"Versión tradicional de uso de Scanner. No podrás salir del bucle.");
        IO.prtln(1,"Para forzar la salida pulse la combinación CTRL+D en Unix/Mac o CTRL+Z en Windows y después INTRO para terminar el programa.");
    }

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
