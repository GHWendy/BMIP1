/*
 * Descripcion de clase.
 */
package es.uam.eps.bmi.search.ranking.impl;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * Recuperado de Beginner's Book at Java Collection.
 */
public class TreeSorter {
    public static <K, V extends Comparable<V>> Map<K, V> 
            
    sortByValues(final Map<K, V> map) {
    Comparator<K> valueComparator = 
             new Comparator<K>() {
      public int compare(K k1, K k2) {
        int compare = map.get(k1).compareTo(map.get(k2));
        if (compare == 0) 
          return 1;
        else 
          return compare;
      }
    };
 
    Map<K, V> sortedByValues = 
      new TreeMap<K, V>(valueComparator.reversed());
    sortedByValues.putAll(map);
    return sortedByValues;
  }
}
