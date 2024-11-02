package core;

import tileengine.TETile;
import tileengine.Tileset;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Room {
    private Random random;
    private int roomCnts;
    private int goldCnts = 10;
    private List<int[]> centerCoords;//存储每个房间中心坐标
    private List<int[]> baseCoords; //存储每个房间的基坐标
    private List<int[]> properties; //存储每个房间的宽高
    private List<Integer> distanceToMST;
    private List<Integer> wannaLinkTo;
    private List<Boolean> visited;


    public Room(Random random) { //只需要传入种子
        this.random = random;
        centerCoords = new ArrayList<>();
        baseCoords = new ArrayList<>();
        properties = new ArrayList<>();
        distanceToMST = new ArrayList<>();
        wannaLinkTo = new ArrayList<>();
        visited = new ArrayList<>();
    }

    public void generateRooms(TETile[][] world, int[] JackCoord, int attemptTimes) {
        boolean isJackGenerate = false;
        for (int i = 0; i < attemptTimes; i++) {
            int width = random.nextInt(5) + 2; // 房间宽度限制
            int height = random.nextInt(5) + 2; // 房间高度限制
            int x = random.nextInt(world.length - width -1) + 1;
            int y = random.nextInt(world[0].length - height - 1-4) + 1+2;//边界预留两行

            // 检查是否重叠
            if (canPlaceRoom(world, x, y, width, height)) {
                placeRoom(world, x, y, width, height);
                if(!isJackGenerate){
                    addJack(world,JackCoord,x,y);
                    isJackGenerate = true;
                }
                int[] centerCoord = {width / 2 + x - 1, height / 2 + y - 1};
                int[] baseCoord = {x,y};
                int[] property = {width, height};
                centerCoords.add(centerCoord);
                baseCoords.add(baseCoord);
                properties.add(property);
                visited.add(false);
            }
        }
        this.roomCnts = centerCoords.size();
        visited.set(0, true);
    }

    private boolean canPlaceRoom(TETile[][] world, int x, int y, int width, int height) {
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                if (world[i][j] != Tileset.NOTHING) return false;
            }
        }
        return true;
    }

    private void placeRoom(TETile[][] world, int x, int y, int width, int height) {
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                world[i][j] = Tileset.FLOOR;
            }
        }
    }

    //using prims algorithm
    public void addCorridor(TETile[][] world) {
        initDistance(distanceToMST);
        for (Integer integer : distanceToMST) {
            System.out.print(integer + " ");
            System.out.println();
        }
        for (int j = 1; j < roomCnts; j++) {
            int minId = -1;
            int minDist = 1000;
            for (int i = 1; i < roomCnts; i++) {
                if (distanceToMST.get(i) < minDist && !visited.get(i)) {
                    minId = i;
                    minDist = distanceToMST.get(i);
                }
            }
            System.out.println("mindist = " + minDist);

            int id1 = wannaLinkTo.get(minId);
            connectTowRooms(world, id1, minId);
            visited.set(minId, true);
            for (int i = 0; i < roomCnts; i++) {
                int dist = getDist(i, minId);
                if (!visited.get(i) && distanceToMST.get(i) > dist) {
                    distanceToMST.set(i, dist);
                    wannaLinkTo.set(i, minId);
                }
            }
        }

    }

    //Manhattan distance
    private void initDistance(List<Integer> distanceToMST) {
        int x0 = centerCoords.getFirst()[0];
        int y0 = centerCoords.getFirst()[1];
        for (int i = 0; i < roomCnts; i++) {
            int xi = centerCoords.get(i)[0];
            int yi = centerCoords.get(i)[1];
            int distance = Math.abs(xi - x0) + Math.abs(yi - y0);
            distanceToMST.add(i, distance);
            wannaLinkTo.add(i, 0);
        }

    }
    private boolean isJack(TETile[][]world,int x,int y){
        return world[x][y] == Tileset.AVATAR;
    }
    private void connectTowRooms(TETile[][] world, int id1, int id2) {
        int[] coord1 = getRandomCoordinateInRoom(id1);
        int[] coord2 = getRandomCoordinateInRoom(id2);
        int x1 = coord1[0];
        int y1 = coord1[1];
        int x2 = coord2[0];
        int y2 = coord2[1];
        //for debugging
        System.out.println("x1:" + x1 + "  y1:" + y1);
        System.out.println("x2:" + x2 + "  y2:" + y2);
        //横向连接
        for (int i = Math.min(x1, x2); i <= Math.max(x1, x2); i++) {
            if(isJack(world,i,y1)){
                continue;
            }
            world[i][y1] = Tileset.FLOOR;
        }
        // 纵向连接
        for (int j = Math.min(y1, y2); j <= Math.max(y1, y2); j++) {
            if(isJack(world,x2,j)){
                continue;
            }
            world[x2][j] = Tileset.FLOOR;
        }

    }
    private int[] getRandomCoordinateInRoom(int roomId) {
        int width = properties.get(roomId)[0]; // 房间的宽度
        int height = properties.get(roomId)[1]; // 房间的高度

        // 计算房间的有效范围
        int minX = baseCoords.get(roomId)[0];
        int minY = baseCoords.get(roomId)[1];

        // 随机生成房间内的坐标
        int randomX = random.nextInt(width) + minX; // 在 [minX, maxX] 范围内
        int randomY = random.nextInt(height) + minY; // 在 [minY, maxY] 范围内

        return new int[]{randomX, randomY}; // 返回随机坐标
    }
    private int getDist(int id1, int id2) {
        int x1 = centerCoords.get(id1)[0];
        int y1 = centerCoords.get(id1)[1];
        int x2 = centerCoords.get(id2)[0];
        int y2 = centerCoords.get(id2)[1];
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    public void addWalls(TETile[][] world) {
        int[][] steps = {{1,0}, {-1,0}, {0,1}, {0,-1},{1,1},{1,-1},{-1,1},{-1,-1}};
        for (int x = 1; x < world.length-1; x++) {
            for (int y = 1; y < world[0].length-1; y++) {
                if (world[x][y] == Tileset.FLOOR || world[x][y] == Tileset.AVATAR) {
                    for (int[] step : steps) {
                        int nx = x + step[0];
                        int ny = y + step[1];
                        if (world[nx][ny] == Tileset.NOTHING) {
                            world[nx][ny] = Tileset.WALL;
                        }
                    }
                }
            }
        }

    }

    public void addJack(TETile[][] world,int[] JackCoord,int x,int y){
        world[x][y] = Tileset.AVATAR;
        JackCoord[0] = x;
        JackCoord[1] = y;
    }

    public void addGolds(TETile[][] world){
        for(int i = 0; i<goldCnts;){
            int j = random.nextInt(roomCnts);
            int x = centerCoords.get(j)[0];
            int y = centerCoords.get(j)[1];
            if(world[x][y] != Tileset.AVATAR && world[x][y] != Tileset.GOLD){
                world[x][y] = Tileset.GOLD;
                i++;
            }
        }
    }
}
