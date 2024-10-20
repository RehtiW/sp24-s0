package main;

import browser.NgordnetQueryHandler;
import graph.Graph;
import ngrams.NGramMap;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymsHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {
            Graph graph = new Graph();
            graph.loadWordNetData(synsetFile,hyponymFile);
            NGramMap ngm = new NGramMap(wordFile,countFile);
            return new HyponymsHandler(graph,ngm);

    }
}
