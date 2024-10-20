package main;
import java.util.*;
import edu.princeton.cs.algs4.In;
/*表示单词ID之间的上下位关系*/
public class Graph {
    private Map<Integer,Set<Integer>> adjList;
    private Map<String,Set<Integer>> wordToIdsMap;
    private Map<Integer,Set<String>> idToWordsMap;
    public Graph(){
        adjList=new HashMap<>();
        wordToIdsMap = new HashMap<>();
        idToWordsMap = new HashMap<>();
    }

    public void addEdge(int hypernymId, int hyponymId) {
        adjList.computeIfAbsent(hypernymId,k-> new HashSet<>()).add(hyponymId);
    }
    //根据ID返回下位ID
    public Set<Integer> getHyponyms(int wordId) {
        Set<Integer> result = new HashSet<>();
        dfs(wordId,result);
        return result;
    }

    private void dfs(int wordId,Set<Integer> result){
        if(!adjList.containsKey(wordId)){
            return;
        }
        for(int neibor:adjList.get(wordId)){
            if(result.add(neibor)){         //若已经添加过则跳过,防止无限循环
                dfs(neibor,result);
            }
        }
    }

    //根据单词返回下位单词
    public Set<String> getHyponyms(String word){
        Set<String>result = new HashSet<>();
        Set<Integer> ids = getIdsByWord(word);
        for(Integer id: ids){
            Set<Integer> hyponymIds =getHyponyms(id);
            for(Integer hyponymId: hyponymIds){
                result.addAll(getWordsById(hyponymId));
            }
        }
        return result;
    }

    /// 根据ID返回对应的词汇集合
    public Set<String> getWordsById(int id) {
        return idToWordsMap.getOrDefault(id, Collections.emptySet());
    }

    // 根据词汇返回对应的ID集合
    public Set<Integer> getIdsByWord(String word) {
        return wordToIdsMap.getOrDefault(word, Collections.emptySet());
    }

    public void loadWordNetData(String synsetsFile, String hyponymsFile) {
        // 加载synsets文件
        In synsetsIn = new In(synsetsFile);
        while(synsetsIn.hasNextLine()){
            String line = synsetsIn.readLine();
            String [] parts = line.split(",");
            int id = Integer.parseInt(parts[0]);
            String [] words = parts[1].split(" ");
            for(String word:words){
                wordToIdsMap.computeIfAbsent(word,k->new HashSet<>()).add(id);
                idToWordsMap.computeIfAbsent(id,k->new HashSet<>()).add(word);
            }
        }

        // 加载hyponyms文件
        In hyponymsIn = new In(hyponymsFile);
        while(hyponymsIn.hasNextLine()){
            String line = hyponymsIn.readLine();
            String [] parts = line.split(",");
            int hypernymId=Integer.parseInt(parts[0]);
            for(int i=1;i<parts.length;i++){
                int hyponymId = Integer.parseInt(parts[i]);
                addEdge(hypernymId, hyponymId);
            }
        }
    }
}
