/*
 * Descripcion de clase.
 */
package es.uam.eps.bmi.search.vsm;

/**
 *
 * @author Wendy Sosa
 */
public class Parser {

    public String[] parse(String query) {
        String toLowerCase = query.toLowerCase();
        String[] querySplit = toLowerCase.split(" ");
        return querySplit;
    }
    
}
