package core;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.Random;

public class World {
    // build your own world!

    private static int WIDTH = 64;
    private static int HEIGHT = 32;
    private TETile[][] world;
    private TERenderer ter;
    private Random random;
    private Room room;
    /*constructor*/
    public World(long seed){
        ter = new TERenderer();
        ter.initialize(WIDTH,HEIGHT);
        this.world = new TETile[WIDTH][HEIGHT];
        random = new Random(seed);
        room = new Room(world,random);
    }

    private void generateTheWorld(){
        for(int x = 0 ; x<WIDTH ; x++){
            for(int y = 0; y < HEIGHT ; y++){
                this.world[x][y] = Tileset.NOTHING;
            }
        }
        room.generateRooms(this.world,50);
        room.addWalls(world);
    }


    /*render the world*/
    public void renderTheWorld(){
        generateTheWorld();
        ter.renderFrame(this.world);
    }

}
