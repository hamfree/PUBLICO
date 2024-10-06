package es.nom.juanfranciscoruiz.utiles;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test de la clase Util
 *
 * @author juanf
 */
public class UtilTest {
    
    public final static Logger logger = LoggerFactory.getLogger(UtilTest.class);

    @Test
    public void getFeatures() {
        imprimeTitulo("getFeatures()");

        Map<String, String> expectedValue = new HashMap<>();
        Map<String, String> actualValue;

        Runtime rt = Runtime.getRuntime();
        NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.getDefault());

        System.gc();
        String freeMemory = numberFormatter.format(rt.freeMemory());
        String maxMemory = numberFormatter.format(rt.maxMemory());
        String totalMemmory = numberFormatter.format(rt.totalMemory());

        expectedValue.put("Nucleos Procesador", String.valueOf(rt.availableProcessors()));
        expectedValue.put("Memoria Libre", freeMemory);
        expectedValue.put("Memoria Disponible", maxMemory);
        expectedValue.put("Memoria Total", totalMemmory);

        actualValue = Util.getFeatures();

        imprimeResultados(expectedValue, actualValue);
        

        assertEquals(expectedValue.size(), actualValue.size(), "Debe devolver un mapa con cuatro claves específicas");
        assertEquals(expectedValue.get("Memoria Disponible"), actualValue.get("Memoria Disponible"), "La memoria disponible debe ser la misma");

    }

    @Test
    public void getProperties() {
        imprimeTitulo("getProperties()");

        HashMap<String, String> expectedValue = new HashMap<>();
        HashMap<String, String> actualValue;
        Object key;
        Object value;
        String sKey = null;
        String sValue = null;
        Class<?> clazz;

        Properties p = System.getProperties();
        for (Map.Entry<Object, Object> entry : p.entrySet()) {
            key = entry.getKey();
            value = entry.getValue();
            
            clazz = key.getClass();
            if (clazz.isAssignableFrom(String.class)) {
                sKey = (String) key;
            }
            clazz = value.getClass();
            if (clazz.isAssignableFrom(String.class)){
                sValue = (String) value;
            }
            expectedValue.put(sKey, sValue);
        }
        
        actualValue = (HashMap<String, String>) Util.getProperties();
        
        imprimeResultados(expectedValue, actualValue);
        
        assertEquals(expectedValue, actualValue, "Deben tener las mismas claves y valores");
        
    }

    @Test
    public void getArgs() {
        imprimeTitulo("getArgs()");

        String[] array = generaArray();

        List<String> actualValue;
        List<String> expectedValue = generaLista();

        actualValue = Util.getArgs(array);

        imprimeResultados(expectedValue, actualValue);

        assertEquals(expectedValue, actualValue, "Debería convertir la matriz en una lista");
    }

    @Test
    public void getAllCharsets() {
    }

    /**
     * Test of CollectionToString method, of class C.
     */
    @Test
    public void testCollectionToString() {
        imprimeTitulo("CollectionToString()");

        List<String> lista = new ArrayList<>();

        String[] array = generaArray();

        lista.addAll(Arrays.asList(array));

        assertAll(() -> {
            System.out.println("Muestra los 10 primeros elementos de la lista...");
            String expResult = toStringEsperadoDeLaLista(lista, 10);
            String result = Util.CollectionToString(lista, true, 10);
            assertEquals(expResult, result, "Debería mostra como máximo 10 elementos de la lista.");
        },
                () -> {
                    String expResult = "java.util.ArrayList 36 elementos.";
                    String result = Util.CollectionToString(lista, false, 1);
                    assertEquals(expResult, result, "Deberia mostrar el tipo y el número de elementos.");
                },
                () -> {
                    System.out.println("Muestra los 5 primeros elementos del mapa...");
                    Map<String, String> mapa = new HashMap<>();

                    mapa.put("0", array[0]);
                    mapa.put("1", array[1]);
                    mapa.put("2", array[2]);
                    mapa.put("3", array[3]);
                    mapa.put("4", array[4]);
                    mapa.put("5", array[5]);
                    mapa.put("6", array[6]);
                    mapa.put("7", array[7]);
                    String expResult = toStringEsperadoDelMapa(mapa, 5);
                    String result = Util.CollectionToString(mapa, true, 5);
                    assertEquals(expResult, result, "Deberia mostrar como máximo 5 pares de valores del mapa.");
                },
                () -> {
                    System.out.println("Muestra el tipo de colección (mapa) y la cantidad de elementos que contiene...");
                    Map<String, String> mapa = new HashMap<>();

                    mapa.put("0", array[0]);
                    mapa.put("1", array[1]);
                    mapa.put("2", array[2]);
                    mapa.put("3", array[3]);
                    mapa.put("4", array[4]);
                    mapa.put("5", array[5]);
                    mapa.put("6", array[6]);
                    mapa.put("7", array[7]);
                    String expResult = "java.util.HashMap 8 elementos.";
                    String result = Util.CollectionToString(mapa, false, 2);
                    assertEquals(expResult, result, "Deberia mostrar el tipo y el número de elementos.");
                },
                () -> {
                    Map<Integer, Integer> mapa = generaMapaEnteros(15, 5, 10);

                    System.out.println("mapa -> " + mapa.toString());

                    String expResult = toStringEsperadoDelMapa(mapa, 7);
                    String result = Util.CollectionToString(mapa, true, 7);
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

    private List<String> generaLista() {
        List<String> lista = new ArrayList<>();

        lista.add("jabswitch.exe");
        lista.add("jaccessinspector.exe");
        lista.add("jaccesswalker.exe");
        lista.add("jar.exe");
        lista.add("jarsigner.exe");
        lista.add("java.exe");
        lista.add("javac.exe");
        lista.add("javadoc.exe");
        lista.add("javap.exe");
        lista.add("javaw.exe");
        lista.add("jcmd.exe");
        lista.add("jconsole.exe");
        lista.add("jdb.exe");
        lista.add("jdeprscan.exe");
        lista.add("jdeps.exe");
        lista.add("jfr.exe");
        lista.add("jhsdb.exe");
        lista.add("jimage.exe");
        lista.add("jinfo.exe");
        lista.add("jlink.exe");
        lista.add("jmap.exe");
        lista.add("jmod.exe");
        lista.add("jpackage.exe");
        lista.add("jps.exe");
        lista.add("jrunscript.exe");
        lista.add("jshell.exe");
        lista.add("jstack.exe");
        lista.add("jstat.exe");
        lista.add("jstatd.exe");
        lista.add("jwebserver.exe");
        lista.add("keytool.exe");
        lista.add("kinit.exe");
        lista.add("klist.exe");
        lista.add("ktab.exe");
        lista.add("rmiregistry.exe");
        lista.add("serialver.exe");

        return lista;
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

    private String toStringEsperadoDelMapa(Map<?, ?> mapa, int maximoElementos) {
        StringBuilder sb = new StringBuilder();

        sb.append("[");
        if (mapa.size() < maximoElementos) {
            for (Map.Entry<?, ?> e : mapa.entrySet()) {
                sb.append("{'").append(String.valueOf(e.getKey())).append("'->'").append(String.valueOf(e.getValue())).append("'}").append(" ");
            }
        } else {
            int i = 0;
            for (Iterator<?> it = mapa.entrySet().iterator(); it.hasNext();) {
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
    
    private void imprimeTitulo(String nombreMetodo){
        String test = "TEST " + nombreMetodo;
        logger.debug(test);
    }
    
    private void imprimeResultados(Object expectedValue, Object actualValue){
        String actVal = "Valor devuelto -> " + String.valueOf(actualValue);
        String expVal = "Valor esperado -> " + String.valueOf(expectedValue);
        logger.debug(actVal);
        logger.debug(expVal);
    }
}
