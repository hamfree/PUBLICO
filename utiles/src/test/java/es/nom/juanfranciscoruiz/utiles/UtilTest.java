package es.nom.juanfranciscoruiz.utiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    void getFeatures() {
    }

    @Test
    void getProperties() {
    }

    @Test
    void getArgs() {
    }

    @Test
    void getAllCharsets() {
    }

    /**
     * Test of toString method, of class C.
     */
    @Test
    public void testToString() {
        System.out.println("TEST toString() de la clase C");

        List<String> lista = new ArrayList<>();

        String[] array = generaArray();

        lista.addAll(Arrays.asList(array));

        assertAll(
                () -> {
                    System.out.println("Muestra los 10 primeros elementos de la lista...");
                    String expResult = toStringEsperadoDeLaLista(lista, 10);
                    String result = Util.toString(lista, true, 10);
                    assertEquals(expResult, result, "Debería mostra como máximo 10 elementos de la lista.");
                },
                () -> {
                    String expResult = "java.util.ArrayList 36 elementos.";
                    String result = Util.toString(lista, false, 1);
                    assertEquals(expResult, result, "Deberia mostrar el tipo y el número de elementos.");
                },
                () -> {
                    System.out.println("Muestra los 5 primeros elementos del mapa...");
                    Map mapa = new HashMap();

                    mapa.put("0", array[0]);
                    mapa.put("1", array[1]);
                    mapa.put("2", array[2]);
                    mapa.put("3", array[3]);
                    mapa.put("4", array[4]);
                    mapa.put("5", array[5]);
                    mapa.put("6", array[6]);
                    mapa.put("7", array[7]);
                    String expResult = toStringEsperadoDelMapa(mapa, 5);
                    String result = Util.toString(mapa, true, 5);
                    assertEquals(expResult, result, "Deberia mostrar como máximo 5 pares de valores del mapa.");
                },
                () -> {
                    System.out.println("Muestra el tipo de colección (mapa) y la cantidad de elementos que contiene...");
                    Map mapa = new HashMap();

                    mapa.put("0", array[0]);
                    mapa.put("1", array[1]);
                    mapa.put("2", array[2]);
                    mapa.put("3", array[3]);
                    mapa.put("4", array[4]);
                    mapa.put("5", array[5]);
                    mapa.put("6", array[6]);
                    mapa.put("7", array[7]);
                    String expResult = "java.util.HashMap 8 elementos.";
                    String result = Util.toString(mapa, false, 2);
                    assertEquals(expResult, result, "Deberia mostrar el tipo y el número de elementos.");
                },
                () -> {
                    Map mapa = generaMapaEnteros(15, 5, 10);

                    System.out.println("mapa -> " + mapa.toString());

                    String expResult = toStringEsperadoDelMapa(mapa, 7);
                    String result = Util.toString(mapa, true, 7);
                    assertEquals(expResult, result, "Deberia mostrar como máximo 7 pares de valores del mapa.");
                }
        );

    }

    
    // Métodos de ayuda para la ejecución del test testToString()
    
    private String[] generaArray() {
        String[] array = new String[]{
            "jabswitch.exe",
            "jaccessinspector.exe",
            "jaccesswalker.exe",
            "jar.exe",
            "jarsigner.exe",
            "java.exe",
            "javac.exe",
            "javadoc.exe",
            "javap.exe",
            "javaw.exe",
            "jcmd.exe",
            "jconsole.exe",
            "jdb.exe",
            "jdeprscan.exe",
            "jdeps.exe",
            "jfr.exe",
            "jhsdb.exe",
            "jimage.exe",
            "jinfo.exe",
            "jlink.exe",
            "jmap.exe",
            "jmod.exe",
            "jpackage.exe",
            "jps.exe",
            "jrunscript.exe",
            "jshell.exe",
            "jstack.exe",
            "jstat.exe",
            "jstatd.exe",
            "jwebserver.exe",
            "keytool.exe",
            "kinit.exe",
            "klist.exe",
            "ktab.exe",
            "rmiregistry.exe",
            "serialver.exe"
        };
        return array;
    }

    private Map<Integer, Integer> generaMapaEnteros(int elementos, int inicio, int incremento) {
        Integer[] claves = generaArrayClaves(elementos);
        Integer[] valores = generarArrayValores(elementos, inicio, incremento);
        Map<Integer, Integer> mapa = new HashMap<>();
        for (int i = 0; i < elementos; i++) {
            mapa.put(claves[i], valores[i]);
        }

        return mapa;
    }

    private String toStringEsperadoDelMapa(Map<Integer, Integer> mapa, int maximoElementos) {
        StringBuilder sb = new StringBuilder();

        sb.append("[");
        if (mapa.size() < maximoElementos) {
            for (Map.Entry<?, ?> e : mapa.entrySet()) {
                sb.append("{'").append(String.valueOf(e.getKey())).append("'->'").append(String.valueOf(e.getValue())).append("'}").append(" ");
            }
        } else {
            int i = 0;
            for (Iterator it = mapa.entrySet().iterator(); it.hasNext();) {
                if (i < maximoElementos) {
                    Map.Entry<?, ?> e = (Map.Entry<?, ?>) it.next();
                    sb.append("{'").append(String.valueOf(e.getKey())).append("'->'").append(String.valueOf(e.getValue())).append("'}").append(" ");
                    i++;
                } else {
                    sb.append(" ...");
                    break;
                }
            }
        }
        sb.append("]");
        return sb.toString();
    }

    private String toStringEsperadoDeLaLista(List<?> lista, int maximoElementos) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if (lista.size() < maximoElementos) {
            for (Object o : lista.toArray()) {
                sb.append("'").append(String.valueOf(o)).append("'").append(" ");
            }
        } else {
            for (int i = 0; i < maximoElementos; i++) {
                sb.append("'").append(String.valueOf(lista.get(i))).append("'").append(" ");
            }
            sb.append(" ...");
        }
        sb.append("]");
        return sb.toString();
    }

    private Integer[] generaArrayClaves(int elementos) {
        Integer[] array = new Integer[elementos];
        for (int i = 0; i < elementos; i++) {
            array[i] = i;
        }
        return array;
    }

    private Integer[] generarArrayValores(int elementos, int inicio, int incremento) {
        Integer[] array = new Integer[elementos];
        for (int i = 0; i < elementos; i++) {
            array[i] = inicio;
            inicio = +incremento;
        }
        return array;
    }
}
