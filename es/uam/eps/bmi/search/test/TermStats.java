package es.uam.eps.bmi.search.test;

import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.index.IndexBuilder;
import es.uam.eps.bmi.search.index.lucene.LuceneBuilder;
import es.uam.eps.bmi.search.index.lucene.LuceneIndex;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TermStats {
    public static void main(String[] args) throws IOException {
        displayGraphics("collections/urls.txt", "index/urls", "wikipedia", "information probability");
        // getTotalFreqInCollection("src/es/uam/eps/bmi/search/ranking", "index/src", "size", "public abstract");
        // getTotalFreqInCollection("collections/docs1k.zip", "index/docs", "seat", "obama family tree");

    }

    static void displayGraphics(String collectionPath, String indexPath, String word, String query) throws IOException{
        clear(indexPath);
        IndexBuilder builder = new LuceneBuilder();
        builder.build(collectionPath, indexPath);
        Index index = new LuceneIndex(indexPath);
        List<String> terms = new ArrayList<String>(index.getAllTerms());

        String barChartName1 = "Frecuencias totales";
        String barChartName2 = "Documentos por término";
        String tagX = "Términos";


        BarChart barChart1 = new BarChart( barChartName1);
        barChart1.createDataSet( barChart1, getTotalFreqInCollection(terms,index), "a", index);
        barChart1.createBarChart(tagX,barChartName1,"Frecuencias totales en la colección de los términos, ordenadas de menor a mayor\n");

        BarChart barChart2 = new BarChart( barChartName2);
        barChart2.createDataSet( barChart2,getDocFreqWithTermInCollection(terms,index), "b", index);
        barChart2.createBarChart(tagX,barChartName2," Número de documentos que contiene cada término, ordenados de menor a mayor\n");

    }

    static List<String> getTotalFreqInCollection( List<String> terms, Index index) throws IOException{
        Collections.sort(terms, new Comparator<>() {
            public int compare(String t1, String t2) {
                try {
                    return (int) Math.signum(index.getTotalFreq(t2) - index.getTotalFreq(t1));
                } catch (IOException ex) {
                    ex.printStackTrace();
                    return 0;
                }
            }
        });
        return terms;
    }

    static List<String> getDocFreqWithTermInCollection(List<String> terms, Index index) {
        Collections.sort(terms, new Comparator<>() {
            public int compare(String t1, String t2) {
                return Integer.parseInt(index.getDocFreq(t2)) - Integer.parseInt(index.getDocFreq(t1));
            }
        });

        return terms;
    }

    static void clear (String indexPath) throws IOException {
        File dir = new File(indexPath);
        if (!dir.exists()) Files.createDirectories(Paths.get(indexPath));
        for (File f : dir.listFiles()) if (f.isFile()) f.delete();
    }
}
