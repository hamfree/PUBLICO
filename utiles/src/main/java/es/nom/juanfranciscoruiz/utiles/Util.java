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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger logger = LoggerFactory.getLogger(Util.class);

    final static String SL = System.lineSeparator();

    /**
     * Evitamos que se pueda instanciar
     */
    private Util() {
    }

    /**
     * Devuelve en un mapa las cpu's existentes en el PC, la memoria libre 
     * disponible para la máquina virtual (MV), la cantidad máxima de memoria que la 
     * MV intentará utilizar y la cantidad total de memoria 
     * actualmente disponible para los objetos actuales y futuros, medida en 
     * bytes.
     * @return un mapa con cuatro pares de datos:
     * 
     * <ol>
     * <li> Clave: "Nucleos Procesador". Valor: la cantidad de núcleos de CPU disponibles para la MV.</li>
     * <li> Clave: "Memoria Libre". Valor: la memoria libre disponible para la MV</li>
     * <li> Clave: "Memoria Disponible". Valor: la cantidad máxima de memoria que la MV intentará utilizar</li>
     * <li> Clave: "Memoria Total". Valor: la cantidad total de memoria actualmente disponible en la MV para los objetos actuales y futuros.</li>
     * </ol>
     */
    public static Map<String, String> getFeatures() {
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

    /**
     * Devuelve en un mpa las propiedades del sistema
     * @return un mapa con las propiedades del sistema.
     */
    public static Map<String, String> getProperties() {
        HashMap<String, String> hm = new HashMap<>();

        Properties p = System.getProperties();

        p.forEach((key, value) -> hm.put(String.valueOf(key), String.valueOf(value)));
        return hm;
    }

    /**
     * Convierte una matriz de strings en una Lista de Strings
     * @param args la matriz de strings
     * @return una lista de strings cuyos elementos son los strings de la matriz 
     * que se le pasa como argumento.
     */
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
     *
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
    public static String CollectionToString(Object obj, boolean muestraValores, int maximoElementos) {

        StringBuilder sb = new StringBuilder();

        if (obj == null) {
            sb.append("null");
        } else {
            Class<?> clazz = obj.getClass();
            if (clazz.isAssignableFrom(ArrayList.class)) {
                List<?> l = (List) obj;
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
                Map<?, ?> m = (Map) obj;
                if (muestraValores) {
                    sb.append("[");
                    if (m.size() < maximoElementos) {
                        for (Iterator<?> it = m.entrySet().iterator(); it.hasNext();) {
                            Map.Entry<?, ?> e = (Map.Entry<?, ?>) it.next();
                            sb.append("{'").append(String.valueOf(e.getKey())).append("'->'").append(String.valueOf(e.getValue())).append("'}").append(" ");
                        }
                    } else {
                        int i = 0;
                        for (Iterator<?> it = m.entrySet().iterator(); it.hasNext();) {
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
}
