package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryTextHandler extends NgordnetQueryHandler {

    private NGramMap ngm;
    public HistoryTextHandler(NGramMap ngm){
        this.ngm=ngm;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        Map<String, TimeSeries> historyMap = new HashMap<>();

        for (String word : words) {
            // 获取该单词的历史数据
            TimeSeries history = ngm.weightHistory(word, startYear, endYear);
            historyMap.put(word, history);
        }

        // 构建输出字符串
        StringBuilder response = new StringBuilder();
        for (Map.Entry<String, TimeSeries> entry : historyMap.entrySet()) {
            String word = entry.getKey();
            TimeSeries timeSeries = entry.getValue();
            response.append(word).append(": ").append(timeSeries.toString()).append("\n");
        }

        return response.toString();
    }

}
