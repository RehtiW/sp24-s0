package core;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
import utils.FileUtils;

import java.util.*;
import java.util.Random;

public class World {
    // build your own world!
    private static int WIDTH = 72;
    private static int HEIGHT = 47;
    private TETile[][] world;
    private int amountToWin=2;
    private int[] JackCoord;
    private int[] HimCoord;
    private TERenderer ter;
    private Random random;
    private Room room;
    /*constructor*/
    public World(long seed){
        ter = new TERenderer();
        ter.initialize(WIDTH,HEIGHT);
        this.world = new TETile[WIDTH][HEIGHT];
        random = new Random(seed);
        room = new Room(random);
        JackCoord=new int[2];
        HimCoord = new int[2];
    }
    public World(String filePath){
        System.out.println("loading...");
        ter = new TERenderer();
        ter.initialize(WIDTH,HEIGHT);
        this.world = new TETile[WIDTH][HEIGHT];
        JackCoord=new int[2];
        HimCoord = new int[2];
        In in = new In(filePath);
        int y = HEIGHT-1;
        while(!in.isEmpty()){
            if(y>=0){
                String line = in.readLine();
                for(int x = 0;x<WIDTH;x++){
                    char ch = line.charAt(x);
                    switch (ch){
                        case '0':
                            world[x][y] = Tileset.NOTHING;
                            break;
                        case '1':
                            world[x][y] = Tileset.FLOOR;
                            break;
                        case 'w':
                            world[x][y] = Tileset.WALL;
                            break;
                        case 'g':
                            world[x][y] = Tileset.GOLD;
                            break;
                        case 'j':
                            world[x][y] = Tileset.AVATAR;
                            break;
                        case 'h':
                            world[x][y] = Tileset.HIM;
                            break;
                    }
                }
                y--;
                continue;
            }
            String []coords = in.readLine().split(",");
            for(int i = 0; i<2 ;i++){
                JackCoord[i] = Integer.parseInt(coords[i]);
            }
            for(int i = 2;i<4;i++){
                HimCoord[i-2] = Integer.parseInt(coords[i]);
            }
        }
        System.out.println("JackCoord["+JackCoord[0]+","+JackCoord[1]+"]");
        System.out.println("HimCoord["+HimCoord[0]+","+HimCoord[1]+"]");
    }
    private void generateTheWorld(){
        for(int x = 0 ; x<WIDTH ; x++){
            for(int y = 0; y < HEIGHT ; y++){
                this.world[x][y] = Tileset.NOTHING;
            }
        }
        room.generateRooms(this.world,JackCoord,100);
        room.addCorridor(this.world);
        room.addWalls(world);
        room.addGolds(world,amountToWin);
        room.addHim(world,HimCoord);
    }


    /*jack movement*/
    public void updateWorld(char key) {
        int px = JackCoord[0];
        int py = JackCoord[1];
        switch (key) {
            case 'a':
                if (JackcanMoveTo(px - 1,py)) {
                    moveJackTo(px-1,py);
                    JackCoord[0] -=1 ;
                }
                break;
            case 'd':
                if(JackcanMoveTo(px+1,py)){
                    moveJackTo(px+1,py);
                    JackCoord[0] +=1;
                }
                break;
            case 's':
                if(JackcanMoveTo(px,py-1)){
                    moveJackTo(px,py-1);
                    JackCoord[1]-=1;
                }
                break;
            case 'w':
                if(JackcanMoveTo(px,py+1)){
                    moveJackTo(px,py+1);
                    JackCoord[1]+=1;
                }
                break;

        }

    }
    private void moveJackTo(int nx,int ny){
        int px = JackCoord[0];
        int py = JackCoord[1];
        if(world[nx][ny] == Tileset.GOLD){
            amountToWin --;
            System.out.println(amountToWin+"to go!");
        }
        world[nx][ny] = Tileset.AVATAR;
        world[px][py] = Tileset.FLOOR;
    }
    private boolean JackcanMoveTo(int x, int y){
        return world[x][y] != Tileset.WALL;
    }

    /*him movement*/
    public void moveHim(){
        int startX = HimCoord[0];
        int startY = HimCoord[1];
        int goalX = JackCoord[0];
        int goalY = JackCoord[1];
        List<int[]>path = findPath(startX,startY,goalX,goalY);
        if(!path.isEmpty()){
            int[]nextStep = path.get(1);
            world[startX][startY] = Tileset.FLOOR;
            world[nextStep[0]][nextStep[1]] = Tileset.HIM;
            HimCoord[0] = nextStep[0];
            HimCoord[1] = nextStep[1];
            /*System.out.println("Him Moves To : ["+HimCoord[0]+","+HimCoord[1]+"]!");*/
        }
        RenderTheWorld();
    }
    /* using AStar algorithm*/
    private List<int[]> findPath(int startX, int startY, int goalX, int goalY){
        int[]DX={1,-1,0,0};
        int[]DY={0,0,1,-1};
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(node-> node.fScore));
        Set<String> closedSet = new HashSet<>();
        Map<String, Node> allNodes = new HashMap<>();

        Node startNode = new Node(startX, startY, 0, heuristic(startX, startY, goalX, goalY));
        openSet.add(startNode);
        allNodes.put(toString(startX,startY), startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            if (current.x == goalX && current.y == goalY) {
                return reconstructPath(current);
            }
            closedSet.add(toString(current.x, current.y));

            for (int i = 0; i < 4; i++) {
                int neighborX = current.x + DX[i];
                int neighborY = current.y + DY[i];
                if (HimcanMoveTo(neighborX, neighborY) && !closedSet.contains(toString(neighborX,neighborY))) {
                    int neighborGScore = current.gScore + 1;
                    String neighborKey = toString(neighborX,neighborY);

                    if (!allNodes.containsKey(neighborKey) || neighborGScore < allNodes.get(neighborKey).gScore) {
                        Node neighbor = new Node(neighborX, neighborY, neighborGScore, heuristic(neighborX, neighborY, goalX, goalY));
                        allNodes.put(neighborKey, neighbor);
                        neighbor.parent = current;
                        openSet.add(neighbor);
                    }
                }
            }
        }
        return new ArrayList<>();
    }
    private int heuristic(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2); // 曼哈顿距离
    }
    private List<int[]> reconstructPath(Node current) {
        List<int[]> path = new ArrayList<>();
        while (current != null) {
            path.add(new int[]{current.x, current.y});
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }
    private boolean HimcanMoveTo(int x,int y){
        return world[x][y] != Tileset.WALL && world[x][y] != Tileset.GOLD;
    }
    public boolean isJackGotCaught(){
        return JackCoord[0] == HimCoord[0] && JackCoord[1] == HimCoord[1];
    }


    /*render the world*/
    public void generateAndRenderTheWorld(){
        generateTheWorld();
        ter.renderFrame(world);
    }

    public void RenderTheWorld(){
        ter.drawTiles(world);
        System.out.println("drawing...");
        StdDraw.show();
    }

    public TETile[][] getWorld(){
        return this.world;
    }

    /*save the world*/
    public void saveWorld(){
        StringBuilder save = new StringBuilder();
        for(int y = HEIGHT-1;y>=0;y--){
            for(int x = 0;x<WIDTH;x++){
                if(world[x][y]==Tileset.NOTHING){
                    save.append(0);
                }else if(world[x][y] == Tileset.FLOOR){
                    save.append(1);
                }else if(world[x][y]==Tileset.WALL){
                    save.append('w');
                }else if(world[x][y]==Tileset.GOLD){
                    save.append('g');
                }else if(world[x][y]==Tileset.AVATAR){
                    save.append('j');
                }else if(world[x][y]==Tileset.HIM){
                    save.append('h');
                }
            }
            save.append("\n");
        }
        save.append(JackCoord[0]).append(",").append(JackCoord[1]);
        save.append(",");
        save.append(HimCoord[0]).append(",").append(HimCoord[1]);
        FileUtils.writeFile("E:\\cs61b\\sp24-s0\\proj3\\src\\save.txt", save.toString());
    }

    private String toString(int x,int y) {
        return x + "," + y;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public int getAmountToWin() {
        return amountToWin;
    }

    private class Node {
        int x, y;
        int gScore; // 从起点到当前节点的代价
        int fScore; // 从起点到目标节点的估计代价,f=g+h
        Node parent;

        Node(int x, int y, int gScore, int fScore) {
            this.x = x;
            this.y = y;
            this.gScore = gScore;
            this.fScore = gScore + fScore;
        }
    }
}
