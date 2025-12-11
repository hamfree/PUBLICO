package es.nom.juanfranciscoruiz.ejemplos.usoscanner;

import es.nom.juanfranciscoruiz.ejemplos.usoscanner.opciones.*;
import es.nom.juanfranciscoruiz.utiles.IO;
import es.nom.juanfranciscoruiz.utiles.Menu;
import es.nom.juanfranciscoruiz.utiles.exceptions.MenuException;
import java.util.ArrayList;
import java.util.List;

/**
 * Entrada de la aplicación. Clase que muestra los distintos enfoques a la
 * hora de utilizar Scanner en un programa y como controlar la terminación
 * de las entradas del usuario en un bucle. Cada 'forma' de controlar la
 * terminación se ha implementado en una clase independiente del paquete
 * 'opciones'.
 * NOTA: este código es una adaptación del proyecto original asociado al
 * artículo en Baeldung que puedes encontrar en:
 * <a href="https://github.com/eugenp/tutorials/tree/master/core-java-modules/core-java-io-8">...</a>
 */
public class UsoScanner {

  public static void main(String[] args) throws Exception {
    UsoScanner us = new UsoScanner();

    limpiaPantalla();
    IO.prtln(2, " ");
    IO.prt("Este programa se compone de un menú donde usted podrá elegir "
            + "la ejecución de varios métodos de uso del objeto Scanner "
            + "según se explica en el artículo 'Finalizando Scanner cuando "
            + "se completa la entrada en Java'");
    us.pulsaIntroParaContinuar();

    us.ejecuta();
    IO.prt("Programa terminado");
    System.exit(0);
  }

  public void ejecuta() throws Exception {
    limpiaPantalla();
    Menu menu = new Menu();
    List<String> opciones = new ArrayList<>();
    opciones.add("1. Ejemplo de Scanner con hasNextLine()");
    opciones.add("2. Uso erróneo del operador == para comprobar la entrada");
    opciones.add("3. Uso del marcador Final-de-fichero (EOF)");
    opciones.add("4. Uso de un valor centinela");
    opciones.add("5. Uso de Do While");
    menu.setOptions(opciones);
    menu.setIsHomeMenu(true);
    menu.setMessage("Elija una opción y pulse INTRO para continuar."
            + System.lineSeparator()
            + "¡ATENCIÓN! Alguna de las opciones elegida terminará en un bucle "
            + "infinito. Deberá forzar el cierre del programa para "
            + "terminar.");
    menu.setTitle("Buenas prácticas con el objeto Scanner");
    menu.generateMenuView();

    do {
      IO.prt(menu.getMenuView());
      try {
        menu.awaitResponse("¿Opción? :");
      } catch (MenuException ex) {
        IO.prt("Opción no reconocida");
        pulsaIntroParaContinuar();
      }
      int opcionElegida = menu.getSelectedOption().intValue();
      switch (opcionElegida) {
        case 1 -> ejecutaSampleScanner(opciones.get(1));
        case 2 -> ejecutaSampleScannerScan(opciones.get(2));
        case 3 -> ejecutaEOFEXample(opciones.get(3));
        case 4 -> ejecutaSampleScannerSentinel(opciones.get(4));
        case 5 -> ejecutaDoWhileScanner(opciones.get(5));
        default -> {
          if (opcionElegida != 0) {
            IO.prt("Opción no reconocida");
            pulsaIntroParaContinuar();
          }
        }
      }
    } while (menu.getSelectedOption() != 0L);

  }

  public void ejecutaSampleScanner(String msgOpcion) throws Exception {
    limpiaPantalla();
    IO.prt(IO.title(msgOpcion, '*', msgOpcion.length() + 8));
    SampleScanner obj = new SampleScanner();
    obj.ejecuta();
    pulsaIntroParaContinuar();
  }

  public void ejecutaSampleScannerScan(String msgOpcion) throws Exception {
    limpiaPantalla();
    IO.prt(IO.title(msgOpcion, '*', msgOpcion.length() + 8));
    SampleScannerScan obj = new SampleScannerScan();
    obj.ejecuta();
    pulsaIntroParaContinuar();
  }

  public void ejecutaEOFEXample(String msgOpcion) throws Exception {
    limpiaPantalla();
    IO.prt(IO.title(msgOpcion, '*', msgOpcion.length() + 8));
    EOFExample obj = new EOFExample();
    obj.ejecuta();
    pulsaIntroParaContinuar();
  }

  public void ejecutaDoWhileScanner(String msgOpcion) throws Exception {
    limpiaPantalla();
    IO.prt(IO.title(msgOpcion, '*', msgOpcion.length() + 8));
    DoWhileScanner obj = new DoWhileScanner();
    obj.ejecuta();
    pulsaIntroParaContinuar();
  }

  public void ejecutaSampleScannerSentinel(String msgOpcion) throws Exception {
    limpiaPantalla();
    IO.prt(IO.title(msgOpcion, '*', msgOpcion.length() + 8));
    SampleScannerSentinel obj = new SampleScannerSentinel();
    obj.ejecuta();
    pulsaIntroParaContinuar();
  }

  /* Métodos de utilidad */
  /**
   * Uso de códigos ANSI para limpiar la pantalla.
   */
  private static void limpiaPantalla() {
    /*
     * Borra toda la pantalla
     * (tomado de otro proyecto mío, 'ansiterm'), pero NO mueve el cursor a la
     * posición 0,0 del terminal.
     */
    final String ESC = "\033";
    final String BOR_PAN = ESC + "[2J";
    IO.prt(BOR_PAN);

  }

  /**
   * Muestra el mensaje "Pulse 'INTRO' para continuar..." en pantalla y espera a
   * que el usuario pulse la tecla INTRO para continuar
   */
  private void pulsaIntroParaContinuar() throws Exception {
    IO.prtln(2, System.lineSeparator());
    IO.prt("Pulse 'INTRO' para continuar..." + System.lineSeparator());
    IO.read();
  }
}
