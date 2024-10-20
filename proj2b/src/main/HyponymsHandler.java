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
        // 用第一个单词的下位词初始化交集
        Set<String> commonHyponyms = new HashSet<>(wordNetGraph.getHyponyms(wordList.get(0)));

        // 遍历其余单词，逐步求交集
        for (int i = 1; i < wordList.size(); i++) {
            Set<String> currentHyponyms = wordNetGraph.getHyponyms(wordList.get(i));
            if (currentHyponyms != null) {
                commonHyponyms.retainAll(currentHyponyms); // 求交集
            } else {
                commonHyponyms.clear(); // 如果某个单词没有下位词，交集为空
                break;
            }
        }

        // 对交集中的下位词进行排序
        TreeSet<String> sortedHyponyms = new TreeSet<>(commonHyponyms);

        // 将结果格式化成字符串
        StringBuilder result = new StringBuilder();
        result.append("[");
        boolean isFirst = true;

        // 添加排序后的下位词
        for (String hyponym : sortedHyponyms) {
            if (!isFirst) {
                result.append(", ");
            }
            result.append(hyponym);
            isFirst = false;
        }

        result.append("]");
        return result.toString();
    }

}
