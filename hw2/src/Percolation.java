import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private boolean[][] grid;
    private int openCnt;
    private int N;
    private WeightedQuickUnionUF wqu;
    private WeightedQuickUnionUF wquWithoutBottom;

    public Percolation(int N) {
        if(N<0){
            throw new IllegalArgumentException("IllegalArgument");
        }
        grid = new boolean[N][N];
        openCnt=0;
        this.N =N;
        wqu=new WeightedQuickUnionUF(N*N+2);
        //回流:底部开站点皆与虚拟底部节点连通,
        // percolated后虚拟底部节点和虚拟顶部节点连通,
        // 导致在isFill函数中凡底部站点皆Filled
        wquWithoutBottom=new WeightedQuickUnionUF(N*N+2);
    }
    //二维转一维编号
    private int xyTo1D(int mx,int my){
        return mx*N+my;
    }

    public void open(int row, int col) {
        int move[][]=new int[][]{
                {0,1},
                {1,0},
                {0,-1},
                {-1,0}
        };
        if (row<0||row>=N||col<0||col>=N)
            throw new IllegalArgumentException("IllegalArgument");
        if(grid[row][col])
            return;
        grid[row][col]=true;
        openCnt+=1;
        for(int i=0;i<4;i++){
            int mx = row + move[i][0];
            int my = col + move[i][1];
            if (my<0||my>=N)
                continue;
            if (mx==-1){    //将第一排的开放站点与虚拟顶部相连
                wqu.union(xyTo1D(row, col),N*N);
                wquWithoutBottom.union(xyTo1D(row, col),N*N);
                continue;
            }
            else if (mx==N){ //与虚拟底部相连
                wqu.union(xyTo1D(row, col),N*N+1);
                continue;
            }
            if(isOpen(mx,my) && !wquWithoutBottom.connected(xyTo1D(mx, my), xyTo1D(row, col))){
                wqu.union(xyTo1D(mx,my),xyTo1D(row, col));
                wquWithoutBottom.union(xyTo1D(mx,my),xyTo1D(row, col));
            }
        }

    }

    public boolean isOpen(int row, int col) {
        if (row<0||row>=N||col<0||col>=N)
            throw new IllegalArgumentException("IllegalArgument");
        return grid[row][col];
    }
    //is it full site?
    public boolean isFull(int row, int col) {
        if (row<0||row>=N||col<0||col>=N)
            throw new IllegalArgumentException("IllegalArgument");
        return wquWithoutBottom.connected(xyTo1D(row, col),N*N); //该点和虚拟顶部节点连通
    }

    public int numberOfOpenSites() {
        return openCnt;
    }

    public boolean percolates() {
        return wqu.connected(N*N,N*N+1);
    }

}
