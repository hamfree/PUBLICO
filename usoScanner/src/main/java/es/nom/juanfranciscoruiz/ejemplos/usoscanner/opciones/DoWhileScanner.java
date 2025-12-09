package es.nom.juanfranciscoruiz.ejemplos.usoscanner.opciones;

import java.util.Scanner;

/**
 *
 * @author juanf
 */
public class DoWhileScanner {
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
