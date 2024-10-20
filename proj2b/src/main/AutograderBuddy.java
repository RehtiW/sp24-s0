package main;

import browser.NgordnetQueryHandler;


public class AutograderBuddy {
    /** Returns a HyponymHandler */
    public static NgordnetQueryHandler getHyponymsHandler(
            String wordFile, String countFile,
            String synsetFile, String hyponymFile) {
            Graph graph = new Graph();
            graph.loadWordNetData(synsetFile,hyponymFile);
            return new HyponymsHandler(graph);

    }
}
