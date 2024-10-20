package main;

import graph.Graph;
import ngrams.NGramMap;
import java.util.*;

public class HyponymsHelper {

    private Graph wordNetGraph;
    private NGramMap wordNetNGramMap;

    // 构造方法，传入图结构和NGramMap作为参数
    public HyponymsHelper(Graph wordNetGraph, NGramMap wordNetNGramMap) {
        this.wordNetGraph = wordNetGraph;
        this.wordNetNGramMap = wordNetNGramMap;
    }

    // 获取共同下位词的方法
    public Set<String> getCommonHyponyms(List<String> words) {
        Set<String> commonHyponyms = new HashSet<>(wordNetGraph.getHyponyms(words.get(0)));

        for (int i = 1; i < words.size(); i++) {
            Set<String> currentHyponyms = wordNetGraph.getHyponyms(words.get(i));
            if (currentHyponyms != null) {
                commonHyponyms.retainAll(currentHyponyms); // 求交集
            } else {
                commonHyponyms.clear(); // 如果某个单词没有下位词，交集为空
                break;
            }
        }
        return commonHyponyms;
    }

    // 获取前k个频率最高的下位词的输出
    public String getTopKOutput(Set<String> commonHyponyms, int k, int start, int end) {
        List<Map.Entry<String, Double>> hyponymsWithFrequencies = new ArrayList<>();
        for (String hyponym : commonHyponyms) {
            Double frequency = wordNetNGramMap.allTimeWeighted(hyponym, start, end); // 获取频率
            if (frequency != null) {
                hyponymsWithFrequencies.add(new AbstractMap.SimpleEntry<>(hyponym, frequency));
            }
        }

        hyponymsWithFrequencies.sort((entry1, entry2) -> {
            int cmp = entry2.getValue().compareTo(entry1.getValue());
            return cmp != 0 ? cmp : entry1.getKey().compareTo(entry2.getKey());
        });

        StringBuilder result = new StringBuilder();
        result.append("[");
        boolean isFirst = true;

        for (Map.Entry<String, Double> entry : hyponymsWithFrequencies) {
            if (isFirst) {
                isFirst = false;
            } else {
                result.append(", ");
            }
            result.append(entry.getKey());
            if (--k == 0) break; // 只返回k个元素
        }

        result.append("]");
        return result.toString();
    }

    // 格式化输出下位词
    public String formatOutput(Set<String> hyponyms) {
        StringBuilder result = new StringBuilder();
        result.append("[");

        boolean isFirst = true;
        for (String hyponym : hyponyms) {
            if (isFirst) {
                isFirst = false;
            } else {
                result.append(", ");
            }
            result.append(hyponym);
        }

        result.append("]");
        return result.toString();
    }

    // 获取共同祖先
    public Set<String> getCommonAncestors(List<String> words) {
        Set<String> commonAncestors = new HashSet<>(wordNetGraph.getAncestors(words.get(0)));

        for (int i = 1; i < words.size(); i++) {
            Set<String> currentAncestors = wordNetGraph.getAncestors(words.get(i));
            if (currentAncestors != null) {
                commonAncestors.retainAll(currentAncestors);
            } else {
                commonAncestors.clear();
                break;
            }
        }
        return commonAncestors;
    }
}
