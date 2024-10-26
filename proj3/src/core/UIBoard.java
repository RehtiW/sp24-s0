package core;

import edu.princeton.cs.algs4.StdDraw;
import org.checkerframework.checker.guieffect.qual.UI;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.awt.*;

public class UIBoard {
    private int UIWidth;
    private int UIHeight;
    private TERenderer UIRenderer;
    private TETile[][] UIBoard;
    //starter UI
    public UIBoard(int width,int height){
        UIWidth = width;
        UIHeight = height;
        UIRenderer = new TERenderer();
        UIRenderer.initialize(width,height);
        this.UIBoard = new TETile[width][height];
        for(int x=0;x<UIWidth;x++){
            for(int y=0;y<UIHeight;y++){
                UIBoard[x][y] = Tileset.NOTHING;
            }
        }
    }

    public void drawUI(){
        UIRenderer.drawTiles(UIBoard);
        String[] menuItems = {"New Game (N)", "Load Game (L)", "Quit (Q)"};
        // 计算每行的 Y 坐标位置
        double centerX = UIWidth/2;
        double centerY = UIHeight/2;
        double lineSpacing = 2; // 行间距，可以根据需要调整
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

    public void seedInputUI(){
        double centerX = UIWidth/2;
        double centerY = UIHeight/2;
        StdDraw.clear();
        UIRenderer.drawTiles(UIBoard);
        StdDraw.setPenColor(Color.white);
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 26));
        StdDraw.text(centerX,centerY*1.75,"CS61B: THE GAME");
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 22));
        StdDraw.text(centerX,centerY*1.5,"input your seed");
        StdDraw.show();
    }




}
