package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import org.knowm.xchart.XYChart;
import plotting.Plotter;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {
    private NGramMap ngm;
    public HistoryHandler(NGramMap map){
        this.ngm = map;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        List<TimeSeries> timeSeriesList = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        // 遍历查询中的所有单词
        for (String word : words) {
            // 获取该单词在指定年份范围内的加权流行历史
            TimeSeries timeSeries = ngm.weightHistory(word, startYear,endYear);
            timeSeriesList.add(timeSeries);
            labels.add(word); // 添加标签
        }

        // 生成图表
        XYChart chart = Plotter.generateTimeSeriesChart(labels, timeSeriesList);
        String encodedImage = Plotter.encodeChartAsString(chart);

        return encodedImage; // 返回Base64编码的图像
    }


}
