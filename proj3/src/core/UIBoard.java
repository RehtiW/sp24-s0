package core;

import edu.princeton.cs.algs4.StdDraw;
import org.checkerframework.checker.guieffect.qual.UI;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.awt.*;

public class UIBoard {
    private int UIWidth = 30;
    private int UIHeight = 40;
    private TERenderer UIRenderer;
    private TETile[][] UIBoard;
    private long seed;
    //starter UI
    public UIBoard(){
        UIRenderer = new TERenderer();
        UIRenderer.initialize(UIWidth,UIHeight);
        this.UIBoard = new TETile[UIWidth][UIHeight];
        for(int x=0;x<UIWidth;x++){
            for(int y=0;y<UIHeight;y++){
                UIBoard[x][y] = Tileset.NOTHING;
            }
        }
    }
    //绘制初始UI,不同renderer实例但影响的是全局StdDraw
    public void drawUI(){
        UIRenderer.initialize(UIWidth,UIHeight);
        UIRenderer.renderFrame(UIBoard);
        String[] menuItems = {"New Game (N)", "Load Game (L)", "Quit (Q)"};
        // 计算每行的 Y 坐标位置
        double centerX = UIWidth/2;
        double centerY = UIHeight/2;
        double lineSpacing = 2; // 行间距
        StdDraw.setPenColor(Color.white);
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 20));
        // 绘制多行文本
        for (int i = 0; i < menuItems.length; i++) {
            StdDraw.text(centerX, centerY - i * lineSpacing, menuItems[i]);
        }
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 26));
        StdDraw.text(centerX,centerY*1.75,"CS61B: THE GAME");

        StdDraw.show();
    }
    //绘制输入种子UI
    public void seedInputUI(){
        double centerX = UIWidth/2;
        double centerY = UIHeight/2;
        StringBuilder seedInput = new StringBuilder();

        //show the seedInputUI
        StdDraw.clear(); //clear last UI
        UIRenderer.drawTiles(UIBoard);
        StdDraw.setPenColor(Color.white);
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 26));
        StdDraw.text(centerX,centerY*1.75,"CS61B: THE GAME");
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 22));
        StdDraw.text(centerX,centerY*1.5,"input your seed");
        StdDraw.show();
        //show the seedInputProcess
        while(true){
            if(StdDraw.hasNextKeyTyped()){
                char key = StdDraw.nextKeyTyped();
                if(key == '\n'){
                    seed = (seedInput.toString()).hashCode();
                    break;
                }
                seedInput.append(key);
                //清除旧内容
                StdDraw.clear();
                UIRenderer.drawTiles(UIBoard);
                StdDraw.setPenColor(Color.white);
                StdDraw.setFont(new Font("Arial", Font.PLAIN, 26));
                StdDraw.text(centerX, centerY * 1.75, "CS61B: THE GAME");
                StdDraw.setFont(new Font("Arial", Font.PLAIN, 22));
                StdDraw.text(centerX, centerY * 1.5, "Input your seed:");
                StdDraw.setFont(new Font("Arial", Font.PLAIN, 20));
                StdDraw.text(centerX, centerY * 1.25, seedInput.toString()); // 显示当前输入

                StdDraw.show();
            }
        }
    }

    public long getSeed() {
        return seed;
    }
}
