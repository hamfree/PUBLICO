package es.nom.juanfranciscoruiz.utiles;

/**
 * Simple class to demonstrate printing functionality.
 * Used in the test class MiClaseTest for the demostration of how to test
 * classes that print to standard output.
 */
public class MiObjeto {
    private String s;

    public MiObjeto(String s) {
        this.s = s;
    }

    public void imprimir() {
        System.out.println(this.s);
    }
}
