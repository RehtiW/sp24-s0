package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.NgordnetQueryType;
import graph.Graph;
import ngrams.NGramMap;
import java.util.*;


public class HyponymsHandler extends NgordnetQueryHandler {

    private HyponymsHelper helper;
    public HyponymsHandler(Graph graph,NGramMap ngm){
        helper = new HyponymsHelper(graph,ngm);
    }


    public String handle(NgordnetQuery query) {
        List<String> words = query.words();
        int k = query.k(); // 获取要限制的数量
        int start = query.startYear();
        int end = query.endYear();
        NgordnetQueryType queryType = query.ngordnetQueryType();

        if (queryType == NgordnetQueryType.HYPONYMS) {
            // 获取下位词交集
            Set<String> commonHyponyms = helper.getCommonHyponyms(words);
            // 根据k的值筛选交集
            if (k > 0) {
                return helper.getTopKOutput(commonHyponyms, k, start, end);
            } else {
                return helper.formatOutput(commonHyponyms); // k为0时返回所有下位词
            }
        } else if (queryType == NgordnetQueryType.ANCESTORS) {
            Set<String> commonAncestors = helper.getCommonAncestors(words);
            if(k > 0){
                return helper.getTopKOutput(commonAncestors,k,start,end);
            }else{
                return helper.formatOutput(commonAncestors);
            }
        }
        return null;

    }
}

/* ----------------------------------------------------------------------*/

