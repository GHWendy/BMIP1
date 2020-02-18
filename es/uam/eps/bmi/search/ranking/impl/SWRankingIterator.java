/*
 * Descripcion de clase.
 */
package es.uam.eps.bmi.search.ranking.impl;

import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.index.lucene.LuceneIndex;
import es.uam.eps.bmi.search.ranking.SearchRankingDoc;
import java.util.Iterator;

/**
 *
 * @author Wendy Sosa
 */
public class SWRankingIterator implements Iterator<SearchRankingDoc>{

    SWScoreDoc results[];
    Index index;
    int n = 0;

    public SWRankingIterator (Index idx, SWScoreDoc r[]) {
        index = idx;
        results = r;
    }
    
    // Empty result list
    public SWRankingIterator () {
        index = null;
        results = new SWScoreDoc[0];
    }
    
    public boolean hasNext() {
        return n < results.length;
    }

    public SearchRankingDoc next() {
        return new SWRankingDoc(index, results[n++]);
    }
}
