package graph;


import edu.princeton.cs.algs4.In;

import java.util.*;

/*表示单词ID之间的上下位关系*/
public class Graph {
    private Map<Integer,Set<Integer>> adjList;
    private Map<Integer,Set<Integer>> transposeGraph;
    private Map<String,Set<Integer>> wordToIdsMap;
    private Map<Integer,Set<String>> idToWordsMap;
    public Graph(){
        adjList=new HashMap<>();
        transposeGraph = new HashMap<>();
        wordToIdsMap = new HashMap<>();
        idToWordsMap = new HashMap<>();
    }
                        //上位词ID,下位词ID
    public void addEdge(int hypernymId, int hyponymId ,Map<Integer,Set<Integer>>graph ) {
        graph.computeIfAbsent(hypernymId,k-> new HashSet<>()).add(hyponymId);
    }

    //根据ID返回下位ID
    public Set<Integer> getHyponyms(int wordId) {
        Set<Integer> result = new HashSet<>();
        dfs(wordId,result,adjList);
        return result;
    }
    //根据ID返回祖先ID
    public Set<Integer> getAncestors(int wordId){
        Set<Integer> result = new HashSet<>();
        dfs(wordId,result,transposeGraph);
        return result;
    }
    private void dfs(int wordId,Set<Integer> result,Map<Integer,Set<Integer>>graph){
        if(!graph.containsKey(wordId)){
            return;
        }
        for(int neibor:graph.get(wordId)){
            if(result.add(neibor)){         //若已经添加过则跳过,防止无限循环
                dfs(neibor,result,graph);
            }
        }
    }

    //根据单词返回下位单词
    public Set<String> getHyponyms(String word) {
        Set<String> result = new HashSet<>();
        //获取单词对应ID
        Set<Integer> ids = getIdsByWord(word);
        //对于每个ID
        for (int id : ids) {
            //查找它的下位ID
            Set<Integer> hyponymIds = getHyponyms(id);
            for (Integer hyponymId : hyponymIds) {
                result.addAll(getWordsById(hyponymId));
            }
        }
        return result;
    }
    //根据单词返回祖先单词
    public Set<String> getAncestors(String word){
        Set<String>result = new HashSet<>();
        Set<Integer> ids = getIdsByWord(word);
        for (int id : ids) {
            Set<Integer> ancestorIds = getAncestors(id);
            for (Integer ancestorId : ancestorIds) {
                result.addAll(getWordsById(ancestorId));
            }
        }
        return result;

    }

    // 根据ID返回对应的词汇集合
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
        }//79537,viceroy vicereine

        // 加载hyponyms文件
        In hyponymsIn = new In(hyponymsFile);
        while(hyponymsIn.hasNextLine()){
            String line = hyponymsIn.readLine();
            String [] parts = line.split(",");
            int hypernymId=Integer.parseInt(parts[0]);
            for(int i=1;i<parts.length;i++){
                int hyponymId = Integer.parseInt(parts[i]);
                addEdge(hypernymId, hyponymId,adjList);
                addEdge(hyponymId,hypernymId,transposeGraph);
            }
        }
    }
}
