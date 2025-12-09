package es.nom.juanfranciscoruiz.ejemplos.usoscanner.opciones;

import java.util.Scanner;
public class EOFExample {
    public void ejecuta() {
        Scanner scan = new Scanner(System.in);
        try {
            System.out.println("Introduzca texto (presione CTRL+D en Unix/Mac o CTRL+Z en Windows para terminar):");
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                System.out.println("Usted introdujo: " + line);
            }
            System.out.println("Final de entrada detectado. Programa terminado.");
        } finally {
            scan.close();
        }
    }
}
