/*
 * Descripcion de clase.
 */
package es.uam.eps.bmi.search.index.lucene;

import es.uam.eps.bmi.search.index.IndexBuilder;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.jsoup.Jsoup;

/**
 *
 * @author Wendy Sosa
 */

public class LuceneBuilder implements IndexBuilder {

    IndexWriter builder;
    
    public void build(String collectionPath, String indexPath) throws IOException {
        boolean rebuild = true;
        Directory directory = FSDirectory.open(Paths.get(indexPath));
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        if (rebuild) config.setOpenMode(OpenMode.CREATE);
        else config.setOpenMode(OpenMode.CREATE_OR_APPEND);
        builder =  new IndexWriter(directory, config);
        indexCollection(collectionPath);
    }
    
    private void indexCollection(String collectionPath) throws IOException {
        
        if (collectionPath.endsWith(".zip")){
            indexZip(collectionPath);
        }else if (new File(collectionPath).isDirectory()){
          indexDirectory(collectionPath);
        } else if( collectionPath.endsWith(".txt")){
            Reader fileReader = new Reader();
            ArrayList<String> urls = fileReader.read(collectionPath);
            for (String url : urls) {
                String text = Jsoup.parse(new URL(url), 10000).text();
                addDocument(text, url);
            }
        }
        builder.close();
    }
    
    private void addDocument(String text,String path) throws IOException {
        
            Document doc = new Document();
            doc.add(new TextField("path", path, Field.Store.YES));
            FieldType type = new FieldType();
            type.setIndexOptions (IndexOptions.DOCS_AND_FREQS);
            type.setStoreTermVectors (true);
            doc.add(new Field("content", text, type));
            builder.addDocument(doc);   
    }    
    
    private void indexZip(String collectionPath) throws IOException {
        ZipReader zipReader  = new ZipReader();
        String unzippedPath= zipReader.unzip(collectionPath);
        indexDirectory(unzippedPath);
    }    
    private void indexDirectory(String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        for (File f : directory.listFiles()) {
            if (f.isFile()) {
                String path = f.getAbsolutePath();
                Reader reader = new Reader();
                String text = Jsoup.parse((reader.read(path).toString())).text();
                addDocument(text, path);
            }
        }
    }
}
