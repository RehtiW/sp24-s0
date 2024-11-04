package core;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class Game {
    private World world;
    private Boolean isGameOver = false;
    private Boolean isQuitGame = false;
    private UIBoard uiBoard;
    private long lastHimMoveTime = System.currentTimeMillis();
    private final int himMoveInterval = 2; // HIM 每隔 1000 毫秒移动一次
    public Game(World world,UIBoard ui){
        this.world = world;
        this.uiBoard = ui;
    }

    private boolean isGameOver(){
        return world.isJackGotCaught();
    }
    private boolean isWin(){
        return world.getAmountToWin()==0;
    }
    private void saveGame(){

    }
    public void runGame(){
        int i =1;
        world.generateAndRenderTheWorld();
        StringBuilder inputBuffer = new StringBuilder();
        while(!isGameOver() && !isQuitGame){
            long currentTime = System.currentTimeMillis();
            // 检查 HIM 的移动时间
            if (currentTime - lastHimMoveTime >= himMoveInterval) {
                System.out.println("tryMoveHim "+i+"times");
                world.moveHim();
                lastHimMoveTime = currentTime; // 更新上次移动时间
                i++;
            }
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (key == ':') {
                    System.out.println(key);
                    inputBuffer.append(key);
                }else if(key == 'q'){
                    if(inputBuffer.toString().equals(":")){
                        isQuitGame = true;
                        uiBoard.drawUI();
                    }
                }else{
                    inputBuffer.setLength(0);
                    world.updateWorld(key);
                    world.RenderTheWorld();

                }

            }
        }
        if(isGameOver()){
            printFailureMessage();
        }else if(isWin()){
            printVictoryMessage();
        }
        while(!StdDraw.hasNextKeyTyped()){

        }
        uiBoard.drawUI();
    }
    private void printVictoryMessage(){
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 26));
        StdDraw.setPenColor(Color.white);
        StdDraw.text((double) World.getWIDTH() /2, (double) World.getHEIGHT() /2,"YOU WIN! CONGRATS!");
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 18));
        StdDraw.text((double) World.getWIDTH() /2, (double) World.getHEIGHT() /2-2,"Press any to proceed!");
        StdDraw.show();
    }
    private void printFailureMessage(){
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 26));
        StdDraw.setPenColor(Color.white);
        StdDraw.text((double) World.getWIDTH() /2, (double) World.getHEIGHT() /2,"YOU LOSE! WHAT A NOOB!");
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 18));
        StdDraw.text((double) World.getWIDTH() /2, (double) World.getHEIGHT() /2-2,"Press any to proceed!");
        StdDraw.show();
    }

}
