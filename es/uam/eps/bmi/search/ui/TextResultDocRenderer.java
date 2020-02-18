package es.uam.eps.bmi.search.ui;

import es.uam.eps.bmi.search.ranking.SearchRankingDoc;
import es.uam.eps.bmi.search.ranking.impl.SWRankingDoc;
import es.uam.eps.bmi.search.ranking.lucene.LuceneRankingDoc;

import java.io.IOException;


public class TextResultDocRenderer extends ResultsRenderer {
    SearchRankingDoc searchRankingDoc;
    
    public TextResultDocRenderer(SearchRankingDoc searchRankingDoc) {
        searchRankingDoc.compareTo(searchRankingDoc);
        if(searchRankingDoc instanceof LuceneRankingDoc){
            this.searchRankingDoc = (LuceneRankingDoc) searchRankingDoc;
        } else if(searchRankingDoc instanceof SWRankingDoc){
            this.searchRankingDoc = (SWRankingDoc) searchRankingDoc;
        }
    }

    @Override
    public String toString() {
        try {
            return searchRankingDoc.getScore() + "\t" + searchRankingDoc.getPath();
        }catch (IOException e){
            return null;
        }

    }

    // System.out.println(d.score + "\t" + index.document(d.doc).get("path"));
}
