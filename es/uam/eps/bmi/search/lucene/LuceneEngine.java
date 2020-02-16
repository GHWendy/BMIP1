package es.uam.eps.bmi.search.lucene;

import es.uam.eps.bmi.search.AbstractEngine;
import es.uam.eps.bmi.search.index.lucene.LuceneIndex;
import es.uam.eps.bmi.search.ranking.SearchRanking;
import es.uam.eps.bmi.search.ranking.lucene.LuceneRanking;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import java.io.IOException;

public class LuceneEngine extends AbstractEngine {
    IndexSearcher searcher;

    public LuceneEngine(String indexPath ) throws IOException {
        super(new LuceneIndex(indexPath));
        if (index instanceof LuceneIndex){
            IndexReader indexReader = ((LuceneIndex) index).getIndexReader();
            this.searcher = new IndexSearcher(indexReader);
        }
    }

    @Override
    public SearchRanking search(String query, int cutoff) throws IOException {

        Analyzer analyzer = new StandardAnalyzer();
        QueryParser parser = new QueryParser("content", analyzer);
        try{
            Query q = parser.parse(query);
            ScoreDoc result[] = searcher.search(q, cutoff).scoreDocs;
            SearchRanking searchRanking = new LuceneRanking(result);
            return searchRanking;
        }catch(ParseException e){
            e.printStackTrace();
        }
        return null;
    }
}
