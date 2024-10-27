package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.Random;

public class Room {
    private Random random;

    public Room(TETile[][] world,Random random){
        this.random = random;
    }
    public void generateRooms(TETile[][] world, int roomCount) {
        for (int i = 0; i < roomCount; i++) {
            int width = random.nextInt(8) + 2; // 房间宽度限制
            int height = random.nextInt(8) + 2; // 房间高度限制
            int x = random.nextInt(world.length - width - 1) + 1;
            int y = random.nextInt(world[0].length - height - 1) + 1;

            // 检查是否重叠
            if (canPlaceRoom(world, x, y, width, height)) {
                placeRoom(world, x, y, width, height);
            }
        }
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

    public void addWalls(TETile[][] world) {
        for (int x = 0; x < world.length; x++) {
            for (int y = 0; y < world[0].length; y++) {
                if (world[x][y] == Tileset.FLOOR) {
                    for (int dx = -1; dx <= 1; dx++) {
                        for (int dy = -1; dy <= 1; dy++) {
                            int nx = x + dx;
                            int ny = y + dy;
                            if (world[nx][ny] == Tileset.NOTHING) {
                                world[nx][ny] = Tileset.WALL;
                            }
                        }
                    }
                }
            }
        }
    }
}
