package es.uam.eps.bmi.search.ranking;

import java.io.IOException;

public class SearchRankingDoc implements Comparable<SearchRankingDoc>{
    @Override
    public int compareTo(SearchRankingDoc o) {
        return 0;
    }

    public double getScore() {
        return 0.0;
    }

    public String getPath() throws IOException{
        return "";
    }

}
