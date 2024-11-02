package core;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class Game {
    private World world;
    private Boolean isGameOver = false;
    private Boolean isQuitGame = false;
    private UIBoard uiBoard;
    public Game(World world,UIBoard ui){
        this.world = world;
        this.uiBoard = ui;
    }

    private boolean isGameOver(){
        return false;
    }

    private void saveGame(){

    }
    public void runGame(){
        world.generateAndRenderTheWorld();
        StringBuilder inputBuffer = new StringBuilder();
        while(!isGameOver() && !isQuitGame){
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (key == ':') {
                    System.out.println(key);
                    inputBuffer.append(key);
                }else if(key == 'q'){
                    if(inputBuffer.toString().equals(":")){
                        isQuitGame = true;
                    }
                }else{
                    inputBuffer.setLength(0);
                    world.updateWorld(key);
                    world.RenderTheWorld();

                }
            }
        }
        uiBoard.drawUI();
    }
}
