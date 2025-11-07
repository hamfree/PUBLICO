package es.nom.juanfranciscoruiz.ansiterm.util;

import es.nom.juanfranciscoruiz.utiles.Util;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author juanf
 */
public class MenuTest {

    public MenuTest() {
    }

    /**
     * Test of getOpciones method, of class Menu.
     */
    @Test
    public void testGetOpciones() {
        Menu instance = new Menu();
        instance.setEsMenuInicio(true);
        instance.setOpciones(generaOpciones());
        List<String> expResult = generaOpciones();
        expResult.add(0,"0. Salir de la aplicación");

        List<String> result = instance.getOpciones();
        assertEquals(expResult, result);
    }

    /**
     * Test of setOpciones method, of class Menu.
     */
    @Test
    public void testSetOpciones() {
        List<String> opciones = generaOpciones();
        Menu instance = new Menu();
        instance.setEsMenuInicio(false);
        instance.setOpciones(opciones);
        List<String> expResult = generaOpciones();
        List<String> result = instance.getOpciones();
        assertEquals(expResult, result);
    }

    /**
     * Test of getTitulo method, of class Menu.
     */
    @Test
    public void testGetTitulo() {
        Menu instance = new Menu();
        String expResult = "Sin título";
        String result = instance.getTitulo();
        assertEquals(expResult, result);
    }

    /**
     * Test of setTitulo method, of class Menu.
     */
    @Test
    public void testSetTitulo() {
        String titulo = "Aplicación Test";
        Menu instance = new Menu();
        instance.setTitulo(titulo);
        String expResult = "Aplicación Test";
        String result = instance.getTitulo();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMensaje method, of class Menu.
     */
    @Test
    public void testGetMensaje() {
        Menu instance = new Menu();
        String expResult = "";
        String result = instance.getMensaje();
        assertEquals(expResult, result);
    }

    /**
     * Test of setMensaje method, of class Menu.
     */
    @Test
    public void testSetMensaje() {
        String mensaje = "Un mensaje cualquiera";
        Menu instance = new Menu();
        instance.setMensaje(mensaje);
        String result = instance.getMensaje();
        String expResult = "Un mensaje cualquiera";
        assertEquals(expResult, result);
    }

    /**
     * Test of getOpcionSeleccionada method, of class Menu.
     */
    @Test
    public void testGetOpcionSeleccionada() {
        Menu instance = new Menu();
        Long expResult = null;
        Long result = instance.getOpcionSeleccionada();
        assertEquals(expResult, result);
    }

    /**
     * Test of setOpcionSeleccionada method, of class Menu.
     */
    @Test
    public void testSetOpcionSeleccionada() {
        Long opcionSeleccionada = 5L;
        Menu instance = new Menu();
        instance.setOpcionSeleccionada(opcionSeleccionada);
        Long expResult = 5L;
        Long result = instance.getOpcionSeleccionada();
        assertEquals(expResult, result);
    }

    /**
     * Test of isEsMenuInicio method, of class Menu.
     */
    @Test
    public void testIsEsMenuInicio() {
        Menu instance = new Menu();
        boolean expResult = false;
        boolean result = instance.isEsMenuInicio();
        assertEquals(expResult, result);
    }

    /**
     * Test of setEsMenuInicio method, of class Menu.
     */
    @Test
    public void testSetEsMenuInicio() {
        boolean esMenuInicio = true;
        Menu instance = new Menu();
        instance.setEsMenuInicio(esMenuInicio);
        boolean expResult = true;
        boolean result = instance.isEsMenuInicio();
        assertEquals(expResult, result);
    }

    /**
     * Test of mostrar method, of class Menu.
     * @param out
     */
//    @Test
//    @ReadsStdIo
//    @WritesStdIo
//    @StdIo("")
//    public void testMostrar(StdOut out) throws Exception {
//        Menu instance = new Menu();
//        instance.setTitulo("Título");
//        instance.setMensaje("Mensaje");
//        instance.setEsMenuInicio(true);
//        instance.setOpciones(generaOpciones());
//        instance.mostrar();
//
//        String expResult = """
//                           
//                           **********
//                             T\u00edtulo  
//                           **********
//
//                                                      
//                           0. Salir de la aplicaci\u00f3n
//                           1. Opci\u00f3n 1
//                           2. Opci\u00f3n 2
//                           3. Opci\u00f3n 3
//                           
//                           Mensaje
//                           
//                           """;
//
//        instance.mostrar();
//        assertEquals(expResult, out.capturedString());
//    }
//
//    /**
//     * Test of esperarRespuesta method, of class Menu.
//     */
//    @Test
//    @StdIo({"2"})
//    public void testEsperarRespuesta(StdIn in) throws Exception {
//        String msg = "";
//        Menu instance = new Menu();
//        Long expResult = 2L;
//        assertEquals(expResult, instance.esperarRespuesta(msg));
//
//    }

    /**
     * Test of toString method, of class Menu.
     */
    @Test
    public void testToString() {
        Menu instance = new Menu();
        StringBuilder sb = new StringBuilder();
        sb.append("Menu{");
        sb.append("opciones=").append(Util.CollectionToString(instance.getOpciones(), true, 10));
        sb.append(", titulo=").append(instance.getTitulo());
        sb.append(", mensaje=").append(instance.getMensaje());
        sb.append(", opcionSeleccionada=").append(instance.getOpcionSeleccionada());
        sb.append(", esMenuInicio=").append(instance.isEsMenuInicio());
        sb.append('}');
        String expResult = sb.toString();
        String result = instance.toString();
        assertEquals(expResult, result);
    }

    private List<String> generaOpciones() {
        List<String> opciones = new ArrayList<>();
        opciones.add("1. Opción 1");
        opciones.add("2. Opción 2");
        opciones.add("3. Opción 3");
        return opciones;
    }
}
