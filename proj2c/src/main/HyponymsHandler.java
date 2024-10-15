package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import java.util.*;
public class HyponymsHandler extends NgordnetQueryHandler {

    private Graph wordNetGraph;

    public HyponymsHandler(Graph graph){
        this.wordNetGraph=graph;
    }


    @Override
    public String handle(NgordnetQuery q) {
        // 获取查询的单词
        List<String> wordList = q.words();  //处理多个单词
        Set<String> hyponyms=new HashSet<>();
        for(int i=0;i<wordList.size();i++){
            hyponyms.addAll(wordNetGraph.getHyponyms(wordList.get(i)));
        }
        TreeSet<String> sortedHyponyms = new TreeSet<>(hyponyms);

        // 将结果格式化成要求的字符串格式
        StringBuilder result = new StringBuilder();
        result.append("[");
        // 添加排序后的下位词
        boolean first = true;
        for (String hyponym : sortedHyponyms) {
            if (!first) {
                result.append(", ");
            }
            result.append(hyponym);
            first = false;
        }

        result.append("]");

        return result.toString();
    }

}
