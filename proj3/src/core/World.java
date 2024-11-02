package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.Random;

public class World {
    // build your own world!
    private static int WIDTH = 72;
    private static int HEIGHT = 47;
    private TETile[][] world;
    private int[] JackCoord;
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
        room.addGolds(world);
    }

    public void updateWorld(char key) {
        int px = JackCoord[0];
        int py = JackCoord[1];
        switch (key) {
            case 'a':
                if (canMoveTo(px - 1,py)) {
                    JackCoord[0] -=1 ;
                    moveJackTo(px,py);
                }
                break;
            case 'd':
                if(canMoveTo(px+1,py)){
                    JackCoord[0] +=1;
                    moveJackTo(px,py);
                }
                break;
            case 's':
                if(canMoveTo(px,py-1)){
                    JackCoord[1]-=1;
                    moveJackTo(px,py);
                }
                break;
            case 'w':
                if(canMoveTo(px,py+1)){
                    JackCoord[1]+=1;
                    moveJackTo(px,py);
                }
                break;

        }

    }
    private void moveJackTo(int px,int py){
        int nx = JackCoord[0];
        int ny = JackCoord[1];
        world[nx][ny] = Tileset.AVATAR;
        world[px][py] = Tileset.FLOOR;
    }
    private boolean canMoveTo(int x, int y){
        return world[x][y] != Tileset.WALL;
    }
    private void drawWorld(){
        ter.drawTiles(this.world);
        StdDraw.show();
    }
    /*render the world*/
    public void generateAndRenderTheWorld(){
        generateTheWorld();
        ter.renderFrame(world);
    }

    public void RenderTheWorld(){
        drawWorld();
    }

    public TETile[][] getWorld(){
        return this.world;
    }

}
