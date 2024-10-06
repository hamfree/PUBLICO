/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package es.nom.juanfranciscoruiz.utiles;

import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.logging.Logger;

/**
 * Clase con metodos de utilidad usados frecuentemente por las otras clases de
 * la aplicación
 *
 * @author hamfree
 */
public class Util {

    /**
     * Para la depuracion.
     */
    private static final Logger LOG = Logger.getLogger(Util.class.getName());

    final static String SL = System.lineSeparator();

    /**
     * Evitamos que se pueda instanciar
     */
    private Util() {
    }

    public static HashMap<String, String> getFeatures() {
        HashMap<String, String> hm = new HashMap<>();

        Runtime rt = Runtime.getRuntime();
        NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.getDefault());

        System.gc();
        String freeMemory = numberFormatter.format(rt.freeMemory());
        String maxMemory = numberFormatter.format(rt.maxMemory());
        String totalMemmory = numberFormatter.format(rt.totalMemory());

        hm.put("Nucleos Procesador", String.valueOf(rt.availableProcessors()));
        hm.put("Memoria Libre", freeMemory);
        hm.put("Memoria Disponible", maxMemory);
        hm.put("Memoria Total", totalMemmory);
        return hm;
    }

    public static HashMap<String, String> getProperties() {
        HashMap<String, String> hm = new HashMap<>();

        Properties p = System.getProperties();

        p.forEach((key, value) -> hm.put(String.valueOf(key), String.valueOf(value)));
        return hm;
    }

    public static List<String> getArgs(String[] args) {
        return Arrays.asList(args);
    }

    /**
     * Obtiene todos los charsets soportados por la MVJ actual.
     *
     * @return una lista con todos los charsets soportados por la MVJ actual.
     */
    public static List<Charset> getAllCharsets() {
        SortedMap<String, Charset> sm;
        ArrayList<Charset> al = null;
        sm = Charset.availableCharsets();
        Iterator<Charset> it = sm.values().iterator();
        al = new ArrayList<>();
        while (it.hasNext()) {
            Charset ch = it.next();
            al.add(ch);
        }
        return al;
    }
    
    /**
     * Procesa las listas y los mapas para mejorar la salida del metodo 
     * 'toString()' de los objetos de la aplicación.
     * @param obj el mapa o la lista del que se quiere mostrar sus elementos o 
     * indicar su tipo y tamaño.
     * @param muestraValores boolean, si es true devolverá una cadena con los 
     * elementos de la lista o el mapa. Si es false, se devolverá una cadena con
     * el tipo de objeto y la cantidad de elementos que contiene.
     * @param maximoElementos En caso de que se muestren los valores, establece
     * el número máximo de elementos cuya representación textual se devolveran 
     * en la cadena.
     * @return una cadena con la representación textual de los elementos que 
     * contiene la lista o el mapa o el tipo del objeto y la cantidad de 
     * elementos que contiene. Si el objeto pasado no es ni un mapa ni una lista 
     * la cadena que devolverá es generada por el método estático 
     * String.valueOf(). Si el objeto es nulo devolverá la cadena "null".
     */
    public static String toString(Object obj, boolean muestraValores, int maximoElementos) {

        StringBuilder sb = new StringBuilder();

        if (obj == null) {
            sb.append("null");
        } else {
            Class<?> clazz = obj.getClass();
            if (clazz.isAssignableFrom(ArrayList.class)) {
                List l = (List) obj;
                if (muestraValores) {
                    sb.append("[");
                    if (l.size() < maximoElementos) {
                        for (Object o : l.toArray()) {
                            sb.append("'").append(String.valueOf(o)).append("'").append(" ");
                        }
                    } else {
                        for (int i = 0; i < maximoElementos; i++) {
                            sb.append("'").append(String.valueOf(l.get(i))).append("'").append(" ");
                        }
                        sb.append(" ...");
                    }
                    sb.append("]");
                } else {
                    sb.append(obj.getClass().getCanonicalName())
                            .append(" ")
                            .append(String.valueOf(l.size()))
                            .append(" elementos.");
                }

            } else if (clazz.isAssignableFrom(java.util.HashMap.class)) {
                Map m = (Map) obj;
                if (muestraValores) {
                    sb.append("[");
                    if (m.size() < maximoElementos) {
                        for (Iterator it = m.entrySet().iterator(); it.hasNext();) {
                            Map.Entry<?, ?> e = (Map.Entry<?, ?>) it.next();
                            sb.append("{'").append(String.valueOf(e.getKey())).append("'->'").append(String.valueOf(e.getValue())).append("'}").append(" ");
                        }
                    } else {
                        int i = 0;
                        for (Iterator it = m.entrySet().iterator(); it.hasNext();) {
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
                } else {
                    sb.append(obj.getClass().getCanonicalName())
                            .append(" ")
                            .append(String.valueOf(m.size()))
                            .append(" elementos.");
                }
            } else {
                sb.append(String.valueOf(obj));
            }
        }
        return sb.toString();
    }

    /**
     * @return el log
     */
    public static Logger getLog() {
        return LOG;
    }
}
