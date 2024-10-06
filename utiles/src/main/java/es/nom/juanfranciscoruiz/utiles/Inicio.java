package es.nom.juanfranciscoruiz.utiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author juanf
 */
public class Inicio {
    public static void main(String[] args) {
        HashMap<String,Integer> map = new HashMap<>();
        map.put("Uno", 1);
        map.put("Dos", 2);
        map.put("Tres", 3);
        map.put("Cuatro", 4);
        map.put("Cinco", 5);
        map.put("Seis", 6);

        List<Integer> lista1 = new ArrayList<>();

        lista1.add(1);
        lista1.add(2);
        lista1.add(3);
        lista1.add(4);
        lista1.add(5);
        lista1.add(6);
        lista1.add(7);

        List<Integer> lista2 = new ArrayList<>();
        lista2.add(7);
        lista2.add(6);
        lista2.add(5);
        lista2.add(4);
        lista2.add(3);
        lista2.add(2);
        lista2.add(1);


        HashMap<String,List<Integer>> map2 = new HashMap<>();
        map2.put("lista1", lista1);
        map2.put("lista2", lista2);


        System.out.println(TypeConverter.map2String(map));
        System.out.println();
        System.out.println(TypeConverter.map2String(map2));
    }
}
