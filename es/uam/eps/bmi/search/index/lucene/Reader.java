/*
 * Descripcion de clase.
 */
package es.uam.eps.bmi.search.index.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Reader {    
     
    public ArrayList<String> read(String nombreArchivo){
        File file = null;
        java.io.FileReader fr = null;
        BufferedReader br = null;
        ArrayList<String> lista = new ArrayList<>();
        try{
            file = new File(nombreArchivo);
            fr = new java.io.FileReader(file);
            br = new BufferedReader(fr);
            String linea;
            while((linea = br.readLine()) != null){
                if(!linea.isEmpty()){
                    lista.add(linea);
                }
            }
            
        }catch(FileNotFoundException e){
            JOptionPane.showMessageDialog(null, "Archivo no existente ");
        }catch(IOException e){
            JOptionPane.showMessageDialog(null, "Ops, algo salió mal. " + e.getMessage());
        }finally{
            
            try{
                if(fr != null){
                    fr.close();
                }
                br.close();
               
            }catch(IOException e){
                JOptionPane.showMessageDialog(null, "Error en Reader (buffer o file). " + e.getMessage());
            }
        }
       return lista;
    }
}

