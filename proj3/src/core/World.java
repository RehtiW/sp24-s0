package core;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.Random;

public class World {
    // build your own world!


    private static int WIDTH = 50;
    private static int HEIGHT = 40;
    private TETile[][] world;
    private TERenderer ter;
    private Random random;
    /*constructor*/
    public World(long seed){
        ter = new TERenderer();
        ter.initialize(WIDTH,HEIGHT);
        this.world = new TETile[WIDTH][HEIGHT];
        random = new Random(seed);
    }
    public World(String filename){
        ter = new TERenderer();
        ter.initialize(WIDTH,HEIGHT);
        this.world = new TETile[WIDTH][HEIGHT];
        //world = loadBoard(filename);
    }

    /*Helper method of fillTheWorld*/
    private TETile randomTile() {
        // The following call to nextInt() uses a bound of 3 (this is not a seed!) so
        // the result is bounded between 0, inclusive, and 3, exclusive. (0, 1, or 2)
        int tileNum = random.nextInt(2);
        return switch (tileNum) {
            case 0 -> Tileset.FLOOR;
            default -> Tileset.WALL;
        };
    }
    public void fillTheWorld(){
        for(int x = 0 ; x<WIDTH ; x++){
            for(int y = 0; y < HEIGHT ; y++){
                this.world[x][y] = Tileset.NOTHING;
            }
        }
        for(int x = 0 ; x<WIDTH ; x++){
            for(int y = 0; y < HEIGHT ; y++){
                this.world[x][y] = randomTile();
            }
        }
    }
    /*render the world*/
    public void drawWorld(){
        ter.renderFrame(this.world);
    }

}
