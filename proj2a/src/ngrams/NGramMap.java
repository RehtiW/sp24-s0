package ngrams;

import java.sql.Time;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;
import edu.princeton.cs.algs4.In;
/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {
    /*<Word,TimeSeries<year,frequency>>*/
    private Map<String,TimeSeries> wordCounts;
    private TimeSeries totalCounts;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */

    public NGramMap(String wordsFilename, String countsFilename) {
        wordCounts = new HashMap<>();
        totalCounts = new TimeSeries();

        // 读取wordsFilename文件
        In wordsFile = new In(wordsFilename);
        while (wordsFile.hasNextLine()) {
            String[] tokens = wordsFile.readLine().split("\\s+");
            String word = tokens[0];
            int year = Integer.parseInt(tokens[1]);
            double count = Double.parseDouble(tokens[2]);

            // 添加到wordCounts中
            wordCounts.putIfAbsent(word, new TimeSeries());
            // 将年份和计数添加到对应单词的 TimeSeries 中
            wordCounts.get(word).put(year, count);
        }

        // 读取countsFilename文件
        In countsFile = new In(countsFilename);
        while (countsFile.hasNextLine()) {
            String[] tokens = countsFile.readLine().split(",");
            int year = Integer.parseInt(tokens[0]);
            double totalCount = Double.parseDouble(tokens[1]);

            // 添加到totalCounts中
            totalCounts.put(year, totalCount);
        }
    }


    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if (!wordCounts.containsKey(word)) {
            return new TimeSeries();  // 如果单词不存在，返回空的TimeSeries
        }
        TimeSeries fullHistory = wordCounts.get(word);
        return new TimeSeries(fullHistory, startYear, endYear);  // 返回防御性副本
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        if (!wordCounts.containsKey(word)) {
            return new TimeSeries();  // 如果单词不存在，返回空的TimeSeries
        }
        TimeSeries fullHistory = wordCounts.get(word);
        return new TimeSeries(fullHistory, MIN_YEAR, MAX_YEAR);  // 返回所有年份的历史
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(totalCounts, MIN_YEAR, MAX_YEAR);  // 返回所有年份的总计数
    }


    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    /*查看当前word占各年份总字符输入数的比例*/
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries countHistory = countHistory(word, startYear, endYear);  // 获取该单词的计数历史
        TimeSeries weightHistory = new TimeSeries();
        weightHistory = countHistory.dividedBy(totalCountHistory());
        return weightHistory;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        return weightHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words, int startYear, int endYear) {
        TimeSeries result = new TimeSeries();

        for (String word : words) {
            TimeSeries wordWeightHistory = weightHistory(word, startYear, endYear);
            result = result.plus(wordWeightHistory);  // 累加每个单词的相对频率
        }

        return result;
    }


    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        return summedWeightHistory(words, MIN_YEAR, MAX_YEAR);
    }



    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
