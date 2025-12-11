package es.nom.juanfranciscoruiz.ejemplos.usoscanner.opciones;

import java.util.Scanner;

import es.nom.juanfranciscoruiz.utiles.IO;

/**
 *
 *
 */
public class DoWhileScanner {
    public DoWhileScanner(){
        IO.prtln(1, this.getClass().getSimpleName());
        IO.prtln(1, "Para terminar el bucle escriba 'salir'");
        IO.prtln(1, " ");
    }
    public void ejecuta(){
        Scanner sc = new Scanner(System.in);
        String input;
        do {
            input = sc.nextLine();
            System.out.println(input);
        } while (!input.equals("salir"));
        sc.close();

    }
}
