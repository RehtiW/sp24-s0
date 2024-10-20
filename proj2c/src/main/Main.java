package main;

import browser.NgordnetServer;
import graph.Graph;
import org.slf4j.LoggerFactory;
import ngrams.NGramMap;
public class Main {
    static {
        LoggerFactory.getLogger(Main.class).info("\033[1;38mChanging text color to white");
    }
    public static void main(String[] args) {
        NgordnetServer hns = new NgordnetServer();
        


        String wordFile = "./data/ngrams/top_14377_words.csv";
        String countFile = "./data/ngrams/total_counts.csv";
        String synsetFile = "./data/wordnet/synsets16.txt";
        String hyponymFile = "./data/wordnet/hyponyms16.txt";

        NGramMap ngm = new NGramMap(wordFile, countFile);
        Graph graph = new Graph();
        graph.loadWordNetData(synsetFile,hyponymFile);
        hns.startUp();
        hns.register("history", new HistoryHandler(ngm));
        hns.register("historytext", new HistoryTextHandler(ngm));
        hns.register("hyponyms", new HyponymsHandler(graph,ngm));
        System.out.println("Finished server startup! Visit http://localhost:4567/ngordnet.html");
    }
}
