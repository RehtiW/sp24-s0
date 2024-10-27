public class UnionFind {
    // TODO: Instance variables
    private int numb;
    private int [] parentOf;
    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("IllegalArgument");
        }
        parentOf = new int[N];
        numb = N;
        for (int i = 0; i < N; i++) {
            parentOf[i] = -1; // 初始化每个元素为独立集合，大小为1
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        if(parentOf[v]<0){
            return Math.abs(parentOf[v]);
        }
        return sizeOf(parentOf[v]);
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        if(parentOf[v]<0){
            return Math.abs(parentOf[v]);
        }
        return parentOf[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        if(v<0||v>= numb){
            throw new IllegalArgumentException("IllegalArgument");
        }
        if(parentOf[v]<0){
            return v;
        }else{
            parentOf[v]=find((parentOf[v])); //剪枝
            return parentOf[v];
        }

    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        if(!connected(v1,v2)){
            if(sizeOf(v1)<=sizeOf(v2)){//weighted union
                parentOf[find(v2)] -= sizeOf(v1);
                parentOf[find(v1)] = find(v2); //v1祖先悬挂在v2祖先下
            }else{
                parentOf[find(v1)] -= sizeOf(v2);
                parentOf[find(v2)] = find(v1);
            }
        }
    }

}
